package Pages.PropertyManagementPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyManagementPage 
{
	
	protected WebDriver driver;
	
	@FindBy(xpath="//h3[contains(text(),'Property Management')]")
	private WebElement pagetitle;
	
	@FindBy(xpath="//div[@id='flowsList']//a[text()='Property Access Management']")
	private WebElement propAccessMgmt;
	
	@FindBy(xpath="//div[@id='flowsList']//a[text()='EOD Processing']")
	private WebElement EODProcessing;
	
	@FindBy(xpath="//div[@id='flowsList']//a[text()='Space Status']")
	private WebElement spaceStatus;
	
	@FindBy(xpath="//a[text()='Receive Merchandise']") //Inventory Delivery
	private WebElement InventoryDelivery_Btn;
	
	@FindBy(xpath="//a[text()='Inventory Management']")
	private WebElement inventoryManagement;
	
	@FindBy(xpath="//div[contains(text(),'Property Number:')]/span[@class='k-widget k-dropdown k-header']")
	private WebElement dropdown_PropertyNum;
	
	@FindBy(partialLinkText="OK")
	private WebElement okBtn;
	
	public PropertyManagementPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
		this.driver = driver;
	}
	
	public void click_OkBtn(){
		okBtn.click();
	}

	
	public void select_PropNum(String siteNum) throws InterruptedException{
		dropdown_PropertyNum.click();
		Thread.sleep(3000);
		
		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar;
		String getSiteNum = "";
		
		List<WebElement> options_dropdown_PropertyNum = driver.findElements(By.xpath("//ul[@id='spaceStatusSelectPropertyNumberDropDown_listbox']/li"));
		for(int i=0; i<options_dropdown_PropertyNum.size(); i++){
			
			if(i>4){
				draggablePartOfScrollbar = driver.findElement(By.xpath("//ul[@id='spaceStatusSelectPropertyNumberDropDown_listbox']/div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
				dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0, 10).release().build().perform();
				Thread.sleep(1000);
			}
			
			getSiteNum = options_dropdown_PropertyNum.get(i).getText().trim();
			if(getSiteNum.equals(siteNum)){
				options_dropdown_PropertyNum.get(i).click();
				break;
			}
		}
		
	}
	
	
	public void click_InventoryManagement(){
		inventoryManagement.click();
	}
	public void clickPropAccessMgmt()
	{
		propAccessMgmt.click();
	}
	
	public void clickEODProcessing()
	{
		EODProcessing.click();
	}

	public void clickspaceStatus()
	{
		spaceStatus.click();
	}
	
	
	public void click_InventoryDelivery_Btn(){
		InventoryDelivery_Btn.click();
	}

	
	
	public boolean verify_PageTitle(){
		return pagetitle.isDisplayed();
	}
}
