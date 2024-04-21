package com.example.myapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PointF
import android.location.LocationListener
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.gson.Gson
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.logo.Alignment
import com.yandex.mapkit.logo.HorizontalAlignment
import com.yandex.mapkit.logo.VerticalAlignment
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.math.abs
lateinit var reader:BufferedReader
lateinit var csvParser:CSVParser
private lateinit var checkLocationPermission: ActivityResultLauncher<Array<String>>
private lateinit var userLocationLayer: UserLocationLayer
private var routeStartLocation = Point(0.0, 0.0)
private lateinit var mapView: MapView
private var permissionLocation = false
private var followUserLocation = false
private lateinit var userLocationFab: FloatingActionButton
class MainActivity : AppCompatActivity(), UserLocationObjectListener, CameraListener {
    fun addPlacemarks(csvParser: CSVParser) {
        val imageProvider = ImageProvider.fromResource(this, R.drawable.rec32)
        for (item in csvParser) {
            val lon = item.get(1).toDouble()
            val lat = item.get(2).toDouble()
            val fractionsMask = item.get(3).toInt()
            val placemark = mapView.map.mapObjects.addPlacemark().apply {
                geometry = Point(lat, lon)
                setIcon(imageProvider)
            }
            list.add(placemark)
            fractionsNumsList.add(fractionsMask)
            placemark.addTapListener(pointTapListener)
        }
    }

    private var pointTapListener = object : MapObjectTapListener {
        override fun onMapObjectTap(mapObject: MapObject, point: Point): Boolean{
            reader = BufferedReader(assets.open("database.csv").reader())
            csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT)
            var bestMatch = findBestMatch(csvParser, point)

            val path = "reformed_data/${bestMatch}.json"
            val jsonFile = assets.open(path)
            val jsonObject = ReadJsonFile(jsonFile)

            val dialog = BottomSheetDialog(this@MainActivity)
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            SetBottomSheet(jsonObject, view)
            dialog.setContentView(view)
            dialog.show()
            return true
        }}
    fun initMenuFab() {
        val menuFab = findViewById<FloatingActionButton>(R.id.menu_fab)
        menuFab.setOnClickListener{
            val dialog = SideSheetDialog(this@MainActivity)
            val view = layoutInflater.inflate(R.layout.side_sheet_dialog, null)
            dialog.setContentView(view)
            dialog.show()
            initFractionsButtons(view, dialog)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(MAPS_TOKEN)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.map_view)
        checkLocationPermission = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                onMapReady()
            }
        }
        userLocationFab = findViewById(R.id.user_location_fab)
        checkPermission()

        userInterface()
        mapView.map.addCameraListener(this)
        reader = BufferedReader(assets.open("database.csv").reader())
        csvParser = CSVParser.parse(reader, CSVFormat.DEFAULT)
        addPlacemarks(csvParser)
        initMenuFab()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onMapReady()
        } else {
            checkLocationPermission.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }

    private fun userInterface() {
        val mapLogoAlignment = Alignment(HorizontalAlignment.LEFT, VerticalAlignment.BOTTOM)
        mapView.map.logo.setAlignment(mapLogoAlignment)

        userLocationFab.setOnClickListener {
            if (permissionLocation) {
                cameraUserPosition()

                followUserLocation = true
            } else {
                checkPermission()
            }
        }
    }

    private fun onMapReady() {
        val mapKit = MapKitFactory.getInstance()
        userLocationLayer = mapKit.createUserLocationLayer(mapView.mapWindow)
        userLocationLayer.isVisible = true
        userLocationLayer.isHeadingEnabled = true
        userLocationLayer.setObjectListener(this)

        mapView.map.addCameraListener(this)

        cameraUserPosition()

        permissionLocation = true
    }

    private fun cameraUserPosition() {
        if (userLocationLayer.cameraPosition() != null) {
            routeStartLocation = userLocationLayer.cameraPosition()!!.target
            mapView.map.move(
                CameraPosition(routeStartLocation, 16f, 0f, 0f), Animation(Animation.Type.SMOOTH, 1f), null
            )
        } else {
            mapView.map.move(CameraPosition(Point(0.0, 0.0), 16f, 0f, 0f))
        }
    }

    override fun onCameraPositionChanged(
        map: com.yandex.mapkit.map.Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        if (finished) {
            when {
                cameraPosition.zoom >= ZOOM_BOUNDARY && zoomValue <= ZOOM_BOUNDARY -> {
                    for (placemark in list) {
                        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.rec32))
                    }
                }
                cameraPosition.zoom <= ZOOM_BOUNDARY && zoomValue >= ZOOM_BOUNDARY -> {
                    for (placemark in list) {
                        placemark.setIcon(ImageProvider.fromResource(this, R.drawable.tochka24))
                    }
                }
            }
            zoomValue = cameraPosition.zoom
            if (followUserLocation) {
                followUserLocation = setAnchor(mapView, userLocationLayer, userLocationFab)
            }
        } else {
            if (!followUserLocation) {
                noAnchor(userLocationLayer, userLocationFab)
            }
        }
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        followUserLocation = setAnchor(mapView, userLocationLayer, userLocationFab)

        userLocationView.pin.setIcon(ImageProvider.fromResource(this, R.drawable.user_arrow))
        userLocationView.arrow.setIcon(ImageProvider.fromResource(this, R.drawable.user_arrow))
    }

    override fun onObjectUpdated(p0: UserLocationView, p1: ObjectEvent) {}
    override fun onObjectRemoved(p0: UserLocationView) {}

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }
}