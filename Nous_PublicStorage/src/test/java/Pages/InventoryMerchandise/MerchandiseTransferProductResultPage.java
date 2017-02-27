package Pages.InventoryMerchandise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MerchandiseTransferProductResultPage 
{
	@FindBy(xpath="//a[contains(text(),'Ok')]")
	private WebElement ok_Button;

	public MerchandiseTransferProductResultPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}

	

}
