package com.davevarga.tmdbmoviespaging.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.davevarga.tmdbmoviespaging.models.Genre
import com.davevarga.tmdbmoviespaging.models.GenreList
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.network.*
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Response

class NetworkRepository(private val apiService: GetData) {

    private var readyList: List<Genre>? = null

    private lateinit var moviePagedList: LiveData<PagedList<Movie>>
    private lateinit var upcomingPagedList: LiveData<PagedList<Movie>>
    private lateinit var moviesDataSourceFactory: MovieDataSourceFactory
    private lateinit var upcomingDataSourceFactory: UpcomingDataSourceFactory

    fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable,
        minYear: String,
        maxYear: String,
        genre: String
    ): LiveData<PagedList<Movie>> {
        moviesDataSourceFactory =
            MovieDataSourceFactory(apiService, compositeDisposable, minYear, maxYear, genre)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(moviesDataSourceFactory, config).build()

        return moviePagedList
    }

    fun getUpcoming(compositeDisposable: CompositeDisposable): LiveData<PagedList<Movie>> {
        upcomingDataSourceFactory = UpcomingDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        upcomingPagedList = LivePagedListBuilder(upcomingDataSourceFactory, config).build()

        return upcomingPagedList
    }

    fun refreshMovies() {
        moviesDataSourceFactory.refresh()
    }

    suspend fun getAllGenres(): List<Genre>? {
        val genreList: Response<GenreList>

        genreList = ServiceBuilder.getNetworkClient(GetData::class.java)
            .getAllGenres(
                "d00127676d268780e41811f616e4fbb0"
            )

        genreList.body()?.let {
            readyList = it.genres
        }
        return readyList
    }

}