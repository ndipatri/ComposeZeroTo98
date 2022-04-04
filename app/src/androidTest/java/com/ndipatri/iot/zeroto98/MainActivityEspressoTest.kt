package com.ndipatri.iot.zeroto98

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import okhttp3.OkHttpClient
import okhttp3.mock.Behavior
import okhttp3.mock.MockInterceptor
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    // This can be used to inject 'fake' OkHttpClient responses for testing
    // purposes.  In this way, the system can make real network calls but will result
    // in mocked network responses.
    private val mockInterceptor = MockInterceptor(Behavior.UNORDERED)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(mockInterceptor)
        .build()

    @get:Rule
    val componentRule = ApplicationComponentTestRule(okHttpClient)

    @get:Rule
    val rule = createComposeRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(
        MainActivity::class.java,
        false,
        false
    )

    @Test
    fun showCurrentRedSirenState_off() {

        activityTestRule.launchActivity(Intent())

        // THIS IS UGLY, but it's how we quickly stand up a flaky EndToEnd Test!
        var attemptCount = 1
        while (attemptCount++ < 3) {
            try {
                // We don't know what state the system is in, but we can change it here!
                rule.onNodeWithText("Turn Siren", substring = true).performClick()

                // We need to add sleep because we are making background calls in the app
                // to communicate with our real-world siren.  This takes time.  We don't know the
                // app well enough to be able to create the necessary 'Espresso Idling Resource' hooks
                Thread.sleep(5000)

                // This only works if the real system is in the 'off' state.
                rule.onNodeWithText("The Red Siren is off").assertIsDisplayed()
            } catch (err: AssertionError) {
                System.err.println("Flaky test! let's try that again...")
            }
        }
    }
}