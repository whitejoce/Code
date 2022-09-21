package com.example.newweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    static class ViewHolder{
        TextView tvDate;
        TextView tvHigh;
        TextView tvLow;
        TextView weatherType;
        //mageView iv;
    }
    Context context;
    ArrayList<WeatherBean> arrayList;

    public ListViewAdapter(Context context, ArrayList<WeatherBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        if (view==null){
            view=layoutInflater.inflate(R.layout.line_item,viewGroup,false);
            viewHolder= new ViewHolder();
            viewHolder.tvDate=view.findViewById(R.id.tvDate);
            viewHolder.tvHigh=view.findViewById(R.id.tvHigh);
            viewHolder.tvLow=view.findViewById(R.id.tvLow);
            viewHolder.weatherType=view.findViewById(R.id.weatherType);
            //viewHolder.iv=view.findViewById(R.id.weatherType);
            view.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) view.getTag();
        }
        String date = arrayList.get(i).getDate();
        date = date.substring(4,6)+"-"+date.substring(6,8);
        viewHolder.tvDate.setText(date);
        viewHolder.tvHigh.setText(arrayList.get(i).getHigh()+" °C");
        viewHolder.tvLow.setText(arrayList.get(i).getLow()+" °C");
        viewHolder.weatherType.setText(arrayList.get(i).getStatus());

        /*
        添加天气图片
        WeatherBean weatherBean=arrayList.get(i);
        String t=weatherBean.getStatus();
        switch (t) {
            case "雨":
                viewHolder.iv.setImageResource(R.drawable.rain);
                break;
            case "晴":
                viewHolder.iv.setImageResource(R.drawable.sun);
                break;
            case "雪":
                viewHolder.iv.setImageResource(R.drawable.snow);
                break;
            default:
                viewHolder.iv.setImageResource(R.drawable.snow);
                //throw new IllegalStateException("Unexpected value: " + t);
        }
         */
        return view;
    }
}
