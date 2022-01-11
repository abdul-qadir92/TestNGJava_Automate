package Page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Amazon {
    WebDriver driver ;
    WebDriverWait wait;

    public Amazon(WebDriver Ldriver){
        this.driver = Ldriver;
        wait = new WebDriverWait(driver,10);
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
        JavascriptExecutor js = (JavascriptExecutor) driver;
        driver.get("https://www.amazon.com");
        /*String a= (String) js.executeScript("return navigator.userAgent");
        System.out.println("User agent:"+a);*/
        wait.until(ExpectedConditions.elementToBeClickable(txtSearch));
        txtSearch.sendKeys("iPhone X");
        btnSearch.click();
        wait.until(ExpectedConditions.elementToBeClickable(btnSort));
        btnSort.click();
        js.executeScript("arguments[0].click();",lblHigh2Low);
        wait.until(ExpectedConditions.elementToBeClickable(chkiOS));
        js.executeScript("arguments[0].scrollIntoView;",chkiOS);
        chkiOS.click();
        List<WebElement> alliPhone;
        alliPhone = driver.findElements(By.xpath("//a/span[contains(text(),'iPhone X')]"));
        //System.out.println(alliPhone.size());
        String Name, Price, Link;
        for(int i=0;i<alliPhone.size()-1;i++){
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
                    Price = "Price Not Available";
                }
                driver.navigate().back();
                System.out.println(Name+"\t"+Price+"\t"+Link);
            }

        }

    }


}
