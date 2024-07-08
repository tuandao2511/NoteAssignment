package com.example.core.util.navigate

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions

fun NavController.safeNavigate(
    navDirections: NavDirections,
    navOptions: NavOptions? = null
) {
    currentDestination?.getAction(navDirections.actionId)?.let {
        navigate(navDirections, navOptions)
    }
}

fun NavController.safeDeepLinkNavigate(uri: Uri, navOptions: NavOptions? = null) {
    if (graph.hasDeepLink(uri)) {
        navigate(uri, navOptions)
    }
}