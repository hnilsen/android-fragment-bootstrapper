package no.hnilsen.bootstrapper.app.slidemenu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivityBase;
import com.slidingmenu.lib.app.SlidingActivityHelper;

/**
 * User: Haakon
 * Date: 01.10.12
 * Time: 22:07
 *
 * Helper class to bundle ActionBarSherlock with SlidingMenu
 */
public class SlidingSherlockFragmentActivity extends SherlockFragmentActivity implements SlidingActivityBase {

    private SlidingActivityHelper mHelper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new SlidingActivityHelper(this);
        mHelper.onCreate(savedInstanceState);
    }

    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate(savedInstanceState);
    }

    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mHelper.findViewById(id);
    }

    public void setContentView(int id) {
        setContentView(getLayoutInflater().inflate(id, null));
    }

    public void setContentView(View v) {
        setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setContentView(View v, LayoutParams params) {
        super.setContentView(v, params);
        mHelper.registerAboveContentView(v, params);
    }

    public void setBehindContentView(int id) {
        setBehindContentView(getLayoutInflater().inflate(id, null));
    }

    public void setBehindContentView(View v) {
        setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    public void setBehindContentView(View v, LayoutParams params) {
        mHelper.setBehindContentView(v, params);
    }

    public SlidingMenu getSlidingMenu() {
        return mHelper.getSlidingMenu();
    }

    public void toggle() {
        mHelper.toggle();
    }

    public void setSlidingActionBarEnabled(boolean b) {
        mHelper.setSlidingActionBarEnabled(b);
    }

	@Override
	public void showContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showSecondaryMenu() {
		// TODO Auto-generated method stub
		
	}
}