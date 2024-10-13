package se.braindome.skywatch.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import androidx.annotation.RequiresApi
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
import kotlin.collections.get

@Singleton
class LocationRepository @Inject constructor(
    private val context: Context,
) {

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                geocoder.getFromLocation(location.latitude, location.longitude, 5, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: List<Address?>) {
                        var locationName : String? = null
                        for (address in addresses) {
                            locationName = address?.locality
                            if (locationName != null) break
                        }
                        if (locationName == null) locationName = "Unknown location"
                        Timber.tag("LocationRepository").d("Location: ${location.latitude}, ${location.longitude}, $locationName")
                        onSuccess(location.latitude, location.longitude, locationName.toString())
                    }

                    override fun onError(errorMessage: String?) {
                        Timber.e("Failed to get location: $errorMessage")
                        onSuccess(location.latitude, location.longitude, "Unknown location")
                    }

                })

            } else {
                Timber.e("Location is null")
                onSuccess(location?.latitude ?: 0.0, location?.longitude ?: 0.0, "Unknown location")

            }
        }.addOnFailureListener { exception ->
            Timber.e(exception, "Failed to get location")
        }
    }
}