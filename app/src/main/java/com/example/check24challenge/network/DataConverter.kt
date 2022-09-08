package com.example.check24challenge.network

import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.StringWriter

class DataConverter {
  @Throws(IOException::class)
  fun getStringFromInputStream(stream: InputStream?): String {
    var n: Int
    val buffer = CharArray(1024 * 4)
    val reader = InputStreamReader(stream, "UTF8")
    val writer = StringWriter()
    while (-1 != reader.read(buffer).also { n = it }) writer.write(buffer, 0, n)
    return writer.toString()
  }
}
