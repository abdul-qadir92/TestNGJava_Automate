package TestCase;

import Setup.DriverClass;
import Page.Amazon;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class SearchiPhone extends DriverClass {
    @Test
    public void SearchiPhoneX() throws InterruptedException {
        Amazon amz = PageFactory.initElements(getDriver(), Amazon.class);
        amz.launchAmzn();
    }

}
