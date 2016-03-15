package net.yufan.finalproject.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yufangong on 7/28/14.
 */
public class HomeFragment extends Fragment  {

    public String domain;
    public static String myAddress;
    InetAddress inetAddress;

    private WifiUtility myWifiUtility = new WifiUtility();
    private ListView devicesList;

    private static List<Map<String, ?>> devices;

    private List<Map<String, ?>> devicesFoundByWifi;
//    private List<Map<String, ?>> devices;
//    private FindDevices myFindDevices;

    public interface OnItemSelectedListener{
        public void onItemSelected(int position);
    }

    OnItemSelectedListener mCallback;

    public static List<Map<String, ?>> getFoundDevices() {
        return devices;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mCallback = (OnItemSelectedListener) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + "must implement OnItemSelectedListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homefragment, parent, false);
        TextView my_device = (TextView) rootView.findViewById(R.id.textview_mydevice);
        devicesList = (ListView) rootView.findViewById(R.id.otherdevices);

        devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {

                mCallback.onItemSelected(position);

            }
        });
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.home_menu, menu);

        MenuItem discoverItem = menu.findItem(R.id.action_discover);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_discover:
//                myFindDevices = new FindDevices(getActivity(), devicesList);
//                myFindDevices.execute(myWifiUtility);
                myWifiUtility.setDomain(getActivity());
                myWifiUtility.discoverDevices(getActivity());

                //devicesFoundByWifi = myWifiUtility.getFoundDevices();
                //discover other devices and return to listView
                //setList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public class WifiUtility {

        WifiManager wifiManager;
        WifiInfo wifiInfo;



        public ArrayList<String> foundAddresses = new ArrayList<String>();


        ProgressDialog progressDialog;
        int progress;
        private Handler progressbarHandler = new Handler();
        private Handler listViewHandler = new Handler();

        public void setDomain(Context context) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiInfo = wifiManager.getConnectionInfo();
            int address = wifiInfo.getIpAddress();

            //this code fragment come from internet should figure out later
            domain = String.format("%d.%d.%d.",
                    (address & 0xff),
                    (address >> 8 & 0xff),
                    (address >> 16 & 0xff));
            myAddress = String.format("%d.%d.%d.%d",
                    (address & 0xff),
                    (address >> 8 & 0xff),
                    (address >> 16 & 0xff),
                    (address >> 24 & 0xff));
        }

        public void discoverDevices(final Context context) {
            //MainActivity.foundDevices.clear();

            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Dicovering Users");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();


            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        for (int i = 1; i < 256; i++) {
                            inetAddress = InetAddress.getByName(domain + Integer.toString(i));
                            if (inetAddress.isReachable(50)) {
                                if (!foundAddresses.contains(domain + Integer.toString(i))) {
                                    foundAddresses.add(domain + Integer.toString(i));
                                    Log.v("address found", domain + Integer.toString(i));
                                }

                            }

                            progress = i;
                            progressbarHandler.post(new Runnable() {
                                public void run() {
                                    progressDialog.setProgress((int) (progress / 2.55));
                                }
                            });
                        }
                        progressDialog.dismiss();
                        setList();

                        listViewHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                DevicesAdapter myBaseAdapter;
                                myBaseAdapter = new DevicesAdapter(getActivity(), devices);
                                devicesList.setAdapter(myBaseAdapter);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        public void setList() {
            devices = new ArrayList<Map<String,?>>();

            for (int i = 0; i < foundAddresses.size(); i++) {
                devices.add(createDevicesList("Device " + Integer.toString(i), foundAddresses.get(i).toString()));
            }
//            devicesFoundByWifi = devices;

//            MyBaseAdapter myBaseAdapter;
//            myBaseAdapter = new MyBaseAdapter(getActivity(), devices);
//            devicesList.setAdapter(myBaseAdapter);

//            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),
//                    devices, R.layout.list_row,
//                    new String[]{"deviceName", "deviceAddress"},
//                    new int[]{R.id.device_name, R.id.device_address});
//
//            devicesList.setAdapter(simpleAdapter);
        }

        private HashMap createDevicesList(String deviceName, String deviceAddress) {
            HashMap myDevices = new HashMap();
            myDevices.put("deviceName", deviceName);
            myDevices.put("deviceAddress", deviceAddress);
            return myDevices;
        }


    }

}
