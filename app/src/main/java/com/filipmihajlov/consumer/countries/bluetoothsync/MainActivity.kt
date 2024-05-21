package com.filipmihajlov.consumer.countries.bluetoothsync

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.filipmihajlov.consumer.countries.bluetoothsync.ui.theme.BluetoothSyncTheme
import java.util.UUID


class MainActivity : ComponentActivity() {

  private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
  private var bluetoothSocket: BluetoothSocket? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      setContent {
        BluetoothSyncApp()
      }
    }
  }

  private fun connectToDevice(device: BluetoothDevice) {
    // Example UUID, replace with your service's UUID
    val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.BLUETOOTH_CONNECT
      ) != PackageManager.PERMISSION_GRANTED
    ) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return
    }
    bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
    bluetoothSocket?.use { socket ->
      socket.connect()
      val bluetoothService = BluetoothService(socket)
      setContent {
        BluetoothSyncApp(bluetoothService)
      }
    }
  }
}

@Composable
fun BluetoothSyncApp(bluetoothService: BluetoothService? = null) {
  val context = LocalContext.current

  val viewModel = BluetoothSyncViewModel()

  BluetoothSyncTheme {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
      verticalArrangement = Arrangement.Center
    ) {
      Button(
        onClick = {
          viewModel.playAudio(context = context)
          bluetoothService?.write("PLAY".toByteArray())
        },
        modifier = Modifier.fillMaxWidth().padding(8.dp)
      ) {
        Text("Play")
      }

      Button(
        onClick = {
          viewModel.pauseAudio()
          bluetoothService?.write("PAUSE".toByteArray())
        },
        modifier = Modifier.fillMaxWidth().padding(8.dp)
      ) {
        Text("Pause")
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  BluetoothSyncTheme {
    Greeting("Android")
  }
}