package com.example.newweather

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    var weatherList = ArrayList<WeatherBean>()
    private fun getNextMonthWeather(cityId:String){
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val formatdate = SimpleDateFormat("yyyyMMdd")
        val year = formatdate.format(Date()).substring(0,4)
        val month = formatdate.format(Date()).substring(4,6)

        val nextMonth = if(month.toInt() == 12) 1 else month.toInt() + 1
        val nextYear = if(month.toInt() == 12) year.toInt() + 1 else year.toInt()

        val fc15url = buildString {
            append("http://d1.weather.com.cn/calendar_new/")
            append(nextYear)
            append("/")
            append(cityId)
            append("_")
            append(nextYear)
            append(nextMonth)
            append(".html?_=")
            append(timeStamp)
        }
        val okHttpClient = OkHttpClient()
        val requestBuilder = Request.Builder()
            .url(fc15url)
            .get()
            .addHeader("Referer", "http://www.weather.com.cn")
            .addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
        okHttpClient.newCall(requestBuilder.build()).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body.string() + ";"
                val fc15 =Regex("fc40 = (.*?);").find(res)?.groupValues?.get(1).toString()
                val pattrern= Pattern.compile("\\{.*?\\}")
                val matcher = pattrern.matcher(fc15)

                var counter = weatherList.size
                val lastDate = weatherList[counter - 1].date
                
                while (matcher.find()&&counter<15){
                    val temp = matcher.group()
                    val tempJson = JSONObject(temp)

                    if (tempJson.getString("w1") == "") continue
                    if (lastDate.toInt()>=tempJson.getString("date").toInt()) continue
                    weatherList.add(WeatherBean(tempJson.getString("date"), tempJson.getString("w1"), tempJson.getString("max"), tempJson.getString("min")))
                    counter++
                }
                runOnUiThread {
                    val adapter = ListViewAdapter(this@MainActivity, weatherList)
                    val listView = findViewById<ListView>(R.id.weatherList)
                    listView.adapter = adapter
                    Toast.makeText(
                        this@MainActivity,
                        "数据更新成功",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun getFC15Weather(cityId:String) {
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val formatdate = SimpleDateFormat("yyyyMMdd")
        val year = formatdate.format(Date()).substring(0,4)
        val month = formatdate.format(Date()).substring(4,6)

        val fc15url = buildString {
        append("http://d1.weather.com.cn/calendar_new/")
        append(year)
        append("/")
        append(cityId)
        append("_")
        append(year)
        append(month)
        append(".html?_=")
        append(timeStamp)
        }
        val okHttpClient = OkHttpClient()
        val requestBuilder = Request.Builder()
            .url(fc15url)
            .get()
            .addHeader("Referer", "http://www.weather.com.cn")
            .addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
        okHttpClient.newCall(requestBuilder.build()).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body.string() + ";"
                val fc15 =Regex("fc40 = (.*?);").find(res)?.groupValues?.get(1).toString()
                val pattrern= Pattern.compile("\\{.*?\\}")
                val matcher = pattrern.matcher(fc15)
                val fc15list = ArrayList<WeatherBean>()
                var counter=0
                while (matcher.find()){
                    val temp = matcher.group()
                    val tempJson = JSONObject(temp)

                    if (tempJson.getString("w1") == "") continue
                    fc15list.add(WeatherBean(tempJson.getString("date"), tempJson.getString("w1"), tempJson.getString("max"), tempJson.getString("min")))
                    counter++
                }
                weatherList=fc15list
                if (counter<15) {
                    getNextMonthWeather(cityId)
                }
                else {
                    runOnUiThread {
                        val adapter = ListViewAdapter(this@MainActivity, weatherList)
                        val listView = findViewById<ListView>(R.id.weatherList)
                        listView.adapter = adapter
                        Toast.makeText(
                            this@MainActivity,
                            "数据更新成功",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        })
    }


    private fun setMainCityWeather() {

        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val okHttpClient = OkHttpClient()
        val requestBuilder = Request.Builder()
            .url("http://wgeo.weather.com.cn/ip/?_=$timeStamp")
            .get()
            .addHeader("Referer", "http://www.weather.com.cn")
            .addHeader("User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0")
        okHttpClient.newCall(requestBuilder.build()).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                Log.d("error",e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body.string()
                //正则匹配id
                val id = Regex("id=\"(.*?)\"").find(res)?.groupValues?.get(1).toString()
                if (id == "") {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "获取天气失败", Toast.LENGTH_LONG).show()
                    }
                    return
                }else {
                    val fc15requestBuilder = Request.Builder()
                        .url("http://d1.weather.com.cn/sk_2d/$id.html?_=$timeStamp")
                        .get()
                        .addHeader("Referer", "http://www.weather.com.cn")
                        .addHeader(
                            "User-Agent",
                            "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0"
                        )
                    okHttpClient.newCall(fc15requestBuilder.build()).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.d("error", e.toString())
                        }

                        override fun onResponse(call: Call, response: Response) {
                            var weaResponse = response.body.string()+";"
                            /*
                            var dataSK={"nameen":"hangzhou","cityname":"杭州","city":"101210101","temp":"22","tempf":"71",\
                            "WD":"东风","wde":"E","WS":"2级","wse":"7km\/h","SD":"67%","sd":"67%","qy":"1012","njd":"25km",\
                            "time":"09:25","rain":"0","rain24h":"0","aqi":"37","aqi_pm25":"37","weather":"多云",\
                            "weathere":"Cloudy","weathercode":"d01","limitnumber":"3和7","date":"09月21日(星期三)"}
                            */
                            Log.d("res",weaResponse)
                            weaResponse=Regex("dataSK=(.*?);").find(weaResponse)?.groupValues?.get(1).toString()

                            val weatherJson = JSONObject(weaResponse)
                            val weather = weatherJson["weather"].toString()
                            val temp = weatherJson["temp"].toString()
                            val updateTime = weatherJson["time"].toString()
                            /*
                            val SD = weatherJson["SD"].toString()
                            val WD = weatherJson["WD"].toString()
                            val WS = weatherJson["WS"].toString()
                            */
                            val city = weatherJson["cityname"].toString()
                            //Log.d("res", weather + temp + updateTime + city + id)
                            getFC15Weather(id)

                            runOnUiThread {
                                val cityName = findViewById<TextView>(R.id.cityName)
                                val cityTemp = findViewById<TextView>(R.id.cityTemp)
                                val weatherType = findViewById<TextView>(R.id.weatherType)
                                val update = findViewById<TextView>(R.id.updateTime)
                                cityName.text=city
                                cityTemp.text=temp
                                weatherType.text=weather
                                update.text= buildString {
                                    append("更新于: ")
                                    append(updateTime)
                                }
                            }
                        }

                    })
                }
            }
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setMainCityWeather()

        /*
        val listView = findViewById<ListView>(R.id.weatherList)
        val list = ArrayList<WeatherBean>()
        list.add(WeatherBean("2-17", "晴", "27", "21"))
        list.add(WeatherBean("2-18", "雨", "25", "23"))
        list.add(WeatherBean("2-19", "雪", "-1", "-9"))
        list.add(WeatherBean("2-20", "晴", "27", "21"))
        list.add(WeatherBean("2-21", "雨", "25", "23"))
        list.add(WeatherBean("2-22", "晴", "-1", "-9"))
        list.add(WeatherBean("2-23", "晴", "27", "21"))
        list.add(WeatherBean("2-24", "雨", "25", "23"))
        list.add(WeatherBean("2-25", "晴", "-1", "-9"))
        list.add(WeatherBean("2-26", "晴", "27", "21"))
        list.add(WeatherBean("2-27", "雨", "25", "23"))
        list.add(WeatherBean("2-28", "晴", "-1", "-9"))
        val listViewAdapter = ListViewAdapter(this, list)
        listView.adapter = listViewAdapter
        */


    }
}