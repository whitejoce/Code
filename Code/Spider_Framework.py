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
from lxml import etree
#import scrapy

'''
#header:
    'Referer' : '',
    'Cookie' : '',
'''
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.0'
    }

'''
HTTP Request BEGIN
'''

def httpAsk(method,url):
    if method=="head":
        try:
            response = requests.head(url)
            print(response.headers)
        except Exception as Error:
            raise Error
    elif method=="options":
        try:
            response = requests.options(url)
            print(response.headers)
        except Exception as Error:
            raise Error

'''
END
'''

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
    bs = bs4.BeautifulSoup(res.text,"lxml")
    #html = bs.prettify()
    return bs

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
        '''
        #XPath
        selector = etree.HTML(html,etree.HTMLParser(encoding='utf-8'))
        selector.xpath()
        '''

    except Exception as Error:
        raise Error
