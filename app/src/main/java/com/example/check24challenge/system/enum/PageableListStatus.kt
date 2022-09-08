package com.example.check24challenge.system.enum

enum class PageableListStatus constructor(private val intValue: Int) {
  NOTHING(0),
  LOADING(1),
  LIST(2),
  RETRY(3),
  NO_RESULT(4);

  fun get(): Int {
    return intValue
  }
}
