package Pages.AWB;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PropertyManagementPage {

	WebDriver driver;


	@FindBy(xpath="//h3[contains(text(),'Property Management')]")
	private WebElement pagetitle;

	@FindBy(xpath="//div[@id='flowsList']//a[contains(text(),'Auction Management')]")
	private WebElement auctionManagementLink;


	@FindBy(linkText="Back to Dashboard")
	private WebElement backToDashboard_btn;

	@FindBy(xpath="//a[text()='Inventory Receive']")
	private WebElement InventoryReceiveLnk;
	
	@FindBy(xpath="//a[text()='Inventory Management']")
	private WebElement invMgmt;

	public PropertyManagementPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean verify_PageTitle(){
		return pagetitle.isDisplayed();
	}

	public void click_AuctionManagementLink(){
		auctionManagementLink.click();
	}


	public void click_BackToDashboard_btn(){
		backToDashboard_btn.click();
	}

	public void clk_InventoryReceiveLink(){

		InventoryReceiveLnk.click();
	}
	
	public void click_InvMgmt()
	{
		invMgmt.click();
	}
}
