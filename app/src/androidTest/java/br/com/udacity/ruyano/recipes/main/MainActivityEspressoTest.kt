package br.com.udacity.ruyano.recipes.main

import androidx.test.rule.ActivityTestRule
import br.com.udacity.ruyano.recipes.views.main.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainActivityEspressoTest {

    @get:Rule
    var mActivityRule = ActivityTestRule(MainActivity::class.java, false, false)

    private var mRobot = MainActivityRobot(mActivityRule)

    @Before
    fun setup() {
        mRobot.setup()
    }

    @After
    fun tearDown() {
        mRobot.stop()
    }

    @Test
    fun testNoInternet() {
        mRobot.disableNetWorkConnection()
                .start()
                .checkIfNoInternetViewIsDisplayed()
    }

    @Test
    fun testServerError() {
        mRobot.injectServerError()
                .start()
                .checkIfEmptyViewIsDisplayed()
    }

    @Test
    fun testServerSucsses() {
        mRobot.injectServerSuccess()
                .start()
                .checkIfRecyclerViewIsDisplayed()
                .checkIfEachRowIsTheEcspected()
    }

}