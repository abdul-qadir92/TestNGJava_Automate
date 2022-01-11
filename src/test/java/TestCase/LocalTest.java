package TestCase;

import Page.Amazon;
import Page.BsDemo;
import Setup.DriverClass;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class LocalTest extends DriverClass {

    @Test
    public void test() throws Exception {
        BsDemo bsdemo = PageFactory.initElements(getDriver(), BsDemo.class);
        bsdemo.NavigateBSDemo();
        bsdemo.filterVendorPrice();
        Map<String, String> iphonelist = bsdemo.searchResult();
        System.out.println("Details of Apple Products:");
        iphonelist.forEach((k,v)->System.out.println(k+" $"+v));
    }
}
