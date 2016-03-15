package net.yufan.finalproject.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yufangong on 8/3/14.
 */
final class ChatMessageAdapter extends ArrayAdapter<SimpleMessage> {

    public static final int VIEW_TYPE_MYMSG = 0;
    public static final int VIEW_TYPE_INMSG = 1;
    public static final int VIEW_TYPE_COUNT = 2;    // msg sent by me, or all incoming msgs
    private LayoutInflater mInflater;

    public ChatMessageAdapter(Context context, List<SimpleMessage> objects){
        super(context, 0, objects);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        SimpleMessage item = this.getItem(position);
        if ( item.mSenderAddress.equals(HomeFragment.myAddress )){
            return VIEW_TYPE_MYMSG;
        }
        return VIEW_TYPE_INMSG;
    }

    /**
     * assemble each row view in the list view.
     * http://dl.google.com/googleio/2010/android-world-of-listview-android.pdf
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;  // old view to re-use if possible. Useful for Heterogeneous list with diff item view type.
        SimpleMessage item = this.getItem(position);
        boolean mymsg = false;

        if ( getItemViewType(position) == VIEW_TYPE_MYMSG){
            if( view == null ){
                view = mInflater.inflate(R.layout.send_msg, null);  // inflate chat row as list view row.
            }
            mymsg = true;
            // view.setBackgroundResource(R.color.my_msg_background);
        } else {
            if( view == null ){
                view = mInflater.inflate(R.layout.receive_msg, null);  // inflate chat row as list view row.
            }
            // view.setBackgroundResource(R.color.in_msg_background);
        }

        TextView sender = (TextView)view.findViewById(R.id.sender);
        sender.setText(item.mSender);

        TextView msgRow = (TextView)view.findViewById(R.id.messageContent);
        msgRow.setText(item.mContent);


        TextView time = (TextView)view.findViewById(R.id.time);
        time.setText(item.mTime);

//            if( mymsg ){
//                msgRow.setBackgroundResource(R.color.my_msg_background);
//            }else{
//                msgRow.setBackgroundResource(R.color.in_msg_background);
//            }

        return view;
    }
}
