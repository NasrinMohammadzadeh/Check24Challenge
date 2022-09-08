package com.example.check24challenge.system.enum

enum class RequestStatus constructor(private val intValue: Int) {
  LOADING(0),
  CALL_SUCCESS(1),
  CALL_FAILURE(2);

  fun get(): Int {
    return intValue
  }
}
