package com.WebBrowser.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.WebBrowser.Base.BaseClass;
import com.WebBrowser.Utilities.TestUtility;
import com.aventstack.extentreports.Status;

public class TestListeners extends BaseClass implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		test = report.createTest(result.getName().toUpperCase());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, result.getName().toUpperCase()+": is Passed");
		test.addScreenCaptureFromPath(TestUtility.getScreenshot(result.getName().toUpperCase()), result.getName().toUpperCase());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		test.log(Status.FAIL, result.getName().toUpperCase()+": is Failed");
		test.addScreenCaptureFromPath(TestUtility.getScreenshot(result.getName().toUpperCase()), result.getName().toUpperCase());
	}

	@Override
	public void onTestSkipped(ITestResult result) {

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();
	}

}
