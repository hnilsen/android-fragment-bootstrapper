package no.hnilsen.bootstrapper.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.ListFragment;
import no.hnilsen.bootstrapper.R;
import no.hnilsen.bootstrapper.app.activities.SecondActivity;
import no.hnilsen.bootstrapper.app.lists.MainListAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * User: Haakon
 * Date: 28.02.12
 * Time: 06:38
 */
public class MainListFragment extends ListFragment {
    SecondListFragment.OnFragmentUpdateListener mCallback;
    List<String> mainList;
    Context context;
    int mExampleId = 0;
    boolean mDualPane;

    public static MainListFragment newInstance(int regionId) {
        MainListFragment f = new MainListFragment();
        
        // Supply index input as an argument
        Bundle args = new Bundle();
        args.putInt("mExampleId", regionId);
        f.setArguments(args);
        
        return f;
    }

    /**
     * make sure the activity has implemented the fragmentupdatelistener
     * @param activity our activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (SecondListFragment.OnFragmentUpdateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentUpdateListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();

        // get the main list
        mainList = Arrays.asList(this.getResources().getStringArray(R.array.main_list));

        setListAdapter(new MainListAdapter(mainList, context));

        FrameLayout frameLayout = (FrameLayout)getActivity().findViewById(R.id.second_pane);
        mDualPane = frameLayout != null && frameLayout.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mExampleId = savedInstanceState.getInt("mExampleId", 0);
        }

        if(mDualPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showSecondFragment();
        }
    }

    @Override
    public void onListItemClick(org.holoeverywhere.widget.ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        mExampleId = position;
        showSecondFragment();
    }

    private void showSecondFragment() {
        if (mDualPane) {
            mCallback.onFragmentUpdate(mExampleId, false);
        } else {
            Intent intent = new Intent(getActivity(), SecondActivity.class);
            intent.putExtra("mExampleId", mExampleId);
            startActivity(intent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mExampleId", mExampleId);
    }
}
