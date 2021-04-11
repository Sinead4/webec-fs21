package webeng.contactlist;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactsPageIT {

    @LocalServerPort
    int port;

    @Test
    void allContactListEntriesAreShown(){
        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:"+port +"/contacts");

        var contactLinks = driver.findElements(By.className("contactLinks"));  //cssSelector("main ul a"));
        assertEquals(30, contactLinks.size());

    }

    @Test
    void clickingAContactLinkShowsDetails(){
        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:"+port +"/contacts");
//        var pageobject = PageObject.create(driver, port);

        var contactLinks = driver.findElements(By.cssSelector("main ul a"));  //cssSelector("main ul a"));
        contactLinks.get(0).click();
        //beim klicken wartet er kurz bis die Seite geladen ist.
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        var firstName = driver.findElement(By.id("first-name")).getText();
        assertEquals("Mabel", firstName);


    }

    //dieser Test wird mit allen angegebenen ids ausgeführt
    @ParameterizedTest
    @ValueSource(ints = {0,1,2,5,8,15,19,22})
    void clickingAnyContactLinkShowsDetails(int id){
        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:"+port +"/contacts");

        var contactLinks = driver.findElements(By.cssSelector("main ul a"));  //cssSelector("main ul a"));
        contactLinks.get(id).click();
        //beim klicken wartet er kurz bis die Seite geladen ist.
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

        var tables = driver.findElements(By.tagName("table"));
        assertEquals(1, tables.size());



    }

}
