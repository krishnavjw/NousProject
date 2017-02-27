package Pages.Walkin_Reservation_Lease;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CancelLeasePopup {
	
	private WebDriver driver;
	
	@FindBy(id="employeeNumber")
	private WebElement empnum;
	

	@FindBy(xpath="//div[@id='cancelLeaseDialog']//a[contains(text(),'No')]")
	private WebElement cancelNoBtn;
	
	@FindBy(xpath="//div[@id='cancelLeaseDialog']//a[contains(text(),'Yes')]")
	private WebElement cancelYesBtn;
	
	
	@FindBy(xpath="//div[@id='cancelLeaseDialog']//span[contains(text(),'SELECT')]")
	private WebElement cancelReasonSel;
	
	
	public CancelLeasePopup(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void enterEmpNum(String empNum)
	{
		empnum.sendKeys(empNum);
	}
	
	public void selectCancelReason() throws InterruptedException {

		cancelReasonSel.click();
		Thread.sleep(3000);
		List<WebElement> ListWbEle = driver.findElements(By.xpath("//div[@class='k-animation-container']/div[@class='k-list-container k-popup k-group k-reset']/ul/li"));
		int Size = ListWbEle.size();
		for (int i = 0; i < Size; i++) {
			if (i == 2) {
				ListWbEle.get(i).click();
				break;
			}
		}

	}
	

	
	public void clk_CancelNoBtn(){
		cancelNoBtn.click();
	}
	
	public void clk_CancelYesBtn(){
		cancelYesBtn.click();
	}


}
