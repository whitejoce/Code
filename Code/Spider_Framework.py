#!/usr/bin/python
# _*_coding: utf-8 _*_
#Coder:Whitejoce

import sys
import time

'''//Request//'''
#from fake_useragent import UserAgent
import requests
#import urllib as urlparse
#import http.client

'''//Modify//'''
import json
import re

'''//Framework//'''
import bs4
#import scrapy

'''
#header:
    'Referer' : '',
    'Cookie' : '',
'''
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0'
    }

def timestamp(length):
    t = time.time()
    #10(s)
    if length==10:
        t=int(t)
    #13(ms)
    elif length==13:
        t= str(int(round(t * 1000)))
    else:
        print(" [!] 时间戳长度设置错误(10/13)")
    return t

def get_html(url,headers):
    res = requests.get(url, headers=headers)
    ##print(res.text)
    s=res.content
    s.decode('ISO-8859-1')
    bs = bs4.BeautifulSoup(s,"html.parser")
    html = bs.prettify()
    return html

def json_process(html):
    pattern = r''
    result = re.findall(pattern, html)[0]
    temp = json.loads(result)
    return temp

'''
re:
    re.findall('(.*?)',foo)
    re.findall('(.*?)',foo,flags=16)
process:
    .replace("","")
    .strip('') #str to list
    "".join(foo) #list to str
'''

if __name__ == '__main__':
    try:
        url=""
        html=get_html(url,headers=headers)
        print(html)
        #container=json_process(html)
        #print(container)

    except Exception as Error:
        raise Error