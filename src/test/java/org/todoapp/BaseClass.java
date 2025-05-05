package org.todoapp;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.todoapp.pageobjects.MainPage;
import org.todoapp.utils.AppiumUtils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

public class BaseClass {

    public static AppiumDriverLocalService service;
    public static IOSDriver driver;
    public MainPage mainPage;
    public AppiumUtils appiumUtils = new AppiumUtils();

    @BeforeClass
    public void ConfigureAppium() throws IOException {

        HashMap<String, String> testData = appiumUtils.getJsonData("src/test/java/org/todoapp/testdata/setupAppium.json");
        service = new AppiumServiceBuilder()
                .withAppiumJS(new File(testData.get("appiumJSPath")))
                .withIPAddress("127.0.0.1")
                .usingPort(4723)
                .build();
        service.start();

        XCUITestOptions options = new XCUITestOptions();
        options.setPlatformName(testData.get("platformName"));
        options.setAutomationName(testData.get("automationName"));
        options.setPlatformVersion(testData.get("platformVersion"));
        options.setApp(testData.get("appPath"));
        options.setDeviceName(testData.get("deviceName"));
        options.setBundleId(testData.get("bundleId"));
        options.useNewWDA();
        options.setWdaLaunchTimeout(Duration.ofSeconds(60));


        driver = new IOSDriver(service.getUrl(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        mainPage = new MainPage(driver);

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
        service.stop();
    }

}
