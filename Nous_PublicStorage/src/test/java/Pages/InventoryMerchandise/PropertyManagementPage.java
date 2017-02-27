package Pages.InventoryMerchandise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyManagementPage 
{
	@FindBy(id="btnInventoryTransfer")
	private WebElement inventoryTrans;
	
	public PropertyManagementPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void clickInventoryTrans()
	{
		inventoryTrans.click();
	}

}
