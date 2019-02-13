package br.com.udacity.ruyano.recipes.main;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.List;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.networking.RetrofitConfig;
import br.com.udacity.ruyano.recipes.utils.Constants;
import br.com.udacity.ruyano.recipes.utils.NetworkUtil;
import br.com.udacity.ruyano.recipes.utils.RecipesRequestSucssesMock;
import br.com.udacity.ruyano.recipes.utils.recyclerview.RecyclerViewInteraction;
import br.com.udacity.ruyano.recipes.views.main.MainActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(JUnit4.class)
public class MainActivityEspressoTest {

    private MockWebServer server;
    private IdlingResource idlingResource;

    {
        server = new MockWebServer();
        try {
            server.start(8080);
            Constants.BASE_URL = server.url("/").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        idlingResource = OkHttp3IdlingResource.create(
                "okhttp", RetrofitConfig.getInstance().getOkHttpClient());
        IdlingRegistry.getInstance().register(idlingResource);

    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, false, false);

    @After
    public void tearDown() throws IOException {
        server.shutdown();
        IdlingRegistry.getInstance().unregister(idlingResource);

    }

    @Test
    public void testNoInternet() {
        NetworkUtil.isTestingDesconnection = true;
        mActivityRule.launchActivity(new Intent());
        onView(ViewMatchers.withId(R.id.no_internet_view)).check(matches(isDisplayed()));

    }

    @Test
    public void testServerError() {
        server.enqueue(new MockResponse().setResponseCode(404).setBody("{}"));
        mActivityRule.launchActivity(new Intent());
        onView(ViewMatchers.withId(R.id.empty_view)).check(matches(isDisplayed()));

    }

    @Test
    public void testServerSucsses() {
        server.enqueue(new MockResponse().setResponseCode(RecipesRequestSucssesMock.CODE).setBody(RecipesRequestSucssesMock.BODY));
        mActivityRule.launchActivity(new Intent());
        // test if recyclerview is visible
        onView(withId(R.id.recipes_recyclerview)).check(matches(isDisplayed()));
        List<Recipe> recipes = new Gson().fromJson(RecipesRequestSucssesMock.BODY,new TypeToken<List<Recipe>>(){}.getType());
        // test each item of recyclerview comparating whith itens in list
        RecyclerViewInteraction.<Recipe>onRecyclerView(withId(R.id.recipes_recyclerview))
                .withItems(recipes)
                .check(new RecyclerViewInteraction.ItemViewAssertion<Recipe>() {
                    @Override
                    public void check(Recipe item, View view, NoMatchingViewException e) {
                        matches(hasDescendant(withText(item.getName())))
                                .check(view, e);
                    }
                });

    }

}
