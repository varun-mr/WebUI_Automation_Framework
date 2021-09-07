package com.WebBrowser.TestCases;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.WebBrowser.PageObjects.GoogleHomePage;
import com.WebBrowser.Utilities.TestUtility;

public class FirstTest extends BaseTest {

	GoogleHomePage go = new GoogleHomePage();

	@Test
	public void searchBox() {
		go.searchBoxPresence();
	}

	@Test(dataProviderClass = TestUtility.class, dataProvider = "dp")
	public void searchGoogle(HashMap<String, String> data) {
		go.enterValueInSearchBoxAndClear(data.get("ValueToSearch"));
	}
	
	@Test(dataProviderClass = TestUtility.class, dataProvider = "dp")
	public void singleSearchGoogle(HashMap<String, String> data) {
		go.enterSingleValue("SingleWord");
	}

	
}
