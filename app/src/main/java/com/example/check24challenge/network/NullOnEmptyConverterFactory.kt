package com.example.check24challenge.network

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NullOnEmptyConverterFactory : Converter.Factory() {
  override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
    val delegate: Converter<ResponseBody, *> = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
    return (Converter<ResponseBody, Any?> { body: ResponseBody ->
      val inputStream = body.byteStream()
      val bodyString = DataConverter().getStringFromInputStream(inputStream)
      if (bodyString.isEmpty()) {
        JSONObject()
      } else {
        delegate.convert(bodyString.toResponseBody("application/json".toMediaTypeOrNull()))
      }
    })
  }
}
