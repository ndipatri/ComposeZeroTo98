package com.ndipatri.iot.zeroto98

import android.content.Intent
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

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

        // We need to add sleep because we are making background calls in the app
        // to communicate with our real-world siren.  This takes time.  We don't know the
        // app well enough to be able to create the necessary 'Espresso Idling Resource' hooks
        Thread.sleep(5000)

        // This only works if the real system is in the 'off' state.
        onView(withId(R.id.textview_first)).check(matches(withText("RedSiren is off!")))
    }
}