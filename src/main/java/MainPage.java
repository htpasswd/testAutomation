import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;

    public MainPage(WebDriver driver){
        this.driver = driver;
    }

    private By mainPreloader = By.cssSelector("#main-preloader");
    private By loginLink = By.cssSelector("a.open-login-form");
    private By loginInput = By.cssSelector("#_username");
    private By passwordInput = By.cssSelector("#_password");
    private By rememberCheckBox = By.cssSelector("#_remember_me");
    private By loginButton = By.cssSelector("#login-btn");

    public MainPage initLoginForm(){
        WebElement element = driver.findElement(loginLink);
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        new WebDriverWait(driver, 5).until(
                                                        ExpectedConditions.
                                                                visibilityOfElementLocated
                                                                    (loginInput));
        return this;
    }

    public MainPage typeLogin(String login){
        driver.findElement(loginInput).sendKeys(login);
        return this;
    }

    public MainPage typePassword(String passwd){
        driver.findElement(passwordInput).sendKeys(passwd);
        return this;
    }

    public MainPage rememberMe(boolean value){
        WebElement rememberCheckB = driver.findElement(rememberCheckBox);
        if(!rememberCheckB.isSelected() == value){
            rememberCheckB.click();
        }
        return this;
    }

    public MainPage loginButtonClick(){
        new WebDriverWait(driver, 5).until(
                ExpectedConditions.
                        elementToBeClickable
                                (loginButton));
        driver.findElement(loginButton).click();
        return this;
    }


}
