package Pages.CustDashboardPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Buy_MerchandisePage {
	
	@FindBy(xpath="//div[@id='MerchandisePage']//h3")
	public WebElement pagetitle;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[text()='All']")
	public WebElement All;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[contains(text(),'Boxes')]")
	public WebElement Boxes;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[contains(text(),'Locks')]")
	public WebElement Locks;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[contains(text(),'PAKS')]")
	public WebElement PAKS;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[contains(text(),'Covers')]")
	public WebElement covers;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[contains(text(),'Tape')]")
	public WebElement Tape;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[@class='button']/following-sibling::span[contains(text(),'Miscellaneous')]")
	public WebElement Miscellanious;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'All')]/preceding-sibling::span[@class='button']")
	public WebElement All_radiobtn;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'Boxes')]/preceding-sibling::span[@class='button']")
	public WebElement Boxes_radiobtn;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'Locks')]/preceding-sibling::span[@class='button']")
	public WebElement  Locks_radiobtn;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'PAKS')]/preceding-sibling::span[@class='button']")
	public WebElement PAKS_radiobtn;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'Covers')]/preceding-sibling::span[@class='button']")
	public WebElement Covers_radiobtn;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'Tape')]/preceding-sibling::span[@class='button']")
	public WebElement Tape_radiobtn;
	
	@FindBy(xpath="//div[@id='Merchandise']//ul/li//span[contains(text(),'Miscellaneous')]/preceding-sibling::span[@class='button']")
	public WebElement Miscellanious_radiobtn;
	
	@FindBy(xpath="(//div[@class='merchandise-description']/div[@class='floatleft product-details-container']/div[@class='title bold half-margin-bottom'])[1]")
	public WebElement itemName;
	
	@FindBy(xpath="(//div[@class='merchandise-description']/div[@class='floatleft product-details-container']/div[@class='description half-margin-bottom'])[1]")
	public WebElement itemDesc;
	
	@FindBy(xpath="(//div[@class='merchandise-description']/div[@class='floatleft product-details-container']/div[@class='product-number bold'])[1]")
	public WebElement SKU;
	
	@FindBy(xpath="(//div[@class='merchandise-description']//div[@class='available floatleft'])[1]")
	public WebElement QuantityOnHand;
	
	@FindBy(xpath="(//div[@class='merchandise-description']//div[contains(@class,'price floatright')])[1]")
	public WebElement pricePerUnit;
	
	@FindBy(xpath="(//input[@class='product-quantity webchamp-textbox'])[1]")
	public WebElement Quantity_item1;
	
	@FindBy(xpath="(//input[@class='product-quantity webchamp-textbox'])[2]")
	public WebElement Quantity_item2;
	
	@FindBy(id="MerchandiseWindow-AddToCart")
	public WebElement AddItemsToCart_btn;
	
	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']/ul/li/span[contains(text(),' Script')]")
	public WebElement Script;
	
	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']/ul/li/span[contains(text(),' Customer Screen')]")
	public WebElement customerscreen;
	
	@FindBy(xpath="//a[@id='checkOutButton']")
	public WebElement checkout_btn;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table//tbody/tr[1]/td[2]//a[@onclick='Merchandise.updateQty($(this),1)']")
	public WebElement add_item_Cart;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table//tbody/tr[2]/td[2]//a[@onclick='Merchandise.updateQty($(this),-1)']")
	public WebElement subtarct_item_Cart;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table//tbody/tr/td[4]//a[contains(text(),'Remove')]")
	public WebElement Remove;
	
	@FindBy(xpath="//div[@id='merchandiseCart']//table//tbody/tr[1]/td[4]//a[contains(text(),'Remove')]")
	public WebElement firstRemovelink;
	
	@FindBy(id="clearCart")
	public WebElement RemoveAll;
	
	@FindBy(xpath="//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y-after']")
	public WebElement scroll_down;
	
	@FindBy(xpath="//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y-before']")
	public WebElement scroll_Up;

	private WebDriver driver;
	
	
	

	public Buy_MerchandisePage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);

	}
	
	
	public boolean verify_pagetitle()
	{
		return pagetitle.isDisplayed();
	}
	
	public boolean getAttribute_LocationRadio()
    {
          return Locks_radiobtn.isSelected();
    }

	public void click_All_radiobtn(){
		All_radiobtn.click();
	}
	
	public boolean verify_itemName()
	{
		return itemName.isDisplayed();
	}
	
	public String gettxt_itemName()
    {
          return itemName.getText();
    }
	
	public boolean verify_itemDesc()
	{
		return itemDesc.isDisplayed();
	}
	
	public String gettxt_itemDesc()
    {
          return itemDesc.getText();
    }
	
	public boolean verify_SKU()
	{
		return SKU.isDisplayed();
	}
	
	public String gettxt_SKU()
    {
          return SKU.getText();
    }
	
	public boolean verify_QuantityOnHand()
	{
		return QuantityOnHand.isDisplayed();
	}
	
	public String gettxt_QuantityOnHand()
    {
          return QuantityOnHand.getText();
    }
	
	public boolean verify_pricePerUnit()
	{
		return pricePerUnit.isDisplayed();
	}
	
	public String gettxt_pricePerUnit()
    {
          return pricePerUnit.getText();
    }

	
	public void enter_Quantity_item1(String val){
		Quantity_item1.sendKeys(val);
	}
	
	public void enter_Quantity_item2(String val){
		Quantity_item2.sendKeys(val);
	}
	
	public void click_AddItemsToCart_btn(){
		AddItemsToCart_btn.click();
	}
	
	public void click_checkout_btn(){
		checkout_btn.click();
	}
	
	public boolean verify_Script()
	{
		return Script.isDisplayed();
	}
	
	public boolean verify_customerscreen()
	{
		return customerscreen.isDisplayed();
	}
	
	public void click_add_item(){
		add_item_Cart.click();
	}
	
	public void click_subtarct_item(){
		subtarct_item_Cart.click();
	}
	
	public boolean verify_RemoveLink()
	{
		return Remove.isDisplayed();
	}
	
	public boolean verify_RemoveAllLink()
	{
		return RemoveAll.isDisplayed();
	}
	
	public void click_RemoveLink(){
		firstRemovelink.click();
	}
	
	public void click_RemoveAllLink(){
		RemoveAll.click();
	}
	
	
	public void scrolling(){
		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar = driver.findElement(By.xpath("//div[@class='ps-scrollbar-y-rail']/div[@class='ps-scrollbar-y']"));
		int numberOfPixelsToDragTheScrollbarDown = 5000;
		dragger.moveToElement(draggablePartOfScrollbar).clickAndHold().moveByOffset(0,numberOfPixelsToDragTheScrollbarDown).release().perform();
		
	}
	
	
}
