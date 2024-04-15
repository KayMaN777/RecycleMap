package com.example.myapplication

import android.util.Log
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import org.apache.commons.csv.CSVParser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun ReadJsonFile(jsonFile:InputStream):JSONObject {
    val bufferedReader = BufferedReader(InputStreamReader(jsonFile))
    val stringBuilder = StringBuilder()
    bufferedReader.useLines { lines ->
        lines.forEach {
            stringBuilder.append(it)
        }
    }
    val jsonString = stringBuilder.toString()
    val jsonObject = JSONObject(jsonString)
    return jsonObject
}
