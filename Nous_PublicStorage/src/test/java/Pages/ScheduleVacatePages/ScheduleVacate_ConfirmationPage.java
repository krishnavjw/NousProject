package Pages.ScheduleVacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ScheduleVacate_ConfirmationPage 
{
	@FindBy(id="completeVacateDialog_wnd_title")
    //xpath="//div[@class='k-window-titlebar k-header']/span[@id='completeVacateDialog_wnd_title']"
	private WebElement confir_Title;
	
	@FindBy(xpath="//div[@id='completeVacateDialog']//div[contains(text(),'Customer Name:')]/following-sibling::div")
	private WebElement cust_Name;
	
	
		
	@FindBy(xpath="//div[@id='completeVacateDialog']//div[contains(text(),'Account Number:')]/following-sibling::div")
	private WebElement acc_Num;
	
	@FindBy(xpath="//div[@id='completeVacateDialog']//div[contains(text(),'Total Estimated Balance:')]/following-sibling::div")
	private WebElement total_Balance;
	
	@FindBy(id="noteText")
     private WebElement noteText;
	
	@FindBy(id="employeeNumber")
	private WebElement empNum;
	
	@FindBy(id="cancel-vacate-button")
	private WebElement cancel_btn;
	
	@FindBy(id="create-vacate-button")
	private WebElement confirm_btn;
	
	public ScheduleVacate_ConfirmationPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public String verifyConfirm_Title()
	{
		return confir_Title.getText();
	}
	
	public String getCustName()
	{
		return cust_Name.getText();
	}
	
	public String getAccNumber()
	{
		return acc_Num.getText();
	}
	
	public String getTotalBalance()
	{
		return total_Balance.getText();
	}
	
	public void enterNote(String note)
	{
		noteText.sendKeys(note);
	}
	
}
