package com.WebBrowser.TestCases;

import org.testng.annotations.AfterSuite;

import com.WebBrowser.Base.BaseClass;

public class BaseTest {

	@AfterSuite
	public void teardown() {
		BaseClass.driver.quit();
	}

}
