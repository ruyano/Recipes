package br.com.udacity.ruyano.recipes.recipe.details;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import br.com.udacity.ruyano.recipes.R;
import br.com.udacity.ruyano.recipes.models.Recipe;
import br.com.udacity.ruyano.recipes.utils.RecipesRequestSucssesMock;
import br.com.udacity.ruyano.recipes.views.recipe.details.RecipeDetailsActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(JUnit4.class)
public class RecipeDetailsEspressoTest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityRule = new ActivityTestRule<>(RecipeDetailsActivity.class, false, false);

    @Test
    public void testGenericError() {
        mActivityRule.launchActivity(RecipeDetailsActivity.getIntent(InstrumentationRegistry.getInstrumentation().getTargetContext(), null));
        onView(ViewMatchers.withId(R.id.generic_error_view)).check(matches(isDisplayed()));

    }

    @Test
    public void testShowDetails() {
        Recipe recipe = RecipesRequestSucssesMock.getRecipeObjectList().get(0);
        mActivityRule.launchActivity(RecipeDetailsActivity.getIntent(InstrumentationRegistry.getInstrumentation().getTargetContext(), recipe));
        onView(ViewMatchers.withId(R.id.recipe_detail_fragment)).check(matches(isDisplayed()));

    }

}
