package com.github.goutarouh.notionboost.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.github.goutarouh.notionboost.repository.NotionDatabaseRepository
import com.github.goutarouh.notionboost.ui.theme.NotionBoostTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var queryDatabaseRepository: NotionDatabaseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotionBoostTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isUpdated by remember {
                        mutableStateOf<Boolean?>(null)
                    }
                    UpdateButton(
                        isUpdated = isUpdated
                    ) {
                        lifecycleScope.launch {
                            try {
                                val result = queryDatabaseRepository.queryDatabase("f59002096e874781aa3a659e78aa46d6")
                                queryDatabaseRepository.updateWidget(result)
                                isUpdated = true
                            } catch (e: CancellationException) {
                                isUpdated = false
                                return@launch
                            } catch (e: Exception) {
                                isUpdated = false
                                return@launch
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UpdateButton(
    isUpdated: Boolean? = null,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onClick
        ) {
            Text(text = "Update")
        }
        Spacer(modifier = Modifier.height(20.dp))
        when (isUpdated) {
            null -> {
                Text(text = "")
            }
            true -> {
                Text(text = "Success")
            }
            else -> {
                Text(text = "Failed")
            }
        }
    }
}