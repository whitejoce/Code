#!/bin/bash

function ChackPing() {
	 echo -e "[...] Check the network connection \c"
	 ping -c 3 8.8.8.8 | grep -q "0% packet loss" && result1=1 || result1=0
	 if [ $result1 -eq 0 ];then
	 	 echo -e "[-] Can't Ping 8.8.8.8 \c"
	 	 ping -c 3 8.8.4.4 | grep -q "0% packet loss" && result2=1 || result2=0
		 if [ $result2 -eq 0 ];then
		 	  echo -e "[-] Network is not connected."
			  exit -1
		 fi 
	 fi
	 echo "[Done.]"
}

ChackPing
echo "-------------------------------------------\
----------------------------------"
while :
do
		echo -e "\n Menu:"
		echo " [1] ALL"
		echo " [2] Single"
		echo -e "\033[31m [9] Exit \033[0m"
		echo ""
		read -p ' [&] Clone Mode: ' o1
		
		if [ ${o1} == 1 ]; then
			echo "-------------------------------------------\
----------------------------------"
			echo "[+] Install now......"
			git clone https://github.com/whitejoce/Pentest_Toolbox.git
			git clone https://github.com/whitejoce/Get_Weather.git
			git clone https://github.com/guelfoweb/peframe.git
			git clone https://github.com/EnableSecurity/wafw00f.git
			git clone https://www.github.com/FluxionNetwork/fluxion.git
			git clone https://github.com/v1s1t0r1sh3r3/airgeddon.git
			#peframe
			cd peframe
			sudo bash install.sh
			cd ..
			sudo chmod -R 777 Wireless-Toolbox
			sudo chmod -R 777 peframe
			#Python
			read -rsp $'Press enter to continue...\n'
			sudo apt-get install -y python3-dev build-essential libssl-dev libffi-dev libxml2 libxml2-dev libxslti-dev zlibig-dev libcurl4-openssl-dev
			sudo apt-get install -y python3 
			sudo apt-get install -y python3-pip
			#wafw00f
			cd wafw00f
			python3 setup.py install
			cd ..
			#fluxion
			cd fluxion
			sudo ./fluxion.sh -i 
			cd ..
			#airgeddon
			sudo chmod -R 777 airgeddon
			sudo bash airgeddon/airgeddon.sh
		elif [ ${o1} == 2 ]; then
			while :
			do
				echo "-------------------------------------------\
----------------------------------"
		 		 echo -e " Options:"
				 echo " [1] Pentest_Toolbox"
				 echo " [2] Get_Weather"
				 echo " [3] peframe"
				 echo " [4] wafw00f"
				 echo " [5] fluxion"
				 echo " [6] airgeddon"
				 echo ' [7] Python3 & pip'
				 
				 echo -e "\033[31m [9] Menu \033[0m"
				 echo -e "\033[31m [0] Exit \033[0m"
				 echo ""
				 read -p ' [&] Clone: ' o2
				 if [ ${o2} == 1 ]; then
				 	 echo "-------------------------------------------\
----------------------------------"
				 	 git clone https://github.com/whitejoce/Pentest_Toolbox.git
					 sudo chmod -R 777 Pentest_Toolbox
					 mv Pentest_Toolbox Toolbox
					 rm $PWD/Toolbox/LICENSE $PWD/Toolbox/README.md
					 continue
				 elif [ ${o2} == 2 ]; then
				     echo "-------------------------------------------\
----------------------------------"
				 	 git clone https://github.com/whitejoce/Get_Weather.git
					 mv Get_Weather/GetWeather.py $PWD
					 rm -rf $PWD/Get_Weather
					 continue
				 elif [ ${o2} == 3 ]; then
				     echo "-------------------------------------------\
----------------------------------"
				 	 git clone https://github.com/guelfoweb/peframe.git
					 cd peframe
					 sudo bash install.sh
					 cd ..
					 sudo chmod -R 777 peframe
					 continue
				 elif [ ${o2} == 4 ]; then
				     echo "-------------------------------------------\
----------------------------------"
					 git clone https://github.com/EnableSecurity/wafw00f.git
					 cd wafw00f
					 python3 setup.py install
					 cd ..
					 sudo chmod -R 777 peframe
					 continue
				 elif [ ${o2} == 5 ]; then
				     echo "-------------------------------------------\
----------------------------------"
					 git clone https://www.github.com/FluxionNetwork/fluxion.git
					 cd fluxion
					 sudo ./fluxion.sh -i
				 elif [ ${o2} == 6 ]; then
				     echo "-------------------------------------------\
----------------------------------"
					 git clone https://github.com/v1s1t0r1sh3r3/airgeddon.git
					 sudo chmod -R 777 airgeddon
					 sudo bash airgeddon/airgeddon.sh
				 elif [ ${o2} == 7 ]; then 
				     echo "-------------------------------------------\
----------------------------------"
					 read -rsp $'Press enter to continue...\n'
				 	 sudo apt-get install -y python3-dev build-essential \
libssl-dev libffi-dev libxml2 libxml2-dev libxslti-dev zlibig-dev libcurl4-openssl-dev
					 sudo apt-get install -y python3 
					 sudo apt-get install -y python3-pip
					 continue
				 elif [ ${o2} == 9 ]; then
					 break 1
					 echo "-------------------------------------------\
----------------------------------"
				  elif [ ${o2} == 0 ]; then
					 exit
				 else 
				 	 echo -e "\n [!] Error: $o2"
				 fi
			done
		elif [ ${o1} == 9 ]; then
			 exit
		else 
			 echo -e " [!] Error: $o1"
			 echo "-------------------------------------------\
----------------------------------"
		fi
done