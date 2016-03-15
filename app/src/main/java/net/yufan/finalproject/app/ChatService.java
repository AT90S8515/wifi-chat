package net.yufan.finalproject.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * Created by yufangong on 7/30/14.
 */
public class ChatService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ServerSocket serverSocket;
    public static DatagramSocket datagramSocket;
    public static DatagramPacket serverPacket;

    private serverThread sThread;

    Intent intent;

    myBroadcastReceiver myReceiver;

    public void onCreate(){
        try{
            datagramSocket = new DatagramSocket(9000);
            sThread = new serverThread();
            sThread.start();
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }


    private class serverThread extends Thread{
        public void run() {
            try {
                while(true) {
                    byte[] buffer = new byte[1024];
                    serverPacket = new DatagramPacket(buffer, buffer.length);
                    ChatService.datagramSocket.receive(serverPacket);
                    byte[] result = new byte[serverPacket.getLength()];
                    System.arraycopy(serverPacket.getData(), 0, result, 0, serverPacket.getLength());
                    String msg = new String(result);
                    recreateView(msg, serverPacket.getAddress());
                }
            } catch (Exception e) {}
        }
    }

    private void recreateView(String msg, InetAddress address) {

        intent = new Intent(Constant.BROADCAST_ACTION);
        intent.putExtra("message", msg);
        intent.putExtra("senderAddress", address.toString().substring(1, address.toString().length()));

        sendBroadcast(intent);
    }

    public void onDestroy() {
    }

}
