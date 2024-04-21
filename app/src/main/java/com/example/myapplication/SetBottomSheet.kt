package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.view.MarginLayoutParamsCompat
import androidx.core.view.marginTop
import org.json.JSONObject

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

val fractionsTextList = mutableListOf(
    "Бумага", "Пластик", "Стекло", "Металл", "Тетра-пак", "Одежда", "Лампочки",
    "Крышечки", "Техника", "Батарейки", "Шины", "Опасное", "Другое")

val fractionsWeight = mutableListOf(60, 70, 60, 60, 90, 60, 80, 80, 70, 90, 40, 70, 60)
@SuppressLint("ResourceAsColor")
fun SetFractions(jsonObject: JSONObject, view: View) {
    val fractions = jsonObject.get("fractions").toString()
    val fractionsList = fractions.split(" ")
    for (i in fractionsList.indices) {
        val j = fractionsList[i].toInt()-1
        if (i == 0) {
            val ll = view.findViewById<LinearLayout>(R.id.fractions_layout1)
            ll.layoutParams.height = LayoutParams.WRAP_CONTENT
            var params:MarginLayoutParams = ll.layoutParams as MarginLayoutParams
            params.topMargin = 10
        } else if (i == 4) {
            val ll = view.findViewById<LinearLayout>(R.id.fractions_layout2)
            ll.layoutParams.height = LayoutParams.WRAP_CONTENT
            var params:MarginLayoutParams = ll.layoutParams as MarginLayoutParams
            params.topMargin = 10
        } else if (i == 8) {
            val ll = view.findViewById<LinearLayout>(R.id.fractions_layout3)
            ll.layoutParams.height = LayoutParams.WRAP_CONTENT
            var params:MarginLayoutParams = ll.layoutParams as MarginLayoutParams
            params.topMargin = 10
        } else if (i == 12) {
            val ll = view.findViewById<LinearLayout>(R.id.fractions_layout4)
            ll.layoutParams.height = LayoutParams.WRAP_CONTENT
            var params:MarginLayoutParams = ll.layoutParams as MarginLayoutParams
            params.topMargin = 10
        }
        val fractionImageView = view.findViewById<ImageView>(fractionsLayoutList[i].first)
        val fractionTextView = view.findViewById<TextView>(fractionsLayoutList[i].second)

        fractionImageView.setImageResource(fractionsViewList[j].first)
        fractionImageView.setBackgroundColor(fractionsViewList[j].second)
        fractionImageView.layoutParams.height = 60

        val param = LayoutParams(
            0,
            LayoutParams.WRAP_CONTENT,
            fractionsWeight[j].toFloat())
        fractionTextView.setLayoutParams(param)

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