package com.sriyank.ajirihiringapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sriyank.ajirihiringapp.databinding.ActivityMapsBinding
import com.sriyank.ajirihiringapp.model.TaskLocation
import java.util.*

private const val TAG = "MainActivity"
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    var currentLocation: Location? = null
    var currentMarker: Marker? = null
    //error could occur here make it lateinit var
    var locationLogging: TaskLocation = TaskLocation(0.00, 0.00)
    private val firebaseAuth = FirebaseAuth.getInstance()
    val uid = firebaseAuth.currentUser!!.uid
    private val database = FirebaseDatabase.getInstance()
    val databaseRef = database.getReference()
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val taskKey = databaseRef.child("user").child(uid).child("taskInfo")
        .push().key
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val latlong = currentLocation?.let { LatLng(it.latitude, currentLocation!!.longitude) }
        if (latlong != null) {
            placeMarkerOnMap(latlong, uid.toString())
        }

        map.uiSettings.isZoomControlsEnabled = true

        setUpMaps()
    }

    private fun setUpMaps(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
                ,REQUEST_LOCATION_PERMISSION)
            return
        }
        map.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener {location ->
            if (location != null){
                val currentLatLong = LatLng(location.latitude, location.longitude)
                currentLocation = location
                Log.d("MainActivity", "The current location is $currentLocation")
                placeMarkerOnMap(currentLatLong, uid.toString())
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong,15f))
            }
        }
    }

    private fun placeMarkerOnMap(currentlatLng: LatLng, uid: String) {
        val markerOption = MarkerOptions().position(currentlatLng).title("Task Location")
            .draggable(true).snippet(getAddress(currentlatLng))
        map.addMarker(markerOption)

        map.setOnMarkerDragListener(object: GoogleMap.OnMarkerDragListener{
            override fun onMarkerDrag(p0: Marker) {
                p0.title= p0.position.toString()
                p0.showInfoWindow()
                p0.alpha = 15f
            }

            override fun onMarkerDragEnd(p0: Marker) {
                val newLatLng = LatLng(p0.position.latitude, p0.position.longitude)
                placeMarkerOnMap(newLatLng, uid)
                Log.d(TAG, "New marker location is $newLatLng")
                 locationLogging = TaskLocation(newLatLng.latitude, newLatLng.longitude)
//                val uid = "$uid"

                databaseRef.child("user").child(uid).child("taskInfo")
                    .child("taskLocation")
                    .setValue(locationLogging).addOnSuccessListener {
                        Toast.makeText(this@MapsActivity, "Location saved", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(this@MapsActivity, "Error saving location", Toast.LENGTH_LONG).show()
                    }

            }

            override fun onMarkerDragStart(p0: Marker) {
                p0.title= p0.position.toString()
                p0.showInfoWindow()
                p0.alpha = 15f
            }

        })

    }

    private fun saveTaskToDatabase(uid: String){

    }

    private fun getAddress(crntLng: LatLng): String?{
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(crntLng.latitude, crntLng.longitude, 1)
        val add = address[0].getAddressLine(0).toString()
        Log.d(TAG, "The markers location is at $add")
        return add
//        return address[0].getAddressLine(0).toString()
    }


}