package Pages.AWB;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.apache.bcel.generic.NEW;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AuctionManagementPage {

	WebDriver driver;
	@FindBy(xpath="//h2[contains(text(),'Auction Management')]")
	private WebElement pageTitle;

	@FindBy(xpath="//div[@id='Propertygrid']//div[@class='k-grid-header']//table/thead/tr/th[@data-title='DRA']")
	private WebElement dRA_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//div[@class='k-grid-header']//table/thead/tr/th[@data-title='Property #']")
	private WebElement property_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//div[@class='k-grid-header']//table/thead/tr/th[@data-title='Auction Date']")
	private WebElement auctionDate_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[1]/th[7][text()='Approved']")
	private WebElement approved_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//div[@class='k-grid-header']//table/thead/tr/th[@data-title='Units']")
	private WebElement units_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[2]/th[2][text()='DM']")
	private WebElement dM_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//table/thead/tr[2]/th[3][text()='RM']")
	private WebElement rM_Column;

	@FindBy(xpath="//div[@id='Propertygrid']//div[@class='k-grid-header']//table/thead/tr/th[@data-title='Employee']")
	private WebElement employee_Column;

	@FindBy(xpath="//a[@href='/Customer/BackToDashboard']")
	private WebElement backToDashBoard_Btn;

	@FindBy(xpath="//h3[contains(text(),'Filters')]")
	private WebElement filters_Section;

	@FindBy(xpath="//button[@id='ScheduleAuctionMWOpen']")
	private WebElement newAuction_Btn;

	@FindBy(xpath="//div[@id='Propertygrid']//table//tbody/tr[1]/td[1]/a")
	private WebElement arrow_expand;

	@FindBy(xpath="//div[@id='Propertygrid']//table//tbody/tr[2]/td[2]/div/div[@id='example']/ul/li/span")
	private WebElement dRA_DetailsSection;

	@FindBy(xpath="//div[@id='Propertygrid']//table/tbody/tr[1]/td[4]/a")
	private WebElement auctionDate_Dm;


	@FindBy(xpath="//a[contains(text(),'Unit Details')]")
	private WebElement unitDetailsLink;

	@FindBy(xpath="//a[@id='IdAvailableUnits']")
	private WebElement btn_AddavailableUnits;

	@FindBy(xpath=".//*[@id='AvailableUnitgrid']/div[2]/table/tbody/tr[1]/td[1]/input")
	private WebElement btn_selectFirstUnit;

	@FindBy(xpath="//button[@id='AvailableUnitbtnSave']")
	private WebElement btn_SaveUnits;

	@FindBy(xpath="//div[@id='Unitgrid']/div[2]/table/tbody/tr[1]/td[1]/a")
	private WebElement btn_expandFirstUnit;

	@FindBy(xpath="//tr[1]//a[@id='idremoveAuction']")
	private WebElement firstUnitName;

	@FindBy(xpath="//span[contains(text(),'Auction Approval')]")
	private WebElement auctionApproval_Header;

	@FindBy(xpath="//button[@id='btn_DTMReset']")
	private WebElement btn_DTMReset;


	@FindBy(xpath="//p/b[2]")
	private WebElement spaceNameinDTMResetPopup;

	@FindBy(xpath="//input[@id='txt_Remove_Employee']")
	private WebElement empIdforDTMReset;

	@FindBy(xpath="//button[text()='Yes']")
	private WebElement dtmReset_Yes;


	@FindBy(xpath="//h3[text()='Unit Details']")
	private WebElement unitDetails_Heading;

	@FindBy(xpath="//div[@id='Unitgrid']/div[2]/table/tbody/tr[1]/td[1]/a")
	private WebElement arrow_expandIngrid;


	public AuctionManagementPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean verify_AuctionManagementtitle(){
		return pageTitle.isDisplayed();
	}

	public boolean Verify_DRA(){
		return dRA_Column.isDisplayed();
	}

	public boolean verify_property(){
		return property_Column.isDisplayed();
	}

	public boolean verify_AuctionDate(){
		return auctionDate_Column.isDisplayed();
	}

	public boolean verify_Units(){
		return units_Column.isDisplayed();
	}

	public boolean verify_DM(){
		return dM_Column.isDisplayed();
	}

	public boolean verify_RM(){
		return rM_Column.isDisplayed();
	}

	public boolean verify_Employee(){
		return employee_Column.isDisplayed();
	}

	public boolean Verify_unitDetailsLink(){
		return unitDetailsLink.isDisplayed();
	}

	public void click_unitDetailsLink(){
		unitDetailsLink.click();;
	}

	public void click_BackToDashBoard_Btn(){
		backToDashBoard_Btn.click();
	}

	public boolean verify_filterssection(){
		return filters_Section.isDisplayed();
	}

	public  boolean verify_Newauction_BTN(){
		return newAuction_Btn.isDisplayed();
	}

	public  boolean verify_approved(){
		return approved_Column.isDisplayed();
	}

	public void click_arrow_expand(){
		arrow_expand.click();;
	}

	public  boolean verify_dRA_DetailsSection(){
		return dRA_DetailsSection.isDisplayed();
	}



	public void click_AuctionDateDM(){
		auctionDate_Dm.click();
	}

	public String getAuctionDateTxt(){
		return auctionDate_Dm.getText();
	}


	public void click_UnitDetails(){
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='Propertygrid']//table/tbody/tr/td[10]/a[contains(text(),'Unit Details')]"));
		lst.get(0).click();

	}

	public boolean Verify_unitDetailsHeading(){
		return unitDetails_Heading.isDisplayed();
	}

	public void click_arrow_expandInGrid(){
		arrow_expandIngrid.click();;
	}

	public void click_addAvailableUnits(){
		btn_AddavailableUnits.click();
	}


	public void selectFirstUnit(){
		btn_selectFirstUnit.click();
	}


	public void click_SaveUnits(){
		btn_SaveUnits.click();
	}


	public String click_expandFirstUnit(){
		btn_expandFirstUnit.click();
		return firstUnitName.getText();
	}

	public void SelectRMdropVal(String value,String spacenum,WebDriver driver) throws Exception{

		WebElement element=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='RM:']/../following-sibling::td/select[contains(@id,'ddl_rmApproval')]"));
		Select se=new Select(element);
		Thread.sleep(2000);
		se.selectByVisibleText(value);

	}

	public boolean checkDMDropDownDisplayed(String spacenum,WebDriver driver){

		WebElement element=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='DM:']/../following-sibling::td/select[contains(@id,'ddl_dmApproval')]"));
		return element.isDisplayed();

	}
	
	
	public boolean checkDMDropDownEnabled(String spacenum,WebDriver driver){

		WebElement element=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='DM:']/../following-sibling::td/select[contains(@id,'ddl_dmApproval')]"));
		return element.isEnabled();

	}
	
	
	
	
	
	
	

	public boolean isDmDropDownEnable(String spacenum,WebDriver driver){

		return driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='DM:']/../following-sibling::td/select[contains(@id,'ddl_dmApproval')]")).isEnabled();

	}
	
	

	public void click_AuctionApprovalHeader(){
		auctionApproval_Header.click();
	}


	public void click_DTMReset(){
		btn_DTMReset.click();
	}


	public String spaceNameforDTMReset(){
		return spaceNameinDTMResetPopup.getText();
	}

	public void enterEmpIdforDTMReset(String value){
		empIdforDTMReset.sendKeys(value);

	}

	public void dtmResetYes(){
		dtmReset_Yes.click();
	}

	public void clk_UnitDeatilsBasedOnSpaceNum(String spacenum,WebDriver driver) throws InterruptedException{

		WebElement ele=driver.findElement(By.xpath("//div[@id='PropertySection']//table/tbody/tr//td[text()='"+spacenum+"']/following-sibling::td/a[text()='Unit Details']"));
		Thread.sleep(1000);
		ele.click();
	}


	public void clk_ExpandBasedOnSpaceNum(String spacenum,WebDriver driver) throws Exception{

		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td"));
		Thread.sleep(1000);
		ele.click();
	}

	public void clk_AuctionApprovalBasedOnSpaceNum(String spacenum,WebDriver driver) throws Exception{

		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/span[contains(text(),'Auction Approval')]"));
		Thread.sleep(1000);
		ele.click();
	}

	public boolean checkRMDropDownStatus(String spacenum,WebDriver driver){
		return driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='RM:']/../following-sibling::td/select")).isEnabled();
	}

	public void SelectDMDropVal(String value,String spacenum,WebDriver driver) throws Exception{

		WebElement element=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='DM:']/../following-sibling::td/select[contains(@id,'ddl_dmApproval')]"));
		Select se=new Select(element);
		Thread.sleep(2000);
		se.selectByVisibleText(value);

	}

	public void SelectSystemDropVal(String value,String spacenum,WebDriver driver) throws Exception{

		WebElement element=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='System:']/../following-sibling::td/select[contains(@id,'ddl_sysApproval')]"));
		Select se=new Select(element);
		Thread.sleep(2000);
		se.selectByVisibleText(value);

	}

	

		
public void enterDMApprovalReason(String reason,String spacenum,WebDriver driver){
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='DM:']/../following-sibling::td/input[contains(@id,'txtdmApproval')]"));
		ele.click();
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_DELETE);
	        robot.keyRelease(KeyEvent.VK_DELETE);
	        Thread.sleep(1000);
	        ele.sendKeys(reason);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}
	
	
	public void clk_SaveBtnBasedOnSpaceNum(String spacenum,WebDriver driver){
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='right']/following-sibling::div/button[@id='btn_Save']"));
	    ele.click();
	}
	
	public void enterReasonForOverride(String reason,String spacenum,WebDriver driver) throws Exception{
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td[contains(text(),'Reason for Override')]/following-sibling::td/input"));
		Thread.sleep(2000);
		ele.click();
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_DELETE);
	        robot.keyRelease(KeyEvent.VK_DELETE);
	        Thread.sleep(1000);
	        ele.sendKeys(reason);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void clear_ReasonForOverrideTextField(String spacenum,WebDriver driver) throws Exception{
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td[contains(text(),'Reason for Override')]/following-sibling::td/input"));
		Thread.sleep(2000);
		ele.click();
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_DELETE);
	        robot.keyRelease(KeyEvent.VK_DELETE);
	        Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
   public void enterRMApprovalReason(String reason,String spacenum,WebDriver driver){
		
		WebElement ele=driver.findElement(By.xpath("//div[@id='Unitgrid']/div[@class='k-grid-content']/table/tbody/tr/td/a[text()='"+spacenum+"']/../preceding-sibling::td/../following-sibling::tr[1]/td//ul/li/div/div[@id='left']//td/b[text()='RM:']/../following-sibling::td/input[contains(@id,'txtrmApproval')]"));
		ele.click();
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
	        robot.keyPress(KeyEvent.VK_A);
	        robot.keyRelease(KeyEvent.VK_CONTROL);
	        robot.keyRelease(KeyEvent.VK_A);
	        robot.keyPress(KeyEvent.VK_DELETE);
	        robot.keyRelease(KeyEvent.VK_DELETE);
	        Thread.sleep(1000);
	        ele.sendKeys(reason);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
	}





}
