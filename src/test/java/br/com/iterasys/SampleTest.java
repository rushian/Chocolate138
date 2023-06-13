package br.com.iterasys;


import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.junit.After;
import org.junit.Before;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class SampleTest {

     AppiumDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
        desiredCapabilities.setCapability("appium:deviceName", "emulator-5554");
        desiredCapabilities.setCapability("appium:appPackage", "com.google.android.calculator");
        desiredCapabilities.setCapability("appium:appActivity", "com.android.calculator2.Calculator");
        desiredCapabilities.setCapability("appium:avd", "nexus10");
        desiredCapabilities.setCapability("appium:deviceOrientation", "portrait");
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);

        URL remoteUrl = new URL("http://0.0.0.0:4723/wd/hub");

        driver = new AppiumDriver(remoteUrl, desiredCapabilities);
    }

    @Test
    public void sampleTest() {
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        WebElement el1 = driver.findElement(By.id("5"));
        el1.click();
        el1.click();
        el1.click();
        el1.click();
        el1.click();
        el1.click();
        el1.click();
        el1.click();
        el1.click();
        /*WebElement el2 = (WebElement) driver.findElementByAccessibilityId("plus");
        el2.click();
        WebElement el3 = (WebElement) driver.findElementByAccessibilityId("6");
        el3.click();
        WebElement el4 = (WebElement) driver.findElementByAccessibilityId("equals");
        el4.click();*/
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
