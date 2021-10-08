package com.WebBrowser.Utilities;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import com.WebBrowser.Base.BaseClass;

public class TestUtility extends BaseClass {

	public static String SheetName;

	public static String getScreenshot(String methodName) {

		TakesScreenshot ts = ((TakesScreenshot) driver);
		File sourcePath = ts.getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		SheetName = methodName + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".png";

		String destPath = System.getProperty("user.dir") + "\\target\\html\\" + SheetName;
		File destinationPath = new File(destPath);
		try {
			FileUtils.copyFile(sourcePath, destinationPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return destPath;
	}

	@DataProvider(name = "dp")
	public Object[][] getData(Method m) throws IOException {
		String filePath = System.getProperty("user.dir")
				+ "\\src\\test\\resources\\com\\WebBrowser\\TestData\\testData.xlsx";

		String sheetName = m.getName();
		int rows = ExcelOperations.getRowCount(filePath, sheetName);
		int cols = ExcelOperations.getCellCount(filePath, sheetName, 0);

		Object[][] data = new Object[rows][cols];

		for (int i = 0; i < rows; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			for (int j = 0; j < cols; j++) {
				map.put(ExcelOperations.getCellData(filePath, sheetName, 0, j),
						ExcelOperations.getCellData(filePath, sheetName, i + 1, j));
			}
			data[i][0] = map;
		}
		return data;

	}

}
