package com.example.newweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ListViewAdapter(var context: Context, var arrayList: ArrayList<WeatherBean>) : BaseAdapter(){
    class ViewHolder{
        var tvDate: TextView? = null
        var tvLow: TextView? = null
        var tvHigh: TextView? = null
        var weatherType: TextView? = null
    }
    
    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(i: Int, p1: View?, p2: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (p1 == null){
            view = LayoutInflater.from(context).inflate(R.layout.line_item, p2, false)
            viewHolder = ViewHolder()
            viewHolder.tvDate = view.findViewById(R.id.tvDate)
            viewHolder.tvLow = view.findViewById(R.id.tvLow)
            viewHolder.tvHigh = view.findViewById(R.id.tvHigh)
            viewHolder.weatherType = view.findViewById(R.id.weatherType)
            view.tag = viewHolder
        }else{
            view = p1
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.tvDate?.text = arrayList[i].date
        viewHolder.tvLow?.text = arrayList[i].low
        viewHolder.tvHigh?.text = arrayList[i].high
        viewHolder.weatherType?.text = arrayList[i].status
        var date = arrayList[i].date
        var month = date.substring(4, 6)
        var day = date.substring(6, 8)
        if (month[0] == '0') month = String.format(" %s", month.substring(1))
        if (day[0] == '0') day = String.format(" %s", day.substring(1))
        date = month + '月' + day + '日'

        viewHolder.tvDate!!.text = date
        viewHolder.tvHigh!!.text = String.format("%s °C", arrayList[i].high)
        viewHolder.tvLow!!.text = String.format("%s °C", arrayList[i].low)
        viewHolder.weatherType!!.text = arrayList[i].status
        return view!!
    }
}