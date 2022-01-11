package Page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BsDemo {
    WebDriver driver ;
    WebDriverWait wait;
    public BsDemo(WebDriver Ldriver){
        this.driver = Ldriver;
        wait = new WebDriverWait(driver,10);
    }
    @FindBy(how = How.XPATH, using="//span[text()='Apple']")
    WebElement lblApple ;
    @FindBy(xpath="//select")
    WebElement ddPrice ;

    public void NavigateBSDemo(){
        driver.manage().window().maximize();
        driver.get("http://localhost:3000/");
    }

    public void filterVendorPrice() throws InterruptedException {
        lblApple.click();
        Select sort = new Select(ddPrice);
        sort.selectByValue("lowestprice");
        Thread.sleep(5000);

    }
    public Map<String, String> searchResult(){
        List<WebElement> iphones = driver.findElements(By.cssSelector("div.shelf-item"));
        Assert.assertEquals(9,iphones.size(),"9 products not shown as expected");
        String title,price;
        Map<String,String> iphoneDetails= new HashMap<String,String>();
        for (int i = 0;i<iphones.size()-1;i++){
            title = iphones.get(i).findElement(By.xpath(".//p[@class='shelf-item__title']")).getAttribute("innerText");
            price = iphones.get(i).findElement(By.xpath(".//div[@class='val']/b")).getText();
            iphoneDetails.put(title,price);
        }
        return iphoneDetails;
    }

}
