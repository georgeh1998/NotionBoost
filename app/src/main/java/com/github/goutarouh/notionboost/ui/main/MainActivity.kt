package com.github.goutarouh.notionboost.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.github.goutarouh.notionboost.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = (supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment).navController

        lifecycleScope.launch {
            viewModel.initialDestination.collectLatest { initialDestination ->
                if (initialDestination != null) {
                    navController.navigate(initialDestination.destinationId)
//                    val inflater = navController.navInflater
//                    val graph = inflater.inflate(R.navigation.nav_main)
//
//                    navController.graph = graph.apply {
//                        setStartDestination(R.id.monthlyWidgetSettingFragment)
//                    }
                }
            }
        }
    }
}