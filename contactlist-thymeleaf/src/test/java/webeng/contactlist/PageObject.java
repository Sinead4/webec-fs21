package webeng.contactlist;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class PageObject {

    public static PageObject create(WebDriver driver, int port){
        driver.navigate().to("http://localhost:" +port + "/");
        return PageFactory.initElements(driver, PageObject.class);
    }


}
