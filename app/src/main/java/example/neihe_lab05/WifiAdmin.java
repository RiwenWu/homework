package example.neihe_lab05;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;

import java.util.List;

/**
 * Created by wrw on 2017/3/28.
 */

public class WifiAdmin {

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private List<ScanResult> wifiList;
    private List<WifiConfiguration> wifiConfigration;
    //定义一个WifiLock
    WifiLock mWifiLock;

    public WifiAdmin(Context context){
        wifiManager = (WifiManager) context.getSystemService(
                Context.WIFI_SERVICE
        );
        wifiInfo = wifiManager.getConnectionInfo();
    }

    public void OpenWifi(){//open wifi
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }

    public void CloseWifi(){//close wifi
        if (!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }
    }

    //创建一个WifiLock
    public void CreatWifiLock()
    {
        mWifiLock = wifiManager.createWifiLock("Test");
    }

    //获得配置好的网络
    public List<WifiConfiguration> GetConfiguration(){
        return wifiConfigration;
    }

    //指定网络进行连接
    public void ConnectConfiguration(int index){
        //索引大于配置好的网络索引返回
        if (index > wifiConfigration.size()){
            return;
        }
        wifiManager.enableNetwork(
                wifiConfigration.get(index).networkId,
                true
        );
    }

    public void StartScan(){
        wifiManager.startScan();
        wifiList = wifiManager.getScanResults();//get result
        wifiConfigration = wifiManager.getConfiguredNetworks();
    }

    public List<ScanResult> GetWifiList(){
        return wifiList;
    }

    public StringBuilder LookUpScan(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < wifiList.size(); i++){
            stringBuilder.append("Index_" + new Integer(i + 1).toString() + ";");
            //将ScanResult信息转换成一个字符串包
            stringBuilder.append(wifiList.get(i)).toString();
            stringBuilder.append("\n");
        }
        return stringBuilder;
    }

    //get SSID
    public String GetSSID(){
        return (wifiInfo == null) ? "NULL" : wifiInfo.getSSID();
    }

    //get MAC address
    public String GetMacaddress(){
        return (wifiInfo == null) ? "NULL" : wifiInfo.getMacAddress();
    }

    //get DSSID
    public String GetBSSID(){
        return (wifiInfo == null) ? "NULL" : wifiInfo.getBSSID();
    }

    //get IP address
    public int GetIPAddress(){
        return (wifiInfo == null) ? 0 : wifiInfo.getIpAddress();
    }

    //get netword ID
    public int GetNetworkId(){
        return (wifiInfo == null) ? 0 :wifiInfo.getNetworkId();
    }

    //get wifi's all message
    public String GetWifiInfo(){
        return (wifiInfo == null) ? "NULL" :wifiInfo.toString();
    }

    //get wifi link speed
    public int GetWifiLinkSpeed(){
        return (wifiInfo == null) ? 0 :wifiInfo.getLinkSpeed();
    }

    //get wifi rssi-接受信号的强度指示
    public int GetWifiRssi(){
        return (wifiInfo == null) ? 0:wifiInfo.getRssi();
    }

    //add a network and connect
    public void AddNetwork(WifiConfiguration wcg){
        int wcgID = wifiManager.addNetwork(wcg);
        wifiManager.enableNetwork(wcgID, true);
    }

    //over the point ID network
    public void DisconnectWifi(int netId){
        wifiManager.disableNetwork(netId);
        wifiManager.disconnect();
    }

    //
    public WifiConfiguration CreateWifiInfo(String SSID, String Password, int Type){
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedGroupCiphers.clear();
        configuration.allowedKeyManagement.clear();
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedProtocols.clear();
        configuration.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null){
            wifiManager.removeNetwork(tempConfig.networkId);
        }

        if (Type == 1) {//不用密码
            configuration.wepKeys[0] = "";
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            configuration.wepTxKeyIndex = 0;
        }

        if (Type == 2){//wep加密
            configuration.hiddenSSID = true;
            configuration.wepKeys[0] = "\"" + Password + "\"";
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            configuration.wepTxKeyIndex = 0;
        }

        if (Type == 3){//wpa加密
            configuration.preSharedKey = "\""+Password+"\"";
            configuration.hiddenSSID = true;
            configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            configuration.status = WifiConfiguration.Status.ENABLED;
        }

        return configuration;
    }

    private WifiConfiguration IsExsits(String SSID){
        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs){
            if (existingConfig.SSID.equals("\""+SSID+"\"")){
                return existingConfig;
            }
        }
        return null;
    }
}
