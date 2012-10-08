package no.hnilsen.bootstrapper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import com.actionbarsherlock.view.MenuItem;
import no.hnilsen.bootstrapper.app.fragments.SecondListFragment;

public class AndFragBootstrapHelper {
    /**
     * Change this if you want logging
     */
    public static final boolean DEBUG = true;

    /**
     * Log string
     */
    public static final String LOGGER = "Bootstrapper";

    private static Context context;

    /**
     * Check if internet connection is up
     *
     * @param context The application context
     * @return True if it's up, false if it's not
     */
    static public boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Shorthand-function for updating something when online and updating the UI when done
     * @param context Applikasjonskontekst
     */
    public static void updateDataWithABSRefresh(Context context,
                                                MenuItem item,
                                                SecondListFragment.OnFragmentUpdateListener callback,
                                                int position) {
        if(isOnline(context)) {
            new updateDataWithABSRefreshTask(context, item, callback, position).execute();
        }
    }

    /**
     * Shorthand-function for doing something ASyncTask-y while online only
     *
     * @param context Applikasjonskonteksten
     */
    public static void updateData(Context context) {
        if(isOnline(context)) {
            new updateDataTask(context).execute();
        }
    }

    /**
     * Updating something with ASyncTask while updating the UI after it's done
     */
    private static class updateDataWithABSRefreshTask extends AsyncTask<Void, Void, Void> {
        SecondListFragment.OnFragmentUpdateListener callback;
        Context context;
        MenuItem item;
        int position;

        private updateDataWithABSRefreshTask(Context context, MenuItem item, SecondListFragment.OnFragmentUpdateListener callback, int position) {
            super();
            this.context = context;
            this.item = item;
            this.callback = callback;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {
            item.setActionView(R.layout.menuitem_action_refresh);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(isOnline(context)) {
                // do fancy download stuff here
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            callback.onFragmentUpdate(position, true);
            item.setActionView(null);
        }
    }

    /**
     * Do something and don't update the UI
     */
    private static class updateDataTask extends AsyncTask<Void, Void, Void> {
        Context context;

        private updateDataTask(Context context) {
            super();
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(isOnline(context)) {
                // do fancy online stuff here
            }

            return null;
        }
    }
}

