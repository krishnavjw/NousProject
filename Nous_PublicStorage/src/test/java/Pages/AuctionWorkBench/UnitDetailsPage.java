package Pages.AuctionWorkBench;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

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

	@FindBy(linkText="Add Available Units")
	private WebElement addAvailableUnits;

	@FindBy(xpath="//select[contains(@id,'ddl_rmApproval')]")
	private WebElement dropdown_RMApproval;

	@FindBy(xpath="//select[contains(@id,'ddl_sysApproval')]")
	private WebElement dropdown_SystemApproval;

	@FindBy(xpath="//select[contains(@id,'ddl_dmApproval')]")
	private WebElement dropdown_DMApproval;

	@FindBy(xpath="//input[contains(@id,'txtreasonOverride')]")
	private WebElement systemApproval_ReasonForOverride;

	@FindBy(xpath="//button[@id='btn_Save']")
	private WebElement auctionApproval_SaveBtn;

	@FindBy(xpath="//div[contains(@class,'modal-content')]/div[@class='container']")
	private WebElement alertMessage;

	@FindBy(xpath="//a[contains(text(),'OK')]")
	private WebElement okButton;

	@FindBy(xpath="//input[contains(@id,'txtdmApproval')]")
	private WebElement reason_DMApproval;

	@FindBy(xpath="//td[@id='DispNameDate']")
	private WebElement displayNameDate;

	@FindBy(xpath="//div[@id='content']//div//table//tbody//tr//td//input[contains(@id,'txtreasonOverride')]")
	private WebElement reasonForOverride;

	
	@FindBy(id="IdAvailableUnits")
	private WebElement availableUnits_Btn;
	
	@FindBy(id="IdOtherUnits")
	private WebElement addOtherUnits_Btn;
	
	@FindBy(id="btn_showInventory")
	private WebElement showInventory_Btn;
	
	@FindBy(id="btn_showAd")
	private WebElement ShowAuctionAd_Btn;





	public UnitDetailsPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isRMDropdownDisabled(){

		try{
			dropdown_RMApproval.click();
			return false;
		}catch(Exception e){
			return true;
		}
	}

	public boolean isDisabled_DMApprovalDropdown(){

		try{
			String prop = dropdown_DMApproval.getAttribute("disabled");

			return true;
		}
		catch(Exception ex){
			return false;
		}
	}


	

	
	public void enter_ReasonForOverride(String value){
		reasonForOverride.sendKeys(value);
	}


	public String getDisplayeNamedate(){
		return displayNameDate.getText();
	}

	public boolean isDisplayed_Reason_DMApproval(){
		return reason_DMApproval.isDisplayed();
	}

	public String getReason_DMApproval(){
		return reason_DMApproval.getAttribute("value");
	}

	public void enterReason_DMApproval(String text){
		reason_DMApproval.sendKeys(text);
	}

	public void click_OkButton(){
		okButton.click();
	}

	public String getAlertMessage(){
		return alertMessage.getText();
	}

	public void click_AuctionApproval_SaveBtn(){
		auctionApproval_SaveBtn.click();
	}

	public void enterReason_SystemOverride(String text){
		systemApproval_ReasonForOverride.sendKeys(text);
	}

	public void click_DMApproval(){
		dropdown_DMApproval.click();
	}

	public boolean isExists_Option_DMApproval(String text){
		Select sel = new Select(dropdown_DMApproval);
		List<WebElement> options = sel.getOptions();
		for(WebElement option: options){
			if(option.getText().trim().equals(text))
				return true;
		}
		return false;
	}

	public void select_SystemApproval(String text){
		Select sel = new Select(dropdown_SystemApproval);
		sel.selectByVisibleText(text);
	}

	public void select_DMApproval(String text){
		Select sel = new Select(dropdown_DMApproval);
		sel.selectByVisibleText(text);
	}

	public boolean isDisabled_RMApprovalDropdown(){

		try{
			dropdown_RMApproval.getAttribute("disabled");
			return true;
		}catch(Exception ex){
			return false;
		}
	}

	public void click_AddAvailableUnits(){
		addAvailableUnits.click();
	}

	public boolean verify_pageTitle(){
		return pageTitle.isDisplayed();
	}

	public boolean verify_filter_toggleBtn(){
		return filter_toggleBtn.isDisplayed();
	}

	public void click_filter_toggleBtn(){
		filter_toggleBtn.click();
	}

	public void click_BackToPropertyView_Btn(){
		backToPropertyView_Btn.click();
	}

	public void click_BackToDashBoard_Btn(){
		backToDashBoard_Btn.click();
	}

	
	public boolean verify_availableUnits_Btn(){
		return availableUnits_Btn.isDisplayed();
	}
	
	public void click_availableUnits_Btn(){
		availableUnits_Btn.click();
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
	
	@FindBy(linkText="Remove")
	private WebElement remove;
	
	@FindBy(linkText="Move")
	private WebElement move;
	
	@FindBy(linkText="Expand All")
	private WebElement expand_All;
	
	@FindBy(linkText="Collapse All")
	private WebElement collapse_All;
	
	public boolean verify_remove(){
		return remove.isDisplayed();
	}
	
	public void click_remove(){
		remove.click();
	}
	
	
	
	public boolean verify_move(){
		return move.isDisplayed();
	}
	public boolean verify_expand_All(){
		return expand_All.isDisplayed();
	}
	
	public boolean verify_collapse_All(){
		return collapse_All.isDisplayed();
	}
	
	@FindBy(xpath="//div[@id='Unitgrid']//table//tbody//tr[1]")
	private WebElement unitdetails_section;
	
	@FindBy(xpath="//span[contains(text(),'Auction Advertisment')]")
	private WebElement auction_Advertisment;
	
	@FindBy(xpath="//span[contains(text(),'Auction Unit Inventory')]")
	private WebElement auctionUnit_Inventory;
	

	public boolean verify_unitdetails_section(){
		return unitdetails_section.isDisplayed();
	}
	
	public boolean verify_auction_Advertisment(){
		return auction_Advertisment.isDisplayed();
	}
	
	public boolean verify_auctionUnit_Inventory(){
		return auctionUnit_Inventory.isDisplayed();
	}
	
	public boolean verify_upload_show_Documents(){
		return upload_show_Documents.isDisplayed();
	}
	
	@FindBy(id="removeAuctionWindow_wnd_title")
	private WebElement removeUnit_Mw;
	
	@FindBy(id="ddl_remove_reasontype")
	private WebElement reason_Dropdown;
		
	@FindBy(id="txt_Remove_Reason")
	private WebElement reason;
	
	@FindBy(id="txt_Remove_Employee")
	private WebElement employeeid;
	
	public boolean verify_removeUnit_Mw(){
		return removeUnit_Mw.isDisplayed();
	}
	
	public void click_reason_Dropdown(){
		reason_Dropdown.click();
	}
	
	public void enter_Reason(String reasontype){
		reason.sendKeys(reasontype);
	}
	
	public void enter_employeeid(String empid){
		employeeid.sendKeys(empid);
	}
	
	public void select_reasonType(String text){
		WebElement element=driver.findElement(By.id("ddl_remove_reasontype"));
		Select sel=new Select(element);
		sel.selectByVisibleText(text);
		
	}
	
	@FindBy(id="RemoveBtnCancel")
	private WebElement cancel_Btn;
	
	@FindBy(id="btn_RemoveUnit")
	private WebElement save_Btn;
	
	public void click_cancel_Btn(){
		cancel_Btn.click();
	}
	
	public void click_save_Btn(){
		save_Btn.click();
	}
	
	
	@FindBy(xpath="//div[@id='Unitgrid']//table//tbody//tr[1]//td[2]/a")
	private WebElement unitspave;
	
	public String get_unitspave(){
		return unitspave.getText();
	}
	
	public void click_auction_Advertisment(){
		auction_Advertisment.click();
	}
	
	
	public String get_unitColumnNames(){
		String columnheaders = null;
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='Unitgrid']//table//thead/tr/th[@class='cent k-header']"));
		for(WebElement columns:lst){
			
			 columnheaders=columnheaders+","+columns.getText().trim();
		
		}
		return columnheaders;
		
	}
	
	//================Anjana=================
		@FindBy(xpath="//table[@id='tblAddCount2']/tbody/tr/td/b")
		private WebElement advSection;
		
		public boolean verify_AdvertiseSection(){
			return advSection.isDisplayed();
		}
		
		@FindBy(xpath="//table[@id='tblAddCount2']/tbody/tr/th[text()='Publication Name']")
		private WebElement publicationname;
		
		public boolean verify_publicationname(){
			return publicationname.isDisplayed();
		}
		
		@FindBy(xpath="//table[@id='tblAddCount2']/tbody/tr/th[text()='Publication Date']")
		private WebElement publicationDate;
		
		public boolean verify_publicationDate(){
			return publicationDate.isDisplayed();
		}
		
		@FindBy(xpath="//b[text()='Select Advertisement Document']")
		private WebElement selectAdvSection;
		
		public boolean verify_selectAdvSection(){
			return selectAdvSection.isDisplayed();
		}
		
		@FindBy(xpath="//a[text()='Scan']")
		private WebElement scan;
		
		public boolean verify_scan(){
			return scan.isDisplayed();
		}
		
		@FindBy(xpath="//a[text()='Upload']")
		private WebElement upload;
		
		public boolean verify_upload(){
			return upload.isDisplayed();
		}
		
		public void click_Upload(){
			upload.click();
		}
		
		@FindBy(xpath="//i[text()='delete']")
		private WebElement delete;
		
		public boolean verify_delete(){
			return delete.isDisplayed();
		}
		
		@FindBy(xpath="(//input[@id='txt_ad_#: AuctionID #'])[1]")
		private WebElement enter_publicationName;
		
		public void enter_publicationName(String name){
			enter_publicationName.sendKeys(name);
		}
		
		@FindBy(xpath="(//span[text()='select'])[1]")
		private WebElement calendericon;
		
		public void select_calendericon(){
			calendericon.click();
		}
		
		public void select_Date(String year,String month,String date){
			WebElement select_Date=driver.findElement(By.xpath("//table[@class='k-content']/tbody/tr[4]/td/a[@data-value='"+year+"/"+month+"/"+date+"']"));
			select_Date.click();
		}
		
		@FindBy(id="btn_savead")
		private WebElement save;
		
		public void click_save(){
			save.click();
		}
		
	
}
