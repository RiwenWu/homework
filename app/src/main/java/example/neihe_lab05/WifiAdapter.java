package example.neihe_lab05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by wrw on 2017/3/28.
 */

public class WifiAdapter extends BaseAdapter {

    private Context context;
    LayoutInflater inflater;
    String[][] list;

    public WifiAdapter(Context context, String[][] list){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list != null ? this.list.length: 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflater.inflate(R.layout.item_wifi_list, null);

        TextView textView1 = (TextView) view.findViewById(R.id.wifi_ssid);
        TextView textView2 = (TextView) view.findViewById(R.id.wifi_rssi);

        textView1.setText(list[i][0]);
        textView2.setText(list[i][1]);
        return view;
    }
}
