package Pages.InventoryMerchandise;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReceiveTransferredMerchandisePage {

	@FindBy(xpath="//div/h3[text()='Receive Transferred Merchandise']")
	private WebElement pageTitle;

	@FindBy(xpath="//h3[text()='Transferring From']")
	private WebElement TransferringFromText;

	@FindBy(xpath="//h3[text()='Transferring From']/following-sibling::div/span[@class='k-widget k-dropdown k-header k-ddl']")
	private WebElement TransferringFromDropDwn;
	
	@FindBy(xpath="//button[text()='Accept']")
	private WebElement accptBtn;

	public ReceiveTransferredMerchandisePage(WebDriver driver){

		PageFactory.initElements(driver, this);
	}


	public boolean isReceiveTransferredMerchandiseTitleDiplayed(){

		return pageTitle.isDisplayed();
	}

	public boolean isTransferringFromDropDwnDisplayed(){

		return TransferringFromText.isDisplayed();
	}

	public void clk_TransferringFromDropDown(){

		TransferringFromDropDwn.click();
	}
	
	public void clk_accptBtn(){
		
		accptBtn.click();
	}


	public void selectValFromTransferringFromDropDwn(String input,WebDriver driver) throws Exception{
		clk_TransferringFromDropDown();
		Thread.sleep(1000);
		List<WebElement> ele=driver.findElements(By.xpath("//ul[@id='InventoryReceive_ReceiveFrom_listbox']/li"));
		Thread.sleep(1000);
		for(WebElement option:ele){	
		String value=option.getText().trim();
		if(value.contains(input)){
			option.click();
			break;
		}

		}
	}


}
