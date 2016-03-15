//package net.yufan.finalproject.app;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * Created by yufangong on 7/30/14.
// */
//public class Message implements Parcelable {
//    private String mSender;
//    private String mTime;
//    private String mContent;
//    private String mSeparator = "&";
//
//    public int describeContents() {
//        return 0;
//    }
//
//    public void writeToParcel(Parcel out, int flags) {
//        out.writeString(mSender);
//        out.writeString(mTime);
//        out.writeString(mContent);
//    }
//
//    public static final Parcelable.Creator<Message> CREATOR
//            = new Parcelable.Creator<Message>() {
//        public Message createFromParcel(Parcel in) {
//            return new Message(in);
//        }
//
//        public Message[] newArray(int size) {
//            return new Message[size];
//        }
//    };
//
//    private Message(String sender, String time, String content){
//        mTime = time;
//        if(time == null){
//            Date now =  new Date();
//            mTime = new SimpleDateFormat("h:mm a").format(now);
//        }
//        mSender = sender;
//        mContent = content;
//    }
//
//    private Message(Parcel in) {
//        mData = in.readInt();
//    }
//
//    private String messageToString(){
//        return mSender + mSeparator + mTime + mSeparator + mContent;
//    }
//}