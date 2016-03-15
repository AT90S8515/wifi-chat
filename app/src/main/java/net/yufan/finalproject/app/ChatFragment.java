package net.yufan.finalproject.app;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yufangong on 7/29/14.
 */
public class ChatFragment extends ListFragment{

    public static ArrayList<SimpleMessage> mMessageList;
    public static ArrayAdapter<SimpleMessage> mListAdapter;


    public static ChatFragment newInstance (String myName, String myAddress, String deviceName, String deviceAddress){

        ChatFragment fragment = new ChatFragment();

        Bundle args = new Bundle();
        args.putString(Constant.PEER_NAME, deviceName);
        args.putString(Constant.PEER_ADDRESS, deviceAddress);
        args.putString(Constant.MY_NAME, myName);
        args.putString(Constant.MY_ADDRESS, myAddress);
        fragment.setArguments(args);

        return fragment;
    }



    public interface OnSendClicked{
        public void onSendClicked(String message, String address, boolean broadcast);
    }

    OnSendClicked mCallback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        //setHasOptionsMenu(true);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try {
            mCallback = (OnSendClicked) activity;
        }
        catch (ClassCastException e){
            throw new ClassCastException(activity.toString()
                    + "must implement OnSendListener");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("MESSAGE_LIST", messageList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);

        final String device_name = getArguments().getString(Constant.PEER_NAME);
        final String device_address = getArguments().getString(Constant.PEER_ADDRESS);
        final String my_name = getArguments().getString(Constant.MY_NAME);
        final String my_address = getArguments().getString(Constant.MY_ADDRESS);
        mMessageList = new ArrayList<SimpleMessage>();
        mListAdapter = new ChatMessageAdapter(getActivity(), mMessageList);

        setListAdapter(mListAdapter);


        final EditText editing_message = (EditText) rootView.findViewById(R.id.edit_message);
        Button btn_send = (Button) rootView.findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editing_message.getText().toString().matches("")){

                    Date now = new Date();
                    String time = new SimpleDateFormat("h:mm a").format(now);

                    SimpleMessage simpleMessage = new SimpleMessage();
                    String message = simpleMessage.createMessage(my_name, my_address, device_name, device_address, time, editing_message.getText().toString());

                    mCallback.onSendClicked(message, device_address, true);
                    appendChatMessage(simpleMessage);
                    editing_message.setText("");
                }
                else{
                    Toast toast = Toast.makeText(getActivity(), "Cannot send blank message.", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });


        return rootView;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//
//        if( messageList.size() > 0){
//            getListView().smoothScrollToPosition(messageList.size()-1);
//        }
        setHasOptionsMenu(true);
    }

    public void appendChatMessage(SimpleMessage simpleMessage) {
        mMessageList.add(simpleMessage);
        getListView().smoothScrollToPosition(mMessageList.size()-1);
        mListAdapter.notifyDataSetChanged();  // notify the attached observer and views to refresh.
        return;
    }


//    final class ChatMessageAdapter extends ArrayAdapter<SimpleMessage> {
//
//        public static final int VIEW_TYPE_MYMSG = 0;
//        public static final int VIEW_TYPE_INMSG = 1;
//        public static final int VIEW_TYPE_COUNT = 2;    // msg sent by me, or all incoming msgs
//        private LayoutInflater mInflater;
//
//        public ChatMessageAdapter(Context context, List<SimpleMessage> objects){
//            super(context, 0, objects);
//            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        }
//
//        @Override
//        public int getViewTypeCount() {
//            return VIEW_TYPE_COUNT;
//        }
//
//        @Override
//        public int getItemViewType(int position) {
//            SimpleMessage item = this.getItem(position);
//            if ( item.mSenderAddress.equals(HomeFragment.myAddress )){
//                return VIEW_TYPE_MYMSG;
//            }
//            return VIEW_TYPE_INMSG;
//        }
//
//        /**
//         * assemble each row view in the list view.
//         * http://dl.google.com/googleio/2010/android-world-of-listview-android.pdf
//         */
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = convertView;  // old view to re-use if possible. Useful for Heterogeneous list with diff item view type.
//            SimpleMessage item = this.getItem(position);
//            boolean mymsg = false;
//
//            if ( getItemViewType(position) == VIEW_TYPE_MYMSG){
//                if( view == null ){
//                    view = mInflater.inflate(R.layout.send_msg, null);  // inflate chat row as list view row.
//                }
//                mymsg = true;
//                // view.setBackgroundResource(R.color.my_msg_background);
//            } else {
//                if( view == null ){
//                    view = mInflater.inflate(R.layout.receive_msg, null);  // inflate chat row as list view row.
//                }
//                // view.setBackgroundResource(R.color.in_msg_background);
//            }
//
//            TextView sender = (TextView)view.findViewById(R.id.sender);
//            sender.setText(item.mSender);
//
//            TextView msgRow = (TextView)view.findViewById(R.id.messageContent);
//            msgRow.setText(item.mContent);
////            if( mymsg ){
////                msgRow.setBackgroundResource(R.color.my_msg_background);
////            }else{
////                msgRow.setBackgroundResource(R.color.in_msg_background);
////            }
//
//            TextView time = (TextView)view.findViewById(R.id.time);
//            time.setText(item.mTime);
//
//            return view;
//        }
//    }


}
