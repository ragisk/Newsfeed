# NewsFeed App
NewsFeed is an Android application built with Kotlin that displays the latest news articles using the [NewsData API](https://newsdata.io/). The app is designed using modern Android development practices like MVVM architecture, Room for offline support, WorkManager for periodic background sync, Paging 3 for seamless pagination, and Glide for image loading.

## Architecture

This project uses **MVVM (Model-View-ViewModel)** architecture:

### Key Components:

- **ViewModel**: Handles UI logic and exposes paginated news articles using Kotlin Flow.
- **Repository**: Coordinates between API and Room database.
- **PagingSource**: Fetches data either from remote API or Room depending on context.
- **Room**: Used to cache articles locally for offline support.
- **WorkManager**: Syncs data every 4 hours in the background.
- **Glide**: Efficient image loading into views.

## Features

- Paginated list of news articles using **Paging 3**
- Offline caching of news using **Room**
- **Background sync every 4 hours** using **WorkManager**
- **Different app flavors**:
    - `free` flavor: App name `NewsFeed Free` with free icon
    - `premium` flavor: App name `NewsFeed Premium` with premium icon
- MVVM architecture with clean separation of concerns