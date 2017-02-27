package Pages.AWB;

import java.util.List;

import javax.print.attribute.standard.MediaSize.NA;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.stringtemplate.v4.compiler.CodeGenerator.primary_return;

public class UnitDetailsPage {

	WebDriver driver;

	@FindBy(xpath="//h3[contains(text(),'Unit Details')]")
	private WebElement pageTitle;

	@FindBy(xpath="//div[@id='FilterOverrideContainer']//label[@class='onoffswitch-label']")
	private WebElement filter_toggleBtn;

	@FindBy(linkText="Back To Property View")
	private WebElement backToPropertyView_Btn;

	@FindBy(partialLinkText="Back to Dashboard")
	private WebElement backToDashBoard_Btn;

	@FindBy(id="IdAvailableUnits")
	private WebElement availableUnits_Btn;

	@FindBy(id="IdOtherUnits")
	private WebElement addOtherUnits_Btn;

	@FindBy(id="btn_showInventory")
	private WebElement showInventory_Btn;

	@FindBy(id="btn_showAd")
	private WebElement ShowAuctionAd_Btn;

	@FindBy(xpath="//button[@id='btnCancel']")
	private WebElement windowcancel_Btn;

	//button[contains(text(), 'Cancel')]

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[2][text()='Unit #']")
	private WebElement unitColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[3][text()='Name']")
	private WebElement nameColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[4][text()='DM']")
	private WebElement dmColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[5][text()='RM']")
	private WebElement rmColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[6][text()='INV']")
	private WebElement invColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[7][text()='AD']")
	private WebElement adcolumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[8][text()='Size']")
	private WebElement sizeColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[9][text()='Rent']")
	private WebElement rentColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[10][text()='Fees']")
	private WebElement fesColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[11][text()='Total Owed']")
	private WebElement totOwedColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/thead/tr[1]/th[12][text()='Paid Thru']")
	private WebElement paidthruColumn;

	@FindBy(xpath="//div[@id='Unitgrid']//table/tbody/tr[1]/td[1]/a")
	private WebElement expandBtn;

	@FindBy(xpath="(//a[@id='idremoveAuctionunit1'])[1]")
	private WebElement removeBtn;

	@FindBy(xpath="(//a[@id='idremoveAuctionunit'])[1]")
	private WebElement moveBtn;

	@FindBy(xpath="//a[contains(text(),'Expand All')]")
	private WebElement expandAll;

	@FindBy(xpath="//a[contains(text(),'Collapse All')]")
	private WebElement collapseAll;

	@FindBy(xpath="//span[contains(text(),'Auction Advertisment')]")
	private WebElement auctionAdvSection;

	@FindBy(xpath="//span[contains(text(),'Auction Unit Inventory')]")
	private WebElement auctionUnitInvSection;

	@FindBy(xpath="//span[contains(text(),'Upload/show Documents')]")
	private WebElement uploadShowDocSection;
	
	@FindBy(xpath="//a[@id='btn_showApprovals']")
	private WebElement showApprovalsBtn;
	
	
	@FindBy(xpath="//label[text()='Filters']")
	private WebElement Filters_txt;
	
	@FindBy(xpath="//input[@id='txt_Remove_Reason']")
	private WebElement Reason_Enter;
	
    @FindBy(xpath="//p[@id='empBlock']//span[text()='Employee Id']/following-sibling::span/input")
    private WebElement employeeid;
    
    @FindBy(xpath="//button[contains(@id,'btn_Inventory')]")
	private WebElement ViewInventoryControlSheet;
	
	@FindBy(xpath="//button[@id='btn_SealHistory']")
	private WebElement ViewSealHistory_btn;
	
	@FindBy(xpath="//button[@id='btn_RemoveUnit']")
	private WebElement RemoveUnit_SaveBtn;
	
	
	@FindBy(xpath="//a[@id='IdAvailableUnits']")
 	private WebElement AddAvailableUnits_btn;
 	
 	
 	@FindBy(xpath="//span[@id='AvailableUnitsWindow_wnd_title']")
 	private WebElement AvailableUnits_ModalWindow;
 	
 	@FindBy(xpath="//a[@id='btn_showApprovals']")
 	private WebElement showAuctionApproval_Btn;
 
	
	@FindBy(xpath="//h3/b[contains(text(),'Inventory Details')]")
	private WebElement inventoryDetails_txt;
	
	
	@FindBy(xpath="//div[@id='right']/table[1]/tbody/tr[1]/td[1]/b[contains(text(),'DTM seal number')]")
	private WebElement DTMsealnumber_txt;
	
	@FindBy(xpath="//div[@id='right']/table[1]/tbody/tr[2]/td[1]/b[text()='Lock Status']")
	private WebElement LockStatus_txt;
	
	@FindBy(xpath="//div[@id='right']/table[1]/tbody/tr[2]/td[contains(text(),'DTM Lock Cut')]")
	private WebElement DTMLockCut_txt;
	
	
	@FindBy(xpath="//div[@id='right']/table[2]/tbody/tr/td[1]/b[text()='Comments']")
	private WebElement Comments_txt;
	
	@FindBy(xpath="//a[text()='Add']")
	private WebElement add_lnk;
	


	public UnitDetailsPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean verify_pageTitle(){
		return pageTitle.isDisplayed();
	}

	public boolean verify_filter_toggleBtn(){
		return filter_toggleBtn.isDisplayed();
	}
	
	
	public boolean verify_ViewSealHistory_btn(){
 		return ViewSealHistory_btn.isDisplayed();
 	}
 	
 	
 	public void click_AddAvailableUnits_btn(){
 		AddAvailableUnits_btn.click();
 	}
 	
 	
 	public boolean verify_AvailableUnits_ModalWindow(){
 		return AvailableUnits_ModalWindow.isDisplayed();
 	}
 	
 	
 	public boolean verify_showAuctionApproval_Btn(){
 		return showAuctionApproval_Btn.isDisplayed();
 	}
 	
 	 public void click_VehicleBoats_Chk(){
   		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//div[@id='left']/table/tbody/tr[3]/td[3]/input[@class='inventorytype']"));
   		
   		AllMakeOptins.get(0).click();
   		
   	}
 	
 
	
	
	public boolean verify_inventoryDetails_txt(){
		return inventoryDetails_txt.isDisplayed();
	}

	public void click_filter_toggleBtn(){
		filter_toggleBtn.click();
	}

	public void click_windowcancel_Btn(){
		windowcancel_Btn.click();
	}

	public boolean isshowApprovalsBtnDisplayed(){

		return showApprovalsBtn.isDisplayed();
	}

	public void click_BackToPropertyView_Btn(){
		backToPropertyView_Btn.click();
	}
	
	public boolean verify_ViewInventoryControlSheet_btn(){
		return ViewInventoryControlSheet.isDisplayed();
	}
	

	public void click_BackToDashBoard_Btn(){
		backToDashBoard_Btn.click();
	}
	
	public void click_ShowUnitInventory_Btn(){
		showInventory_Btn.click();
	}
	
	

	public boolean verify_availableUnits_Btn(){
		return availableUnits_Btn.isDisplayed();
	}

	public boolean verify_addOtherUnits_Btn(){
		return addOtherUnits_Btn.isDisplayed();
	}


	public boolean verify_showInventory_Btn(){
		return showInventory_Btn.isDisplayed();
	}

	public boolean verify_ShowAuctionAd_Btn(){
		return ShowAuctionAd_Btn.isDisplayed();
	}

	public String get_unitColumnNames(){
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='Unitgrid']//table//thead/tr/th[@class='cent k-header']"));
		for(WebElement columns:lst){
			String names=columns.getText().trim();
			System.out.println(names);
		}
		return null;

	}


	public void click_addOtherUnits_Btn(){
		addOtherUnits_Btn.click();
	}

	public boolean verify_UnitColumn(){
		return unitColumn.isDisplayed();
	}

	public boolean verify_NameColumn(){
		return nameColumn.isDisplayed();
	}

	public boolean verify_DMColumn(){
		return dmColumn.isDisplayed();
	}

	public boolean verify_RMColumn(){
		return rmColumn.isDisplayed();
	}

	public boolean verify_InvColumn(){
		return invColumn.isDisplayed();
	}
	
	
	
	 public String getUnitNumber(){
	   		List<WebElement> AllUnitNoList = driver.findElements(By.xpath("//a[@id='idremoveAuction']"));
	   		
	   		 String UnitNumber = AllUnitNoList.get(0).getText();
	   		 
	   		 return UnitNumber;
	   		
	   	}
	 
	 
	 public void select_reasonType(String text){
      WebElement element=driver.findElement(By.id("ddl_remove_reasontype"));
      Select sel=new Select(element);
      sel.selectByVisibleText(text);
      
}

  
  public void Enter_Reason(String Reason){
		
 	 Reason_Enter.sendKeys(Reason);
 	
		
	}
  
  public void enter_employeeid(String empid){
      employeeid.sendKeys(empid);
}

  
  public void click_RemoveUnit_SaveBtn(){
		
		
 	 RemoveUnit_SaveBtn.click();
	}
  
  

	public boolean verify_AddColumn(){
		return adcolumn.isDisplayed();
	}

	public boolean verify_SizeColumn(){
		return sizeColumn.isDisplayed();
	}

	public boolean verify_RentColumn(){
		return rentColumn.isDisplayed();
	}

	public boolean verify_FeesColumn(){
		return fesColumn.isDisplayed();
	}

	public boolean verify_TotOwedColumn(){
		return totOwedColumn.isDisplayed();
	}

	public boolean verify_PaidThrColumn(){
		return paidthruColumn.isDisplayed();
	}

	public void Click_ExpandBtn(){
		expandBtn.click();
	}

	public boolean verify_RemoveBtn(){
		return removeBtn.isDisplayed();
	}

	public boolean verify_MoveBtn(){
		return moveBtn.isDisplayed();
	}

	public void click_MoveBtn(){
		moveBtn.click();
	}

	public boolean verify_ExpandAllBtn(){
		return expandAll.isDisplayed();
	}

	public boolean verify_CollapseBtn(){
		return collapseAll.isDisplayed();
	}

	public boolean verify_AuctionAdvSection(){
		return auctionAdvSection.isDisplayed();
	}

	public boolean verify_AuctionUnitInvSection(){
		return auctionUnitInvSection.isDisplayed();
	}

	public boolean verify_UploadShowDocSection(){
		return uploadShowDocSection.isDisplayed();
	}

	public boolean verify_Filters_txt(){
		return Filters_txt.isDisplayed();
	}
	
	
	public boolean verify_DTMsealnumber_txt(){
		return DTMsealnumber_txt.isDisplayed();
	}
	
	
	public boolean verify_LockStatus_txt(){
		return LockStatus_txt.isDisplayed();
	}
	
	public boolean verify_DTMLockCut_txt(){
		return DTMLockCut_txt.isDisplayed();
	}
	
	
	public boolean verify_Comments_txt(){
		return Comments_txt.isDisplayed();
	}
	
	
	public void Enter_Make(String Make){
		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//input[contains(@id,'IdMake')]"));
		
		AllMakeOptins.get(0).sendKeys(Make);
		
	}
	
	public void Enter_Model(String Model){
		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//input[contains(@id,'IdModel')]"));
		
		AllMakeOptins.get(0).sendKeys(Model);
		
	}
	
	public void Enter_Year(String Year){
		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//input[contains(@id,'IdYear')]"));
		
		AllMakeOptins.get(0).sendKeys(Year);
		
	}
	
	
	public void Enter_LastFourDigit(String Digit){
		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//input[contains(@id,'IdDigitsofVIN')]"));
		
		AllMakeOptins.get(0).sendKeys(Digit);
		
	}
	
	
	public void click_Add(){
		
		add_lnk.click();
	}
	
	public void click_SaveAll(){
		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//button[@id='btn_SaveAll']"));
		
		AllMakeOptins.get(0).click();
		
	}
	
	//@FindBy(xpath="//p[@id='empBlock']//span[text()='Employee Id']/following-sibling::span/input")
		@FindBy(xpath="(//input[@id='txt_Remove_Employee'])[2]")
		private WebElement EmployeeID;
		
		//@FindBy(xpath="//button[text()='Confirm']")
		@FindBy(xpath="//button[@class='inv-delete-confirm close psbutton-priority margin-left floatright']")
		private WebElement ConfirmButton;
	
	public void Enter_EmployeeID(String EmpID){
		
		
		EmployeeID.sendKeys(EmpID);
		
	}
	
     public void click_Confirm(){
		
		
    	 ConfirmButton.click();
	}
	
     
     public void click_Remove(){
 		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//a[text()='Remove']"));
 		
 		AllMakeOptins.get(0).click();
 		
 	}

     public void Enter_DTMSealNumber(String SealNumber) throws InterruptedException{
 		List<WebElement> AllMakeOptins = driver.findElements(By.xpath("//input[contains(@id,'Idsealnumber')]"));
 		AllMakeOptins.get(0).clear();
 		Thread.sleep(1000);
 		AllMakeOptins.get(0).sendKeys(SealNumber);
 		
 	}
     
 	
 	@FindBy(xpath="//div[@id='Unitgrid']//table//tbody//tr[1]/td/a[@class='k-icon k-plus']")
 	private WebElement arrow_link;
 	
 	public void click_arrow_link(){
 		arrow_link.click();
 	}
 	
 	@FindBy(xpath="//span[contains(text(),'Upload/show Documents')]")
 	private WebElement upload_show_Documents;
 	
 	public void click_upload_show_Documents(){
 		upload_show_Documents.click();
 	}
 	
 	@FindBy(xpath="//div[@id='content']//b[text()='View Document(s)']")
 	private WebElement viewDocuments;
 	
 	public boolean verify_viewDocuments(){
 		return viewDocuments.isDisplayed();
 	}
     
 	@FindBy(xpath="//button[@id='AvailableUnitbtnCancel']")
 	private WebElement click_Cancel;
 	
 	@FindBy(xpath="//a[@id='btn_showInventory']")
 	private WebElement btn_showInventory;
 	
 	@FindBy(xpath="//a[@id='btn_showAd']")
 	private WebElement btn_showAuctionAd;
 	
 	@FindBy(xpath="//div[@id='header-logo-container']/a/img")
 	private WebElement click_PublicStorageHeader;
 	
 	
 	public void click_btn_showInventory(){
 		btn_showInventory.click();
 	}
 	
 	
 	public void click_btn_showAuctionAd(){
 		btn_showAuctionAd.click();
 	}
 	
 	public void click_PublicStorageHeader(){
 		click_PublicStorageHeader.click();
 	}
 	
 	public void click_Cancel(){
 		click_Cancel.click();
 	}
 	
 	public void click_showAuctionApproval_Btn(){
		 showAuctionApproval_Btn.click();
	}

	@FindBy(xpath="//select[contains(@id,'ddl_rmApproval')]")
	private WebElement dropdown_RMApproval;

	
	public boolean isDisabled_RMApprovalDropdown(){
		
		try{
			dropdown_RMApproval.getAttribute("disabled");
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	@FindBy(xpath="//select[contains(@id,'ddl_sysApproval')]")
	private WebElement dropdown_SystemApproval;
	
	@FindBy(xpath="//select[contains(@id,'ddl_dmApproval')]")
	private WebElement dropdown_DMApproval;
	
	@FindBy(xpath="//input[contains(@id,'txtreasonOverride')]")
	private WebElement systemApproval_ReasonForOverride;
	
	public boolean isDisplayed_DMApproval(){
		return dropdown_DMApproval.isDisplayed();
	}
	
	public boolean isDisplayed_SystemApproval(){
		return dropdown_SystemApproval.isDisplayed();
	}
	
	public boolean isDisplayed_ReasonForOverride(){
		return systemApproval_ReasonForOverride.isDisplayed();
	}
	
	@FindBy(xpath="//button[text()='View Ledger Report']")
	private WebElement view_Ledgerreport_btn;
	
	@FindBy(xpath="//button[text()='Ledger Notes']")
	private WebElement LedgerNotes_btn;
	
	@FindBy(xpath="//button[text()='Email']")
	private WebElement Email_btn;
	
	public boolean isDisplayed_view_Ledgerreport_btn(){
		return view_Ledgerreport_btn.isDisplayed();
	}
	
	public boolean isDisplayed_LedgerNotes_btn(){
		return LedgerNotes_btn.isDisplayed();
	}
	
	public boolean isDisplayed_Email_btn(){
		return Email_btn.isDisplayed();
	}
	
	@FindBy(xpath="//span/b[text()='Comments']")
	private WebElement comments;
	
	public boolean isDisplayed_comments(){
		return comments.isDisplayed();
	}
	
	@FindBy(id="btn_Save")
	private WebElement save;
	
	public boolean isDisplayed_save(){
		return save.isDisplayed();
	}
	
	@FindBy(id="btn_DTMReset")
	private WebElement dtmreset;
	
	public boolean isDisplayed_dtmreset(){
		return dtmreset.isDisplayed();
	}
	
	@FindBy(id="btn_ResetApprovals")
	private WebElement resetapproval;
	
	public boolean isDisplayed_resetapproval(){
		return resetapproval.isDisplayed();
	}
	
	
	public void clk_BasedOnAuctionDate(String auctionDate,WebDriver driver){
 		
	 	WebElement ele=	driver.findElement(By.xpath("//div[@id='Propertygrid']//table/tbody/tr/td/span[contains(text(),'"+auctionDate+"')]/../following-sibling::td/a[contains(text(),'Unit Details')]"));
	 	ele.click();
	 	}

}
