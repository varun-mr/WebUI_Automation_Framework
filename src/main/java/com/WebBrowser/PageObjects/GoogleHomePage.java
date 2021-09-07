package com.WebBrowser.PageObjects;

import com.WebBrowser.Base.BaseClass;

public class GoogleHomePage extends BaseClass {

	public void searchBoxPresence() {
		AssertTrue("Search_Box_XPATH");
	}

	public void enterValueInSearchBoxAndClear(String value) {
		type("Search_Box_XPATH", value);
		clear("Search_Box_XPATH");
	}

	public void enterSingleValue(String val) {
		type("Search_Box_XPATH", val);
		clear("Search_Box_XPATH");
	}

}
