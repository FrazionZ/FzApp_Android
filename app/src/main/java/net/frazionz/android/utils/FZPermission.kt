package fr.northenflo.webiauth.utils

import android.Manifest


enum class FZPermission(val permission: String) {

    ACCESS_WIFI_STATE(Manifest.permission.ACCESS_WIFI_STATE),
    ACCESS_FINE_LOCATION(Manifest.permission.ACCESS_FINE_LOCATION),
    ACCESS_COARSE_LOCATION(Manifest.permission.ACCESS_COARSE_LOCATION);

}