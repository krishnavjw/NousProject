package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class AddDepositPage {

WebDriver driver;

@FindBy(xpath="//div[@class='page-header container-heading']//h3[text()='Add Deposit']")
private WebElement get_AddDepositTxt;

@FindBy(xpath="//div[@class='payment-row__detail']//span[text()='Select']")
private WebElement clk_PymtDrpDwn;

@FindBy(xpath="//ul[@class='k-list k-reset ps-container']/li")
private List<WebElement> ls_DepositAmt;

@FindBy(xpath="//div[@class='payment-methods__method payment-method-form payment-method-form--credit-card']//a[text()='Enter Manually']")
private WebElement btn_EnterManually;

@FindBy(xpath="//span[contains(text(),'Deposit Amount')]/following-sibling::span")
private WebElement Txt_DepAmt;

@FindBy(xpath="//input[@id='cashAmount']")
private WebElement Txt_Amount;

@FindBy(xpath="//div[@class='payment-method-form__apply--cash floatright']/a")
private WebElement Btn_Apply;

@FindBy(xpath="//div[@class='command-row']//a[@id='confirmButton']")				//(xpath="//a[contains(text(),'Create Reservation')][@id='submitDeposit']")
private WebElement Btn_CreateReservation;

public AddDepositPage(WebDriver driver){
	
	//this.driver=driver;
	PageFactory.initElements(driver, this);
}

public String get_AddDepositTxt(){
	
	
	return get_AddDepositTxt.getText();
}

public void ClickOn_EnterManuallyButton(){
	
	
	btn_EnterManually.click();
}


public String get_DepositAmtReq()
{
	String DepAmt = Txt_DepAmt.getText();
	return DepAmt;
}


public void enter_Amount(String Amt)
{
	Txt_Amount.clear();
	Txt_Amount.sendKeys(Amt);
}


public void ClickOnApplyBtn()
{
	Btn_Apply.click();
}

public void ClickOnCreateReservationBtn()
{
	Btn_CreateReservation.click();
}

public void sel_PaymentTypeFrmDrpDwn(String value) throws InterruptedException{
	clk_PymtDrpDwn.click();
	Thread.sleep(3000);
	List<WebElement> lsDepAmt = ls_DepositAmt;
	for(int i=0;i<lsDepAmt.size();i++)
	{
		String DepAmt = lsDepAmt.get(i).getText().trim();
		Reporter.log("DepAmt:"+DepAmt,true);
		//Thread.sleep(3000);
		if(DepAmt.contains(value))
		{
			lsDepAmt.get(i).click();
		break;
		}
	}
	
	}
}
