package com.filipmihajlov.consumer.countries.bluetoothsync

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri

class AudioPlayer {
  private var mediaPlayer: MediaPlayer? = null

  fun playAudio(context: Context, audioUri: Uri) {
    if (mediaPlayer == null) {
      mediaPlayer = MediaPlayer.create(context, audioUri)
    }
    mediaPlayer?.start()
  }

  fun pauseAudio() {
    mediaPlayer?.takeIf { it.isPlaying }?.pause()
  }

  fun stopAudio() {
    mediaPlayer?.apply {
      stop()
      release()
    }
    mediaPlayer = null
  }

  fun seekTo(position: Int) {
    mediaPlayer?.seekTo(position)
  }
}