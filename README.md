PhoneCloud-Java
===============

基于HBase实现的手机数据备份系统，实现了手机关键信息的备份，如短信、联系人等。

包括服务器端(Server)和客户端(Client)

Server运行环境：Hadoop+HBase+Tomcat

Client运行环境: Eclipse+Android

Server端介绍：

接收客户端请求的Action：

1. 添加短信Action----------->AddInfoAction.java 
2. 获取短信Action----------->GetInfoAction.java 
3. 同步联系人Action--------->AddContactAction.java 
4. 上传文件Action----------->UploadFileAction.java 
5. 下载文件Action----------->DownloadFileAction.java 

封装了3个实体： 

1. 短信------->SmsInfo.java 
2. 联系人----->ContactInfo.java 
3. 文件------->MyFileInfo.java 
 
底部存储层： 

1. Hdfs操作类-------->Hdfs.java 
2. HBase操作类------->HBaseUtil.java 
3. 短信操作类-------->Sms.java 
4. 联系人操作类------>Contact.java 
5. 文件操作类-------->MyFile.java 
 
上面的Action都配置到Struts.xml中。


Client端介绍：

封装了三个实体bean

1. ContactInfo
2. SmsInfo
3. FileInfo

操作类

1. Contact
2. Sms  

如有疑问请联系：912750350@qq.com

