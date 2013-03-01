package no.hnilsen.bootstrapper.app.lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.holoeverywhere.LayoutInflater;
import no.hnilsen.bootstrapper.R;

import java.util.List;

public class MainListAdapter extends BaseAdapter {
    private Context context;
    private List<String> mainListItems;

    public MainListAdapter(List<String> mainListItems, Context context) {
        this.context = context;
        this.mainListItems = mainListItems;
    }

    public int getCount() {
        return mainListItems.size();
    }

    public String getItem(int position) {
        return mainListItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout listView;
        listView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listitem_mainlist, parent, false);

        TextView regionItemText = (TextView)listView.findViewById(R.id.mainlist_item_text);
        regionItemText.setText(mainListItems.get(position));

        return listView;
    }
}
