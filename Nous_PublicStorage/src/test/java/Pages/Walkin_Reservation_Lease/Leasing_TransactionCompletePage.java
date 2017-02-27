package Pages.Walkin_Reservation_Lease;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_TransactionCompletePage 
{
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement empNum;
	
	@FindBy(xpath="//a[contains(text(),'Ok')]")
	private WebElement ok_btn;
	
	
	public Leasing_TransactionCompletePage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void enterEmployeeNum(String num)
	{
		empNum.sendKeys(num);
	}
	
	public void clickOk_btn()
	{
		ok_btn.click();
	}

}
