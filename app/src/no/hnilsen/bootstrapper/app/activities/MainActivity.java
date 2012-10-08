package no.hnilsen.bootstrapper.app.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import com.actionbarsherlock.view.MenuItem;
import no.hnilsen.bootstrapper.AndFragBootstrapHelper;
import no.hnilsen.bootstrapper.R;
import no.hnilsen.bootstrapper.app.fragments.MainListFragment;
import no.hnilsen.bootstrapper.app.fragments.SecondListFragment;

/**
 * User: Haakon
 * Date: 28.02.12
 * Time: 05:51
 */
public class MainActivity extends BaseActivity implements SecondListFragment.OnFragmentUpdateListener {
    int mExampleId = 0;
    boolean mDualPane = false;

    /**
     * Main activity - will be the one to fire when the app is started
     * @param savedInstanceState App/activity state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);

        // check for state (for orientation changes etc)
        if(savedInstanceState != null) {
            mExampleId = savedInstanceState.getInt("curRegionId");
        }

        // create left pane
        addMainFragment();

        // check if there is a right pane
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.second_pane);
        mDualPane = frameLayout != null && frameLayout.getVisibility() == View.VISIBLE;

        if(mDualPane) {
            // there is room for two fragments
            addSecondFragment();
        } else {
            // no room for two fragments, we're on a single fragment view - display actionbar
            actionBarSetup();
        }
    }

    /**
     * Region fragment
     */
    private void addMainFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentByTag("main_fragment") == null) {
            MainListFragment fragment = MainListFragment.newInstance(mExampleId);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.main_fragment, fragment, "main_fragment");
            transaction.commit();
        }
    }

    /**
     * Pollen fragment
     */
    private void addSecondFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag("pollenFragment") == null) {
            replaceSecondFragment(mExampleId, false);
        }
    }

    /**
     * Replace fragment with existing fragment
     * @param position position id
     */
    private void replaceSecondFragment(int position, boolean forceupdate) {
        FragmentManager fm = getSupportFragmentManager();
        SecondListFragment fragment = SecondListFragment.newInstance(position);

        System.out.println(getmExampleId() + " - " + position);
        if(fm.findFragmentByTag("pollenFragment") == null || getmExampleId() != position || forceupdate) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.setCustomAnimations(R.anim.fragment_fade_in, R.anim.fragment_fade_out);
            ft.replace(R.id.second_pane, fragment, "second_fragment");
            ft.commit();

            mExampleId = position;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mExampleId", mExampleId);
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
                toggle();
                return true; // true if it's supposed to do something
            case R.id.menu_refresh:
                AndFragBootstrapHelper.updateDataWithABSRefresh(this, item, this, mExampleId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Fragment update callback
     * @param position Position to send in
     */
    @Override
    public void onFragmentUpdate(int position, boolean forceupdate) {
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.second_pane);
        mDualPane = frameLayout != null && frameLayout.getVisibility() == View.VISIBLE;

        if(mDualPane) {
            replaceSecondFragment(position, forceupdate);
        }
    }

    @Override
    public int getmExampleId() {
        return mExampleId;
    }
}
