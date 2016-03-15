package net.yufan.finalproject.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by yufangong on 7/29/14.
 */
public class DevicesAdapter extends BaseAdapter {
    private final Context context;
    private final List<Map<String, ?>> DevicesList;

    public DevicesAdapter(Context context, List<Map<String, ?>> devicesList){
        this.context=context;
        DevicesList = devicesList;
    }

    @Override
    public int getCount() {
        return DevicesList.size();
    }

    @Override
    public Object getItem(int position) {
        return DevicesList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = getView_basic(position, view, parent);
        return rowView;
    }


    View getView_basic(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_row, parent, false);

        TextView device_name = (TextView) rowView.findViewById(R.id.device_name);
        TextView device_address = (TextView) rowView.findViewById(R.id.device_address);

        Map<String, ?> entry = DevicesList.get(position);
        device_name.setText((String) entry.get("deviceName"));


        device_address.setText((String) entry.get("deviceAddress"));

        return rowView;
    }

//    View getView_recycling(int position, View view, ViewGroup parent){
//        View rowView;
//        ViewHolder viewHolder = null;
//
//        if(view == null){
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            rowView = inflater.inflate(R.layout.each_listrow, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.movie_title = (TextView) rowView.findViewById(R.id.movie_title);
//            viewHolder.year = (TextView) rowView.findViewById(R.id.year);
//            viewHolder.rating = (TextView) rowView.findViewById(R.id.rating);
//            viewHolder.length = (TextView) rowView.findViewById(R.id.length);
//            viewHolder.director = (TextView) rowView.findViewById(R.id.director);
//            viewHolder.actor = (TextView) rowView.findViewById(R.id.actor);
//            viewHolder.movie_image = (ImageView) rowView.findViewById(R.id.movie_image);
//            //viewHolder.selection = (CheckBox) rowView.findViewById(R.id.selection);
//            rowView.setTag(viewHolder);
//        }
//        else{
//            rowView = view;
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        Map<String, ?> entry = movieList.get(position);
//
//        if(position%2 == 0){
//            viewHolder.movie_title.setTypeface(Typeface.create("serial", 3));
//        }
//        else {
//            viewHolder.movie_title.setTypeface(Typeface.create("serial", 1));
//
//        }
//        viewHolder.movie_title.setText((String) entry.get("name"));
//        viewHolder.year.setText((String) entry.get("year"));
//        viewHolder.rating.setText(entry.get("rating").toString());
//        viewHolder.length .setText((String) entry.get("length"));
//        viewHolder.director.setText("Directors: " + entry.get("director"));
//        viewHolder.actor.setText("Stars: " + entry.get("stars"));
//        viewHolder.movie_image.setImageResource((Integer) entry.get("image"));
//        //viewHolder.selection.setChecked((Boolean) entry.get("selection"));
//        return rowView;
//
//    }

//    class ViewHolder{
//        TextView movie_title;
//        TextView movie_description;
//        ImageView movie_image;
//        TextView year;
//        TextView rating;
//        TextView length;
//        TextView director;
//        TextView actor;
//        CheckBox selection;
//    }
}

