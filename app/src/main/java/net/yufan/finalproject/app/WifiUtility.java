package net.yufan.finalproject.app;

/**
 * Created by yufangong on 7/28/14.
 */
//public class WifiUtility {
//
//    public static ArrayList<String> devices = new ArrayList<String>();
//    WifiManager wifiManager;
//    WifiInfo wifiInfo;
//
//    public String domain;
//    public String myAddress;
//    InetAddress inetAddress;
//
//    public ArrayList<InetAddress> foundAddresses = new ArrayList<InetAddress>();
//
//
//    ProgressDialog progressDialog;
//    int progress;
//    private Handler progressbarHandler = new Handler();
//
//    public void setDomain(Context context){
//        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        wifiInfo = wifiManager.getConnectionInfo();
//        int address = wifiInfo.getIpAddress();
//
//        //this code fragment come from internet should figure out later
//        domain = String.format("%d.%d.%d.",
//                (address & 0xff),
//                (address >> 8 & 0xff),
//                (address >> 16 & 0xff));
//        myAddress = String.format("%d.%d.%d.%d",
//                (address & 0xff),
//                (address >> 8 & 0xff),
//                (address >> 16 & 0xff),
//                (address >> 24 & 0xff));
//    }
//
//    public void discoverDevices(final Context context){
//        //MainActivity.foundDevices.clear();
//
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Scanning Users");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.setProgress(0);
//        progressDialog.setMax(100);
//        progressDialog.show();
//
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try{
//                    for(int i = 1; i < 256; i++){
//                        inetAddress = InetAddress.getByName(domain + Integer.toString(i));
//                        if(inetAddress.isReachable(50))
//                        {
//                            if(!foundAddresses.contains(inetAddress)){
//                                foundAddresses.add(inetAddress);
//                                Log.v("address found", domain + Integer.toString(i));
//                            }
//
//                        }
//
//                    progress = i;
//                    progressbarHandler.post(new Runnable() {
//                        public void run() {
//                            progressDialog.setProgress((int)(progress/2.55));
//                        }
//                    });
//                }
//                progressDialog.dismiss();
//                }
//                catch (Exception e)
//                {
//
//                }
//            }
//        }).start();
//    }
//
//    public ArrayList<InetAddress> getFoundDevices()
//    {
//        return foundAddresses;
//    }
//
//
//}
