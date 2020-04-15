package com.github.herowzz.atm.driver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * AtmChrome驱动
 * 
 */
public class AtmWebDriver extends ChromeDriver {

	public AtmWebDriver(ChromeOptions options) {
		super(options);
	}

	/**
	 * 判断一个元素是否存在
	 */
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
	 * 判断一个元素是否存在，根据自定义属性
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


	public WebElement findByOne(String attr, String value) {
		return this.findElement(By.xpath("//*[@" + attr + "='" + value + "']"));
	}

	public List<WebElement> findByMany(String attr, String value) {
		return this.findElements(By.xpath("//*[@" + attr + "='" + value + "']"));
	}
}
