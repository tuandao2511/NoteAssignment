package com.example.noteappassignment

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.core.ui.activity.BaseActivity
import com.example.core.util.eventbus.MainEvent
import com.example.core.util.eventbus.MainEventDispatcher
import com.example.noteappassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val viewModel by viewModels<MainViewModel>()

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navHostFragment.navController
    }
    private val appNavigator by lazy { GlobalAppNavigator(navController) }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initViews() {
    }

    override fun observeViewModels() {
        MainEventDispatcher.listener.onEach {
            when(it) {
                is MainEvent.Detail -> {
                    appNavigator.navigateToDetail(it.noteId)
                }
                else -> Unit
            }
        }.launchIn(lifecycleScope)
    }

}