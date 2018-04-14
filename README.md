# 软件测试Selenium使用

### Selenium实验

1. 安装SeleniumIDE插件
2. 学会使用SeleniumIDE录制脚本和导出脚本
3. 编写Selenium Java WebDriver程序



#### 环境:

##### 浏览器:chrome

##### 扩展:Katalon Automation Recorder

##### 语言:java

##### 其他工具:chromedriver,junit4

##### 导入jar包:selenium-server-standalone-3.11.0.jar



#### 过程描述:

1. 安装谷歌浏览器,比较新的版本也可以,我的是version 65.0.3325.181

2. 安装Katalon Automation Recorder插件,Selenium IDE的替代品,功能比较齐全而且支持新版本的浏览器

3. 新建java project,加入junit4的库,junit5不支持Katalon导出的java代码

4. 用Katalon插件export出java/junit的代码放到eclipse的项目中

5. 将从网上下载的seleniumRC的jar包导入到项目中,导出的代码的报错就没了,但是用junit运行一下还是不行,那是因为seleniumRC打不开谷歌浏览器,要专门下载一个[chromedriver](https://sites.google.com/a/chromium.org/chromedriver/downloads)

6. 插件即使是谷歌商店的但还是默认用的firefox浏览器,在导出代码的setUp()函数里用的仍然是FirefoxDriver(),所以要改一下代码,我从[ChromeDriver的网站](https://sites.google.com/a/chromium.org/chromedriver/getting-started)上找到了解决办法,把driver的实例化部分稍微修改一下即可:

   ```java
   @Before
   	public void setUp() throws Exception {
   		// driver = new FirefoxDriver();
   		System.setProperty("webdriver.chrome.driver", "/Users/ene/Downloads/chromedriver");
   		driver = new ChromeDriver();
   		baseUrl = "https://www.google.com/";
   		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
   	}
   ```

7. 执行导出的代码,可以正常运行,命令行输出如下:

   ```java
   Starting ChromeDriver 2.37.544337 (8c0344a12e552148c185f7d5117db1f28d6c9e85) on port 27794
   Only local connections are allowed.
   Apr 14, 2018 2:21:25 PM org.openqa.selenium.remote.ProtocolHandshake createSession
   INFO: Detected dialect: OSS
   ```

8. 然后就可以按照要求做一些想做的测试了



#### 关于这次作业:

1. 登录网站,输入学号密码(从excel文件中读取),检查网址是否正确
2. 为了从excel中读取信息,导入了org.apache.poi的jar包,使用相关的函数读取,从[apache poi](https://archive.apache.org/dist/poi/release/bin/)的网站可以下载到各种版本
3. 把读取到的用户名密码放入到一个HashMap中即可
4. 使用Katalon记录用用户名密码登录的过程一遍即可,得到导出的代码
5. 了解一下代码用到的函数,然后写一个循环验证HashMap中所有的用户名密码即可



#### 注意事项:

- 从excel中读取到的内容中总有多出来的空格和换行,一开始验证的时候发现有很多不匹配的,然后trim()一下就好了
- 与网络有关的测试还要考虑网速的问题,只有成功登陆之后才能获得内容,但是因为网速的问题,代码执行完了但是还没有得到内容,所以其实应该想办法比如说用线程来一个等待机制的,但是因为各种原因只用了Thread.sleep()来完成测试,要是网速慢就让线程多等一会就好了;总而言之,这一点不能不注意