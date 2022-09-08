package com.example.check24challenge.network

import android.util.Log
import com.example.check24challenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class NetworkClient {
  fun build(): OkHttpClient {
    val builder = OkHttpClient.Builder()
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .connectTimeout(30, TimeUnit.SECONDS)
      .addInterceptor(logInterceptor())
      .addInterceptor(headerInterceptor())
      .addInterceptor(responseInterceptor)

    return builder.build()
  }

  private fun headerInterceptor(): Interceptor {
    return Interceptor { chain ->
      val original = chain.request()
      val request = original.newBuilder()
        .method(original.method, original.body)
        .addHeader("Accept-Encoding", "identity")
        .addHeader("Request-Id", UUID.randomUUID().toString())
        .build()
      chain.proceed(request)
    }
  }

  private fun logInterceptor(): Interceptor {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
      interceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
      interceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return interceptor
  }

  private var responseInterceptor = Interceptor { chain ->
    val request = chain.request()
    val response = chain.proceed(request)

    when (response.code) {
      200 -> return@Interceptor response
      404 -> {
        try {
          log("Error 404", isError = true)
        } catch (ignored: java.lang.Exception) {
        }
        return@Interceptor response
      }
      else -> {
        try {
          val errorResponse = JSONObject(DataConverter().getStringFromInputStream(response.body?.byteStream()))
          log(errorResponse.toString(), isError = true)
        } catch (ignored: Exception) {
          log("Connection Error", isError = true)
        }
        return@Interceptor response
      }
    }
  }

  private fun log(message: String, isError: Boolean? = false) {
    if (BuildConfig.DEBUG) {
      if (isError != null && isError) {
        Log.e("OkHttp", message)
      } else {
        Log.d("OkHttp", message)
      }
    }
  }
}
