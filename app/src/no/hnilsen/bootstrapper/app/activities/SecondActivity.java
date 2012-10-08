package no.hnilsen.bootstrapper.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ArrayAdapter;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import no.hnilsen.bootstrapper.AndFragBootstrapHelper;
import no.hnilsen.bootstrapper.R;
import no.hnilsen.bootstrapper.app.fragments.SecondListFragment;

/**
 * User: Haakon
 * Date: 28.02.12
 * Time: 08:39
 */
public class SecondActivity extends BaseActivity implements ActionBar.OnNavigationListener, SecondListFragment.OnFragmentUpdateListener {
    boolean mFirstRun = true;
    private int mExampleId;
    boolean mIsUpdating = false;
    FragmentManager fm;

    @Override
    public void onFragmentUpdate(int position, boolean forceupdate) {
        replaceFragment(mExampleId);
    }

    /**
     * When creating the activity, run a series of checks and setups
     * @param savedInstanceState State of the activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_second);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Intent i = getIntent();
            mExampleId = i.getIntExtra("regionId", 0);
        }

        // set up static variables
        fm = getSupportFragmentManager();

        SecondListFragment fragment = (SecondListFragment)fm.findFragmentByTag("second_fragment");

        if (savedInstanceState == null || fragment == null) {
            fragment = SecondListFragment.newInstance(mExampleId);

            FragmentTransaction ft = fm.beginTransaction();
            ft.add(android.R.id.content, fragment, "second_fragment");
            ft.commit();
        }

        actionBarSetup();
    }

    /**
     * Fragment replacement snippet - if fragment exists, replace it with a new one with animation
     * @param position Position (region)
     */
    public void replaceFragment(int position) {
        SecondListFragment fragment = (SecondListFragment)fm.findFragmentByTag("second_fragment");

        assert fragment != null;
        if(fragment.getmExampleId() != position || mIsUpdating) {
            fragment = SecondListFragment.newInstance(position);

            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
            ft.replace(android.R.id.content, fragment, "second_fragment");
            ft.commit();
        }
        if(mIsUpdating) mIsUpdating = false;
    }

    /**
     * Setting up the action bar - including inflating the spinner
     */
    @Override
    protected void actionBarSetup() {
        ActionBar ab = getSupportActionBar();

        ab.setDisplayShowTitleEnabled(false);
        ab.setDisplayHomeAsUpEnabled(true);

        Context context = ab.getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.main_list,
                R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        ab.setListNavigationCallbacks(list, this);
        ab.setSelectedNavigationItem(mExampleId);
    }

    /**
     * When clicking action bar menu items, this is where they're handled
     * @param item ID of item pressed
     * @return Do stuff and return true if action should be taken
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;
            case R.id.menu_refresh:
                AndFragBootstrapHelper.updateDataWithABSRefresh(this, item, this, mExampleId);

                mIsUpdating = true;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Replace fragment with which ever item is chosen from the spinner.
     * @param position Item position
     * @param itemId ID of the item clicked.
     * @return True if successful
     */
    @Override
    public boolean onNavigationItemSelected(int position, long itemId) {
        mExampleId = position;
        if(!mFirstRun) {
            replaceFragment(mExampleId);
        }
        mFirstRun = false;
        return true;
    }

    @Override
    public int getmExampleId() {
        return mExampleId;
    }
}
