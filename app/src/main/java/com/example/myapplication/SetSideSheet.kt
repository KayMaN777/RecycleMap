package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.sidesheet.SideSheetDialog

@SuppressLint("ResourceAsColor")
fun initFractionsButtons(view:View, dialog:SideSheetDialog) {
    for (i in filterConditionList.indices) {
        val button = dialog.findViewById<ImageButton>(filterFractionsList[i])
        if (filterConditionList[i] == 0) {
            button!!.setBackgroundColor(Color.parseColor("#BEC0BF"))
        } else {
            button!!.setBackgroundColor(fractionsViewList[i].second)
        }
        button.setOnClickListener{
            if (filterConditionList[i] == 0) {
                filterConditionList[i] = 1
                button!!.setBackgroundColor(fractionsViewList[i].second)
            } else {
                filterConditionList[i] = 0
                button!!.setBackgroundColor(Color.parseColor("#BEC0BF"))
            }
        }
    }
    val button = dialog.findViewById<Button>(R.id.filter_button)
    button!!.setOnClickListener {
        hidePlacemarks(getMask())
        dialog.dismiss()
    }
}

fun getMask() : Int{
    var res:Int = 0
    for (i in filterConditionList.indices) {
        val tmp = filterConditionList[i] shl (i+1)
        res += tmp
    }
    return res
}

fun hidePlacemarks(mask:Int) {
    Log.d("Pipiska", mask.toString())
    for (i in list.indices) {
        val placemark = list[i]
        val opa = fractionsNumsList[i]
        val tmp = opa and mask
        if (mask == 0 || tmp != 0) {
            placemark.isVisible = true
        } else {
            placemark.isVisible = false
        }
    }
}