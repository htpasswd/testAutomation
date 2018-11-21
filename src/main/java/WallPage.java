import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static java.lang.String.format;


public class WallPage {
    private WebDriver driver;
    private int messagesAdded = 0;

    public WallPage(WebDriver driver) {
        this.driver = driver;
    }

    private By postInput = By.xpath("//textarea[@placeholder='Share your thoughts... " +
                                                "Use @ to mention a person, $ to name stock, or add your own hashtags']");
    private By postButton = By.xpath("//div[@class='postIt btn btn-primary js_post_entry_button' " +
                                            "and text() = 'Post']");
    private By deletePostButton = By.xpath("//li[@class='delete js_delete_button ng-scope']");
    private By deletedMessage = By.xpath("//p[contains(., 'Deleted successfully')]");
    //private By postTextDummy = By.xpath("//*[@id='jsentrybox']/div[1]/div[1]"); //Contains text of a post input. Using to determine if its empty after post is submitted.

    //Xpath for selecting all posts with a certain text and "delete" button.
    private String listOwnPostsByText = "//div[contains(@class,'wall_entry_container')" +
                                            " and .//ng-bind-html/text()='%s'" +
                                            " and .//li/@class='delete js_delete_button ng-scope']";


    public WallPage typePost(String post) {
        driver.findElement(postInput).sendKeys(post);
        return this;
    }

    public boolean isMessageVisible(String message){
        return driver.findElements(By.xpath(format(listOwnPostsByText, message)))
                        .size() > 0;
    }

    public void waitForTheMessageToAppear(String messageToWait){
        By theMessagesLoc = By.xpath(format(listOwnPostsByText, messageToWait));
        new WebDriverWait(driver, 10).until(
                ExpectedConditions.
                        numberOfElementsToBeMoreThan(
                                By.xpath(format(listOwnPostsByText, messageToWait)), messagesAdded));
        ++messagesAdded;
    }

    public void submitPost() {
        driver.findElement(postButton).click();
    }

    //Deletes every post with a specified "Text" which has access to [delete] action.
    public void deletePost(String postText) {
        List<WebElement> postsAll = driver.findElements(
                                            By.xpath(format(listOwnPostsByText, postText)));

        for (WebElement postToDelete : postsAll){
            postToDelete.findElement(deletePostButton).click();
            //Wait for the deleting alert to appear.
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.
                            alertIsPresent());
            driver.switchTo()
                    .alert()
                    .accept();

            //Wait for the deleting notification to appear.
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.numberOfElementsToBeMoreThan(
                            deletedMessage, (postsAll.size() - messagesAdded)));

            --messagesAdded;
        }
    }
}