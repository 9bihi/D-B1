package com.deutschb1.util

import android.content.Context
import org.json.JSONArray
import java.io.InputStreamReader

object AssetLoader {
    fun loadWordList(context: Context): List<String> {
        return try {
            val inputStream = context.assets.open("dictionary/words_5000.json")
            val reader = InputStreamReader(inputStream)
            val json = reader.readText()
            val array = JSONArray(json)
            val list = mutableListOf<String>()
            for (i in 0 until array.length()) {
                list.add(array.getString(i))
            }
            list.sorted()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
