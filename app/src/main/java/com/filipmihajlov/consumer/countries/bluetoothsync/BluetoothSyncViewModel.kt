package com.filipmihajlov.consumer.countries.bluetoothsync

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel


class BluetoothSyncViewModel : ViewModel() {
  private val audioPlayer = AudioPlayer()

  fun playAudio(context: Context) {
    // Example URI, replace with your audio file URI
    val audioUri: Uri = Uri.parse("android.resource://com.example.bluetoothsync/" + R.raw.shark)
    audioPlayer.playAudio(context, audioUri)
  }

  fun pauseAudio() {
    audioPlayer.pauseAudio()
  }
}