package br.com.udacity.ruyano.recipes.main

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import br.com.udacity.ruyano.recipes.R
import br.com.udacity.ruyano.recipes.models.Recipe
import br.com.udacity.ruyano.recipes.utils.Constants
import br.com.udacity.ruyano.recipes.utils.NetworkUtil
import br.com.udacity.ruyano.recipes.utils.RecipesRequestSucssesMock
import br.com.udacity.ruyano.recipes.utils.recyclerview.RecyclerViewInteraction
import br.com.udacity.ruyano.recipes.views.main.MainActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MainActivityRobot(
        private val mActivityRule: ActivityTestRule<MainActivity>
) {

    private var server: MockWebServer? = null

    fun setup() = apply {
        server = MockWebServer()
        server?.let { sServer ->
            sServer.start(8080)
            Constants.BASE_URL = server?.url("/").toString()
        }
    }

    fun stop() = apply {
        server?.shutdown()
    }

    fun start() = apply {
        mActivityRule.launchActivity(Intent())
    }

    fun disableNetWorkConnection() = apply {
        NetworkUtil.isTestingDesconnection = true
    }

    fun checkIfNoInternetViewIsDisplayed() = apply {
        Espresso.onView(ViewMatchers.withId(R.id.no_internet_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkIfEmptyViewIsDisplayed() = apply {
        Espresso.onView(ViewMatchers.withId(R.id.empty_view))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkIfRecyclerViewIsDisplayed() = apply {
        Espresso.onView(ViewMatchers.withId(R.id.recipes_recyclerview))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun checkIfEachRowIsTheEcspected() = apply {
        val recipes = Gson().fromJson<List<Recipe>>(
                RecipesRequestSucssesMock.BODY,
                object : TypeToken<List<Recipe?>?>() {}.type
        )
        RecyclerViewInteraction
                .onRecyclerView<Recipe>(ViewMatchers.withId(R.id.recipes_recyclerview))
                .withItems(recipes)
                .check { item, view, e ->
                    ViewAssertions
                            .matches(ViewMatchers.hasDescendant(ViewMatchers.withText(item.name)))
                            .check(view, e)
                }
    }

    fun injectServerError() = apply {
        server?.enqueue(MockResponse()
                .setResponseCode(404)
                .setBody("{}"))
    }

    fun injectServerSuccess() = apply {
        server?.enqueue(MockResponse()
                .setResponseCode(RecipesRequestSucssesMock.CODE)
                .setBody(RecipesRequestSucssesMock.BODY))
    }

}