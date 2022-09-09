#!/bin/bash
#Coder: Whitejoce

#--------Configurations--------#
hadoop_home=/usr/local/hadoop
hbase_home=/usr/local/hbase-2.1.3
spark_home=/usr/local/spark
#------------------------------#

if [[ $EUID -ne 0 ]]; then
	echo " [!] 需要root权限运行"
	exit 1
fi

while :
do
	echo ""
	echo -e "\n 选项:"
	echo "-------------------------------------------\
----------------------------------"
	echo ""
	echo " [1]: 全部启动 "
	echo " [2]: 单独启动功能 "
	echo " [3]: 全部停止 "
	echo " [4]: 重启功能 "
	echo " [5]: 检查配置 "
	echo -e "\033[36m [9]: 退出脚本 \033[0m"
	echo ""	
	read -p " [&] 请选择要使用的功能 : " option

	if [[ $option == 1 ]]; then
	    ${hadoop_home}/sbin/start-all.sh
		${hbase_home}/bin/start-hbase.sh 
		${spark_home}/sbin/start-all.sh
		read -p " [&] 是否启用Spark Shell(y/n): " o1
     	if [ [$o1 == "Y"] -o [$o1 == "y"] ]; then
			${spark_home}/bin/spark-shell
		fi
	elif [[ $option == 2 ]]; then
		while :
		do
			echo ""
			echo -e "\n 选项:"
			echo "-------------------------------------------\
----------------------------------"
			echo ""
			echo " [1]: 启动Hdoop "
			echo " [2]: 启动HBse "
			echo " [3]: 启动Spark "
			echo -e "\033[36m [9]: 返回菜单 \033[0m"
			echo ""	
			read -p " [&] 请选择要使用的功能 : " option

			if [[ $option == 1 ]]; then
				${hadoop_home}/sbin/start-all.sh
				continue
			elif [[ $option == 2 ]]; then
				${hbase_home}/bin/start-hbase.sh 
				continue
			elif [[ $option == 3 ]]; then
				${spark_home}/sbin/start-all.sh
				read -p " [&] 是否启用Spark Shell(y/n): " o1
				if [ [$o1 == "Y"] -o [$o1 == "y"] ]; then
					${spark_home}/bin/spark-shell
				fi
				continue
			elif [[ $option == 9 ]]; then
				break 1
			else
				echo " [-] 未知选项: $option"
				continue
			fi
		done
	elif [[ $option == 3 ]]; then
		${spark_home}/sbin/stop-all.sh
		${hbase_home}/bin/stop-hbase.sh 
		${hadoop_home}/sbin/stop-all.sh
	elif [[ $option == 4 ]]; then
		while :
		do
			echo ""
			echo -e "\n 选项:"
			echo "-------------------------------------------\
----------------------------------"
			echo ""
			echo " [1]: 重启Hdoop "
			echo " [2]: 重启HBse "
			echo " [3]: 重启Spark "
			echo -e "\033[36m [9]: 返回菜单 \033[0m"
			echo ""	
			read -p " [&] 请选择要使用的功能 : " option

			if [[ $option == 1 ]]; then
				echo "-------------------------------------------\
----------------------------------"
				echo ""
				echo " [!] 注意Hadoop集群运行情况!"
				read -p "[&] 确认是否重启Hadoop集群(y/n): " o1
				if [ [$o1 == "Y"] -o [$o1 == "y"] ]; then
					echo " [!] 正在重启Hadoop"
					${hadoop_home}/sbin/stop-all.sh
					${hadoop_home}/sbin/start-all.sh
				fi
				continue
			elif [[ $option == 2 ]]; then
				${hbase_home}/bin/stop-hbase.sh 
				${hbase_home}/bin/start-hbase.sh 
				continue
			elif [[ $option == 3 ]]; then
				${spark_home}/sbin/stop-all.sh
				${spark_home}/sbin/start-all.sh
				read -p "[&] 是否启用Spark Shell(y/n): " o1
				if [ [$o1 == "Y"] -o [$o1 == "y"] ]; then
					${spark_home}/bin/spark-shell
				fi
				continue
			elif [[ $option == 9 ]]; then
				break 1
			else
				echo " [-] 未知选项: $option"
				continue
			fi
		done
	elif [[ $option == 5 ]]; then
		hadoop checknative -a
		read -p " [&] 输入任意键继续... "
	elif [[ $option == 9 ]]; then
		echo -e "-------------------------------------------\
----------------------------------\n"
		break
	else
		echo " [-] 未知选项: $option"
	fi
done

