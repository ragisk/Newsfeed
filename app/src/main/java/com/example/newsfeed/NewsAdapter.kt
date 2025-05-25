package com.example.newsfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsfeed.databinding.ItemNewsBinding
import com.example.newsfeed.model.Article

abstract class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) =
            oldItem.article_id == newItem.article_id

        override fun areContentsTheSame(oldItem: Article, newItem: Article) =
            oldItem == newItem
    }

    inner class NewsViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position) ?: return
        holder.binding.apply {
            title.text = article.title
            description.text = article.description
            Glide.with(articleIV).load(article.image_url).into(articleIV)
        }
        holder.binding.articleLL.setOnClickListener{
            onclick(article)
        }
    }

    abstract fun onclick(article: Article)
}