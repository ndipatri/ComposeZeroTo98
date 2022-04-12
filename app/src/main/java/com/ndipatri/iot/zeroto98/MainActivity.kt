package com.ndipatri.iot.zeroto98

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.ndipatri.iot.zeroto98.api.ParticleAPI
import com.ndipatri.iot.zeroto98.ui.theme.ZeroTo98Theme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var particleAPI: ParticleAPI

    var sirenState = mutableStateOf("Standby ...")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ApplicationComponent.createIfNecessary().inject(this)

        setContent {
            ZeroTo98Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(Modifier.fillMaxWidth().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "The Red Siren is ${sirenState.value}")
                        OutlinedButton(onClick = {
                            lifecycleScope.launch {
                                if (sirenState.value == "on") {
                                    particleAPI.particleInterface.turnOffRedSiren()
                                } else {
                                    particleAPI.particleInterface.turnOnRedSiren()
                                }
                                updateSirenState()
                            }
                            sirenState.value = "Standby..."

                        }) {
                            Text("Turn Siren ${if (sirenState.value == "off") "On" else "Off"}")
                        }
                    }
                }
            }
        }

        updateSirenState()
    }

    private fun updateSirenState() {
        lifecycleScope.launch {
            sirenState.value = particleAPI.particleInterface.getSirenState().result!!
        }
    }
}