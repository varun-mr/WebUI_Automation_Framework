package com.WebBrowser.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentSparkReporter reporter;
	private static ExtentReports extent;

	public static ExtentReports createExtentReport() {
		if (extent == null) {

			reporter = new ExtentSparkReporter(System.getProperty("user.dir") + "\\target\\html\\Extent.html");
			reporter.config().setDocumentTitle("Web Browser Automation");
			reporter.config().setReportName("UI Automation Report");
			reporter.config().setTheme(Theme.DARK);

			extent = new ExtentReports();
			extent.attachReporter(reporter);
			extent.setSystemInfo("Platform", "Windows");
			extent.setSystemInfo("Browser", "Chrome");
			extent.setSystemInfo("Tester", "Varun");
		}
		return extent;
	}

}
