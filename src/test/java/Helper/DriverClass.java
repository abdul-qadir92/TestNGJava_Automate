package Helper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverClass {
    public WebDriver driver;
    public Properties config;
    BufferedReader reader;

    // instant launch browser
    @Parameters("browser")
    @BeforeMethod()
    public void launch(String browser) throws InterruptedException {
        /*try {
            reader = new BufferedReader(new FileReader("./Config/Config.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        config = new Properties();
        try {
            config.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String browser = config.getProperty("RequiredBrowser");*/
        if(browser.contains("Chrome")){
            //For Chrome Browser
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(browser.contains("Firefox")){
            //For Firefox Browser
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }else if(browser.contains("Safari")){
            //For Safari Browser
            WebDriverManager.safaridriver().setup();
            driver = new SafariDriver();
        }
        WebDriverWait wait = new WebDriverWait(driver,5);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://www.amazon.com");
    }

    @AfterMethod
    public void tearDown(){
        driver.close();

    }

}
