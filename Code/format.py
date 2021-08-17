#!/usr/bin/python
# _*_coding: utf-8 _*_
#Coder:Whitejoce

filepath=""

with open(filepath,'r') as f:
    content=f.read()
    #print(content)
    i=0
    num=80
    while i<len(content):
        word=content[i:i+num]
        print("\t\t\""+ word + "\",") #格式
        i=i+num