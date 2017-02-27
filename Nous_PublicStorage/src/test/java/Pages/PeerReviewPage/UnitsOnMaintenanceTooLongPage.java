package Pages.PeerReviewPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class UnitsOnMaintenanceTooLongPage 
{
	

	@FindBy(xpath = "//button[@id='btnSubmit']")
	private WebElement submit_Btn;
	
	@FindBy(xpath = "//a[@class='psbutton-priority margin-left ok-button']")
	private WebElement ok_Btn;
	
	@FindBy(xpath="//div[@id='questions']/div//div[contains(text(),'Question')]")
	private WebElement questions_Field;
	
	@FindBy(xpath="//div[@id='questions']/div//div[contains(text(),'Response')]")
	private WebElement response_Field;
	
	@FindBy(xpath="//div[contains(text(),'Response must contain')]")
	private WebElement warning_Answer;
	
	@FindBy(name="txtReponse")
	List<WebElement> response_TextArea;
	
    
    @FindBy(xpath="//div[@id='questions']//span[contains(text(),'you have done differently?')]/../following-sibling::div//textarea[@class='responseText']")
    private WebElement secQuestxtArea;
    
    @FindBy(xpath="//div[label[contains(text(),'Certified')]]/input")
    private WebElement certified_checkBox;
	
	@FindBy(xpath="//h3[text()='Units on Maintenance Too Long']")
	private WebElement unitMainTitle;
	
	@FindBy(xpath = "//div[@id='alerts']//div[@class='assignedInfo']//div/label[contains(text(),'Assigned to:')]")
	private WebElement assignedTo;
	
	@FindBy(xpath = "//div[@id='alerts']//div[@class='assignedInfo']//div/label[contains(text(),'Assigned to:')]/../following-sibling::div/label[@class='title']")
	private WebElement assignedToUser;
	
	
	@FindBy(xpath = "//div[@id='questions']//div//label[contains(text(),'Completed')]/preceding-sibling::input")
	private WebElement completedRadio_Btn;

	
	@FindBy(xpath = "//textarea[@id='Comment']")
	private WebElement comments_TxtField;
	
	@FindBy(xpath="//div[@id='questions']//span[contains(text(),'units on maintenance too long?')]/../following-sibling::div//textarea[@class='responseText']")
	private WebElement firstQuestxtArea;
	
	

	public UnitsOnMaintenanceTooLongPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public boolean verifyUnitMaintanceTitle()
	{
		return unitMainTitle.isDisplayed();
	}
	
	
	public boolean verify_WarningMessage()
	{
		return warning_Answer.isDisplayed();
	}
	
	
	public boolean verifyAssignedToField(){
		return assignedTo.isDisplayed();
	}
	
	public void clk_CompletedRadio_Btn(){
		completedRadio_Btn.click();
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
	
	public boolean verifyFirstQuesTxtArea()
	{
		return firstQuestxtArea.isDisplayed();
	}
	
	public void enterFirstQuesTxtArea(String val)
	{
		 firstQuestxtArea.sendKeys(val);
	}
	
	public boolean verifySecondQuesTxtArea()
	{
		return secQuestxtArea.isDisplayed();
	}
	
	public void enterSecondQuesTxtArea(String val)
	{
		secQuestxtArea.sendKeys(val);
	}
	
	  
    public void enable_CertifiedCheckbox()
    {
    	certified_checkBox.click();
    }
    
    public String getAssignedUser()
    {
    	return assignedToUser.getText();
    }

}
