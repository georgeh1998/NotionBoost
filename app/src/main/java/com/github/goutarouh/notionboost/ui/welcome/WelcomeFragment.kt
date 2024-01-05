package com.github.goutarouh.notionboost.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import com.github.goutarouh.notionboost.R
import com.github.goutarouh.notionboost.ui.theme.NotionBoostTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    private val viewModel by viewModels<WelcomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireActivity()).apply {
            setContent {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                NotionBoostTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val uiModel = viewModel.uiModel.collectAsStateWithLifecycle(
                            lifecycle = viewLifecycleOwner.lifecycle,
                            minActiveState = Lifecycle.State.STARTED
                        ).value

                        LaunchedEffect(key1 = uiModel) {
                            if (uiModel.finishWelcomeScreen) {
                                findNavController().navigate(R.id.actionWelcomeFragmentToMonthlyWidgetListFragment)
                            }
                        }

                        WelcomeScreen(uiModel)
                    }
                }
            }
        }
    }
}