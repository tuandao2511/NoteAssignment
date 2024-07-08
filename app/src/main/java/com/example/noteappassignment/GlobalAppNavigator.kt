package com.example.noteappassignment

import android.net.Uri
import androidx.navigation.NavController
import com.example.core.util.navigate.RouteAppUntil
import com.example.core.util.navigate.RouteAppUntil.QUERY_NODE_ID
import com.example.core.util.navigate.safeDeepLinkNavigate

class GlobalAppNavigator(private val navController: NavController) {
    fun navigateToDetail(noteId: String?) {
        val deepLink =
            Uri.parse(RouteAppUntil.DEEPLINK_DETAIL).buildUpon().appendQueryParameter(
                QUERY_NODE_ID, noteId
            ).build()
        navController.safeDeepLinkNavigate(deepLink)
    }
}