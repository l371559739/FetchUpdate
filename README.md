这是一个获取网站文件更新，并通过ftp传到自己博客上去的工具。

-------------
该项目主要是为了抓取imouto提供的hosts文件，然后自己定制化，并上传到自己的ftp。
该项目可配合linux的cron服务自动定时更新。


在0.8版本开始，去掉了7zip解压模块，打包出来的文件由7M减到了200+KB。
1.1添加了发送邮件给管理员的功能，利用java mail
