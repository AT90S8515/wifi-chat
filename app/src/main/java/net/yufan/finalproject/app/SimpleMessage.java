package net.yufan.finalproject.app;

/**
 * Created by yufangong on 7/30/14.
 */
public class SimpleMessage {
    public String mSender;
    public String mSenderAddress;
    public String mReceiver;
    public String mReceiverAddress;
    public String mTime;
    public String mContent;


    String separator  = "&";

    public SimpleMessage(){

    }

    public String createMessage(String sender, String senderAddress, String receiver, String receiverAddress, String time, String content){
        mSender = sender;
        mSenderAddress = senderAddress;
        mReceiver = receiver;
        mReceiverAddress = receiverAddress;
        mTime = time;
        mContent = content;
        String newMessage = mSender + separator + mSenderAddress + separator + mTime + separator + mReceiver + separator + mReceiverAddress + separator + mContent;
        return newMessage;
    }

    public SimpleMessage StringToMessage(String messageString){
        String[] message = messageString.split(separator);
        mSender = message[0];
        mSenderAddress = message[1];
        mTime = message[2];
        mReceiver = message[3];
        mReceiverAddress = message[4];
        mContent = message[5];
        return this;
    }
}
