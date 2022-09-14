#!/usr/bin/env python
# _*_coding: utf-8 _*_
# Coder:Whitejoce

import base64
import time
from concurrent.futures import ThreadPoolExecutor, as_completed

import bs4
import pymysql
import requests
from lxml import etree
from pyquery import PyQuery as pq

url = "https://www.bilibili.com/v/popular/rank/all"
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:66.0) Gecko/20100101 Firefox/66.1',
    'Connection': 'close'
}

mysql_conn = pymysql.connect(host='localhost', port=3306, user='root',
                             passwd=base64.b64decode(b"MTIzNDU2").decode("utf-8"), db='testdb', charset='gbk')


def get_html(url, headers):
    res = requests.get(url, headers=headers)
    # print(res.status_code)
    s = res.content
    s.decode('ISO-8859-1')
    bs = bs4.BeautifulSoup(s, "html.parser")
    html = bs.prettify()
    return html


def getList(li):
    videoDict = dict()
    for i in li:
        name = i.xpath('div/div[2]/a/text()').pop().strip()
        link = "http:"+i.xpath('div/div[2]/a/@href').pop().strip()
        videoHtml = get_html(link, headers)
        time.sleep(3)

        res = pq(videoHtml)
        desc = res('#v_desc .desc-info').text()
        videoList = [link, desc]
        videoDict[name] = videoList
        print(name+" "+videoDict[name][0]+"\n"+videoDict[name][1])

    return videoDict


def threadJob(tag):
    videoDict = dict()
    name = i.xpath('div/div[2]/a/text()').pop().strip()
    link = "http:"+i.xpath('div/div[2]/a/@href').pop().strip()
    videoHtml = get_html(link, headers)
    time.sleep(3)

    res = pq(videoHtml)
    desc = res('#v_desc .desc-info').text()
    videoList = [link, desc]
    videoDict[name] = videoList
    print(name+" "+videoDict[name][0]+"\n"+videoDict[name][1])
    return videoDict


def ThreadPool_getList(li):
    print('\n-----start-----\n')
    videoDict = dict()
    with ThreadPoolExecutor(max_workers=3) as pool:
        task_list = []
        for i in li:
            res = pool.submit(threadJob, i)
            task_list.append(res)

        for future in as_completed(task_list):
            data = future.result()
            videoDict.update(data)
    print('\n-----finish-----\n')
    return videoDict


if __name__ == '__main__':
    try:
        html = get_html(url, headers)
        time.sleep(3)
        selector = etree.HTML(html)
        li = selector.xpath('//*[@id="app"]/div/div[2]/div[2]/ul/li')

        lists = getList(li)
        #调用线程池，速度更快(注释上面的getList()，取消下面的注释)
        # lists=ThreadPool_getList(li)
        for i in lists:
            print(i, lists[i][0]+"\n"+lists[i][1])

        try:
            with mysql_conn.cursor() as cursor:
                cursor.execute(
                    'create table if not exists news(name varchar(20),url varchar(50),desc varchar(300))')
                for i in lists:
                    videoName, videoURL, desc = i, lists[i][0], lists[i][1]
                cursor.execute('insert into news values(' +
                               videoName+','+videoURL+','+desc+');')
            mysql_conn.commit()
        except Exception as Error:
            mysql_conn.rollback()
            raise Error
        finally:
            mysql_conn.close()

    except Exception as Error:
        raise Error
