package Pages.InventoryMerchandise;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryDelivery {

	private static final String String = null;

	WebDriver driver;

	@FindBy(xpath = "//h3[text()='Receive Merchandise']") //Inventory Delivery
	private WebElement InventoryDelivery_Title;

	@FindBy(xpath = "//div[contains(text(),'Open Purchase Orders:')]//following-sibling::span//span[text()='Select Purchase Order']")
	private WebElement select_PurchaseOrder_defaultValue;

	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//div/table/thead/tr/th[text()='Line Item #']")
	private WebElement LineItem_Header;

	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//div/table/thead/tr/th[text()='Product ID/SKU']")
	private WebElement ProductID_Header;

	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//div/table/thead/tr/th[text()='Item Name']")
	private WebElement ItemName_Header;

	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//div/table/thead/tr/th[text()='Ordered Qty.']")
	private WebElement OrderdQty_Header;

	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//div/table/thead/tr/th[text()='Received Qty.']")
	private WebElement ReceivedQty_Header;

	@FindBy(xpath = "//textarea[@id='commentsTextArea']")
	private WebElement txt_Comments;

	@FindBy(xpath = "//div[@id='submitQuantities']")
	private WebElement btn_Submit;

	@FindBy(xpath = "//div[contains(text(),'Quantity at line ')]")
	private WebElement ErrorMsg;

	@FindBy(xpath = "//div[@class='command-row clearfix-container']/a[@class='psbutton-priority margin-left ok-button floatright']")
	private WebElement OK_btn;
	
	
	@FindBy(xpath = "//a[@class='psbutton-priority margin-left ok-button']")
	private WebElement OKbtn_ErrorMsg;
	
	
	
	
	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input")
	private WebElement Enter_Qty;
	
	@FindBy(xpath = "//div[contains(text(),'Comments are required.')]")
	private WebElement ErrorMessage_Comments;
	
	
	@FindBy(xpath = "//input[@id='employeeNumber']")
	private WebElement EmployeeID;
	
	@FindBy(xpath = "//div[@class='command-row clearfix-container']//a[@class='psbutton-low-priority cancel-button floatright']")
	private WebElement Cancel_Btn;
	
	@FindBy(xpath = "//span[text()='Receive Merchandise Complete']") // Inventory Delivery Complete
	private WebElement InventoryDeliveryComplete_Header;
	
	@FindBy(xpath = "//div[@class='input-validation-message']/span")
	private WebElement EMPID_Errormessage;
	
	@FindBy(xpath = "//div[@id='purchase-order-items-grid']//div[@class='k-grid-content ps-container']/table//tr/td[2]")
	private List<WebElement> productIds;
	
	@FindBy(xpath = "//input[@id='employeeNumber']")
	private WebElement employeeId;
	
	@FindBy(xpath = "//a[contains(text(),'Ok')]")
	private WebElement OkBtn;
	

	public InventoryDelivery(WebDriver driver)

	{
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void click_OkBtn(){
		OkBtn.click();
	}
	
	public void enterEmployeeId(String text){
		employeeId.sendKeys(text);
	}
	

	
	public ArrayList<String> getProductIds(){
		ArrayList<String> prods = new ArrayList<String>();
		for(WebElement ele : productIds){
			prods.add(ele.getText().trim());
		}
		return prods;
	}

	public boolean InventoryDelivery_Title_isDisplayed() {
		return InventoryDelivery_Title.isDisplayed();
	}

	public boolean LineItem_Header_isDisplayed() {
		return LineItem_Header.isDisplayed();
	}

	public boolean ProductID_Header_isDisplayed() {
		return ProductID_Header.isDisplayed();
	}

	public boolean ItemName_Header_isDisplayed() {
		return ItemName_Header.isDisplayed();
	}

	public boolean OrderdQty_Header_isDisplayed() {
		return OrderdQty_Header.isDisplayed();
	}

	public boolean ReceivedQty_Header_isDisplayed() {
		return ReceivedQty_Header.isDisplayed();
	}

	public boolean select_PurchaseOrder_defaultValue_isDisplayed() {
		return select_PurchaseOrder_defaultValue.isDisplayed();
	}

	public void select_OpenPurchaseOrder() {
		driver.findElement(By.xpath("//div[@id='MerchandiseInventoryDelivery']//span[@class='k-icon k-i-arrow-s']"))
				.click();
		List<WebElement> Alloptions = driver.findElements(By.xpath("//ul[@id='purchaseOrderDropDown_listbox']//li"));

		Alloptions.get(1).click();

	}

	public String getQtyValBeforeUpdate() {

		return driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"))
				.getAttribute("value");
	}
	
	
	
	public String get_ProductQty(){
		 String qtyVal = driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input")).getAttribute("value");
		 return qtyVal;
		 
	}
	
	
	
	
	
	
	
	
	

	public void EnterUpdatedQtyVal_getVal() {
		String qtyVal = driver
				.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"))
				.getAttribute("value");
		int Qty = Integer.parseInt(qtyVal);
		
		
		int AddQtyVal = Qty + 2;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"))
				.sendKeys(""+AddQtyVal);
		/*
		 * String updatedQtyVal=driver.findElement(By.xpath(
		 * "//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"
		 * )).getAttribute("value"); return updatedQtyVal;
		 */
	}

	
	public void EnterLessQtyVal_getVal() {
		String qtyVal = driver
				.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"))
				.getAttribute("value");
		int Qty = Integer.parseInt(qtyVal);
		int AddQtyVal = Qty - 1;
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"))
				.sendKeys(""+AddQtyVal);
		/*
		 * String updatedQtyVal=driver.findElement(By.xpath(
		 * "//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input"
		 * )).getAttribute("value"); return updatedQtyVal;
		 */
	}
	
	
	
	public void EnterQty(String Qty) {
		Enter_Qty.sendKeys(Qty);
	
	}
	
	
	
	
	
	public void clear_ReceivedQtyField() {

		driver.findElement(By.xpath("//div[@id='purchase-order-items-grid']//table/tbody/tr[1]/td[5]/input")).clear();
		;

	}

	public void enterComments(String Comments) {

		txt_Comments.sendKeys(Comments);
	}
	
	public void ClearComments() {

		txt_Comments.clear();
	}
	
	
	

	public void click_SubmitBtn() {

		btn_Submit.click();
	}

	public String get_ErrorMessage() {

		return ErrorMsg.getText();
	}

	public void click_OK_btn() {

		OK_btn.click();
	}
	
	public void click_OKbtn_ErrorMsg() {


		OKbtn_ErrorMsg.click();
	}
	
	
	
	
	public String get_ErrorMessage_Comments() {

		return ErrorMessage_Comments.getText();
	}
	
	
	public void enter_EmployeeID(String EmpID) {
		
		EmployeeID.sendKeys(EmpID);
	}
	
	
	
    public void clear_EmployeeID() {
		
		EmployeeID.clear();
	}
	
	public void click_Cancel_Btn() {

		Cancel_Btn.click();
	}
	
	
	public boolean InventoryDeliveryComplete_Header_IsDisplayed() {
		return InventoryDeliveryComplete_Header.isDisplayed();
	}
	
	
	

	public String get_EMPID_Errormessage() {

		return EMPID_Errormessage.getText();
	}
	
	
	
	
	
	
	

}
