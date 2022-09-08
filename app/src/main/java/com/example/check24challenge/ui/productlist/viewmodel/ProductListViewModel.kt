package com.example.check24challenge.ui.productlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.check24challenge.model.FilterItemModel
import com.example.check24challenge.model.ProductListModel
import com.example.check24challenge.model.ProductModel
import com.example.check24challenge.ui.productlist.repository.ProductListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val repository: ProductListRepository): ViewModel(){

  fun status(): LiveData<Int> = repository.status

  fun result(): MutableLiveData<ProductListModel?> = repository.result

  fun productList(): MutableLiveData<ArrayList<ProductModel>?> = repository.productList

  fun filters(): MutableLiveData<ArrayList<FilterItemModel>?> = repository.filters

  fun call() {
    repository.call()
  }

  fun changeSelectedFilter(position: Int){
    repository.selectItem(position)
  }
}
