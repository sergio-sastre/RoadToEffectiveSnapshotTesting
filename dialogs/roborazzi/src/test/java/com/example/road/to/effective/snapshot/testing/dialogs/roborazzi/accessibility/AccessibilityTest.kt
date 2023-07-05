package com.example.road.to.effective.snapshot.testing.dialogs.roborazzi.accessibility

import android.graphics.Color
import android.view.ViewGroup
import com.example.road.to.effective.snapshot.testing.dialogs.DialogBuilder
import com.example.road.to.effective.snapshot.testing.dialogs.R
import com.example.road.to.effective.snapshot.testing.dialogs.roborazzi.filePath
import com.example.road.to.effective.snapshot.testing.dialogs.roborazzi.itemArray
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CaptureType.Dump
import com.github.takahirom.roborazzi.RoborazziOptions.CaptureType.Dump.Companion.AccessibilityExplanation
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.GraphicsMode.Mode.NATIVE
import sergio.sastre.uitesting.robolectric.activityscenario.RobolectricActivityScenarioForViewRule
import sergio.sastre.uitesting.robolectric.config.screen.DeviceScreen.Phone.PIXEL_5
import sergio.sastre.uitesting.utils.utils.waitForMeasuredView

/**
 * Execute the command below to run only AccessibilityTests
 * 1. Record:
 *    ./gradlew :dialogs:roborazzi:recordRoborazziDebug --tests '*Accessibility*'
 * 2. Verify:
 *    ./gradlew :dialogs:roborazzi:verifyRoborazziDebug --tests '*Accessibility*'
 */

/**
 * Roborazzi requires Robolectric Native Graphics (RNG) to generate screenshots.
 * Therefore, you can only take Parameterized Screenshot tests with ParameterizedRobolectricTestRunner.
 *
 * Moreover, RNG must be active. In these tests, we do it by annotating tests with @GraphicsMode(NATIVE).
 * Alternatively one could drop the annotation and enable RNG for all Robolectric tests in a module,
 * adding the following in the module's build.gradle:
 *
 *  testOptions {
 *      unitTests {
 *          includeAndroidResources = true
 *          all {
 *              systemProperty 'robolectric.graphicsMode', 'NATIVE' // this
 *          }
 *      }
 *  }
 */
@RunWith(RobolectricTestRunner::class)
class AccessibilityTest {

    @get:Rule
    val activityScenarioForViewRule =
        RobolectricActivityScenarioForViewRule(
            backgroundColor = Color.TRANSPARENT,
            deviceScreen = PIXEL_5,
        )

    @GraphicsMode(NATIVE)
    @Config(sdk = [30])
    @Test
    fun snapDialog() {
        val activity = activityScenarioForViewRule.activity

        val dialogView = waitForMeasuredView {
            DialogBuilder.buildDeleteDialog(
                ctx = activity,
                onDeleteClicked = {},
                bulletTexts = itemArray(activity, listOf(R.string.shortest))
            ).window!!.decorView
        }

        // Dialogs and Activities are displayed in different windows.
        // Roborazzi uses Espresso under the hood to render views in the Activity window.
        // Thus, add the DialogView to the Activity window, so Espresso can find it.
        (activity.window.decorView as ViewGroup).addView(dialogView)

        dialogView
            .captureRoboImage(
                filePath = filePath("DeleteDialog_Accessibility"),
                roborazziOptions = RoborazziOptions(
                    captureType = Dump(explanation = AccessibilityExplanation)
                )
            )
    }
}
