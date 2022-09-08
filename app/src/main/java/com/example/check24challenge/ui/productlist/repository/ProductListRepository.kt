package com.example.check24challenge.ui.productlist.repository

import androidx.lifecycle.MutableLiveData
import com.example.check24challenge.model.ProductListModel
import com.example.check24challenge.network.NetworkUtil
import com.example.check24challenge.network.api.ProductListApi
import com.example.check24challenge.system.Constants
import com.example.check24challenge.system.enum.RequestStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductListRepository @Inject constructor(private val api:ProductListApi) {

    var result: MutableLiveData<ProductListModel?> = MutableLiveData()
    var status: MutableLiveData<Int> = MutableLiveData()

  fun call() {
    status.postValue(RequestStatus.LOADING.get())
    CoroutineScope(NetworkUtil.getCoroutineContext(status = status)).launch {
      status.postValue(RequestStatus.LOADING.get())
      val response = api.getProductsList(Constants.PRODUCT_URL)
      if (response != null) {
        status.postValue(RequestStatus.CALL_SUCCESS.get())
        result.postValue(response)
      } else {
        status.postValue(RequestStatus.CALL_FAILURE.get())
      }
    }
  }
}