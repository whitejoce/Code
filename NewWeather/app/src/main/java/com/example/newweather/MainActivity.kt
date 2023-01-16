package com.example.newweather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import okhttp3.*
import okio.IOException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    var weatherList = ArrayList<WeatherBean>()

    private fun getNextMonthWeather(cityId: String) {
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val formatDate = SimpleDateFormat("yyyyMMdd", Locale.CHINA)

        val year = formatDate.format(Date()).substring(0, 4)
        val month = formatDate.format(Date()).substring(4, 6)

        val nextMonth: String
        val nextYear: String
        if (month == "12") {
            nextMonth = "01"
            nextYear = (year.toInt() + 1).toString()
        } else {
            nextMonth = (month.toInt() + 1).toString()
            nextYear = year
        }

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
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0"
            )
        okHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(
                    this@MainActivity,
                    "15天数据更新失败",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body.string() + ";"
                val fc15 = Regex("fc40 = (.*?);").find(res)?.groupValues?.get(1).toString()
                val pattrern = Pattern.compile("\\{.*?\\}")
                val matcher = pattrern.matcher(fc15)

                var counter = weatherList.size
                val lastDate = weatherList[counter - 1].date

                while (matcher.find() && counter < 15) {
                    val temp = matcher.group()
                    val tempJson = JSONObject(temp)

                    if (tempJson.getString("w1") == "") continue
                    if (lastDate.toInt() >= tempJson.getString("date").toInt()) continue
                    weatherList.add(
                        WeatherBean(
                            tempJson.getString("date"),
                            tempJson.getString("w1"),
                            tempJson.getString("max"),
                            tempJson.getString("min")
                        )
                    )
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

    private fun getFC15Weather(cityId: String) {
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        val formatDate = SimpleDateFormat("yyyyMMdd", Locale.CHINA)
        val year = formatDate.format(Date()).substring(0, 4)
        val month = formatDate.format(Date()).substring(4, 6)

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
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0"
            )
        okHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(
                    this@MainActivity,
                    "15天数据更新失败",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body.string() + ";"
                val fc15 = Regex("fc40 = (.*?);").find(res)?.groupValues?.get(1).toString()
                val pattrern = Pattern.compile("\\{.*?\\}")
                val matcher = pattrern.matcher(fc15)
                val fc15list = ArrayList<WeatherBean>()
                var counter = 0
                while (matcher.find()) {
                    val temp = matcher.group()
                    val tempJson = JSONObject(temp)

                    if (tempJson.getString("w1") == "") continue
                    fc15list.add(
                        WeatherBean(
                            tempJson.getString("date"),
                            tempJson.getString("w1"),
                            tempJson.getString("max"),
                            tempJson.getString("min")
                        )
                    )
                    counter++
                }
                weatherList = fc15list
                if (counter < 15) {
                    getNextMonthWeather(cityId)
                } else {
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


    private fun setMainCityWeather(cityId: String) {
        val timeStamp = (System.currentTimeMillis() / 1000).toString()
        if (cityId == "") {
            val okHttpClient = OkHttpClient()
            val requestBuilder = Request.Builder()
                .url("http://wgeo.weather.com.cn/ip/?_=$timeStamp")
                .get()
                .addHeader("Referer", "http://www.weather.com.cn")
                .addHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0"
                )
            okHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(
                        this@MainActivity,
                        "主城市数据更新失败",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("error", e.toString())
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
                    } else {
                        getMainCityWeather(id, timeStamp)
                    }
                }
            })
        } else {
            getMainCityWeather(cityId, timeStamp)
        }
    }

    private fun getMainCityWeather(id: String, timeStamp: String) {
        val okHttpClient = OkHttpClient()
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
                var weaResponse = response.body.string() + ";"
                /*
                var dataSK={"nameen":"hangzhou","cityname":"杭州","city":"101210101","temp":"22","tempf":"71",\
                "WD":"东风","wde":"E","WS":"2级","wse":"7km\/h","SD":"67%","sd":"67%","qy":"1012","njd":"25km",\
                "time":"09:25","rain":"0","rain24h":"0","aqi":"37","aqi_pm25":"37","weather":"多云",\
                "weathere":"Cloudy","weathercode":"d01","limitnumber":"3和7","date":"09月21日(星期三)"}
                */
                Log.d("res", weaResponse)
                weaResponse =
                    Regex("dataSK=(.*?);").find(weaResponse)?.groupValues?.get(1).toString()

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
                    if (cityName.text == "") {
                        cityName.text = "${city}市"
                    }
                    cityTemp.text = temp
                    weatherType.text = weather
                    update.text = buildString {
                        append("更新于 ")
                        append(updateTime)
                    }
                }
            }
        })
    }

    private fun getLocation(latitude: String, longitude: String) {
        val okHttpClient = OkHttpClient()
        var requestBuilder = Request.Builder()
            .url("http://api.map.baidu.com/geocoder?location=$latitude,$longitude&output=json")
            .get()
            .addHeader(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0"
            )
        okHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //Log.d("error", e.toString())
                setMainCityWeather("")
            }

            override fun onResponse(call: Call, response: Response) {
                val res = response.body.string()
                val locationJson = JSONObject(res)
                var city = locationJson.getJSONObject("result").getJSONObject("addressComponent")
                    .getString("city")
                runOnUiThread {
                    val cityName = findViewById<TextView>(R.id.cityName)
                    cityName.text = city
                }
                city = city.substring(0, city.length - 1)
                requestBuilder = Request.Builder()
                    .url("http://toy1.weather.com.cn/search?cityname=$city")
                    .get()
                    .addHeader("Referer", "http://www.weather.com.cn")
                    .addHeader(
                        "User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0"
                    )
                okHttpClient.newCall(requestBuilder.build()).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("error", e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val result = response.body.string()
                        val cityId =
                            Regex("ref\":\"(.*?)~").find(result)?.groupValues?.get(1).toString()
                        setMainCityWeather(cityId)
                    }
                })
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val positionManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            runOnUiThread {
                Toast.makeText(this, "请开启定位权限", Toast.LENGTH_LONG).show()
            }
            //setMainCityWeather()
        } else {
            val location = positionManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                getLocation(latitude.toString(), longitude.toString())
            } else {
                setMainCityWeather("")
            }
        }
    }
}