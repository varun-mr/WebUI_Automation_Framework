package com.WebBrowser.Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.WebBrowser.Utilities.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	public static WebDriver driver;
	public static WebElement element;
	public static List<WebElement> Multipleelements;
	public static Properties Config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static ChromeOptions Options;
	public static WebDriverWait wait;
	public static List<String> DropDownOptions;
	public static ExtentReports report = ExtentManager.createExtentReport();
	public static ExtentTest test;
	public static Logger log = LogManager.getLogger(BaseClass.class);

	public BaseClass() {
		if (driver == null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\WebBrowser\\Properties\\Config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				Config.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\com\\WebBrowser\\Properties\\OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				OR.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (Config.getProperty("browser").equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();

				Options = new ChromeOptions();
				Options.addArguments("incognito");
				Options.addArguments("start-maximized");

				driver = new ChromeDriver(Options);
			} else if (Config.getProperty("browser").equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				// driver = new FireFoxDriver();
				/*
				 * try { driver = new RemoteWebDriver(new
				 * URL("http:192.168.225.219:4444/wd/hub"), DesiredCapabilities.firefox()); }
				 * catch (MalformedURLException e) { e.printStackTrace(); }
				 */
			} else if (Config.getProperty("browser").equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
			}
			driver.get(Config.getProperty("url"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicitwait")),
					TimeUnit.SECONDS);
		}
	}

	public static void quit() {
		driver.quit();
	}

	public static void close() {
		driver.close();
	}

	public static void navigateBack() {
		driver.navigate().back();
	}

	public static boolean isDisplayed(String locator) {
		try {
			if (locator.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(locator))).isDisplayed();
			} else if (locator.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(locator))).isDisplayed();
			}
			log.debug("Verifying the visibility of the Element: " + locator);
			test.log(Status.INFO, "Verifying the visibility of the Element: " + locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static void AssertTrue(String locator) {
		Assert.assertTrue(isDisplayed(locator));
		log.debug("Verifying the on an Element : " + locator);
		test.log(Status.INFO, "Verifying the on an Element : " + locator);
	}

	public static void type(String locator, String value) {
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		}
		log.debug("Typing the " + value + "in locator: " + locator);
		test.log(Status.INFO, "Typing the " + value + "in locator: " + locator);
	}

	public static void click(String locator) {
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		}
		log.debug("Clicking on the locator: " + locator);
		test.log(Status.INFO, "Clicking on the locator: " + locator);
	}

	public static void clear(String locator) {
		if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).clear();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).clear();
		}
		log.debug("Clicking on the locator: " + locator);
		test.log(Status.INFO, "Clicking on the locator: " + locator);
	}

	static WebElement dropdown;

	public static void selectByVisibleText(String locator, String value) {

		if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		log.debug("Selecting from an element : " + locator + " value as : " + value);
		test.log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);
	}

	public static void selectbyindex(String locator, int index) {

		if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByIndex(index);
		log.debug("Selecting from an element : " + locator + " value as : " + index);
		test.log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + index);
	}

	public static void selectbyValue(String locator, String Val) {

		if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		select.selectByValue(Val);
		log.debug("Selecting from an element : " + locator + " value as : " + Val);
		test.log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + Val);
	}

	public static String getText(String locator) {
		String resultString = null;
		if (locator.endsWith("_XPATH")) {
			resultString = driver.findElement(By.xpath(OR.getProperty(locator))).getText();
		} else if (locator.endsWith("_ID")) {
			resultString = driver.findElement(By.id(OR.getProperty(locator))).getText();
		}
		log.debug("Text of the locator: " + locator + " value as :" + resultString);
		test.log(Status.INFO, "Text of the locator : " + locator + " value as :" + resultString);
		return resultString;
	}

	public static String getAttributeValue(String locator, String value) {
		String resultString = null;
		if (locator.endsWith("_XPATH")) {
			resultString = driver.findElement(By.xpath(OR.getProperty(locator))).getAttribute(value);
		} else if (locator.endsWith("_ID")) {
			resultString = driver.findElement(By.id(OR.getProperty(locator))).getAttribute(value);
		}
		log.debug("Text of the locator: " + locator + " value as :" + resultString);
		test.log(Status.INFO, "Text of the locator : " + locator + " value as :" + resultString);
		return resultString;
	}

	static WebElement act;

	public static void ClickActions(String locator) {
		if (locator.endsWith("_XPATH")) {
			act = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			act = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Actions action = new Actions(driver);
		action.moveToElement(act).click().build().perform();
		log.debug("Clicking on the element : " + locator + "using actions class");
	}

	public static String getdefaultValuefromdropdown(String locator) {
		if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}

		Select select = new Select(dropdown);
		WebElement Option = select.getFirstSelectedOption();
		String defaultItem = Option.getText();
		log.debug("Fetching Default value from dropdown : " + locator + " and default value is: " + defaultItem);
		test.log(Status.INFO,
				"Fetching Default value from dropdown : " + locator + " and default value is: " + defaultItem);
		return defaultItem;
	}

	public static void scrollDownBYWebelemnt(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		log.debug("Scrolling down to webelement: " + element + " using JavascriptExecutor");
		test.log(Status.INFO, "Scrolling down to webelement: " + element + " using JavascriptExecutor");
	}

	public static WebElement findElement(String locator) {
		if (locator.endsWith("_XPATH")) {
			element = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			element = driver.findElement(By.id(OR.getProperty(locator)));
		}
		log.debug("Finding the element: " + locator);
		test.log(Status.INFO, "Element located is: " + "'" + locator + "'");
		return element;
	}

	public static String findelement(String locator) {
		if (locator.endsWith("_XPATH")) {
			element = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			element = driver.findElement(By.id(OR.getProperty(locator)));
		}
		log.debug("Finding the element: " + locator);
		test.log(Status.INFO, "Element located is: " + "'" + locator + "'");
		return locator;
	}

	public static List<WebElement> FindMultipleElements(String locator) {
		if (locator.endsWith("_XPATH")) {
			Multipleelements = driver.findElements(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			Multipleelements = driver.findElements(By.id(OR.getProperty(locator)));
		}
		log.debug("Finding the elements: " + locator);
		test.log(Status.INFO, "Elements located is: " + "'" + locator + "'");
		return Multipleelements;
	}

	public static boolean isElementPresentCheckUsingJavaScriptExecutor(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			Object obj = jse.executeScript("return typeof(arguments[0]) != 'undefined' && arguments[0] != null;",
					element);
			log.debug("Checking the presence of the element " + element + " using javascriptExecutor");
			test.log(Status.INFO, "Checking the presence of the element " + element + " using javascriptExecutor");
			if (obj.toString().contains("true")) {
				System.out.println("isElementPresentCheckUsingJavaScriptExecutor: SUCCESS");
				return true;
			} else {
				System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
			}

		} catch (NoSuchElementException e) {
			System.out.println("isElementPresentCheckUsingJavaScriptExecutor: FAIL");
		}
		return false;

	}

	public static void highLightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
		log.debug("Highlighting the element " + element + " using javascriptExecutor");
		test.log(Status.INFO, "Highlighting the element " + element + " using javascriptExecutor");

	}

	public static void iFrames(int frame) {
		driver.switchTo().frame(frame);
		log.debug("Switching to frame " + frame);
		test.log(Status.INFO, "Switching to frame" + frame);
	}

	public void switchToparentWindow() {
		ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(0));
		System.out.println("The window displaying is : " + driver.switchTo().window(windows.get(0)).getCurrentUrl());
		// driver.close();
		log.debug("Switching to parent window");
		test.log(Status.INFO, "Switching to parent window");
	}

	public void closeTab(int number) throws InterruptedException {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(number));
		System.out.println("The window displaying is : " + driver.switchTo().window(tabs.get(number)).getCurrentUrl());
		Thread.sleep(3000);
		driver.close();
		log.debug("Closing the tab");
		test.log(Status.INFO, "Closing the tab");
	}

	public void switchToNewWindow(int windowNumber) {
		Set<String> s = driver.getWindowHandles();
		Iterator<String> ite = s.iterator();
		int i = 1;
		while (ite.hasNext() && i < 10) {
			String popupHandle = ite.next().toString();
			driver.switchTo().window(popupHandle);
			System.out.println("Window title is : " + driver.getTitle());
			int windowCount = 0;
			if (i == windowCount)
				break;
			i++;
		}
		log.debug("Switching to the new window");
		test.log(Status.INFO, "Switching to the new window");
	}

	public static String time_stamp() {
		String time_stamp = new SimpleDateFormat("dd_MM_yyyy").format(new Date());
		return time_stamp;
	}

	public void waitUntilElementDisplayed(int timeInSec, String locator) {
		wait = new WebDriverWait(driver, timeInSec);
		wait.until(ExpectedConditions.visibilityOf(findElement(locator)));
	}

	public void scrollToBottom() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 350)");
	}

	public List<String> DropDownValues(String locator) {
		Select dropdown = new Select(findElement(locator));
		DropDownOptions = new ArrayList<String>();
		List<WebElement> options = dropdown.getOptions();
		for (WebElement item : options) {
			DropDownOptions.add(item.getText());
		}
		System.out.println("Options under " + "'" + locator + "'" + " drop down are: " + DropDownOptions);

		log.debug("Found options under drop down : " + "'" + locator + "'");
		test.log(Status.INFO, "Options under " + "'" + locator + "'" + " drop down are: " + DropDownOptions);
		return DropDownOptions;
	}

	public void isElementEnabled(String locator, boolean expVal) {
		boolean actVal = findElement(locator).isEnabled();
		Assert.assertEquals(actVal, expVal);
		log.debug("Enabled sttae of Element : " + "'" + locator + "'" + "is : " + actVal);
		test.log(Status.INFO, "Enabled sttae of Element : " + "'" + locator + "'" + "is : " + actVal);
	}

	public void AssertEqualsText(String locator, String expVal) {
		String actVal = getText(locator);
		Assert.assertEquals(actVal, expVal);
		log.debug("Message displayed is : " + "'" + actVal + "'");
		test.log(Status.INFO, "Message displayed is : " + "'" + actVal + "'");
	}

	public void AssertIsBlank(String locator, boolean expFlag) {
		boolean actFlag = locator.isEmpty();
		Assert.assertEquals(actFlag, expFlag);
		test.log(Status.INFO, "IsBlank check for element " + "'" + locator + "'" + " is : " + actFlag);
	}

	public void readOnly(String locator) {
		String readOnly = findElement(locator).getAttribute("readonly");
		Assert.assertNull(readOnly);
		log.debug(" " + "'" + locator + "'" + " is read only");
		test.log(Status.INFO, " " + "'" + locator + "'" + " is read only");
	}

	public void AssertIsSelected(String locator, boolean expVal) {
		boolean actVal = findElement(locator).isSelected();
		Assert.assertEquals(actVal, expVal);
		log.debug("Asserting if " + "'" + locator + "'" + " is selected ");
		test.log(Status.INFO, "Asserting if " + "'" + locator + "'" + " is selected ");
	}

	public static void getUserInputCode(String locator) {
		// Using Scanner for Getting Input from User
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter code: ");
		String code = sc.nextLine();
		type(locator, code);
		sc.close();
	}

	public static boolean isAlertPresent(int timeInSec) {
		try {
			wait = new WebDriverWait(driver, timeInSec);
			wait.until(ExpectedConditions.alertIsPresent());
			driver.switchTo().alert().accept();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public void javascriptClick(WebElement locator) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", locator);
	}
}
