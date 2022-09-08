package com.example.check24challenge.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.check24challenge.BuildConfig
import com.example.check24challenge.system.enum.RequestStatus
import kotlinx.coroutines.CoroutineExceptionHandler
import org.greenrobot.eventbus.EventBus
import java.net.SocketTimeoutException
class NetworkExceptionHandler {
  fun getInstance(status: MutableLiveData<Int>? = null) = CoroutineExceptionHandler { _, throwable ->
    if (BuildConfig.DEBUG)
      throwable.message?.let { Log.e("OkHttp", it) }
    status?.postValue(RequestStatus.CALL_FAILURE.get())

    if (throwable is SocketTimeoutException)
      EventBus.getDefault().post(TimeOutEvent())
  }
}
