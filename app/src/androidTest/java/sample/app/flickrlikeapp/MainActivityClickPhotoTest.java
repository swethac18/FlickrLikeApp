package sample.app.flickrlikeapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Created by swetha on 5/12/19.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityClickPhotoTest{


    static class Matchers {
        public static Matcher<View> withListSize (final int size) {
            return new TypeSafeMatcher<View>() {
                @Override
                public void describeTo(org.hamcrest.Description description) {

                }

                @Override public boolean matchesSafely (final View view) {
                    return ((ListView) view).getCount () == size;
                }


            };
        }
    }
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<MainActivity>
            (MainActivity.class,false, true);


    @Test
    public void performUITasks() {

        // Test static text views on Main Activity launch
        onView(withId(R.id.tv_title))
                .check(matches(withText("Title")));
        onView(withId(R.id.quick_return_banner))
                .check(matches(withText("Recent Flickr Photos")));

        // Scroll down and check if quick_return banner is Gone giving more real estate on screen
        onData(anything())
                    .inAdapterView(withId(R.id.list_view))
                    .atPosition(10)
                    .perform(ViewActions.scrollTo());

        onView(withId(R.id.quick_return_banner))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));


            // Scroll up and check if quick return banner is back
        onData(anything())
                    .inAdapterView(withId(R.id.list_view))
                    .atPosition(2)
                    .perform(ViewActions.scrollTo());

        onView(withId(R.id.quick_return_banner))
                    .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));


    }




}
