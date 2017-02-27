package Pages.PeerReviewPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PeerReviewChecklistPage {
	
	@FindBy(xpath = "//div[@id='questions']//div//label[contains(text(),'Completed')]/preceding-sibling::input")
	private List<WebElement> completedRadio_Btn;
	
	@FindBy(xpath = "//button[@id='btnSubmit']")
	private WebElement submit_Btn;
	
	@FindBy(xpath = "//a[@class='psbutton-priority margin-left ok-button']")
	private WebElement ok_Btn;
	
	@FindBy(xpath = "//textarea[@id='Comment']")
	private WebElement comments_TxtField;
	
	@FindBy(xpath = "//div[@id='alerts']//div[@class='assignedInfo']//div/label[contains(text(),'Assigned to:')]")
    private WebElement assignedTo;
    
    @FindBy(xpath = "//div[@id='alerts']//div[@class='assignedInfo']//div/label[contains(text(),'Assigned to:')]/../following-sibling::div/label[@class='title']")
    private WebElement assignedToUser;
    
    
    @FindBy(xpath ="//div[@id='alerts']//div[@class='descLabel']/following-sibling::div[@class='desc']")
    private WebElement descriptionField;

    @FindBy(xpath = "(//div[@id='alerts']//div[@class='assignedInfo']//div/label[contains(text(),'Task Created:')]/../following-sibling::div//label[@class='title text-align-left'])[1]")
    private WebElement taskCreatedValue;
    
    @FindBy(xpath = "(//div[@id='alerts']//div[@class='assignedInfo']//div/label[contains(text(),'Task Created:')]/../following-sibling::div//label[@class='title text-align-left'])[2]")
    private WebElement taskDueValue;
    
	
	public PeerReviewChecklistPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	
	public boolean verifyAssignedToField(){
		return assignedTo.isDisplayed();
	}
	
	public void clk_CompletedRadio_Btn(){
		completedRadio_Btn.get(0).click();
	}
	
	public boolean verify_CompletedRadio_Btn(){
		if(completedRadio_Btn.size()>0){
			return true;
		}else{
			return false;
		}
		
	}


	public void clk_Submit_Btn(){
		submit_Btn.click();
	}
	
	public void clk_OkBtn(){
		ok_Btn.click();
	}
	
	public boolean verify_OkBtn(){
		return ok_Btn.isDisplayed();
	}
	
	public void enter_CommentsTxtField(String value){
		comments_TxtField.sendKeys(value);
	}
	
	public String getAssignedUser()
    {
       return assignedToUser.getText().trim();
    }
	
	public String getDescriptionValue()
    {
       return descriptionField.getText().trim();
    }
	
	
	public String getTaskCreatedValue()
    {
       return taskCreatedValue.getText().trim();
    }
	
	public String getTaskDueValue()
    {
       return taskDueValue.getText().trim();
    }
	
	
	
	
	

}
