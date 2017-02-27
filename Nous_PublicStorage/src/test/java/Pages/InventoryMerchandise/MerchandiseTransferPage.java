package Pages.InventoryMerchandise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MerchandiseTransferPage 
{
	@FindBy(xpath="//div[@id='propertyManagement']//h3[text()='Merchandise Transfer']")
	private WebElement merchndiseTransTitle;
	
	@FindBy(xpath="//div[@id='propertyManagement']//h3[text()='Transfer To']")
	private WebElement transferTo;
	
	@FindBy(xpath="//span[text()='Select Property Name']")
	private WebElement transferTodropdown;
	
	@FindBy(xpath="//div[@id='propertyManagement']//span[@aria-owns='InventoryTransfer_TransferTo_listbox']//span[@class='k-input']")
	private WebElement TransToValue;
	
	@FindBy(xpath="//div[@class='action-container command-row']//button[text()='Submit']")
	private WebElement submitbtn;
	
	//table/tbody//tr//td//span[text()='Select Product']
	
	public MerchandiseTransferPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public boolean verifyMerchanideTitle()
	{
		return merchndiseTransTitle.isDisplayed();
	}
	
	public boolean verifyTransferTofield()
	{
		return transferTo.isDisplayed();
	}
	
	public void clickTransferTodropdown()
	{
		transferTodropdown.click();
	}
	
	public String getPropertValue()
	{
		return TransToValue.getText().substring(0, 5);
	}
	
	public void clickSubmitbtn()
	{
		submitbtn.click();
	}

}
