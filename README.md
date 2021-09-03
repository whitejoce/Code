# Code

* 声明：本代码仓库采用MIT许可协议（[The MIT License](https://github.com/whitejoce/Code/blob/main/LICENSE)）

* 这里上传自己平常使用的小工具

  1.[Lifehelper.py](https://github.com/whitejoce/Code/blob/main/README.md#lifehelper)
  
  2.[Software_Install.sh](https://github.com/whitejoce/Code#software_intsall)
  
  3.[encoder.py](https://github.com/whitejoce/Code#Encoder)
 
  4.[format.py](https://github.com/whitejoce/Code#format)

  5.[Spider_Framework.py](https://github.com/whitejoce/Code#Spider_Framework)

 * * *

# Lifehelper

* Lifehelper.py
 
    <p align="left">
      <a href="https://docs.python.org/3/download.html">
        <img src="https://img.shields.io/badge/Python-3.x-green.svg">
      </a>
    </p> 

   > 使用爬虫获取当地天气和新闻
   > 
   > 天气模块可以自定义面板，设置细节：[GetWeather](https://github.com/whitejoce/Get_Weather)
   >
   >要求安装`requests,BeautifulSoup`库
   
  `~$ python Lifehelper.py -[h,w,n] 或 --[help,weather,sw,news]` 
      
      [+]选项：
   
           -h, --help    使用帮助
       
           -w, --weather 获取当地天气
       
           --sw          搜索城市天气
       
           -n, --news    获取实时新闻

* * *

# Software_Intsall
 
 * Software_Install.sh
    
    >此脚本旨在方便下载工具，即快速移植自己的开发环境。
    >
    >版本：v0.1
    >
    >适配系统: Linux（Bash）

 
 * 支持一键下载的工具现有：
       
   >Python3 & pip
   >
   >WAF扫描工具：[wafw00f](https://github.com/EnableSecurity/wafw00f)
   >
   >PE文件扫描工具：[peframe](https://github.com/guelfoweb/peframe)
   >
   >渗透测试工具箱：[Pentest-Toolbox](https://github.com/whitejoce/Pentest_Toolbox)   
   >    
   >爬取当地天气脚本：[Get_Weather](https://github.com/whitejoce/Get_Weather)
   >    
   >MITM WPA攻击工具：[fluxion](https://github.com/FluxionNetwork/fluxion)
   >
   >审计无线网络工具：[airgeddon](https://github.com/v1s1t0r1sh3r3/airgeddon)

   后续更新！
   
   * * *
   
   # format
   
   * format.py
   
       > 生成模板化传参，方便传入脚本参数
   
       需要设置的参数：`filepath`文件路径，`num`每行字符数
       
       生成例子(Base64)：
                
                "TGV0IHVzIGdvIHRoZW4sIHlvdSBhbmQgSSwK6YKj5LmI5oiR5Lus6LWw5ZCn77yM5L2g5oiR5Lik5Liq",
                "5Lq677yMCldoZW4gdGhlIGV2ZW5pbmcgaXMgc3ByZWFkIG91dCBhZ2FpbnN0IHRoZSBza3kK5q2j5b2T",
                "5pyd5aSp56m65oWi5oWi6ZO65bGV552A6buE5piPIOOAggpMaWtlIGEgcGF0aWVudCBldGhlcml6ZWQg",
                "dXBvbiBhIHRhYmxlOwrlpb3kvLznl4XkurrpurvphonlnKjmiYvmnK/moYzkuIo7CkxldCB1cyBnbywg",
                "dGhyb3VnaCBjZXJ0YWluIGhhbGYtZGVzZXJ0ZWQgc3RyZWV0cywK5oiR5Lus6LWw5ZCn77yM56m/6L+H",
                "5LiA5Lqb5Y2K5riF5Ya355qE6KGX77yMClRoZSBtdXR0ZXJpbmcgcmV0cmVhdHMK6YKj5YS/5LyR5oap",
                "55qE5Zy65omA5q2j5Lq65aOw5ZaL5ZaLOwpPZiByZXN0bGVzcyBuaWdodHMgaW4gb25lLW5pZ2h0IGNo",
                "ZWFwIGhvdGVscwrmnInlpJzlpJzkuI3lroHnmoTkuIvnrYnmrYflpJzml4XlupcuCkFuZCBzYXdkdXN0",
                "IHJlc3RhdXJhbnRzIHdpdGggb3lzdGVyLXNoZWxsczoK5ZKM5ruh5Zyw6JqM5aOz55qE6ZO66ZSv5pyr",
                "55qE6aWt6aaGOwpTdHJlZXRzIHRoYXQgZm9sbG93IGxpa2UgYSB0ZWRpb3VzIGFyZ3VtZW50Cuihl+i/",
                "nuedgOihl++8jOWlveWDj+S4gOWcuuiuqOWOjOeahOS6ieiuri4KT2YgaW5zaWRpb3VzIGludGVudArl",
                "uKbnnYDpmLTpmannmoTmhI/lm74uClRvIGxlYWQgeW91IHRvIGFuIG92ZXJ3aGVsbWluZyBxdWVzdGlv",
                "biAuLi4K6KaB5oqK5L2g5byV5ZCR5LiA5Liq6YeN5aSn55qE6Zeu6aKY4oCm4oCmCk9oLCBkbyBub3Qg",
                "YXNrLCDigJxXaGF0IGlzIGl0P+KAnQrllInvvIzkuI3opoHpl67vvIzigJzpgqPmmK/ku4DkuYjvvJ/i",
                "gJ0KTGV0IHVzIGdvIGFuZCBtYWtlIG91ciB2aXNpdC4K6K6p5oiR5Lus5b+r54K55Y675YGa5a6i44CC",
   
   * * *
   
   # Spider_Framework
   
   * Spider_Framework.py
   
     > 提供基础爬虫框架代码
       
       
  # Encoder

  * encoder.py
 
      <p align="left">
         <a href="https://docs.python.org/3/download.html">
             <img src="https://img.shields.io/badge/Python-3.x-green.svg">
           </a>
      </p>
    
     > 此脚本用于处理编码，文字字符格式化问题
     > 均采用Python自带库


   *   使用方法:

       `~$: python encoder.py -h`

       `~$: python encoder.py -f 1.txt --rob13`
     
       [./]选项:
          -h, --help    使用帮助
          -f, --file    导入路径文件内容
          -d, --delete  去除文字中指定字符
          --rob13       Rob13解码


