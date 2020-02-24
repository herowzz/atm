# atm
![jitpack](https://jitpack.io/v/herowzz/atm.svg) ![java](https://img.shields.io/badge/java-1.8.0%2B-green)

automation test management

## Useage
Add the following dependency to your pom.xml:
```xml
<dependency>
	<groupId>com.github.herowzz</groupId>
	<artifactId>atm</artifactId>
	<version>v0.1.2.RELEASE</version>
</dependency>
```  

the config.properties
```
product=WINFORM-TEST
version=1.0.0
runPath=C:\\WINDOWS\\system32\\notepad.exe
driverUrl=http://127.0.0.1:4723
outputPath=F:\\temp
```  
- runPath: is the application you want to test, in the web test you can write the web app start url
- driverUrl: in winform app test you must write the Windows Application Driver url in here
- outputPath: output result file path


then you can run main class
```java
@Configuration(packages = "com.github.herowzz.test.winform.useCase", driverType = DriverType.WinForm)
public class TestStart {

	private static Logger log = LoggerFactory.getLogger(TestStart.class);

	public static void main(String[] args) throws Exception {
		log.info("TestStart begin...");
		TestApplication.run(TestStart.class);
		log.info("TestStart finished...");
		System.exit(1);
	}
}
```  

----

### Configuration
Annotation Configuration use packages and driverType param
- packages: you write test use case must in this package for parent package
- driverType: now support Web and Winform

----

### Test UseCase Class
Test UseCase Class use TestModule, DriverInject, UseCase annotation
```java
@TestModule(name = "login", order = 1)
public class LoginModule {

	private static Logger log = LoggerFactory.getLogger(LoginModule.class);

	@DriverInject
	public WindowsDriver<WindowsElement> driver;

	@UseCase(name = "test Version code", order = 1)
	public CaseResult testVersion() {
		CaseResult result = new CaseResult();
		log.info("driver=========================" + driver);
		RemoteWebElement editInput = driver.findElementByClassName("Edit");
		editInput.clear();
		editInput.sendKeys("This is some text");
		editInput.sendKeys(Keys.ENTER);
		editInput.sendKeys(Keys.ENTER);
		return result.ok();
	}
}
```  
- TestModule: have name and order param
- DriverInject: to inject the driver for selenium
- UseCase: have name and order param

### Run start
Finally you run main class then system can execute the all case you write and export the result file

