package com.github.herowzz.atm.driver;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * AtmChrome驱动
 * 
 * @author wangzz
 */
public class AtmWebDriver extends ChromeDriver {

	public AtmWebDriver(ChromeOptions options) {
		super(options);
	}

	private static Select select = null;
	private static Alert alert = null;
	private static WebElement element = null;
	private static List<WebElement> elementList = null;
	private static long timeOutInSeconds = 30;

	// --------------------自定义常量------------------------
	public final String LINE = "\r\n";
	public final String smile = "^_^";
	public final String sad = "*o*";

	// ----------------------------------------------------元素相关-----------------------------------------------------------------------------
	/**
	 * 查找元素
	 * 
	 * @param by      传入一个类型 例如：name
	 * @param byValue 传入一个类型值 例如：username
	 */
	public WebElement findElement(String by, String byValue) {
		try {
			switch (by) {
			case "id":
				element = this.findElement(By.id(byValue));
				break;
			case "name":
				element = this.findElement(By.name(byValue));
				break;
			case "class":
				element = this.findElement(By.className(byValue));
				break;
			case "tag":
				element = this.findElement(By.tagName(byValue));
			case "link":
				element = this.findElement(By.linkText(byValue));
				break;
			case "partiallinktext":
				element = this.findElement(By.partialLinkText(byValue));
			case "css":
				element = this.findElement(By.cssSelector(byValue));
				break;
			case "xpath":
				element = this.findElement(By.xpath(byValue));
				break;
			default:
				throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
			}
		} catch (Exception e) {
			System.out.println("没有找到元素：" + byValue);
		}
		return element;
	}

	/**
	 * 查找一组元素
	 * 
	 * @param by      传入一个类型 例如：name
	 * @param byValue 传入一个类型值 例如：username
	 */
	public List<WebElement> findElements(String by, String byValue) {
		try {
			switch (by) {
			case "id":
				elementList = this.findElements(By.id(byValue));
				break;
			case "name":
				elementList = this.findElements(By.name(byValue));
				break;
			case "class":
				elementList = this.findElements(By.className(byValue));
				break;
			case "tag":
				elementList = this.findElements(By.tagName(byValue));
			case "link":
				elementList = this.findElements(By.linkText(byValue));
				break;
			case "partiallinktext":
				elementList = this.findElements(By.partialLinkText(byValue));
			case "css":
				elementList = this.findElements(By.cssSelector(byValue));
				break;
			case "xpath":
				elementList = this.findElements(By.xpath(byValue));
				break;
			default:
				throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
			}
		} catch (Exception e) {
			System.out.println("没有找到元素：" + byValue);
		}
		return elementList;
	}

	/**
	 * 获取单个元素
	 */
	public WebElement findElementByXpath(String xpath) {
		return this.findElement(By.xpath(xpath));
	}

	public WebElement findElementByTag(String tag) {
		return this.findElement(By.tagName(tag));
	}

	public WebElement findElementById(String id) {
		return this.findElement(By.id(id));
	}

	public WebElement findElementByClassName(String name) {
		return this.findElement(By.className(name));
	}

	/**
	 * 该方法存在问题，暂用自己的方法进行代替
	 * 
	 * @param text
	 * @return
	 */
	public WebElement findElementByLinkText(String text) {
		return this.findElement(By.linkText(text));
	}

	public WebElement findElementByPartialText(String text) {
		return this.findElement(By.partialLinkText(text));
	}

	public WebElement findElementByName(String name) {
		return this.findElement(By.name(name));
	}

	public WebElement findElementByText(String name) {
		return this.findElement(By.xpath("//*[contains(text(), '" + name + "')]"));
	}

	/**
	 * 获取多个元素
	 */
	public List<WebElement> findElementsByClassName(String className) {
		return this.findElements(By.className(className));
	}

	public List<WebElement> findElementsByText(String text) {
		return this.findElements(By.linkText(text));
	}

	public List<WebElement> findElementsByPartialText(String text) {
		return this.findElements(By.partialLinkText(text));
	}

	public List<WebElement> findElementsById(String id) {
		return this.findElements(By.id(id));
	}

	public List<WebElement> findElementsByTag(String tag) {
		return this.findElements(By.tagName(tag));
	}

	/**
	 * 获取一组元素中的指定元素
	 */
	public WebElement FindByElements(By by, int index) {
		WebElement element = null;
		if (this.elementsExists(by)) {
			element = this.findElements(by).get(index);
		}
		return element;
	}

	/**
	 * 查找元素并点击
	 * 
	 * @param by      传入一个类型 例如：name
	 * @param byValue 传入一个类型值 例如：username
	 */
	public boolean findElementClick(String by, String byValue) {
		try {
			switch (by) {
			case "id":
				this.findElement(By.id(byValue)).click();
				return true;
			case "name":
				this.findElement(By.name(byValue)).click();
				return true;
			case "class":
				this.findElement(By.className(byValue)).click();
				return true;
			case "tag":
				this.findElement(By.tagName(byValue)).click();
			case "link":
				this.findElement(By.linkText(byValue)).click();
				return true;
			case "partiallinktext":
				this.findElement(By.partialLinkText(byValue)).click();
			case "css":
				this.findElement(By.cssSelector(byValue)).click();
				return true;
			case "xpath":
				this.findElement(By.xpath(byValue)).click();
				return true;
			default:
				throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
			}
		} catch (Exception e) {
			System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "  的元素或者该元素无法点击****");
			return false;
		}
	}

	/**
	 * 定位元素并点击
	 */
	public void findElementByIdAndClick(String id) {
		this.findElement(By.id(id)).click();
	}

	public void findElementByNameAndClick(String name) {
		this.findElement(By.name(name)).click();
	}

	public void findElementByTextAndClick(String text) {
		this.findElement(By.linkText(text)).click();
	}

	public void findElementByPartiaTextAndClick(String text) {
		this.findElement(By.partialLinkText(text)).click();
	}

	public void findElementByXpathAndClick(String xpath) {
		this.findElement(By.xpath(xpath)).click();
	}

	public void findElementByClassNameAndClick(String name) {
		this.findElement(By.className(name)).click();
	}

	/**
	 * 查找元素并清除文本内容
	 * 
	 * @param by      传入一个类型 例如：name
	 * @param byValue 传入一个类型值 例如：username
	 */
	public boolean findElementClear(String by, String byValue) {
		try {
			switch (by) {
			case "id":
				this.findElement(By.id(byValue)).clear();
				return true;
			case "name":
				this.findElement(By.name(byValue)).clear();
				return true;
			case "class":
				this.findElement(By.className(byValue)).clear();
				return true;
			case "tag":
				this.findElement(By.tagName(byValue)).clear();
				return true;
			case "link":
				this.findElement(By.linkText(byValue)).clear();
				return true;
			case "partiallinktext":
				this.findElement(By.partialLinkText(byValue)).clear();
				return true;
			case "css":
				this.findElement(By.cssSelector(byValue)).clear();
				return true;
			case "xpath":
				this.findElement(By.xpath(byValue)).clear();
				return true;
			default:
				throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
			}
		} catch (Exception e) {
			System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "  的元素或者该元素没有输入值****");
			return false;
		}
	}

	/**
	 * 查找元素并输入值
	 * 
	 * @param by      传入一个类型 例如：name
	 * @param byValue 传入一个类型值 例如：username
	 * @param key     填写要输入的值 例如：zhangsan
	 */
	public boolean findElementSendKeys(String by, String byValue, String key) {
		try {
			switch (by) {
			case "id":
				this.findElement(By.id(byValue)).sendKeys(key);
				return true;
			case "name":
				this.findElement(By.name(byValue)).sendKeys(key);
				return true;
			case "class":
				this.findElement(By.className(byValue)).sendKeys(key);
				return true;
			case "tag":
				this.findElement(By.tagName(byValue)).sendKeys(key);
				return true;
			case "link":
				this.findElement(By.linkText(byValue)).sendKeys(key);
				return true;
			case "partiallinktext":
				this.findElement(By.partialLinkText(byValue)).sendKeys(key);
			case "css":
				this.findElement(By.cssSelector(byValue)).sendKeys(key);
				return true;
			case "xpath":
				this.findElement(By.xpath(byValue)).sendKeys(key);
				return true;
			default:
				throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
			}
		} catch (Exception e) {
			System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "    的元素或者该元素无法输入****");
			return false;
		}
	}

	/**
	 * 查找元素并输入值
	 * 
	 * @param by      传入一个类型 例如：name
	 * @param byValue 传入一个类型值 例如：username
	 * @param key     填写要输入的值 例如：zhangsan
	 */
	public boolean findElementClearAndSendKeys(String by, String byValue, String key) {
		try {
			switch (by) {
			case "id":
				findElementClear(by, byValue);
				this.findElement(By.id(byValue)).sendKeys(key);
				return true;
			case "name":
				findElementClear(by, byValue);
				this.findElement(By.name(byValue)).sendKeys(key);
				return true;
			case "class":
				findElementClear(by, byValue);
				this.findElement(By.className(byValue)).sendKeys(key);
				return true;
			case "tag":
				findElementClear(by, byValue);
				this.findElement(By.tagName(byValue)).sendKeys(key);
				return true;
			case "link":
				findElementClear(by, byValue);
				this.findElement(By.linkText(byValue)).sendKeys(key);
				return true;
			case "partiallinktext":
				findElementClear(by, byValue);
				this.findElement(By.partialLinkText(byValue)).sendKeys(key);
			case "css":
				findElementClear(by, byValue);
				this.findElement(By.cssSelector(byValue)).sendKeys(key);
				return true;
			case "xpath":
				findElementClear(by, byValue);
				this.findElement(By.xpath(byValue)).sendKeys(key);
				return true;
			default:
				throw new RuntimeException("输入的定位类型未在程序中定义，类型为：" + byValue);
			}
		} catch (Exception e) {
			System.out.println("*****没有找到元素,类型为：:" + by + "属性值为：" + byValue + "    的元素或者该元素无法输入****");
			return false;
		}
	}

	/**
	 * 定位元素并清空文本内容，输入相应的值
	 */
	public void findElementByIdAndClearSendkeys(String id, String text) {
		this.findElement(By.id(id)).clear();
		this.findElement(By.id(id)).sendKeys(text);
	}

	public void findElementByIdAndClearSendkeys(String id, int num) {
		this.findElement(By.id(id)).clear();
		this.findElement(By.id(id)).sendKeys(num + "");
	}

	public void findElementByNameAndClearSendkeys(String name, String text) {
		this.findElement(By.name(name)).clear();
		this.findElement(By.name(name)).sendKeys(text);
	}

	public void findElementByNameAndClearSendkeys(String name, int num) {
		this.findElement(By.name(name)).clear();
		this.findElement(By.name(name)).sendKeys(num + "");
	}

	public void findElementByXpathAndClearSendkeys(String xpath, String text) {
		findElementByXpath(xpath).clear();
		findElementByXpath(xpath).sendKeys(text);
	}

	public void findElementByXpathAndClearSendkeys(String xpath, int num) {
		findElementByXpath(xpath).clear();
		findElementByXpath(xpath).sendKeys(num + "");
	}

	public void findElementByClassnameAndClearSendkeys(String classname, String text) {
		this.findElement(By.className(classname)).clear();
		this.findElement(By.className(classname)).sendKeys(text);
	}

	public void findElementByClassnameAndClearSendkeys(String classname, int num) {
		this.findElement(By.className(classname)).clear();
		this.findElement(By.className(classname)).sendKeys(num + "");
	}

	/**
	 * 定位元素，并获取其文本内容
	 */
	public String getTextByXpath(String xpath) {
		return findElementByXpath(xpath).getText();
	}

	public String getTextByClassName(String name) {
		return findElementByClassName(name).getText();
	}

	public String getTextById(String id) {
		return findElementById(id).getText();
	}

	public String getTextByName(String name) {
		return findElementByName(name).getText();
	}

	/**
	 * 定位元素，并指定点击次数(连续点击)
	 */
	public boolean clickById(String id, int clickCount) {
		try {
			for (int i = 0; i < clickCount; i++) {
				this.findElement(By.id(id)).click();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean clickByXpath(String xpath, int clickCount) {
		try {
			for (int i = 0; i < clickCount; i++) {
				this.findElement(By.xpath(xpath)).click();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean clickByCss(String css, int clickCount) {
		try {
			for (int i = 0; i < clickCount; i++) {
				this.findElement(By.cssSelector(css)).click();
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	// 判断元素是否存在
	public boolean exists(By selector) {
		this.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);// 设置查询组件等待时间
		try {
			this.findElement(selector);
			this.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 设置查询组件等待时间
			return true;
		} catch (Exception e) {
			this.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);// 设置查询组件等待时间
			return false;
		}
	}

	/**
	 * 判断一个元素是否存在
	 */
	public boolean isElementExist(By by) {
		try {
			this.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * 判断一个元素下的一个元素是否存在
	 */
	public boolean isElementExist(WebElement parent, By by) {
		try {
			parent.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * 判断一个元素是否存在,自定义属性
	 */
	public boolean isElementExist(String attr, String value) {
		try {
			findByOne(attr, value);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * 判断一组元素是否存在
	 */
	public boolean elementsExists(By by) {
		return (this.findElements(by).size() > 0) ? true : false;
	}
	
	/**
	 * 判断一个元素下的一个元素是否存在，根据Xpath
	 */
	public boolean isElementExist(By parent, By by) {
		try {
			findElement(parent).findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	// ---------------------------------------判断页面是否包含指定文本---------------------------------------------------------
	/**
	 * 1、指定时间内等待直到页面包含文本字符串
	 * 
	 * @param text    期望出现的文本
	 * @param seconds 超时时间
	 * @return Boolean 检查给定文本是否存在于指定元素中, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement(WebElement
	 *      element, String text)
	 */
	public Boolean waitUntilPageContainText(String text, long seconds) {
		try {
			return new WebDriverWait(this, seconds)
					.until(ExpectedConditions.textToBePresentInElement(this.findElement(By.tagName("body")), text));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 2、默认时间等待直到页面包含文本字符串
	 * 
	 * @param text 期望出现的文本
	 * @return Boolean 检查给定文本是否存在于指定元素中, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement(WebElement
	 *      element, String text)
	 */
	public Boolean waitUntilPageContainText(String text) {
		try {
			return new WebDriverWait(this, timeOutInSeconds)
					.until(ExpectedConditions.textToBePresentInElement(this.findElement(By.tagName("body")), text));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ---------------------------------------元素判断---------------------------------------------------------
	/**
	 * 1、指定时间内等待直到元素存在于页面的DOM上并可见, 可见性意味着该元素不仅被显示, 而且具有大于0的高度和宽度
	 * 
	 * @param locator 元素定位器
	 * @param seconds 超时时间
	 * @return Boolean 检查给定元素的定位器是否出现, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By
	 *      locator)
	 */
	public Boolean waitUntilElementVisible(By locator, int seconds) {
		try {
			new WebDriverWait(this, seconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 2、默认时间内等待直到元素存在于页面的DOM上并可见, 可见性意味着该元素不仅被显示, 而且具有大于0的高度和宽度
	 * 
	 * @param locator 元素定位器
	 * @return Boolean 检查给定元素的定位器是否出现, 超时则捕获抛出异常TimeoutException并返回false
	 * @see org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(By
	 *      locator)
	 */
	public Boolean waitUntilElementVisible(By locator) {
		try {
			new WebDriverWait(this, timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断元素是否显示
	 */
	public boolean getDisplayStatById(String id) {
		return this.findElement(By.id(id)).isDisplayed();
	}

	public boolean getDisplayStatByXpath(String xpath) {
		return this.findElement(By.xpath(xpath)).isDisplayed();
	}

	public boolean getDisplayStatByCss(String css) {
		return this.findElement(By.cssSelector(css)).isDisplayed();
	}

	/**
	 * 判断元素是否可写
	 */
	public boolean getEnableStatById(String id) {
		return this.findElement(By.id(id)).isEnabled();
	}

	public boolean getEnableStatByXpath(String xpath) {
		return this.findElement(By.xpath(xpath)).isEnabled();
	}

	public boolean getEnableStatByCss(String css) {
		return this.findElement(By.cssSelector(css)).isEnabled();
	}

	/**
	 * 判断元素是否选中
	 */
	public boolean getSelectStatById(String id) {
		return this.findElement(By.id(id)).isSelected();
	}

	public boolean getSelectStatByXpath(String xpath) {
		return this.findElement(By.xpath(xpath)).isSelected();
	}

	public boolean getSelectStatByCss(String css) {
		return this.findElement(By.cssSelector(css)).isSelected();
	}

	/**
	 * 获取当前焦点所在页面元素的属性值(name,value,id,src等等)
	 */
	public String getFocusAttributeValue(String attribute) {
		String value = "";
		try {
			Thread.sleep(333);
		} catch (Exception e) {
			e.printStackTrace();
		}
		value = this.switchTo().activeElement().getAttribute(attribute);
		System.out.println("The focus Element's " + attribute + "attribute value is>>" + value);
		return value;
	}

	// 等待元素可用再点击
	public void waitForEnabledByXpathAndClick(String xpath) throws InterruptedException {
		boolean key = true;
		while (key) {
			if (findElementByXpath(xpath).isEnabled() && findElementByXpath(xpath).isDisplayed()) {
				clickByJsByXpath(xpath);
				key = false;
			} else {
				sleep(0);
			}
		}
	}

	// 自定义等待时间
	public static void sleep(int key) throws InterruptedException {
		switch (key) {
		case 0:
			Thread.sleep(500);
			break;
		case 1:
			Thread.sleep(2000);
			break;
		case 2:
			Thread.sleep(5000);
			break;
		default:
			System.out.println("错误");
			break;
		}
	}

	// ---------------------------------------下拉列表操作---------------------------------------------------------
	// 根据id获取下拉框，根据index选择选项
	public void findSelectByIdAndSelectByIndex(String id, int index) {
		Select select = new Select(findElementById(id));
		select.selectByIndex(index);
	}

	// 根据id获取下拉框，根据value选择选项
	public void findSelectByIdAndSelectByValue(String id, String value) {
		Select select = new Select(findElementById(id));
		select.selectByValue(value);
	}

	// 根据id获取下拉框，根据text选择选项
	public void findSelectByIdAndSelectByText(String id, String text) {
		Select select = new Select(findElementById(id));
		select.selectByVisibleText(text);
	}

	// 根据classname获取下拉框，根据text选择选项
	public void findSelectByClassNameAndSelectByText(String name, String text) {
		Select select = new Select(findElementByClassName(name));
		select.selectByVisibleText(text);
	}

	// 根据classname获取下拉框，根据Value选择选项
	public void findSelectByClassNameAndSelectByValue(String name, String value) {
		Select select = new Select(findElementByClassName(name));
		select.selectByValue(value);
	}

	// 根据classname获取下拉框，根据index选择选项
	public void findSelectByClassNameAndSelectByIndex(String name, int index) {
		Select select = new Select(findElementByClassName(name));
		select.selectByIndex(index);
	}

	// 根据name获取下拉框，根据text选择选项
	public void findSelectByNameAndSelectByText(String name, String text) {
		Select select = new Select(findElementByName(name));
		select.selectByVisibleText(text);
	}

	// 根据name获取下拉框，根据Value选择选项
	public void findSelectByNameAndSelectByValue(String name, String value) {
		Select select = new Select(findElementByName(name));
		select.selectByValue(value);
	}

	// 根据name获取下拉框，根据index选择选项
	public void findSelectByNameAndSelectByIndex(String name, int index) {
		Select select = new Select(findElementByName(name));
		select.selectByIndex(index);
	}

	/**
	 * 定位select并选中对应text的option
	 * 
	 * @param locator
	 * @param text
	 * @see org.openqa.selenium.support.ui.Select.selectByVisibleText(String text)
	 */
	public void selectByText(By locator, String text) {
		select = new Select(this.findElement(locator));
		select.selectByVisibleText(text);
	}

	/**
	 * 定位select并选中对应index的option
	 * 
	 * @param locator
	 * @param index
	 * @see org.openqa.selenium.support.ui.Select.selectByIndex(int index)
	 */
	public void selectByIndex(By locator, int index) {
		select = new Select(this.findElement(locator));
		select.selectByIndex(index);
	}

	/**
	 * 定位select并选中对应value值的option
	 * 
	 * @param locator 定位select的选择器
	 * @param value   option 中的value值
	 * @see org.openqa.selenium.support.ui.Select.selectByValue(String value)
	 */
	public void selectByValue(By locator, String value) {
		select = new Select(this.findElement(locator));
		select.selectByValue(value);
	}

	// ---------------------------------------弹框操作---------------------------------------------------------
	// 判断是否有弹框
	public boolean isAlertPresent() {
		try {
			alert = this.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	// 接受弹出框
	public void acceptAlert() {
		if (this.isAlertPresent()) {
			alert.accept();
		}
	}

	// 取消弹出框
	public void dimissAlert() {
		if (this.isAlertPresent()) {
			alert.dismiss();
		}
	}

	// 获取弹出内容
	public String getAlertText() {
		String text = null;
		if (this.isAlertPresent()) {
			text = alert.getText();
		} else {
			// todo:log;
		}
		return text;
	}

	// 弹出对话框输入文本字符串
	public void inputTextToAlert(String text) {
		if (this.isAlertPresent()) {
			alert.sendKeys(text);
		} else {
			// todo:log;
		}
	}

	// ---------------------------------------窗口和iframe---------------------------------------------------------
	/**
	 * 切换到当前页面
	 */
	public void switchToCurrentPage() {
		String handle = this.getWindowHandle();
		for (String tempHandle : this.getWindowHandles()) {
			if (tempHandle.equals(handle)) {
				this.close();
			} else {
				this.switchTo().window(tempHandle);
			}
		}
	}

	/**
	 * 切换到指定title的窗口
	 */
	public void switchToWindow(String windowTtitle) {
		Set<String> windowHandles = this.getWindowHandles();
		for (String handler : windowHandles) {
			this.switchTo().window(handler);
			String title = this.getTitle();
			if (windowTtitle.equals(title)) {
				break;
			}
		}
	}

	/**
	 * 切换至父级frame
	 * 
	 * @see org.openqa.selenium.WebDriver.TargetLocator.parentFrame()
	 */
	public void switchToParentFrame() {
		this.switchTo().parentFrame();
	}

	/**
	 * 切换默认最外层frame或者窗口
	 * 
	 * @return 这个驱动程序聚焦在顶部窗口/第一个frame上
	 * @see org.openqa.selenium.WebDriver.TargetLocator.defaultContent()
	 */
	public void switchToDefault() {
		this.switchTo().defaultContent();
	}

	/**
	 * 切换到指定iframe
	 */
	public void switchToFrameById(String frameId) {
		this.switchTo().frame(frameId);
	}

	public void switchToFrameByIndex(int index) {
		this.switchTo().frame(index);
	}

	public void switchToframeByElement(By locator) {
		this.switchTo().frame(this.findElement(locator));
	}

	public void switchToFrameByElement(WebElement frameElement) {
		this.switchTo().frame(frameElement);
	}

	/**
	 * 提交表单
	 * 
	 * @see org.openqa.selenium.WebElement.submit()
	 */
	public void submitForm(By locator) {
		this.findElement(locator).submit();
	}

	/**
	 * 上传文件
	 */
	public void uploadFile(By locator, String filePath) {
		this.findElement(locator).sendKeys(filePath);
	}

	// ---------------------------------------JS操作---------------------------------------------------------
	// JS点击指定元素
	public void clickByJs(WebElement element) {
		((JavascriptExecutor) this).executeScript("arguments[0].click()", element);
	}

	// 定位元素触发JS点击事件
	public void clickByJsByXpath(String xpath) {
		clickByJs(this.findElement(By.xpath(xpath)));
	}

	public void clickByJsByText(String text) {
		clickByJs(findElementByText(text));
	}

	public void clickByJsById(String id) {
		clickByJs(findElementById(id));
	}

	public void clickByJsByClassName(String name) {
		clickByJs(findElementByClassName(name));
	}

	public void clickByJsByName(String name) {
		clickByJs(findElementByName(name));
	}

	// 滚动到窗口最上方
	public void scrollToTop() {
		((JavascriptExecutor) this).executeScript("window.scrollTo(0,0);");
	}

	// 滚动到页面底部
	public void scrollToBottom(String id) {
		((JavascriptExecutor) this).executeScript("window.scrollTo(0,10000);");
	}

	// 滚动到某个元素
	public void scrollToElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) this;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// js给指定元素value赋值
	public void inputTextByJs(String text, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) this;
		js.executeScript("arguments[0].value=" + text + "\"", element);
	}

	// js使元素隐藏元素显示
	public void makeElementDisplay(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) this;
		js.executeScript("arguments[0].style=arguments[1]", element, "display: block;");
	}

	// ---------------------------------------浏览器操作---------------------------------------------------------
	/**
	 * 关闭当前浏览器
	 */
	public void closeCurrentBrowser() {
		this.close();
	}

	/**
	 * 关闭所有selenium驱动打开的浏览器
	 */
	public void closeAllBrowser() {
		this.quit();
	}

	/**
	 * 最大化浏览器
	 */
	public void maxBrowser() {
		this.manage().window().maximize();
	}

	/**
	 * 自定义设置浏览器尺寸
	 */
	public void setBrowserSize(int width, int heigth) {
		this.manage().window().setSize(new Dimension(width, heigth));
	}

	/**
	 * 获取网页的title值
	 */
	public String getTitle() {
		return this.getTitle();
	}

	/**
	 * 获取当前url字符串
	 */
	public String getURL() {
		return this.getCurrentUrl();
	}

	/**
	 * 上一个页面(点击浏览器返回)
	 */
	public void returnToPreviousPage() {
		this.navigate().back();
	}

	/**
	 * 下一个页面(如果没有下一个页面则什么都不做) 浏览器上的前进
	 */
	public void forwardToNextPage() {
		this.navigate().forward();
	}

	/**
	 * 刷新页面
	 */
	public void refreshPage() {
		this.navigate().refresh();
	}

	// 强制刷新页面
	public void refresh() {
		Actions ctrl = new Actions(this);
		ctrl.keyDown(Keys.CONTROL).perform();
		try {
			pressKeyEvent(KeyEvent.VK_F5);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		ctrl.keyUp(Keys.CONTROL).perform();
	}

	/**
	 * 判断是否加载有JQuery
	 */
	public Boolean JQueryLoaded() {
		Boolean loaded;
		JavascriptExecutor js = (JavascriptExecutor) this;
		try {
			loaded = (Boolean) js.executeScript("return" + "JQuery()!=null");
		} catch (WebDriverException e) {
			loaded = false;
		}
		return loaded;
	}
	// ---------------------------------------屏幕截图---------------------------------------------------------
//	    public void screenShot(WebDriver driver) {
//	        String dir_name = "screenshot";
//	        if (!(new File(dir_name).isDirectory())) {
//	            new File(dir_name).mkdir();
//	        }
//	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
//	        String time = sdf.format(new Date());
//	        try {
//	            File source_file = (((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE));// 执行截屏
//	            FileUtils.copyFile(source_file, new File(dir_name + File.separator + time + ".png"));
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//	    // 截图命名为当前时间保存桌面
//	    public void takeScreenshotByNow() throws IOException {
//	        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//	        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
//	        String time = sdf.format(new Date());
//	        String file = "C:\\Users\\zhangsan\\Desktop\\picture\\" + time + ".png";
//	        FileUtils.copyFile(srcFile, new File(file));
//	    }
//	    // 截图重命名保存至桌面
//	    public void takeScreenshotByName(String name) throws IOException {
//	        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//	        String file = "C:\\Users\\zhangsan\\Desktop\\picture\\" + name + ".png";
//	        FileUtils.copyFile(srcFile, new File(file));
//	    }

	/**
	 * 按物理按键(KeyEvent类中查找相关的常量) 例子： Robot robot = new Robot();
	 * robot.keyPress(KeyEvent.VK_ENTER);//按下enter键
	 */
	public void pressKeyEvent(int keycode) throws AWTException {
		Robot robot = new Robot();
		robot.keyPress(keycode);
	}

	// ---------------------------------------鼠标操作---------------------------------------------------------
	// 鼠标悬浮指定元素并点击
	public void moveToElementById(String id) {
		Actions actions = new Actions(this);
		actions.moveToElement(findElementById(id)).perform();
	}

	public void moveToElementByClassName(String name) {
		Actions actions = new Actions(this);
		actions.moveToElement(findElementByClassName(name)).perform();
	}

	// 鼠标右键点击
	public void RightClickWebElement(String id) {
		Actions actions = new Actions(this);
		actions.contextClick(findElementById(id)).perform();
	}

	// 鼠标双击
	public void DoubleClickWebElement(String id) {
		Actions actions = new Actions(this);
		actions.doubleClick(findElementById(id)).perform();
	}

	/**
	 * 模拟点击键盘上的键： keyDown()按下 keyUp()抬起,松开
	 *
	 * 常见的键： Keys.SHIFT Keys.ALT Keys.Tab
	 */
	public void ClickCtrl(String id) {
		Actions actions = new Actions(this);
		actions.keyDown(Keys.CONTROL);// 按下control键
		actions.keyUp(Keys.CONTROL);// 松开control键
	}

	/**
	 * 模拟键盘输入关键字到输入框
	 */
	public void sendText(By by, String text) {
		Actions actions = new Actions(this);
		actions.sendKeys(this.findElement(by), text).perform();
	}

	/**
	 * 模拟鼠标移动到指定元素,并点击
	 */
	public void moveToElementAndClick(By by, String text) {
		Actions actions = new Actions(this);
		actions.moveToElement(this.findElement(by)).click().perform();
	}

	/**
	 * 模拟鼠标点击和释放
	 */
	public void clickHoldAndRelease(By by) {
		Actions actions = new Actions(this);
		actions.clickAndHold(this.findElement(by)).perform();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		actions.release(this.findElement(by)).perform();
	}

	// ---------------------------------------Cookie操作---------------------------------------------------------
	/**
	 * 获取当前域所有的cookies
	 * 
	 * @return Set&lt;Cookie> 当前的cookies集合
	 * @see org.openqa.selenium.WebDriver.Options.getCookies()
	 */
	public Set<Cookie> getAllCookies() {
		return this.manage().getCookies();
	}

	// 输出cookies信息
	public void outputCookie() {
		Set<Cookie> cookie = this.manage().getCookies();
		System.out.println(cookie);
	}

	// 添加cookie信息
	public void addCookie(Map<String, String> args) {
		Set<String> keys = args.keySet();
		for (String key : keys) {
			this.manage().addCookie(new Cookie(key, args.get(key)));
		}
	}

	/**
	 * 用给定的name和value创建默认路径的Cookie并添加, 永久有效
	 * 
	 * @param name
	 * @param value
	 * @see org.openqa.selenium.WebDriver.Options.addCookie(Cookie cookie)
	 * @see org.openqa.selenium.Cookie.Cookie(String name, String value)
	 */
	public void addCookie(String name, String value) {
		this.manage().addCookie(new Cookie(name, value));
	}

	/**
	 * 用给定的name和value创建指定路径的Cookie并添加, 永久有效
	 * 
	 * @param name  cookie名称
	 * @param value cookie值
	 * @param path  cookie路径
	 */
	public void addCookie(String name, String value, String path) {
		this.manage().addCookie(new Cookie(name, value, path));
	}

	/**
	 * 根据cookie名称删除cookie
	 * 
	 * @param name cookie的name值
	 * @see org.openqa.selenium.WebDriver.Options.deleteCookieNamed(String name)
	 */
	public void deleteCookie(String name) {
		this.manage().deleteCookieNamed(name);
	}

	/**
	 * 删除当前域的所有Cookie
	 * 
	 * @see org.openqa.selenium.WebDriver.Options.deleteAllCookies()
	 */
	public void deleteAllCookies() {
		this.manage().deleteAllCookies();
	}

	/**
	 * 根据名称获取指定cookie
	 * 
	 * @param name cookie名称
	 * @return Map&lt;String, String>, 如果没有cookie则返回空, 返回的Map的key值如下:
	 *         <ul>
	 *         <li><tt>name</tt> <tt>cookie名称</tt>
	 *         <li><tt>value</tt> <tt>cookie值</tt>
	 *         <li><tt>path</tt> <tt>cookie路径</tt>
	 *         <li><tt>domain</tt> <tt>cookie域</tt>
	 *         <li><tt>expiry</tt> <tt>cookie有效期</tt>
	 *         </ul>
	 * @see org.openqa.selenium.WebDriver.Options.getCookieNamed(String name)
	 */
	public Map<String, String> getCookieByName(String name) {
		Cookie cookie = this.manage().getCookieNamed(name);
		if (cookie != null) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", cookie.getName());
			map.put("value", cookie.getValue());
			map.put("path", cookie.getPath());
			map.put("domain", cookie.getDomain());
			map.put("expiry", cookie.getExpiry().toString());
			return map;
		}
		return null;
	}

	public WebElement findByOne(String attr, String value) {
		return this.findElement(By.xpath("//*[@" + attr + "='" + value + "']"));
	}

	public List<WebElement> findByMany(String attr, String value) {
		return this.findElements(By.xpath("//*[@" + attr + "='" + value + "']"));
	}
}
