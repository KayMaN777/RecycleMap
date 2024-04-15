package com.example.myapplication

data class PointJson(
    val isSuccess:Boolean,
    val pointId:Int,
    val address:String,
    val addressDescription:String,
    val pointDescription:String,
    val title:String,
    val scheduleDescription:String,
    val fractions:List<Int>,
    val photos:List<String>,
    val schedule:List<List<Int>>
    )