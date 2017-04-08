package example.neihe_lab05;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    private WifiAdmin wifiAdmin;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView wifi_listview;
    private String[][] mlist;
    WifiAdapter adapter;
    WifiDialog wifiDialog;
    int type_wifi;
    String pass_wifi;
    int postion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifi_init();
        initView();
    }

    private void initView(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);

        //设置下拉多少距离之后开始刷新数据
        swipeRefreshLayout.setDistanceToTriggerSync(50);

        //设置进度条背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(
                this.getResources().getColor(android.R.color.holo_blue_light)
        );

        //设置刷新动画的颜色，可以设置1或者更多
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_orange_light)
        );

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * 注意：AsyncTask是单线程顺序执行的线程任务。
                 * 如果涉及到大量、耗时的线程加载任务，可以考虑自己利用Thread(ExecutorService线程池)+Handler+Message实现。
                 * */
                LongTimeOperationTask task = new LongTimeOperationTask();
                task.execute();
            }
        });
    }

    private void wifi_init(){
        wifiAdmin = new WifiAdmin(this);
        wifiAdmin.OpenWifi();
        wifiAdmin.StartScan();
        Log.e("aa",valueOf(wifiAdmin.LookUpScan()));

        mlist = new String[wifiAdmin.GetWifiList().size()][2];
        for (int i = 0; i < wifiAdmin.GetWifiList().size(); i++){
            mlist[i][0] = wifiAdmin.GetWifiList().get(i).SSID;
            mlist[i][1] = valueOf(wifiAdmin.GetWifiList().get(i).level);
        }

        wifi_listview = (ListView) findViewById(R.id.wifi_listView);

        adapter = new WifiAdapter(
                this,
                mlist);
        wifi_listview.setAdapter(adapter);
        wifi_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String ssid = wifiAdmin.GetWifiList().get(i).SSID;
                type_wifi = Type_wifi(wifiAdmin.GetWifiList().get(i).capabilities);

                Log.e("sss", valueOf(type_wifi));

                if (type_wifi == 1) {
                    wifiAdmin.AddNetwork(wifiAdmin.CreateWifiInfo(ssid, null, 1));
                } else {
                    wifiDialog = new WifiDialog(MainActivity.this, ssid);
                    wifiDialog.show();
                    wifiDialog.setClickListenerInterface(new ClickListenerInterface() {
                        @Override
                        public void doCancel() {
                            wifiDialog.dismiss();
                        }

                        @Override
                        public void doConntection() {
                            pass_wifi = wifiDialog.mima.getText().toString();
                            wifiAdmin.AddNetwork(wifiAdmin.CreateWifiInfo(ssid, pass_wifi, type_wifi));
                            wifiAdmin.ConnectConfiguration(postion);
                            wifiDialog.dismiss();
                        }
                    });
                }
            }
        });
    }

    private int Type_wifi(String capabilities) {
        if (capabilities.toUpperCase().contains("WEP")) {
            return 2;
        } else if (capabilities.toUpperCase().contains("WPA")) {
            return 3;
        }
        return 1;
    }

    private class LongTimeOperationTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            wifi_init();
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
