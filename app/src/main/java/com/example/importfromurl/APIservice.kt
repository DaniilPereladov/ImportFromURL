package com.example.importfromurl

import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.logging.Level
import java.util.logging.Logger

class APIService {
    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com"
    }
    fun getContent(url: String, timeout: Int = 10000): String? {
        var c: HttpURLConnection? = null
        try {
            val u = URL(url)
            c = u.openConnection() as HttpURLConnection
            c.setRequestMethod("GET")
            c.setRequestProperty("Content-length", "0")
            c.setUseCaches(false)
            c.setAllowUserInteraction(false)
            c.setConnectTimeout(timeout)
            c.setReadTimeout(timeout)
            c.connect()
            val status: Int = c.getResponseCode()
            when (status) {
                200, 201 -> {
                    val streamReader = InputStreamReader(c.inputStream)
                    var text = ""
                    streamReader.use {
                        text = it.readText()
                    }
                    return text
                }
            }
        } catch (ex: MalformedURLException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } catch (ex: IOException) {
            Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
        } finally {
            if (c != null) {
                try {
                    c.disconnect()
                } catch (ex: Exception) {
                    Logger.getLogger(javaClass.name).log(Level.SEVERE, null, ex)
                }
            }
        }
        return null
    }

    fun getPostById(id: Int): Post? {
        val response = getContent(
            "https://jsonplaceholder.typicode.com/posts/$id",
            1000
        )
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(response)
            val post = Post(
                jsonObject.getInt("id"),
                jsonObject.getInt("userId"),
                jsonObject.getString("title"),
                jsonObject.getString("body"),
            )
            return post
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }
}