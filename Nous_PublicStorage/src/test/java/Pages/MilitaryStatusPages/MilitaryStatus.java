package Pages.MilitaryStatusPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MilitaryStatus {

	WebDriver driver;
	WebDriverWait wait;

	public MilitaryStatus(WebDriver driver) {
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 90);
	}

	@FindBy(xpath = "//input[@id='Form_DeploymentEndDate']")
	private WebElement Active_Duty_End_Date;

	@FindBy(xpath = "//a[contains(text(),'Approve')]")
	private WebElement ApproveBtn;

	@FindBy(xpath = "//a[@id='confirmButton']")
	private WebElement ConfirmWithCustomerBtn;

	@FindBy(xpath = "//canvas[@class='signature-pad']")
	private WebElement SignaturePad;

	@FindBy(xpath = "//button[contains(text(),'Accept')]")
	private WebElement AcceptBtn;

	@FindBy(xpath = "//a[@id='submitButton']")
	private WebElement SubmitButton;

	@FindBy(xpath = "//*[@id='notesText']")
	private WebElement NotesTxtArea;

	@FindBy(xpath = "//input[@id='employeeNumber']")
	private WebElement EmployeeNumber;

	@FindBy(xpath = "//a[contains(text(),'Continue')]")
	private WebElement ContinueBtn;

	@FindBy(xpath = "//div[label[text()='Duty Type']]/following-sibling::div/descendant::span[contains(@class,'k-widget k-dropdown')]")
	private WebElement DutyDropDown;

	@FindBy(xpath = "//div[contains(text(),'Military Status:')]/../div[@class='content floatright']")
	private WebElement DutyStatus;

	@FindBy(xpath = "//input[@id='Form_BirthDate']")
	private WebElement BirthDate;

	@FindBy(xpath = "//input[@id='Form_MilitaryIdentificationNumber']")
	private WebElement MilitaryIdentificationNumber;

	@FindBy(xpath = "//input[@id='Form_DeploymentStartDate']")
	private WebElement ActiveDutyStartDate;

	@FindBy(xpath = "//input[@id='Form_MilitaryUnit']")
	private WebElement MilitaryUnit;

	@FindBy(xpath = "//*[contains(@id,'edit-military-status-form')]/div[2]/div[1]/div[3]/div/div[2]/span/span")
	private WebElement MilitaryBranch;

	@FindBy(xpath = "//input[@id='Form_MilitarySupervisorFirstName']")
	private WebElement MilitarySupervisorFirstName;

	@FindBy(xpath = "//input[@id='Form_MilitarySupervisorLastName']")
	private WebElement MilitarySupervisorLastName;

	@FindBy(xpath = "//input[@id='Form_MilitarySupervisorTitle']")
	private WebElement MilitarySupervisorRank;

	@FindBy(xpath = "//*[contains(@id,'edit-military-status-form')]/div[2]/div[2]/div[3]/div/div[2]/span/span")
	private WebElement PhoneType;

	@FindBy(xpath = "//input[@id='Form_AreaCode']")
	private WebElement AreaCode;

	@FindBy(xpath = "//input[@id='Form_Exchange']")
	private WebElement ExchangeCode;

	@FindBy(xpath = "//input[@id='Form_LineNumber']")
	private WebElement LineNumber;
	
	
	@FindBy(xpath="//div[label[text()='Duty Type']]/following-sibling::div/span[@class='k-widget k-dropdown k-header js-military-type']")
	private WebElement clk_DutyTypedropDwn;

	public boolean waitForElement(WebDriver driver) {
		return wait.until(ExpectedConditions.visibilityOf(DutyStatus)).isDisplayed();
	}

	public String getDutyStatus() {
		return DutyStatus.getText();
	}

	public void clk_DutyDropDown() {
		DutyDropDown.click();
	}

	/*
	 * input 1: Active Duty , input 2: Inactive , input 3: None
	 */

	public void getElement(String input, WebDriver driver) {
		By by = By.xpath("//li[text()='" + input + "']");
		wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
	}

	public void enterActiveDutyEndDate() {
		try {
			if (Active_Duty_End_Date.isDisplayed() == true) {
				Active_Duty_End_Date.clear();
				Active_Duty_End_Date.sendKeys("09302016");
			}
		} catch (Exception e) {
			return;
		}
	}

	public WebElement WaitUntilVisibilityOfSignaturePad() {
		By by = By.xpath("//div[@class='signature-area']/descendant::canvas[@class='signature-pad']");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void clickOnApproveButton() {
		ApproveBtn.click();
	}

	public void ClickOnConfirmButton(WebDriver driver) throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ConfirmWithCustomerBtn);
		Thread.sleep(2000);
		ConfirmWithCustomerBtn.click();
	}

	public void drawSignature(WebDriver driver) {
		Actions builder = new Actions(driver);
		Action drawAction = builder.moveToElement(SignaturePad, 135, 15).clickAndHold().moveByOffset(200, 60).release()
				.clickAndHold().moveByOffset(300, 70).release().doubleClick().build();
		drawAction.perform();
	}

	public void ClickOnAcceptButton() {
		AcceptBtn.click();
	}

	public void ClickOnSubmitButton() {
		SubmitButton.click();
	}

	public void EnterNotes() {
		NotesTxtArea.sendKeys("Entering some text");
	}

	public void EnterEmployeeNumber(String EmployeeCode) {
		EmployeeNumber.sendKeys(EmployeeCode);
	}

	public void ClickOnContinue() {
		ContinueBtn.click();
	}

	public String getDutyStatusTxt() {
		return DutyStatus.getText();
	}

	public void enterBirthDetails(String birthDate) {
		BirthDate.clear();
		BirthDate.sendKeys(birthDate);
	}

	public void enterIdentificationDetails(String identificationNumber) {
		MilitaryIdentificationNumber.clear();
		MilitaryIdentificationNumber.sendKeys(identificationNumber);
	}

	public void enterActivityStartDate(String activeDutyStartDate) {
		ActiveDutyStartDate.sendKeys(activeDutyStartDate);
	}

	public void enterMilitaryUnit(String militaryUnit) {
		MilitaryUnit.clear();
		MilitaryUnit.sendKeys(militaryUnit);
	}
	//changed by basava

	public void select_MilitaryBranch(WebDriver driver, String expectedText) throws InterruptedException {
		driver.findElement(By.xpath("//div[label[text()='Military Branch']]/following-sibling::div/span")).click();
		Thread.sleep(2000);
		List<WebElement> listInDropDown = driver.findElements(By.xpath("//ul[@id='Form_MilitaryBranchTypeId_listbox']/li"));
		for (int i = 0; i < listInDropDown.size(); i++) {
			if (listInDropDown.get(i).getText().contains(expectedText)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", listInDropDown.get(i));
			}
		}
	}

	public void enterSupervisorFirstName(String supervisorFirstName) {
		MilitarySupervisorFirstName.clear();
		MilitarySupervisorFirstName.sendKeys(supervisorFirstName);
	}

	public void enterSupervisorLastName(String superviosrLastName) {
		MilitarySupervisorLastName.clear();
		MilitarySupervisorLastName.sendKeys(superviosrLastName);
	}

	public void enterSupervisorRank(String rank) {
		MilitarySupervisorRank.clear();
		MilitarySupervisorRank.sendKeys(rank);
	}

	//changed by basava
	public void selectPhoneType(WebDriver driver, String expectedPhoneType) throws InterruptedException {
		driver.findElement(By.xpath("//div[label[text()='Phone']]/following-sibling::div/span")).click();
		Thread.sleep(2000);
		List<WebElement> listInDropDown2 = driver.findElements(By.xpath("//ul[@id='Form_MilitarySupervisorPhoneType_listbox']/li"));
		for (int i = 0; i < listInDropDown2.size(); i++) {
			if (listInDropDown2.get(i).getText().contains(expectedPhoneType)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", listInDropDown2.get(i));
			}
		}
	}

	public void enterAreaCode(String areacode) {
		AreaCode.clear();
		AreaCode.sendKeys(areacode);
	}

	public void enterExchangeCode(String exchangecode) {
		ExchangeCode.clear();
		ExchangeCode.sendKeys(exchangecode);
	}

	public void enterLineNumber(String linenumber) {
		LineNumber.clear();
		LineNumber.sendKeys(linenumber);
	}
	
	
	public void clk_DutyTypeDropDownBtn(){
		
		clk_DutyTypedropDwn.click();
	}
	
	
	
	//changed by basava
	/*	public void selectPhoneType(WebDriver driver, String expectedPhoneType) throws InterruptedException {
			driver.findElement(By.xpath("//div[label[text()='Phone']]/following-sibling::div/span")).click();
			Thread.sleep(2000);
			List<WebElement> listInDropDown2 = driver.findElements(By.xpath("//ul[@id='Form_MilitarySupervisorPhoneType_listbox']/li"));
			for (int i = 0; i < listInDropDown2.size(); i++) {
				if (listInDropDown2.get(i).getText().contains(expectedPhoneType)) {
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", listInDropDown2.get(i));
				}
			}
		}*/
		
		public void selectPhoneType(WebDriver driver) throws InterruptedException {
			driver.findElement(By.xpath("//label[text()='Phone']/../following-sibling::div//span[text()='Select']")).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ul[@id='Form_MilitarySupervisorPhoneType_listbox']/li[4]")).click();
				}
	
	
	
	
	
	public void selDutyTypeFromDropDwn(String type){
		
		clk_DutyTypedropDwn.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		List<WebElement> list=driver.findElements(By.xpath("//div[@id='Form_MilitaryTypeId-list']//ul[@id='Form_MilitaryTypeId_listbox']/li"));
		
		for(WebElement data:list){
			
			String value=data.getText().trim();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(value.contains(type)){
				
				data.click();
				
			}
			
		}
		
		
	}

}
