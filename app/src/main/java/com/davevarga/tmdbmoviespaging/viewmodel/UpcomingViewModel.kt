package com.davevarga.tmdbmoviespaging.ui

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.davevarga.tmdbmoviespaging.models.Movie
import com.davevarga.tmdbmoviespaging.repository.NetworkRepository
import io.reactivex.disposables.CompositeDisposable

class UpcomingViewModel(private val networkRepository: NetworkRepository) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val upcomingPagedList: LiveData<PagedList<Movie>> by lazy {
        networkRepository.getUpcoming(compositeDisposable)
    }


//    fun refresh() {
//        networkRepository.refreshUpcoming()
//    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}
@Suppress("UNCHECKED_CAST")
class UpcomingViewModelFactory(
    val networkRepository: NetworkRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpcomingViewModel::class.java)) {
            return UpcomingViewModel(networkRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}