package com.test.weather.presentation.ui.parts

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import com.test.weather.presentation.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@SuppressLint("MissingPermission")
internal fun requestSingleLocationUpdate(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (Double, Double) -> Unit
) {
    val locationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000) // 10 seconds interval
            .setMaxUpdates(1) // Get only one update
            .build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                onLocationResult(location.latitude, location.longitude)
            } ?: run {
                onLocationResult(0.0, 0.0) // Indicate no location
            }
            fusedLocationClient.removeLocationUpdates(this) // Stop updates after receiving one
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability) {
            if (!locationAvailability.isLocationAvailable) {
                onLocationResult(0.0, 0.0)
            }
        }
    }

    fusedLocationClient.requestLocationUpdates(
        locationRequest,
        locationCallback,
        Looper.getMainLooper()
    )
}

@SuppressLint("MissingPermission")
internal fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    onLocationResult: (Double, Double) -> Unit
) {
    val hasFineLocation = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
    val hasCoarseLocation = ContextCompat.checkSelfPermission(
        context, Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!hasFineLocation && !hasCoarseLocation) {
        onLocationResult(0.0, 0.0)
        return
    }

    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            onLocationResult(location.latitude, location.longitude)
        } else {

            requestSingleLocationUpdate(fusedLocationClient, onLocationResult)
        }
    }.addOnFailureListener { e ->
        onLocationResult(0.0, 0.0)
    }
}

@Composable
internal fun RequestLocation(
    fusedLocationClient: FusedLocationProviderClient,
    mainViewModel: MainViewModel,
    requestPermissionLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    var locationAcquired:Boolean by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            getCurrentLocation(context, fusedLocationClient) { lat, lon ->
                if (!locationAcquired) {
                    mainViewModel.loadAddress(lat, lon)
                    locationAcquired = true
                }
            }
        } else {
            // Request permissions if not granted
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
}

@Composable
internal fun managedActivityResultLauncher(
    fusedLocationClient: FusedLocationProviderClient,
    mainViewModel: MainViewModel,
    scope: CoroutineScope
): ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>> {

    val context = LocalContext.current
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val locationPermissionGranted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (locationPermissionGranted) {
            getCurrentLocation(context, fusedLocationClient) { lat, lon ->
                mainViewModel.loadAddress(lat, lon)
            }
        } else {
            scope.launch {
                mainViewModel.showSnackBarMessage("permission not granted ")
            }
        }
    }
    return requestPermissionLauncher

}