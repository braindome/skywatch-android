package se.braindome.skywatch.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.location.LocationManagerCompat.isLocationEnabled
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import se.braindome.skywatch.LOCATION_PERMISSION_REQUEST_CODE
import se.braindome.skywatch.MainActivity
import timber.log.Timber
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val context: Context,
) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastLocation(activity: MainActivity, onSuccess: (latitude: Double, longitude: Double, locationName: String) -> Unit) {
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
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val locationName = if (addresses?.isNotEmpty() ?: false) {
                    addresses.get(0)?.locality ?: addresses[0]?.subLocality ?: "Unknown location"
                } else {
                    "Unknown location"
                }
                Timber.tag("LocationRepository").d("Location: ${location.latitude}, ${location.longitude}, $locationName")
                onSuccess(location.latitude, location.longitude, locationName.toString())
            } else {
                Timber.e("Location is null")
            }
        }.addOnFailureListener { exception ->
            Timber.e(exception, "Failed to get location")
        }
    }
}