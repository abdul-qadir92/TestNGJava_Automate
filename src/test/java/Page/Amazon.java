package Page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Amazon {
    WebDriver driver ;
    WebDriverWait wait;

    public Amazon(WebDriver Ldriver){
        this.driver = Ldriver;
        wait = new WebDriverWait(driver,30);
    }

    @FindBy (id="twotabsearchtextbox")
    WebElement txtSearch;
    @FindBy (id="nav-search-submit-button")
    WebElement btnSearch;
    @FindBy (xpath = "//span[@class='a-button-text a-declarative']")
    WebElement btnSort;
    @FindBy (xpath = "//a[contains(text(),'Price: High to Low')]")
    WebElement lblHigh2Low;
    @FindBy (xpath = "//span[text()='iOS']/preceding-sibling::div/label")
    WebElement chkiOS;

    public void launchAmzn() {
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://www.amazon.com");
        wait.until(ExpectedConditions.elementToBeClickable(txtSearch));
        txtSearch.sendKeys("iPhone X");
        btnSearch.click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        wait.until(ExpectedConditions.elementToBeClickable(btnSort));
        btnSort.click();
        js.executeScript("arguments[0].click();",lblHigh2Low);
        //driver.findElement(By.xpath("//span[@class='a-button a-button-dropdown a-button-small']"));
        //Select sort = new Select(driver.findElement(By.name("s")));
        //sort.selectByValue("price-desc-rank");
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Price: High to Low')]")));
        //driver.findElement(By.xpath("//a[contains(text(),'Price: High to Low')]")).click();
        wait.until(ExpectedConditions.elementToBeClickable(chkiOS));
        js.executeScript("arguments[0].scrollIntoView;",chkiOS);
        //Actions action = new Actions(driver);
        //action.click(driver.findElement(By.xpath("//span[text()='iOS']"))).build().perform();
        //js.executeScript("arguments[0].scrollIntoView()",driver.findElement(By.xpath("//span[text()='iOS']")));
        chkiOS.click();
        List<WebElement> alliPhone;
        alliPhone = driver.findElements(By.xpath("//a/span[contains(text(),'iPhone X')]"));
        //System.out.println(alliPhone.size());
        String Name, Price, Link;
        for(int i=0;i<alliPhone.size();i++){
            try{
                Name = alliPhone.get(i).getText();
            }catch(StaleElementReferenceException e){
                alliPhone = driver.findElements(By.xpath("//a/span[contains(text(),'iPhone X')]"));
                Name = alliPhone.get(i).getText();
            }
            if(Name.toLowerCase().contains("iphone x")){
                //List<WebElement> priceTag= alliPhone.get(i).findElements(By.xpath("//../../../following-sibling::div[2]//a/span[@class='a-price']/span[1]"));
                alliPhone.get(i).click();
                Link = driver.getCurrentUrl();
                try {
                    Price = driver.findElement(By.xpath("//td[text()='Price:']/following-sibling::td/span[1]")).getText();
                }catch (Exception e){
                    Price = "Not Given";
                }
                driver.navigate().back();
                System.out.println(Name+"\t"+Price+"\t"+Link);
            }

        }

    }


}
