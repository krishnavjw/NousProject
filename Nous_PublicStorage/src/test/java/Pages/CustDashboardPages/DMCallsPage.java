package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class DMCallsPage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//h3[starts-with(text(),'Issue')]")
	private WebElement pagetitle;
	
	@FindBy(xpath="(//div[@id='call-screen-container']//span[@class='button'])[1]")
	private WebElement phncheckbx;
	
	@FindBy(xpath="(//div[@id='call-screen-container']//span[text()='Select Result'])[1]")
	private WebElement resultDropdown;
	
	@FindBy(xpath="(//div[@id='call-screen-container']//input[@type='text'])[1]")
	private WebElement commentTxt;
	
	@FindBy(id="saveCallButton")
	private WebElement submitCall;
	
	@FindBy(id="cancelButton")
	private WebElement cancelBtn;
	
	@FindBy(id="employeeNumber")
	private WebElement empNum;
	
	@FindBy(xpath="//a[contains(text(),'Save & Close')]")
	private WebElement saveCloseBtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancelBtn1;
	
	
	public DMCallsPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);

	}
	
	public boolean verify_Title(){
		return pagetitle.isDisplayed();
	}
	
	public void clk_PhnChkBx(){
		phncheckbx.click();
	}
	
	public void sel_DropDownValFromReason(String value) throws InterruptedException{
		resultDropdown.click();
		Thread.sleep(2000);
		List<WebElement> lsReason = driver.findElements(By.xpath("//div[@class='k-animation-container']/div/ul/li"));
		for(int i=0;i<lsReason.size();i++)
		{
			String Reason = lsReason.get(i).getText().trim();
			Reporter.log("Reason :"+Reason,true);
			if(Reason.equalsIgnoreCase(value))
			{
				lsReason.get(i).click();
				break;
			}
		}

	}
	
	
	public void enterComment(String data){
		commentTxt.sendKeys(data);
	}
	
	public void clk_SubmitCallsBtn(){
		submitCall.click();
	}
	
	public void clk_Cancelbtn(){
		cancelBtn.click();
	}
	
	public void enterEmpNum(String data){
		empNum.sendKeys(data);
	}
	
	public void clk_saveCloseBtn(){
		saveCloseBtn.click();
	}
	
	public void clk_CancelBtn1(){
		cancelBtn1.click();
	}
	
}
