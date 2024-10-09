package se.braindome.skywatch.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import se.braindome.skywatch.LOCATION_PERMISSION_REQUEST_CODE
import se.braindome.skywatch.MainActivity
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val context: Context,
) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(activity: MainActivity, onSuccess: (latitude: Double, longitude: Double) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!isLocationEnabled(locationManager)) {
            Timber.e("Location services are disabled")
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                Timber.tag("Timber").d("Location: ${location.latitude}, ${location.longitude}")
                onSuccess(location.latitude, location.longitude)
            } else {
                Timber.e("Location is null")
            }
        }.addOnFailureListener { exception ->
            Timber.e(exception, "Failed to get location")
        }
    }
}