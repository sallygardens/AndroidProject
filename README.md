# AndroidTest
这是一个简单android的app，里面共有七个按钮，每个按钮里面都是现在的app中常见功能，因为添加图片还要截图上传图片，电脑可能在运行虚拟机的过程中过于卡顿，因此就不放图了，可在app的release文件夹里寻找到apk下载体验。其中有几个功能是需要连接服务器的，但在这个仓库中没有服务器代码，如需要使用服务器请自行定义，或在issue中留下邮箱向我索要！
下面来介绍这七个按钮所对应的功能分别是什么……
# 第一个按钮
发送get请求给服务器，并获取服务器中的数据。
# 第二个按钮
滑动冲突的简单实现，参考android艺术探索。
# 第三个按钮
简单模仿android端创建用户操作，点击注册后，用get请求发送数据给服务器，并在数据库中添加该用户。
# 第四个按钮
camera2相机使用，之前本来想用camerax来着，结果因为camerax不稳定就用camera2了，参考google的camera2demo。
# 第五个按钮
使用post请求模拟上传图片给服务器功能。
# 第六个按钮
实现简单的自动滚动view功能。
# 第七个按钮
实现appbarlayout+viewpager+tablayout+recyclerview+fragment的复合使用。
