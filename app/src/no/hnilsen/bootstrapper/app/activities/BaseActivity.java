package no.hnilsen.bootstrapper.app.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import com.WazaBe.HoloEverywhere.app.Fragment;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.slidingmenu.lib.SlidingMenu;
import no.hnilsen.bootstrapper.AndFragBootstrapHelper;
import no.hnilsen.bootstrapper.R;
import no.hnilsen.bootstrapper.app.slidemenu.SlideoutMenuFragment;
import no.hnilsen.bootstrapper.app.slidemenu.SlidingSherlockFragmentActivity;

/**
 * User: Haakon
 * Date: 03.10.12
 * Time: 23:55
 */
public abstract class BaseActivity extends SlidingSherlockFragmentActivity {
    Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // adds behindView with settings
        addSlideMenu();

        // store context
        mContext = getApplicationContext();

        // First-run-checking - useful for downloading initial data, displaying a welcome dialog etc
        boolean mFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstrun", true);
        if(mFirstRun) {
            try {
                AndFragBootstrapHelper.updateData(this);
            } catch (Exception e) {
                if (AndFragBootstrapHelper.DEBUG)
                    Log.d(AndFragBootstrapHelper.LOGGER, "Klarte ikke oppdatere");
            }
            getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                    .edit()
                    .putBoolean("firstrun", false)
                    .commit();
        }
    }

    /**
     * Add slideout menu fragment
     */
    protected void addSlideMenu() {
        setBehindContentView(R.layout.fragment_slideoutmenu);

        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
        Fragment fragment = new SlideoutMenuFragment();
        t.replace(R.id.fragment_slideoutmenu, fragment);
        t.commit();

        // customize the SlidingMenu
        SlidingMenu mSlidingMenu = getSlidingMenu();
        mSlidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shadow);
        mSlidingMenu.setBehindWidth(getDpAsPxFromResource(R.dimen.slidingmenu_width));
        setSlidingActionBarEnabled(true);
    }

    /**
     * Inflate menu with R.menu.mainmenu
     * @param menu The menu
     * @return True if success
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    /**
     * Setting up the action bar with needed parameters
     */
    protected void actionBarSetup() {
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * SlidingMenu takes pixel values for setBehindWidth, so convert dimens values to pixel with this function
     * @param res Android resource
     * @return Pixel size of a resource
     */
    public int getDpAsPxFromResource(int res) {
        return (int)getResources().getDimension(res);
    }
}
