package com.example.importfromurl

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val apiService = APIService()
    private var title = ""
    private val KEY_TITLE = "TITLE"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)
        val editText = findViewById<EditText>(R.id.editTextTextPersonName)

        button.setOnClickListener {
            thread {
                val id = editText.text.toString().toIntOrNull() ?: 0
                var post = apiService.getPostById(id)
                runOnUiThread {
                    textView.text = post?.title
                    title=post?.title.toString()
                }
            }
        }
        if (savedInstanceState != null) {

            title = savedInstanceState.getString(KEY_TITLE, "").toString();
            textView.text = title;
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TITLE, title)
    }
}