package example.neihe_lab05;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by wrw on 2017/3/28.
 */

public class WifiDialog extends Dialog {

    private Context context;
    private String SSID;
    EditText mima;
    private ClickListenerInterface clickListenerInterface;

    public WifiDialog(Context context, String SSID) {
        super(context);
        this.context = context;
        this.SSID = SSID;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_wifi, null);
        setContentView(view);

        TextView wifi_ssid = (TextView) view.findViewById(R.id.wiif_name);
        mima = (EditText)  view.findViewById(R.id.wifi_mima);
        MytextView_dialog_wifi cancel = (MytextView_dialog_wifi) view.findViewById(R.id.cancel);
        MytextView_dialog_wifi connection = (MytextView_dialog_wifi) view.findViewById(R.id.connection);

        wifi_ssid.setText(SSID);


        cancel.setOnClickListener(new clickListener());
        connection.setOnClickListener(new clickListener());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int)(d.widthPixels * 0.9);
        dialogWindow.setAttributes(lp);

        mima.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    //pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
        });
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.cancel:
                    clickListenerInterface.doCancel();
                    break;
                case R.id.connection:
                    clickListenerInterface.doConntection();
                    break;
            }
        }
    }

}
