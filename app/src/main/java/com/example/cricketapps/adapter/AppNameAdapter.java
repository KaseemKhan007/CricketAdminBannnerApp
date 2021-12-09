package com.example.cricketapps.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cricketapps.AppModel;
import com.example.cricketapps.R;

import java.util.List;

public class AppNameAdapter extends ArrayAdapter<AppModel> {

    LayoutInflater flater;

    public AppNameAdapter(Activity context, int resouceId, int textviewId, List<AppModel> list){
        super(context,resouceId,textviewId, list);
//        flater = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return rowview(convertView,position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return rowview(convertView,position);
    }

    private View rowview(View convertView , int position){

        AppModel rowItem = getItem(position);

        viewHolder holder ;
        View rowview = convertView;
        if (rowview==null) {

            holder = new viewHolder();
            flater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowview = flater.inflate(R.layout.item_app_name, null, false);

            holder.tvAppName = (TextView) rowview.findViewById(R.id.tvAppName);
            rowview.setTag(holder);
        }else{
            holder = (viewHolder) rowview.getTag();
        }

        holder.tvAppName.setText(rowItem.getAppName());

        return rowview;
    }

    private class viewHolder{
        TextView tvAppName;
    }
}