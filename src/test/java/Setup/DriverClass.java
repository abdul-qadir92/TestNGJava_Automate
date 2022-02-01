package Setup;

import com.browserstack.local.Local;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import utils.BrowserstackTestStatusListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

@Listeners(BrowserstackTestStatusListener.class)
public class DriverClass {
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public Properties config;
    BufferedReader reader;
    private Local l;

    // instant launch browser
    @Parameters(value = { "config", "environment" })
    @BeforeMethod()
    public WebDriver init_driver(String config_file, String environment) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/com/browserstack/conf/" + config_file));
        JSONObject envs = (JSONObject) config.get("environments");

        DesiredCapabilities capabilities = new DesiredCapabilities();

        Map<String, String> envCapabilities = (Map<String, String>) envs.get(environment);
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }
        if(System.getenv("BROWSERSTACK_BUILD_NAME")!=null){
            capabilities.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
        }

        String username = System.getenv("BROWSERSTACK_USERNAME");
        if (username == null) {
            username = (String) config.get("user");
        }

        String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
        if (accessKey == null) {
            accessKey = (String) config.get("key");
        }

        String browserstackLocalIdentifier = System.getenv("BROWSERSTACK_LOCAL_IDENTIFIER");
        if(browserstackLocalIdentifier!=null){
            capabilities.setCapability("browserstack.localIdentifier", browserstackLocalIdentifier);
            System.out.println("browserstackLocalIdentifier: "+browserstackLocalIdentifier);
        }

        String browserstackLocal = System.getenv("BROWSERSTACK_LOCAL");
        if(browserstackLocalIdentifier!=null){
            capabilities.setCapability("browserstack.local", browserstackLocal);
            System.out.println("browserstackLocal: "+browserstackLocal);
        }

        if (capabilities.getCapability("browserstack.local") != null
                && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }

        //region optionalCapabilities
        if(capabilities.getCapability("realMobile")!=null
                && !capabilities.getCapability("realMobile").toString().toLowerCase().matches(".*(tab|pad).*")){
            String UserAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--user-agent="+UserAgent);
            capabilities.setCapability(ChromeOptions.CAPABILITY,options);
            //capabilities.merge(options);
        }
        //endregion

        tlDriver.set( new RemoteWebDriver(
                new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities));

        //region WindowCustomize
        getDriver().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        if(capabilities.getCapability("realMobile")==null)
            getDriver().manage().window().maximize();
        //endregion
        return getDriver();
    }

    public static synchronized WebDriver getDriver(){
        return tlDriver.get();
    }

    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        if (getDriver() != null) {
            getDriver().quit();
        }
        if(l != null) l.stop();
    }
}
