package Pages.VacatePages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

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
	
	@FindBy(xpath="//div[@id='completeVacateDialog']//div[contains(text(),'Scheduled Vacate Date:')]/following-sibling::div")
	private WebElement sch_vacate_date;
	
	@FindBy(xpath="//div[@id='print-receipt-estimate']//span[@class='button']")
	private WebElement checkbox_printEstimateReceipt;
	
	@FindBy(xpath="//div[@id='completeVacateDialog']//input[@id='employeeNumber']")
	private WebElement employee_num;
	
	
	@FindBy(xpath="//div[contains(text(),'Customer Name:')]/following-sibling::div")
	private WebElement isCustNameDisplayed;
	
	@FindBy(xpath="//div[contains(text(),'Space Number:')]/following-sibling::div")
	private WebElement isSpaceNumDisplayed;
	
	
	@FindBy(xpath="//div[contains(text(),'Esimated Balance:')]/following-sibling::div")
	private WebElement isEstAmtDisplayed;
	
	
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
	
	public void enterEmployeeID(String empID )
	{
		empNum.sendKeys(empID);
	}
	
	public void click_Confirm()
	{
		confirm_btn.click();
	}
	
	public String getVacateDate(){
		return sch_vacate_date.getText();
	}
	
	public boolean isChecked_PrintEstimateReceipt(){
		return checkbox_printEstimateReceipt.isDisplayed();
	}
	
	public void enter_EmpNum(String text){
		employee_num.sendKeys(text);
	}
	
	public boolean isCustomerNameDisplayed(){
		
		return isCustNameDisplayed.isDisplayed();
	}
	
	public boolean isSpaceNumberDisplayed(){
		
		return isSpaceNumDisplayed.isDisplayed();
	}
	
	public boolean isEstimatedBalAmtDisplayed(){
		
		return isEstAmtDisplayed.isDisplayed();
	}
	
	public boolean isScheduleVacetDateDisplayed(){
		
		return sch_vacate_date.isDisplayed();
	}
	
}
