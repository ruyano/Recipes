package br.com.udacity.ruyano.recipes;

import android.content.Intent;
import android.net.Uri;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import androidx.test.rule.ActivityTestRule;
import br.com.udacity.ruyano.recipes.utils.Constants;
import br.com.udacity.ruyano.recipes.views.main.MainActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(JUnit4.class)
public class MainActivityEspressoTest {

    private MockWebServer server;

    {
        server = new MockWebServer();
        try {
            server.start();
            Constants.BASE_URL = server.url("/").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);


    @After
    public void tearDown() throws IOException {
        server.shutdown();

    }

    @Test
    public void testServerError() throws InterruptedException {
        server.enqueue(new MockResponse().setResponseCode(404).setBody("{}"));
        mActivityRule.launchActivity(new Intent());

        Thread.sleep(5000);
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()));

    }

    @Test
    public void testServerSucsses() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody(getJson("json/recipes_json.txt")));
        mActivityRule.launchActivity(new Intent());

        Thread.sleep(5000);
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()));

    }

    private String getJson(String path) throws Exception {
        URL url = this.getClass().getClassLoader().getResource(path);
        return getStringFromFile(url.getPath());
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

}
