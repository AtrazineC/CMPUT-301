package com.example.simpleparadox.listycity;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.junit.*;
import static org.junit.Assert.*;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import com.robotium.solo.Solo;

/**
 * Test class for MainActivity. All the UI tests are written here. Robotium test framework is
 used
 */
public class MainActivityTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<MainActivity> rule =
            new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Runs before all tests and creates solo instance.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    /**
     * Closes the activity after each test
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

    /**
     * Gets the Activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * Add a city to the listview and check the city name using assertTrue
     * Clear all the cities from the listview and check again with assertFalse
     */
    @Test
    public void checkList(){
        // Asserts that the current activity is the MainActivity. Otherwise, show “Wrong Activity”
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("ADD CITY"); //Click ADD CITY Button
        //Get view for EditText and enter a city name
        solo.enterText((EditText) solo.getView(R.id.editText_name), "Edmonton");
        solo.clickOnButton("CONFIRM"); //Select CONFIRM Button
        solo.clearEditText((EditText) solo.getView(R.id.editText_name)); //Clear the EditText
        /* True if there is a text: Edmonton on the screen, wait at least 2 seconds and find
        minimum one match. */
        assertTrue(solo.waitForText("Edmonton", 1, 2000));
        solo.clickOnButton("ClEAR ALL"); //Select ClEAR ALL
        //True if there is no text: Edmonton on the screen
        assertFalse(solo.searchText("Edmonton"));
    }

    /**
     * Check item taken from the listview
     */
    @Test
    public void checkCityListItem(){
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name), "Edmonton");
        solo.clickOnButton("CONFIRM");
        solo.waitForText("Edmonton", 1, 2000);
        // Get MainActivity to access its variables and methods.
        MainActivity activity = (MainActivity) solo.getCurrentActivity();
        final ListView cityList = activity.cityList; // Get the listview
        String city = (String) cityList.getItemAtPosition(0); // Get item from first position
        assertEquals("Edmonton", city);
    }

    /**
     * Check if activity switches when you click on a city
     */
    @Test
    public void checkShowCityActivity() {
        // Add a city first
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        solo.clickOnButton("ADD CITY");
        solo.enterText((EditText) solo.getView(R.id.editText_name), "Edmonton");
        solo.clickOnButton("CONFIRM");
        solo.waitForText("Edmonton", 1, 2000);

        // Click on Edmonton city
        ListView listView = (ListView)solo.getView(R.id.city_list);
        View view = listView.getChildAt(0);
        solo.clickOnView(view);
        assertTrue("Failed to go to ShowActivity",
                solo.waitForActivity(ShowActivity.class, 2000));
    }

    /**
     * Check if activity displays correct test when switching to show activity
     */
    @Test public void checkShowCityNameActivity() {
        checkShowCityActivity();
        solo.waitForText("Edmonton", 1, 2000);
    }

    /**
     * Check if show activity back button works
     */
    @Test public void checkShowActivityBackButton() {
        checkShowCityActivity();
        solo.clickOnButton("Back");
        assertTrue("Failed to go back to MainActivity",
                solo.waitForActivity(MainActivity.class, 2000));
    }
}