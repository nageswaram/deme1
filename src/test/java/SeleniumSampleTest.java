
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class SeleniumSampleTest extends BaseTest{

        @Test
        public void testGoogle() {
            System.out.println("<- TestLambdaTestPlayground -> Executed by Thread : " + Thread.currentThread().getId()
                    + " on driver reference : " + getDriver());
            getDriver().get("https://www.lambdatest.com/selenium-playground/");
            System.out.println("Title printed by Thread : " + Thread.currentThread().getId() + " <- "
                    + getDriver().getTitle() + " -> on driver reference : " + getDriver());
        }


        @Test
        public void testDropDown(){
            getDriver().get("https://www.selenium.dev/selenium/web/formPage.html");
            WebElement selectElement = getDriver().findElement(By.name("selectomatic"));
            Select select = new Select(selectElement);

            WebElement oneEle = getDriver().findElement(By.cssSelector("option[value='one']"));

            select.getOptions().stream().map(s -> s.getText()).forEach(System.out::println);

            select.selectByValue("two");

            System.out.println(select.getFirstSelectedOption().getText());
            System.out.println( );

            select.selectByIndex(0);
            System.out.println(select.getFirstSelectedOption().getText());
            System.out.println(oneEle.isSelected());
        }

        @Test
        public void testWindows(){

            getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            // Navigate to Url
            getDriver().get("https://www.selenium.dev/selenium/web/window_switching_tests/page_with_frame.html");
            //fetch handle of this
            String currHandle=getDriver().getWindowHandle();
            SoftAssert soft = new SoftAssert();
            //assertNotNull(currHandle);
            soft.assertNull(currHandle);

            //click on link to open a new window
            getDriver().findElement(By.linkText("Open new window")).click();
            //fetch handles of all windows, there will be two, [0]- default, [1] - new window
            Object[] windowHandles=getDriver().getWindowHandles().toArray();
            getDriver().switchTo().window((String) windowHandles[1]);
            //assert on title of new window
            String title=getDriver().getTitle();
            //assertEquals("Simple Page",title);
            soft.assertEquals("Simp", title);

            //closing current window
            getDriver().close();
            //Switch back to the old tab or window
            getDriver().switchTo().window((String) windowHandles[0]);

            //Opens a new tab and switches to new tab
            getDriver().switchTo().newWindow(WindowType.TAB);
            //assertEquals("",getDriver().getTitle());
            soft.assertEquals("",getDriver().getTitle());

            //Opens a new window and switches to new window
            getDriver().switchTo().newWindow(WindowType.WINDOW);
            assertEquals("",getDriver().getTitle());
            soft.assertNotEquals("",getDriver().getTitle());

            soft.assertAll();
        }

        @Test
        public void testDialogBoxes(){
            getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            // Navigate to Url
            getDriver().get("https://www.globalsqa.com/demo-site/dialog-boxes/");

            getDriver().switchTo().frame(getDriver().findElement(By.className("demo-frame")));

            getDriver().findElement(By.id("create-user")).click();
            getDriver().findElement(By.id("name")).clear();
            getDriver().findElement(By.id("name")).sendKeys("nageswara mundla");
            getDriver().findElement(By.id("email")).clear();
            getDriver().findElement(By.id("email")).sendKeys("nagtest@yopmail.com");
            getDriver().findElement(By.id("password")).clear();
            getDriver().findElement(By.id("password")).sendKeys("test123");

            getDriver().findElement(By.xpath("//button[text()='Create an account']")).click();

            Reporter.log("Hello world - Selenium sample Test");
            Reporter.log("<img src=\"file://screenshot.png\" />");


            Wait<WebDriver> wait = new FluentWait<>(getDriver())
                    .withTimeout(Duration.ofSeconds(3))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);
        }

}
