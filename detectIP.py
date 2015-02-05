#!/user/bin/python
# -*- coding: utf-8 -*-
from ftplib import FTP

f=open("ftpconfig")
#0ftp address,1username,2password,3upload path,4upload port
config=[]
line=f.readline()
i=0
while line:
	while line[0]=='#':
		line=f.readline()
	config.append(line)
	print(config[i])
	i+=1
	line=f.readline()
f.close()

ftp=FTP()
ftp.set_debuglevel(2)
ftp.connect(config[0],config[4])
ftp.log(config[1],config[2])
print ftp.getwelcome()
bufsize=1024
file_handler=