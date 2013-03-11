package no.hnilsen.bootstrapper.app.slidemenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hnilsen.bootstrapper.R;

/**
 * User: Haakon
 * Date: 02.10.12
 * Time: 13:23
 */
public class SlideoutMenuFragment extends Fragment {
    private static SlideoutMenuFragment instance;

    public static SlideoutMenuFragment getInstance() {
        if (instance == null) {
            return new SlideoutMenuFragment();
        }
        return instance;
    }

    public SlideoutMenuFragment() {
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slidingmenu_layout, null);
    }
}
