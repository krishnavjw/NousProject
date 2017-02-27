package Pages.Walkin_Reservation_Lease;

import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_MilitaryInformationPage 
{
	@FindBy(id="Form_BirthDate")
	private WebElement dob;
	
	@FindBy(id="Form_MilitaryIdentificationNumber")
	private WebElement militaryId;
	
	@FindBy(id="Form_DeploymentStartDate")
	private WebElement startDate;
	
	@FindBy(id="Form_MilitaryUnit")
	private WebElement unitName;

	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header js-military-branch']//span[text()='Select']")
	private WebElement militaryBranch_list;
	
	@FindBy(id="Form_MilitarySupervisorFirstName")
	private WebElement supervisorFN;
	
	@FindBy(id="Form_MilitarySupervisorLastName")
	private WebElement supervisorLN;
	
	@FindBy(id="Form_MilitarySupervisorTitle")
	private WebElement rank;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header phoneDrop floatleft margin-right js-phone']//span[text()='Select']")
	private WebElement Phone_list;
	
	@FindBy(id="Form_AreaCode")
	private WebElement areaCode;

	@FindBy(id="Form_Exchange")
	private WebElement phone_Exchg;

	@FindBy(id="Form_LineNumber")
	private WebElement phone_LineNumber;
	
	@FindBy(id="confirmWithCustomer")
	private WebElement confirmCust_btn;
	
	@FindBy(xpath="//a[@id='notes-add']")
	private WebElement addNotesLink;

	@FindBy(xpath="//h3[text()='Military Information']")
	private WebElement isMilitaryTxtDisplayed;
	
	@FindBy(id = "Form_MilitaryIdentificationNumber")
	private WebElement militaryID;
	
	
	public Leasing_MilitaryInformationPage(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	
	public void enterBirthDate(String DOB)
	{
		dob.sendKeys(DOB);
	}
	
	public void clickAddNotesBtn(){
		addNotesLink.click();
	}
public boolean isMilitaryInfoPageDisplayed(){
		
		return isMilitaryTxtDisplayed.isDisplayed();
	}
	
	public void enterMilitaryId(String id)
	{
		militaryId.sendKeys(id);
	}

	
	public void enterActualStratdate(String start)
	{
		startDate.sendKeys(start);
	}

	public void enterUnitName(String unit)
	{
		unitName.sendKeys(unit);
	}
	
	public void clickmilitaryBranch()
	{
		militaryBranch_list.click();
	}
	
	public void enterSupervisorFN(String firstname)
	{
		supervisorFN.sendKeys(firstname);
	}

	
	public void enterSupervisorLN(String lastName)
	{
		supervisorLN.sendKeys(lastName);
	}
	
public void selectPhoneType(String phtype,WebDriver driver){
		
		Phone_list.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		List<WebElement> phoneval=driver.findElements(By.xpath("//ul[@id='Form_MilitarySupervisorPhoneType_listbox']/li"));
		
		for(WebElement ele:phoneval){
			
			String actual=ele.getText().trim();
			if(actual.contains(phtype)){
				ele.click();
				
			}
		}
		
		
		
	}
	
public void selectMilitaryBranch(String exp){
	militaryBranch_list.click();
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	for(WebElement ele:military_BranchList){
		
		String actual=ele.getText().trim();
		if(actual.contains(exp)){
			ele.click();
		}
	}
}

@FindBy(xpath="//ul[@id='Form_MilitaryBranchTypeId_listbox']/li")
private List<WebElement> military_BranchList;
	


	
	public void enterRank(String milirank)
	{
		rank.sendKeys(milirank);
	}
	
	public void clickPhonelist()
	{
		Phone_list.click();
	}
	
	public void txt_AreaCode(String areacode){
		areaCode.sendKeys(areacode);

	}


	public void txt_Exchg(String exchg){
		phone_Exchg.sendKeys(exchg);

	}


	public void txt_lineNumber(String lineNum){
		phone_LineNumber.sendKeys(lineNum);

	}
	
	public void clickConfirmCust_btn()
	{
		confirmCust_btn.click();
	}
	
	public void enterMilitaryID(String id) {
		militaryID.sendKeys(id);
	}

	public void enterCommandingOfficerFName(String fname) {
		supervisorFN.sendKeys(fname);
	}

	public void enterCommandingOfficerLName(String lname) {
		supervisorLN.sendKeys(lname);
	}
	

	public void click_militaryBranch(){
		militaryBranch_list.click();
	}

	

}
