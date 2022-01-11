package TestCase;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class onPremise {
    @Test
    public void testMe(){
        WebDriverManager.chromedriver().setup();
        DesiredCapabilities capabilities = new DesiredCapabilities();
         ChromeOptions options = new ChromeOptions();
        //options.merge(capabilities);
        options.addArguments("--user-agent=Mozilla/5.0 (Linux; Android 10; SM-G981B) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Mobile Safari/537.36");
        //options.addArguments("start-maximized");
        WebDriver driver = new ChromeDriver(options);driver.manage().window().maximize();
        driver.get("https://www.amazon.com/");
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        String a= (String) jse.executeScript("return navigator.userAgent");
        System.out.print("User agent:"+a);
        driver.quit();
        }

    }
