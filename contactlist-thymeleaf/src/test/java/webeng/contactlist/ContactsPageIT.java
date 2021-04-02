package webeng.contactlist;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.util.AssertionErrors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContactsPageIT {

    @LocalServerPort
    int port;

    @Test
    void allContactListEntriesAreShown(){
        var driver = new HtmlUnitDriver();
        driver.navigate().to("http://localhost:"+port +"/contacts");

        var contactLinks = driver.findElements(By.cssSelector("main ul a"));
        assertEquals(30, contactLinks.size());


    }

}
