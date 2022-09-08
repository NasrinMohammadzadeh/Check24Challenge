package com.example.check24challenge.ui.productlist.repository

import androidx.lifecycle.MutableLiveData
import com.example.check24challenge.model.FilterItemModel
import com.example.check24challenge.model.ProductListModel
import com.example.check24challenge.model.ProductModel
import com.example.check24challenge.network.NetworkUtil
import com.example.check24challenge.network.api.ProductListApi
import com.example.check24challenge.system.Constants
import com.example.check24challenge.system.enum.RequestStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

class ProductListRepository @Inject constructor(private val api: ProductListApi) {

    var result: MutableLiveData<ProductListModel?> = MutableLiveData()
    var filters: MutableLiveData<ArrayList<FilterItemModel>?> = MutableLiveData()
    var productList: MutableLiveData<ArrayList<ProductModel>?> = MutableLiveData()
    var status: MutableLiveData<Int> = MutableLiveData()
    private val gson = Gson()

    fun call() {
        status.postValue(RequestStatus.LOADING.get())
        CoroutineScope(NetworkUtil.getCoroutineContext(status = status)).launch {
            status.postValue(RequestStatus.LOADING.get())
            val response = api.getProductsList(Constants.PRODUCT_URL)
            if (response != null) {
                status.postValue(RequestStatus.CALL_SUCCESS.get())
                result.postValue(response)
                filters.postValue(makeList(response.filters))
                productList.postValue(response.products)
            } else {
                status.postValue(RequestStatus.CALL_FAILURE.get())
            }
        }
    }

    fun selectItem(position: Int) {
        val value = cloneResponse()
        if (value != null && value.size > position) {
            for (i in 0 until value.size) {
                val isSelected = value[i].isSelected
                if (isSelected)
                    value[i].isSelected = false
                if (i == position)
                    value[i].isSelected = true
            }
        }
        filters.postValue(value)
    }

    private fun cloneResponse(): ArrayList<FilterItemModel>? {
        val typeToken: Type = object : TypeToken<ArrayList<FilterItemModel>?>() {}.type
        val stringResult = gson.toJson(filters.value, typeToken)
        return gson.fromJson(stringResult, typeToken)
    }

    private fun makeList(filters: ArrayList<String>?):ArrayList<FilterItemModel>{
        val result = ArrayList<FilterItemModel>()
        filters?.let {
            for(i in filters){
                result.add(FilterItemModel(title = i,  isSelected = false))
            }
        }
        if (result.size > 0){
            result[0].isSelected = true
        }

        return result
    }

}