#!/usr/bin/python
# _*_coding: utf-8 _*_
#Coder:Whitejoce

#import os
#import time
import sys
import re
import requests
import bs4
import json
import urllib as urlparse
import http.client
from lxml import etree
import getopt

headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0'
    }

def Usage():
    print(" [./]选项:")
    print("   -h, --help    使用帮助")
    print("   -w, --weather 获取当地天气")
    print("   --sw          搜索城市天气")
    print("   -n, --news    获取实时新闻")

def getPage(url):
    res = requests.get(url, headers=headers)
    #print(html.text)
    s=res.content
    s.decode('ISO-8859-1')
    bs = bs4.BeautifulSoup(s,"html.parser")
    html = bs.prettify()
    return html

def main():
    try:
        opts, args = getopt.getopt(sys.argv[1:],"hwn",\
["help","weather","news","sw"])
        if not opts and not args:
            Usage()
    except getopt.GetoptError:
        print(" [!] 未知选项")
        Usage()
        sys.exit(-1)
    
    for opt ,arg in opts:
        if opt in ("-h", "--help"):
            Usage()
        elif opt in ("-w", "--weather", "--sw"):
            def get_CityName():
                try:
                    res=requests.post(url='http://ip-api.com/json/?lang=zh-CN', data={'ip': 'myip'},timeout=10)
                    result = res.json()['city']
                except:
                    print(" [!]正在进行网络自检并重试")
                    try:
                        res=requests.post(url='http://ip-api.com/json/?lang=zh-CN', data={'ip': 'myip'},timeout=15)
                        result = res.json()['city']
                    except:
                        print(" [!]无法从相关网站获得请求(请求总时长：25s)，退出脚本")
                        sys.exit(1)
                #print(res.json())
                City = re.findall('(.*?)市',result)
                CityName = "".join(City)
                #CityName = CityName[-2:]
                if len(CityName) ==0:
                        print(' [!] 未自动匹配到你所在地的地区信息:'+result)
                return CityName
            
            def get_city_code(city):
                try:
                    parameter = urlparse.parse.urlencode({'cityname': city})
                    conn = http.client.HTTPConnection('toy1.weather.com.cn', 80, timeout=5)
                    conn.request('GET', '/search?' + parameter)
                    r = conn.getresponse()
                    data = r.read().decode()[1:-1]
                    json_data = json.loads(data)
                    code = json_data[0]['ref'].split('~')[0]
                    #print(code)
                    return code
                except:
                    print(' [!] 错误，未能找到该地区信息')
                    print(" [#] 退出脚本")
                    sys.exit()

            def get_weaPage(url,headers):
                res = requests.get(url, headers=headers)
                #print(html.text)
                s=res.content
                s.decode('ISO-8859-1')
                bs = bs4.BeautifulSoup(s,"html.parser")
                html = bs.prettify()
                return html
            
            def CheckInput(inputstring):
                if any(char.isdigit() for char in inputstring):
                    return True
                match = re.search('[a-zA-Z]+$',inputstring)
                if match:
                    return True
                return False
            
            def GetItem(name,list):
                name = re.findall(r'"'+list+'":"(.*?)"',name)
                name = "".join(name)
                return name

            def get_weather(City_code):
                import time

                headers1 = {
                'Referer' : "http://www.weather.com.cn",
                'Cookie': '',
                'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0'
                }

                #timestamp的长度为13
                #timestamp = ''.join(str(random.choice(range(10))) for i in range(13))
                t = time.time()
                timestamp = str(int(round(t * 1000)))
                port = "http://d1.weather.com.cn/weather_index/"+City_code+".html?_="+timestamp
                html = get_weaPage(port,headers1)
                #print(html)
                wea_list_all= html.split("var")
                #print(wea_list_all)
                temp_port = "http://d1.weather.com.cn/dingzhi/"+City_code+".html?_="+timestamp
                temp_html=get_weaPage(temp_port,headers1)
                #print(temp_html)
                
                #-----------------------------------------------------
                #温度区间:maxtemp,mintemp
                temp_data=re.findall(r'"weatherinfo":{(.*?)}',temp_html)
                temp_data="".join(temp_data)
                temp_json='{'+temp_data+'}'
                temp_json=json.loads(temp_json)
                
                maxtemp=temp_json['temp']
                mintemp=temp_json['tempn']
                
                #alarmDZ
                #-----------------------------------------------------
                wea_list2 = wea_list_all[2]
                wea_alarm_all = re.findall(r'alarmDZ ={"w":\[(.*?)\]};',wea_list2)
                warning = 0
                EmptyList = ['']
                if wea_alarm_all == EmptyList:
                    pass
                else :
                    warning = 1

                #dataSK
                #-----------------------------------------------------
                wea_list3=re.findall(r' dataSK ={(.*?)}',wea_list_all[3])
                wea_list3="".join(wea_list3)
                wea_list3='{'+wea_list3+'}'
                wea_list3_json=json.loads(wea_list3)
                #print(wea_list3_json)
                
                #城市英文
                city_en = wea_list3_json['nameen']
                #城市
                cityname=wea_list3_json['cityname']
                #实时天气
                wea_now=wea_list3_json['weather']
                #当前温度
                temp_now=wea_list3_json['temp']
                #湿度
                wet=wea_list3_json['SD']
                #时间
                update=wea_list3_json['time']
                #空气质量
                aqi=wea_list3_json['aqi']
                #PM2.5
                aqi_pm25=wea_list3_json['aqi_pm25']
                #日期
                date=wea_list3_json['date']
                #-----------------------------------------------------

                #dataZS
                wea_list4=re.findall(r'"zs":{(.*?)}',wea_list_all[4])
                wea_list4="".join(wea_list4)
                wea_list4='{'+wea_list4+'}'
                wea_list4_json=json.loads(wea_list4)
                
                umbrella=wea_list4_json['ys_des_s']


                #和风天气
                headers2 = {
                    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0'
                }
                qwea_url = "https://www.qweather.com/weather/"+city_en+"-"+City_code+".html"
                qwea_html = get_weaPage(qwea_url,headers2)
                #print(qwea_html)
                wea_comment = re.findall(r'<div class="current-abstract">(.*?)</div>',qwea_html,flags=16)
                wea_comment = "".join(wea_comment)
                aqi_level = re.findall(r'<p class="city-air-chart__txt text-center">(.*?)</p>',qwea_html,flags=16)
                aqi_level=aqi_level[0].replace("\n","")
                aqi_level=aqi_level.replace(" ","")
                wea_comment = wea_comment.strip('\n')
                wea_comment = wea_comment.replace(" ","").replace("\n","") 
                
                #-----------------------------------------------------
                print('\n ' + wea_comment)
                
                print(" ==================================")
                print(" 定位城市:  "+cityname)
                print(" 实时天气:  "+wea_now)
                print(" 实时温度:  "+temp_now+"℃")
                print(" 温度区间:  "+maxtemp+" - "+mintemp)
                print(" 空气湿度:  "+wet)
                #0~50优，51~100良，101~150轻度污染，151~200中度污染，201~300重度污染，>300严重污染
                print(" 空气质量:  "+aqi+"("+aqi_level+"),PM2.5: "+aqi_pm25)
                print(" 雨具携带:  "+umbrella)
                print(" [更新时间: "+date+" "+update +"]")
                print(" ==================================")
                if warning==1:
                    wea_alarm_all = "".join(wea_alarm_all)
                    wea_alarm = re.findall(r'"w9":"(.*?)"',wea_alarm_all)
                    #print(wea_alarm)
                    wea_counter=len(wea_alarm)
                    if wea_counter == 1:
                        print(" [!]气象部门发布预警,请注意:")
                    else:
                        print(" [!]气象部门发布"+ str(wea_counter) +"则预警,请注意:")
                    #wea_alarm = "".join(wea_alarm)
                    if wea_alarm == "":
                        print(" [!]无法获取气象预警详情")
                        option = input(" [?]显示完整数据结构?[y/n]")
                        if option=="y" or option=="Y":
                            #wea_alarm = wea_alarm.replace("{","")
                            #wea_alarm = wea_alarm.replace("}","")
                            print(wea_alarm_all)
                    else:
                        i=1
                        for alarm in wea_alarm:
                            alarm=alarm.replace("\\n","")
                            alarm=alarm.replace("：",":\n ",1)
                            if wea_counter==1:
                                print(" "+alarm)
                            else:
                                print(" ["+ str(i) +"]"+alarm)
                                i=i+1

            try:
                address=""
                if opt in ("-w", "--weather"):
                    address = get_CityName()
                if len(address)==0:
                    address = input(" [?] 请手动输入所在地（例：广州）[输入为空即退出]：")
                    if address=="":
                        print(" [#] 退出脚本")
                        sys.exit()
                    else:
                        if CheckInput(address)==1:
                            print(" [!]检测非地名字符，退出脚本")
                            sys.exit()
                        else:    
                            print(" [+] 使用手动输入定位位置："+address)
                else:    
                    print(" [+] 自动定位位置："+address) 
                code = get_city_code(address)
                try:
                    get_weather(code)
                except:
                    print(' [!] 未能找到该地区的天气信息')
                    print(" [#] 退出脚本")
                    sys.exit()
            except Exception as Error:
                raise Error

        elif opt in ("-n", "--news"):
            url="https://top.baidu.com/board?tab=realtime"
            html=getPage(url)
            #print(html)
            selector=etree.HTML(html)
            print("[+] 获取新闻(来自:" + url +"):\n" )
            for i in range(1,31):
                s=selector.xpath('//*[@id="sanRoot"]/main/div[2]/div/div[2]/div['+str(i)+']/div[2]/a/div[1]/text()')
                temp=s.pop().strip()
                print("    "+temp)

if __name__=="__main__":
     main()
