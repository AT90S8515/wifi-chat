package net.yufan.finalproject.app;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

/**
 * Created by yufangong on 7/30/14.
 */
public class clientThread extends Thread{

    public static DatagramPacket clientPacket;

    boolean mBroadcast;
    String mMessage;
    String mAddress;

    public clientThread(String message, String address, boolean broadcast) throws IOException{
        mMessage = message;
        mAddress = address;
        mBroadcast = broadcast;
    }

    public void run(){
        try{
            ChatService.datagramSocket.setBroadcast(mBroadcast);
            clientPacket = new DatagramPacket(mMessage.getBytes(), mMessage.length(), InetAddress.getByName(mAddress), 9000);
            ChatService.datagramSocket.send(clientPacket);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

