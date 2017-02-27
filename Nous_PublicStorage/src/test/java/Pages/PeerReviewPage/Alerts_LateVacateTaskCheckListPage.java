package Pages.PeerReviewPage;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Alerts_LateVacateTaskCheckListPage 
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
	
    @FindBy(xpath="//div[@id='questions']//span[contains(text(),'When do you believe the customer')]/../following-sibling::div//textarea[@class='responseText']")
    private WebElement firstQuestxtArea;
    
    @FindBy(xpath="//div[@id='questions']//span[contains(text(),'Enter the number 1-6')]/../following-sibling::div//textarea[@class='responseText']")
    private WebElement secQuestxtArea;
    
    @FindBy(xpath="//div[@id='questions']//span[contains(text(),'you have done differently?')]/../following-sibling::div//textarea[@class='responseText']")
    private WebElement thirdQuestxtArea;
    
    @FindBy(xpath="//div[label[contains(text(),'Certified')]]/input")
    private WebElement certified_checkBox;
    
    @FindBy(xpath="//div[@class='assignedInfo']/div/div[2]/label[1]")
    private WebElement assignedto_EmpName;
    
    
    @FindBy(xpath="//div[@class='assignedInfo']/div/div[4]/label[3]/span")
    private WebElement label_LateIndicator;
    
    @FindBy(xpath="//div[@class='assignedInfo']/div/div[2]/label[3]")
    private WebElement label_dueDate;
    
    @FindBy(xpath="//div[@class='assignedInfo']/div/div[3]/label[3]")
    private WebElement label_dueTime;
	
	
	public Alerts_LateVacateTaskCheckListPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
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
	
	public boolean verify_Questions()
	{
		return questions_Field.isDisplayed();
	}
	
	public boolean verify_Response()
	{
		return response_Field.isDisplayed();
	}
	
	public boolean verify_WarningMessage()
	{
		return warning_Answer.isDisplayed();
	}
	
	public void enter_response_Text(String str)
	{
		for(WebElement ele:response_TextArea)
		{
			ele.clear();
			ele.sendKeys(str);
		}
		
		
		
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
    
    public boolean verifyThirdQuesTxtArea()
    {
           return thirdQuestxtArea.isDisplayed();
    }
    
    public void enterSecondQuesTxtArea(String val)
    {
           secQuestxtArea.sendKeys(val);
    }
    
    public void enable_CertifiedCheckbox()
    {
    	certified_checkBox.click();
    }
    
    public boolean verify_assignedtoEmployee(){
    	return assignedto_EmpName.isDisplayed();
    }
    
    public String assignedto_EmpName()
    {
    	return assignedto_EmpName.getText();
    }
    
    public boolean verify_lateIndicator()
    {
    	return label_LateIndicator.isDisplayed();
    }
    
    public boolean verify_dueDate()
    {
    	return label_dueDate.isDisplayed();
    }
    
    
    public String get_DueDate()
    {
    	return label_dueDate.getText();
    }
    
    public String get_DueTime()
    {
    	return label_dueTime.getText();
    }






}
