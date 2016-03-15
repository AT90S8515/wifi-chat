package net.yufan.finalproject.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by yufangong on 7/30/14.
 */
public class myBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage = simpleMessage.StringToMessage(message);
        ChatFragment.mMessageList.add(simpleMessage);
        ChatFragment.mListAdapter.notifyDataSetChanged();
    }
}
