import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Tests {
    private WebDriver driver;
    private MainPage mPage;
    private WallPage wPage;
    //--------Settings-------------------
    private String testLogin = "user";
    private String testPass = "123";
    private String expectedUserName = "test test";
    private String msgToPost = "Simple text";
    //\\------Settings-------------------
    private By userLoggedNameLoc = By.cssSelector(".compact-profile-name > span:nth-child(1)");



    @Before
    public void SettingUp(){
        System.setProperty("webdriver.chrome.driver", "/opt/Selenium/chromedriver");
        driver = new ChromeDriver();
        driver.get("https://qa.sprinklebit.com/");
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.
                        invisibilityOfElementLocated
                                (By.cssSelector("#main-preloader")));
    }


    @Test
    public void loginToAccount(){
        mPage = new MainPage(driver);
        //Login
        mPage.initLoginForm()
                .typeLogin(testLogin)
                .typePassword(testPass)
                .rememberMe(false)
                .loginButtonClick();

        //Wait for new page to load.
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.
                        visibilityOfElementLocated
                                (userLoggedNameLoc));
        //Check if the page has correct user's name.
        String actualUserLoggedName = driver.findElement(userLoggedNameLoc).getText();
        Assert.assertTrue(actualUserLoggedName.contains(expectedUserName));
    }

    @Test
    public void makeAPost(){
        loginToAccount();
        wPage = new WallPage(driver);

        wPage.typePost(msgToPost)
                .submitPost();

        wPage.waitForTheMessageToAppear(msgToPost);

        Assert.assertTrue(wPage.isMessageVisible(msgToPost));
    }

    @Test
    public void deleteAPost(){
        makeAPost();

        //Delete the message.
        wPage.deletePost(msgToPost);

        Assert.assertFalse(wPage.isMessageVisible(msgToPost));

    }

    @After
    public void endConnect() {
        //driver.quit();
    }
}
