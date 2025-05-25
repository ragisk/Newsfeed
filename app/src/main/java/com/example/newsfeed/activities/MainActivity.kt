package com.example.newsfeed.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.NewsAdapter
import com.example.newsfeed.NewsUtils
import com.example.newsfeed.NewsViewModel
import com.example.newsfeed.SyncScheduler
import com.example.newsfeed.databinding.ActivityMainBinding
import com.example.newsfeed.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar.visibility = View.VISIBLE

        binding.newsListRV.layoutManager = LinearLayoutManager(this)
        newsAdapter=object : NewsAdapter(){
            override fun onclick(article: Article) {
                val intent = Intent(this@MainActivity,NewsDetailsActivity::class.java)
                intent.putExtra("article", article)
                startActivity(intent)
            }
        }
        binding.newsListRV.adapter = newsAdapter


        if (NewsUtils.isInternetAvailable(this)) {
            lifecycleScope.launch {
                viewModel.getNewsFlow(NewsUtils.apiKey).collectLatest {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.submitData(it)
                }
            }
        }else {
            lifecycleScope.launch {
                viewModel.offlineNewsFlow.collectLatest {
                    binding.progressBar.visibility = View.GONE
                    newsAdapter.submitData(it)
                }
            }
        }
        lifecycleScope.launch {
            newsAdapter.loadStateFlow.collectLatest { loadStates ->
                val isLoading = loadStates.refresh is LoadState.Loading
                val isListEmpty = loadStates.refresh is LoadState.NotLoading &&
                        newsAdapter.itemCount == 0

                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.noDataText.visibility = if (isListEmpty) View.VISIBLE else View.GONE

                val refreshState = loadStates.refresh
                if (refreshState is LoadState.Error){
                    binding.noDataText.visibility = View.VISIBLE
                    val errorMsg = refreshState.error.localizedMessage ?: "Unknown error"
                    binding.noDataText.text=errorMsg
                }

            }
        }

        SyncScheduler.schedule(applicationContext)
    }
}

