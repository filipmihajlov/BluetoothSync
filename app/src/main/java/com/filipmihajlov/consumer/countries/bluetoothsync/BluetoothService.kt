package com.filipmihajlov.consumer.countries.bluetoothsync

import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class BluetoothService(socket: BluetoothSocket) {
  private val inputStream: InputStream = socket.inputStream
  private val outputStream: OutputStream = socket.outputStream

  fun write(bytes: ByteArray) {
    try {
      outputStream.write(bytes)
    } catch (e: IOException) {
      e.printStackTrace()
    }
  }

  fun read(buffer: ByteArray): Int {
    return try {
      inputStream.read(buffer)
    } catch (e: IOException) {
      e.printStackTrace()
      -1
    }
  }
}