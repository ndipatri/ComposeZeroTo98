package com.ndipatri.iot.zeroto98

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.rule.ActivityTestRule
import com.google.gson.Gson
import com.ndipatri.iot.zeroto98.api.ParticleAPI
import okhttp3.OkHttpClient
import okhttp3.mock.*
import org.junit.Rule
import java.net.HttpURLConnection


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.ndipatri.iot.zeroto98", appContext.packageName)
    }
}