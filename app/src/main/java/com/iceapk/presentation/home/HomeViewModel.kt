package com.iceapk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iceapk.data.dao.ProductsDao
import com.iceapk.presentation.home.intent.HomeIntent
import com.iceapk.presentation.home.viewstates.HomeViewState
import com.iceapk.repository.home.HomeRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject constructor(private val repo: HomeRepo, private val dao: ProductsDao): ViewModel() {
    val intent = Channel<HomeIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<HomeViewState>(HomeViewState.Idle)
    val viewState: StateFlow<HomeViewState> = _state
    init{
        handleIntent()
    }
    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { it ->
                when (it){
                    is HomeIntent.getData -> getProducts()
                    is HomeIntent.getProductsByCategory -> getProductByCategory(it.category.toLowerCase())
                }
            }
        }
    }

    private suspend fun getProductByCategory(category: String){
        _state.value = HomeViewState.Success(repo.searchByCategory(category))
    }

    private suspend fun getProducts(){
        _state.value = HomeViewState.Loading
        try{
             repo.getAllProducts()
            _state.value = HomeViewState.Success(dao.readAllProducts())
        }
        catch (e: Exception){
            _state.value = HomeViewState.Error("Error loading products please try again")
        }
    }
}