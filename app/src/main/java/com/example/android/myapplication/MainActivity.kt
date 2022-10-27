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
import java.security.Provider
import java.security.Security
import java.util.*
import java.util.concurrent.CompletableFuture
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var clientSocket: Socket

    private lateinit var client: ChatClient

    private lateinit var transportServer: TransportServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val providers = Security.getProviders()
        for (provider in providers) {
            Log.e("hojiang", "provider: " + provider.name)
        }

//        Thread() {
//            var server = ChatServer()
//            server.startServer()
//        }.start()

//        var client = ChatClient()

        findViewById<Button>(R.id.start_server_button).setOnClickListener { view ->
            CompletableFuture.runAsync { transportServer = TransportServer(this, 33333) }
        }

        findViewById<Button>(R.id.connect_button).setOnClickListener { view ->
            CompletableFuture.runAsync {
                val transportClient = TransportClient(this, "localhost", 33333)
                transportClient.sendMessage("asdfasdfas")
            }
        }

        findViewById<Button>(R.id.read_button).setOnClickListener { view ->
            CompletableFuture.runAsync {
                transportServer.read()
            }
        }

        KeyManager.generateCert("littledog")
//
//        findViewById<Button>(R.id.send_button).setOnClickListener { view ->
//            Thread() {
//                client.sendMessage(findViewById<EditText>(R.id.edit_text).text.toString())
//            }.start()
//        }
//        KeyManager.generateCert("littledog")
//        Log.e("hojiang", "certificate: " + KeyManager.getCert("littledog"))

//        Log.e("hojiang", "KeyManager: " + KeyManager.getKeyPair("littledog").certificate)
    }

}