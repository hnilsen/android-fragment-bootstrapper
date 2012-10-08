package no.hnilsen.bootstrapper.app.lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import com.WazaBe.HoloEverywhere.LayoutInflater;
import com.WazaBe.HoloEverywhere.widget.TextView;
import no.hnilsen.bootstrapper.R;

public class SecondListAdapter extends BaseAdapter {
    private Context context;
    String[] values = {};

    public SecondListAdapter(int inflated_array, Context context) {
        this.context = context;
        values = context.getResources().getStringArray(inflated_array);
    }

    public int getCount() {
        return values.length;
    }

    public Object getItem(int position) {
        return values[position];
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout listView;
        listView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.listitem_second, parent, false);

        TextView itemText = (TextView)listView.findViewById(R.id.secondlist_item_text);
        itemText.setText(values[position]);

        return listView;
    }
}
