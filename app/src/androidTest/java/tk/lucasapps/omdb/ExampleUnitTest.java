package tk.lucasapps.omdb;


import android.os.SystemClock;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import tk.lucasapps.omdb.activity.MainActivity;
import tk.lucasapps.omdb.activity.SearchActivity_;

import android.support.test.runner.AndroidJUnit4;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleUnitTest {
//    @Test
//    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);

    @Rule
    public ActivityTestRule<SearchActivity_> mActivityRule = new ActivityTestRule<>(
            SearchActivity_.class);
//    }
    @Test
    public void sayHello() throws InterruptedException {
        onView(withId(R.id.search_text)).perform(typeText("toy story"));
        onView(withId(R.id.search_button)).perform(click());
        SystemClock.sleep(2000);
        onView(withId(R.id.search_results_recycler_view)).perform(swipeUp()).check(matches(hasDescendant(withText("Toy Story 2"))));
    }


}