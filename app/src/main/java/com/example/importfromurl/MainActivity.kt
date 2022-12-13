package com.example.importfromurl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val apiService = APIService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)

        button.setOnClickListener {
            thread {
                val id = editText.text.toString().toIntOrNull() ?: 0
                val post = apiService.getPostById(id)
                runOnUiThread {
                    textView.text = post?.title
                }
            }
        }
    }
}