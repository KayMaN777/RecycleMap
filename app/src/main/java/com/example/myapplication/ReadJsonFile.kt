package com.example.myapplication

import com.yandex.mapkit.geometry.Point
import org.apache.commons.csv.CSVParser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.math.abs

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
fun findBestMatch(csvParser: CSVParser, point:Point):Int{
    var best_match:Int = -1
    var res:Double = 1e5
    for (item in csvParser) {
        var pointId = item.get(0).toInt()
        var lon = item.get(1).toDouble()
        var lat = item.get(2).toDouble()
        var dlt = abs(lat - point.latitude) + abs(lon - point.longitude)
        if (Less(dlt, res)) {
            res = dlt
            best_match = pointId
        }
    }
    return best_match
}
