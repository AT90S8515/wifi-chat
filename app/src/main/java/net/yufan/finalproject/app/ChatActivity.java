package net.yufan.finalproject.app;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.Map;


public class ChatActivity extends Activity implements ChatFragment.OnSendClicked{

    private String deviceName;
    private String deviceAddress;
    public String myName;
    public String myAddress;
    myBroadcastReceiver broadcastReceiver = new myBroadcastReceiver();
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        myName = "NotSetYet";
        myAddress = HomeFragment.myAddress;

        intent = new Intent(this, ChatService.class);
        registerReceiver(broadcastReceiver, new IntentFilter(Constant.BROADCAST_ACTION));
        startService(new Intent(getApplicationContext(), ChatService.class));

        int index = getIntent().getIntExtra(Constant.DEVICE_NUMBER, 0);

        Map<String,?> entry = HomeFragment.getFoundDevices().get(index);
        deviceName = (String) entry.get("deviceName");
        deviceAddress = (String) entry.get("deviceAddress");

        getFragmentManager().beginTransaction()
                .replace(R.id.container, ChatFragment.newInstance(myName, myAddress, deviceName, deviceAddress))
                .commit();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(Constant.BROADCAST_ACTION));
    }

    public void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);
    }

    private void sendMessage(String message, String address, boolean broadcast) {
        clientThread cThread;
        try {
            cThread = new clientThread(message, address, broadcast);
            cThread.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSendClicked(String message, String address, boolean broadcast) {
            sendMessage(message, address, broadcast);
    }
}
