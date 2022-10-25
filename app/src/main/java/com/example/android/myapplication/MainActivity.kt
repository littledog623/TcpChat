package com.example.android.myapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.example.android.myapplication.databinding.ActivityMainBinding
import com.example.android.myapplication.transport.KeyManager
import com.example.android.myapplication.transport.TransportClient
import com.example.android.myapplication.transport.TransportServer
import java.net.Socket
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var clientSocket: Socket

    private lateinit var client: ChatClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Thread() {
//            var server = ChatServer()
//            server.startServer()
//        }.start()

//        var client = ChatClient()

//        findViewById<Button>(R.id.connect_button).setOnClickListener { view ->
//            Thread() {
//                client = ChatClient()
//                client.startConnect()
//            }.start()
//        }
//
//        findViewById<Button>(R.id.send_button).setOnClickListener { view ->
//            Thread() {
//                client.sendMessage(findViewById<EditText>(R.id.edit_text).text.toString())
//            }.start()
//        }
        KeyManager.generateCert("littledog")

        Log.e("hojiang", "KeyManager: " + KeyManager.getKeyPair("littledog").certificate)

        var transportServer = TransportServer(33333)

        Thread() {
            var transportClient = TransportClient("localhost", 33333)
            transportClient.sendMessage("asasdfasdfasdfdf");
        }.start()

        transportServer.read()
    }

}