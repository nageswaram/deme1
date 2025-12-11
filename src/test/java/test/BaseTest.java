package test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.testng.annotations.*;

public class BaseTest {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    @Parameters("browser")
    @BeforeClass
    public void setDriver(@Optional("chrome") String browser)
    {
        switch(browser.toLowerCase()){
            case "chrome":
                WebDriverManager.chromedriver().getWebDriver();
                driver.set(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().getWebDriver();
                driver.set(new FirefoxDriver());
                break;
            default:
                throw new UnreachableBrowserException("wrong browser");
        }

        //System.out.println("Browser setup by Thread : " + Thread.currentThread().getId() + " and Driver reference is : " + getDriver());
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @AfterClass
    public void closeBrowser() {
//        System.out.println("Browser closed by Thread : " + Thread.currentThread().getId()
//                + " and Closing driver reference is :" + getDriver());
        driver.get().quit();
        driver.remove();
    }
}