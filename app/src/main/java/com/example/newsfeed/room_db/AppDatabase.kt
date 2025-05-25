package com.example.newsfeed.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsfeed.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
