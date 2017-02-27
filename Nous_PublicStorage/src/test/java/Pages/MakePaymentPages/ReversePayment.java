package Pages.MakePaymentPages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class ReversePayment {

	@FindBy(xpath="//div[@id='lease-payment']/div/h3[contains(text(),'Reverse Payment')]")
	private WebElement reversalTitle;

	@FindBy(xpath="//span[text()='No']/preceding-sibling::span")
	private WebElement clk_NoRadioBtn;

	@FindBy(xpath="//span[text()='Yes']/preceding-sibling::span")
	private WebElement clk_YesRadioBtn;


	@FindBy(xpath="//span[text()='Select']")
	private WebElement Slc_Reason;

	@FindBy(xpath="//div[@id='Reason-list']/ul/li[@class='k-item']")
	private List<WebElement> ls_Reason; 

	@FindBy(xpath="//textarea[@id='note-text']")
	private WebElement Txt_Note;

	@FindBy(xpath="//button[@id='reverseButton']")
	private WebElement Btn_Reverse;

	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement Txt_EmpID;


	@FindBy(xpath="(//label[@class='webchamp-checkbox']/span[@class='button'])[2]")
	private WebElement ChkBx_RefundedCash;


	@FindBy(xpath="//a[contains(text(),'Ok')]")
	public WebElement Btn_Ok;


	@FindBy(id="confirmButton")
	private WebElement clk_ConfirmWithCustomerBtn;

	@FindBy(linkText="Approve")
	private WebElement clk_ApproveBtn;

	public ReversePayment(WebDriver driver)
	{
		PageFactory.initElements(driver, this);

	}

	public boolean verifyReservePaymentTitle()
	{
		return reversalTitle.isDisplayed();
	}




	public void Clk_RdBtn_Yes()
	{
		clk_YesRadioBtn.click();
	}


	public void Clk_RdBtn_No()
	{
		clk_NoRadioBtn.click();
	}

	public void Clk_ReasonDrpDwn()
	{
		Slc_Reason.click();
	}

	public boolean SelectValueFromReasonList(String Reason) throws InterruptedException
	{
		Boolean b=false;
		//Clk_ReasonDrpDwn();
		Thread.sleep(1000);
		for(int i=0;i<ls_Reason.size();i++)
		{
			String ActTxt=ls_Reason.get(i).getText();
			Reporter.log("ActTxt: " + ActTxt,true);
			if(ls_Reason.get(i).getText().contains(Reason))
			{
				ls_Reason.get(i).click();
				b=true;
				break;
			}

		}

		return b;
	}

	public void selectReason(String value)
	{
		for(WebElement ele: ls_Reason)
		{
			if(ele.getText().trim().equalsIgnoreCase(value)){
				ele.click();
				break;
			}
		}

	}


	public void enterNote(String note)
	{
		Txt_Note.sendKeys(note);
	}


	public void Clk_RevBtn()
	{
		Btn_Reverse.click();
	}


	public void enter_EmpID(String EmpID)
	{
		Txt_EmpID.sendKeys(EmpID);
	}

	public void Clk_RefCash()
	{
		ChkBx_RefundedCash.click();
	}

	public void Clk_Btn_Ok()
	{
		Btn_Ok.click();
	}

	public void clk_ConfirmWithCustomerBtn(){

		clk_ConfirmWithCustomerBtn.click();
	}

	public void clk_ApproveBtn(){

		clk_ApproveBtn.click();
	}


}
