package com.example.myapplication

import android.graphics.PointF
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.user_location.UserLocationLayer

fun setAnchor(mapView:MapView, userLocationLayer:UserLocationLayer,
                      userLocationFab:FloatingActionButton):Boolean {
    userLocationLayer.setAnchor(
        PointF(
            (mapView.width * 0.5).toFloat(), (mapView.height * 0.5).toFloat()
        ),
        PointF(
            (mapView.width * 0.5).toFloat(), (mapView.height * 0.83).toFloat()
        )
    )

    userLocationFab.setImageResource(R.drawable.ic_my_location_black_24dp)
    return false
}

fun noAnchor(userLocationLayer:UserLocationLayer, userLocationFab:FloatingActionButton) {
    userLocationLayer.resetAnchor()
    userLocationFab.setImageResource(R.drawable.ic_my_location_searching_black_24dp)
}