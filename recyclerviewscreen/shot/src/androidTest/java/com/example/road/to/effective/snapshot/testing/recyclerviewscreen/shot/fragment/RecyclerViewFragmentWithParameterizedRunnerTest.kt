package com.example.road.to.effective.snapshot.testing.recyclerviewscreen.shot.fragment

import com.example.road.to.effective.snapshot.testing.recyclerviewscreen.mvvm.RecyclerViewFragment
import com.example.road.to.effective.snapshot.testing.testannotations.FragmentTest
import com.example.road.to.effective.snapshot.testing.testannotations.HappyPath
import com.example.road.to.effective.snapshot.testing.testannotations.UnhappyPath
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test
import sergio.sastre.uitesting.utils.fragmentscenario.fragmentScenarioConfiguratorRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Execute the command below to run only FragmentTests
 * 1. Record:
 *    ./gradlew :recyclerviewscreen:shot:executeScreenshotTest -Pandroid.testInstrumentationRunnerArguments.annotation=com.example.road.to.effective.snapshot.testing.utils.testannotations.FragmentTest -Precord
 * 2. Verify:
 *    ./gradlew :recyclerviewscreen:shot:executeScreenshotTest -Pandroid.testInstrumentationRunnerArguments.annotation=com.example.road.to.effective.snapshot.testing.utils.testannotations.FragmentTest
 */

/**
 * Example of Parameterized test with Parameterized Runner.
 *
 * Unlike TestParameterInjector, the testItem is used in all @Tests (the test methods do not admit
 * arguments).
 *
 * On the other hand, ParameterizedRunner is compatible with instrumented test of any API level,
 * whereas TestParameterInjector requires API 24+, throwing
 * java.lang.NoClassDefFoundError: com.google.common.cache.CacheBuilder error in lower APIs
 */
@RunWith(Parameterized::class)
class RecyclerViewFragmentParameterizedHappyPathTest(
    private val testItem: HappyPathTestItem,
) : ScreenshotTest {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun testItemProvider(): Array<HappyPathTestItem> = HappyPathTestItem.values()
    }

    @get:Rule
    val fragmentScenarioConfiguratorRule =
        fragmentScenarioConfiguratorRule<RecyclerViewFragment>(config = testItem.item)

    @HappyPath
    @FragmentTest
    @Test
    fun snapFragment() {
        compareScreenshot(
            fragment = fragmentScenarioConfiguratorRule.fragment,
            name = "RecyclerViewFragment_${testItem.name}_Parameterized",
        )
    }
}

@RunWith(Parameterized::class)
class RecyclerViewFragmentParameterizedUnhappyPathTest(
    private val testItem: UnhappyPathTestItem,
) : ScreenshotTest {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun testItemProvider(): Array<UnhappyPathTestItem> = UnhappyPathTestItem.values()
    }

    @get:Rule
    val fragmentScenarioConfiguratorRule =
        fragmentScenarioConfiguratorRule<RecyclerViewFragment>(config = testItem.item)

    @UnhappyPath
    @FragmentTest
    @Test
    fun snapFragment() {
        compareScreenshot(
            fragment = fragmentScenarioConfiguratorRule.fragment,
            name = "RecyclerViewFragment_${testItem.name}_Parameterized",
        )
    }
}
