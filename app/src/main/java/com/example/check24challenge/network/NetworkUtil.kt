package com.example.check24challenge.network

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

object NetworkUtil {
  fun getRequestBody(body: JSONObject): RequestBody{
    return body.toString().toRequestBody("application/json".toMediaTypeOrNull())
  }

  fun getCoroutineContext(status: MutableLiveData<Int>? = null): CoroutineContext{
    return Dispatchers.IO + NetworkExceptionHandler().getInstance(status = status)
  }
}
