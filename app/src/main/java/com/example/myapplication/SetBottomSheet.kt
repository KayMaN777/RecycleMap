package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import org.json.JSONObject
import org.w3c.dom.Text

fun SetPointDescription(jsonObject: JSONObject, view:View) {
    val pointDescriptionText = jsonObject.get("pointDescription").toString()
    val pointDescriptionTextView = view.findViewById<TextView>(R.id.description)
    pointDescriptionTextView.text = pointDescriptionText
}

fun SetTitleText(jsonObject: JSONObject, view: View) {
    val titleText = jsonObject.get("title").toString()
    val titleTextView = view.findViewById<TextView>(R.id.title)
    titleTextView.text = titleText
}

fun SetAdressText(jsonObject: JSONObject, view: View) {
    val addressText = jsonObject.get("address").toString()
    val addressTextView = view.findViewById<TextView>(R.id.address)
    addressTextView.text = addressText
}

val scheduleList = mutableListOf(R.id.monday_text, R.id.tuesday_text, R.id.thursday_text,
    R.id.wednesday_text, R.id.friday_text, R.id.saturday_text, R.id.sunday_text)

fun SetSheduleText(jsonObject: JSONObject, view: View) {
    val schedule = jsonObject.get("schedule").toString()
    val dayList = schedule.split(" ")
    for (i in 0..6) {
        val dayText = dayList[i]
        val dayTextView = view.findViewById<TextView>(scheduleList[i])
        dayTextView.text = dayText
    }
}

val fractionsLayoutList = mutableListOf(
    Pair(R.id.Fraction1, R.id.FractionText1), Pair(R.id.Fraction2, R.id.FractionText2),
    Pair(R.id.Fraction3, R.id.FractionText3), Pair(R.id.Fraction4, R.id.FractionText4),
    Pair(R.id.Fraction5, R.id.FractionText5), Pair(R.id.Fraction6, R.id.FractionText6),
    Pair(R.id.Fraction7, R.id.FractionText7), Pair(R.id.Fraction8, R.id.FractionText8),
    Pair(R.id.Fraction9, R.id.FractionText9), Pair(R.id.Fraction10, R.id.FractionText10),
    Pair(R.id.Fraction11, R.id.FractionText11), Pair(R.id.Fraction12, R.id.FractionText12),
    Pair(R.id.Fraction13, R.id.FractionText13))

val fractionsViewList = mutableListOf(
    Pair(R.drawable.paper, Color.parseColor("#4085F3")), Pair(R.drawable.plastic, Color.parseColor("#ED671C")),
    Pair(R.drawable.glass, Color.parseColor("#227440")), Pair(R.drawable.metall, Color.parseColor("#E83623")),
    Pair(R.drawable.tetrapack, Color.parseColor("#2CC0A5")), Pair(R.drawable.clothes, Color.parseColor("#EA4C99")),
    Pair(R.drawable.lightbulbs, Color.parseColor("#8F6EEF")), Pair(R.drawable.caps, Color.parseColor("#DAA219")),
    Pair(R.drawable.appliances, Color.parseColor("#C06563")), Pair(R.drawable.battery, Color.parseColor("#C8744E")),
    Pair(R.drawable.tires, Color.parseColor("#6F4D41")), Pair(R.drawable.dangerous, Color.parseColor("#242627")),
    Pair(R.drawable.other, Color.parseColor("#3EB8DE")))

val fractionsTextList = mutableListOf(
    "Бумага", "Пластик", "Стекло", "Металл", "Тетра-пак", "Одежда", "Лампочки",
    "Крышечки", "Техника", "Батарейки", "Шины", "Опасное", "Другое")

@SuppressLint("ResourceAsColor")
fun SetFractions(jsonObject: JSONObject, view: View) {
    val fractions = jsonObject.get("fractions").toString()
    val fractionsList = fractions.split(" ")
    for (i in fractionsList.indices) {
        val j = fractionsList[i].toInt()-1
        //Log.d("XER", j.toString())
        val fractionImageView = view.findViewById<ImageView>(fractionsLayoutList[i].first)
        val fractionTextView = view.findViewById<TextView>(fractionsLayoutList[i].second)

        fractionImageView.setImageResource(fractionsViewList[j].first)
        fractionImageView.setBackgroundColor(fractionsViewList[j].second)
        fractionImageView.layoutParams.height = 60

        fractionTextView.text = fractionsTextList[j]
        fractionTextView.setBackgroundColor(fractionsViewList[j].second)
        fractionTextView.layoutParams.height = 60
    }
}

fun SetBottomSheet(jsonObject: JSONObject, view:View) {
    SetPointDescription(jsonObject, view)
    SetAdressText(jsonObject, view)
    SetTitleText(jsonObject, view)
    SetSheduleText(jsonObject, view)
    SetFractions(jsonObject, view)
}