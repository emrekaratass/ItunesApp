package com.example.itunesapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itunesapp.data.Result
import com.example.itunesapp.domain.usecase.CollectionParams
import com.example.itunesapp.domain.usecase.GetCollectionUseCase
import com.example.itunesapp.ui.entity.CollectionViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val collectionUseCase: GetCollectionUseCase
) : ViewModel() {

    private var page: Int = 0
    private var articlesSize: Int = 0
    private var query: String? = null
    private var entity: String? = null
    private var isFetchingData: Boolean = false
    private var queryTextChangedJob: Job? = null

    private val _addListLiveData = MutableLiveData<CollectionViewItem>()
    val addListLiveData: LiveData<CollectionViewItem>
        get() = _addListLiveData

    private val _setListLiveData = MutableLiveData<CollectionViewItem>()
    val setListLiveData: LiveData<CollectionViewItem>
        get() = _setListLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String>
        get() = _errorLiveData

    private fun fetchCollection() {
        if (isFetchingData) return
        isFetchingData = true

        val params = CollectionParams(page = page, query = query, entity = entity)
        viewModelScope.launch {
            val result = collectionUseCase.execute(params)

            when (result) {
                is Result.Error ->
                    if (page == 0) _errorLiveData.value = result.exception.localizedMessage
                is Result.Success -> result.data.let {
                    articlesSize += it.results.size
                    if (page == 0) _setListLiveData.postValue(it) else _addListLiveData.postValue(
                        it
                    )
                    if (it.results.isNotEmpty()) page += 20
                    isFetchingData = false
                }
            }
        }
    }

    fun paginateCollection(lastVisibleItemPosition: Int) {
        if (lastVisibleItemPosition < articlesSize - PAGINATION_THRESHOLD) return
        fetchCollection()
    }

    fun searchCollection(query: String, entity: String) {
        queryTextChangedJob?.cancel()
        queryTextChangedJob = viewModelScope.launch(Dispatchers.Main) {
            delay(SEARCH_DELAY)
            this@MainViewModel.query = query
            this@MainViewModel.entity = entity
            clearData()
            fetchCollection()
        }
    }

    fun clearData() {
        page = 0
        articlesSize = 0
    }

    companion object {
         const val SEARCH_DELAY = 1_000L
        private const val PAGINATION_THRESHOLD = 3
    }
}