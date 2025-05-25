package com.example.newsfeed.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide

import com.example.newsfeed.databinding.ActivityNewsDetailsBinding
import com.example.newsfeed.model.Article

class NewsDetailsActivity : ComponentActivity() {
    private lateinit var binding: ActivityNewsDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.getSerializableExtra("article") as? Article
        article?.let {
            binding.tvTitle.text = it.title
            binding.tvDescription.text = it.description
            binding.tvContent.text = it.content

            Glide.with(this)
                .load(it.image_url)
                .into(binding.ivArticleImage)
        }
    }
}