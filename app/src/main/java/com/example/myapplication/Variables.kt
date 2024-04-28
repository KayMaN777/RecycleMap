package com.example.myapplication

import android.graphics.Color
import com.yandex.mapkit.map.PlacemarkMapObject

const val eps = 1e-9
const val ZOOM_BOUNDARY = 11.4f
var zoomValue = 11.4f
fun Less(a:Double, b:Double): Boolean{
    return b - a > eps
}
var filterConditionList = mutableListOf(0, 0, 0, 0, 0, 0,
    0, 0, 0, 0, 0, 0, 0)
val filterFractionsList = mutableListOf(R.id.filter_paper_button, R.id.filter_plastic_button,
    R.id.filter_glass_button, R.id.filter_metall_button, R.id.filter_tetrapack_button,
    R.id.filter_clothes_button, R.id.filter_lightbulbs_button, R.id.filter_caps_button,
    R.id.filter_appliances_button, R.id.filter_battery_button, R.id.filter_tires_button,
    R.id.filter_dangerous_button ,R.id.filter_other_button)

val fractionsViewList = mutableListOf(
    Pair(R.drawable.paper, Color.parseColor("#4085F3")), Pair(R.drawable.plastic, Color.parseColor("#ED671C")),
    Pair(R.drawable.glass, Color.parseColor("#227440")), Pair(R.drawable.metall, Color.parseColor("#E83623")),
    Pair(R.drawable.tetrapack, Color.parseColor("#2CC0A5")), Pair(R.drawable.clothes, Color.parseColor("#EA4C99")),
    Pair(R.drawable.lightbulbs, Color.parseColor("#8F6EEF")), Pair(R.drawable.caps, Color.parseColor("#DAA219")),
    Pair(R.drawable.appliances, Color.parseColor("#C06563")), Pair(R.drawable.battery, Color.parseColor("#C8744E")),
    Pair(R.drawable.tires, Color.parseColor("#6F4D41")), Pair(R.drawable.dangerous, Color.parseColor("#242627")),
    Pair(R.drawable.other, Color.parseColor("#3EB8DE")))

var list = ArrayList<PlacemarkMapObject>()
var fractionsNumsList = ArrayList<Int>()
val MAPS_TOKEN = "babcf83b-1ef6-4a6e-a7d9-d75097211bc8"
val DATABASE_URL = "http://194.87.92.55:5801/database"
val POINT_URL = "http://194.87.92.55:5801/points"