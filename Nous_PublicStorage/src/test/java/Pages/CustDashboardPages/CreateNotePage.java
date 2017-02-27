package Pages.CustDashboardPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNotePage {
	WebDriver driver;
	
	@FindBy(id="customerNoteDialog_wnd_title")
	private WebElement title_CreateNote;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']/div/div/div[2]//span[contains(text(),'SELECT')]")
	private WebElement category_Dropdown;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']/div/div/div[4]//span[contains(text(),'SELECT')]")
	private WebElement ApplyNoteTo_Dropdown;
	
	@FindBy(id="noteText")
	private WebElement noteText;
	
	@FindBy(id="employeeNumber")
	private WebElement employeeNumber;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//a[contains(text(),'Create Note')]")
	private WebElement createnoteBtn;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//a[contains(text(),'Cancel')]")
	private WebElement cancel_Btn;
	
	@FindBy(xpath="//h2[contains(text(),'Recent Notes History')]")
	private WebElement recentNotesHistory;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//th[@data-title='Date']")
	private WebElement date;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//th[@data-title='Time']")
	private WebElement time;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//th[@data-title='Category']")
	private WebElement category;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//th[@data-title='Space']")
	private WebElement space;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//th[@data-title='Note']")
	private WebElement note;
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//th[@data-title='Notetaker']")
	private WebElement notetaker;
	
	@FindBy(xpath="//div[contains(text(),'Please select a category.')]")
	private WebElement cat_ValidationMsg;
	
	@FindBy(xpath="//div[contains(text(),'Please select an option.')]")
	private WebElement apply_ValidationMsg;
	
	@FindBy(xpath="//div[contains(text(),'Please enter a note.')]")
	private WebElement note_ValidationMsg;
	
	@FindBy(xpath="//span[contains(text(),'Please enter an employee number.')]")
	private WebElement employee_ValidationMsg;
	

	public CreateNotePage(WebDriver driver)
	{

		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	
	public boolean verify_CreateNoteTitle(){
		return title_CreateNote.isDisplayed();
	}
	
	public void enterNote(String Note){
		noteText.sendKeys(Note);
	}
	
	public void enterEmployeeNumber(String Note){
		employeeNumber.sendKeys(Note);
	}
	
	public void clk_CreateNoteBtn(){
		createnoteBtn.click();
	}
	
	public boolean verify_CreateNoteBtn(){
		return createnoteBtn.isDisplayed();
	}
	
	public void clk_CancelBtn(){
		cancel_Btn.click();
	}
	
	public boolean verify_CancelBtn(){
		return cancel_Btn.isDisplayed();
	}
	
	public boolean verify_RecentNotesHistory(){
		return recentNotesHistory.isDisplayed();
	}
	
	public boolean verify_Date(){
		return date.isDisplayed();
	}
	
	public boolean verify_Time(){
		return time.isDisplayed();
	}
	
	public boolean verify_Category(){
		return category.isDisplayed();
	}
	
	public boolean verify_Space(){
		return space.isDisplayed();
	}
	
	public boolean verify_Note(){
		return note.isDisplayed();
	}
	
	public boolean verify_Notetaker(){
		return notetaker.isDisplayed();
	}
	
	public boolean verify_CatValidation(){
		return cat_ValidationMsg.isDisplayed();
	}
	
	public boolean verify_applyValidation(){
		return apply_ValidationMsg.isDisplayed();
	}
	
	public boolean verify_NoteValidation(){
		return note_ValidationMsg.isDisplayed();
	}
	
	public boolean verify_empValidation(){
		return employee_ValidationMsg.isDisplayed();
	}
	
	public boolean verify_CategoryDropdown(){
		return category_Dropdown.isDisplayed();
	}
	
	public boolean verify_applyNoteToDropdown(){
		return ApplyNoteTo_Dropdown.isDisplayed();
	}
	
	public boolean verify_NoteTxt(){
		return note.isDisplayed();
	}
	
	public boolean verify_EmployeeTxt(){
		return employeeNumber.isDisplayed();
	}
	
	
	public void select_Category(String data) throws InterruptedException{
		category_Dropdown.click();
		Thread.sleep(2000);
		List<WebElement> cate_List = driver.findElements(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li"));
		for(int i=0;i<cate_List.size();i++){
			if(cate_List.get(i).getText().equalsIgnoreCase(data)){
				cate_List.get(i).click();
				break;
			}
		}
	}
	
	public void select_ApplyNoteTo(String data1) throws InterruptedException{
		ApplyNoteTo_Dropdown.click();
		Thread.sleep(2000);
		List<WebElement> apply_List = driver.findElements(By.xpath("//div[@class='k-animation-container']//ul[@class='k-list k-reset']/li"));
		for(int i=0;i<apply_List.size();i++){
			if(apply_List.get(i).getText().equalsIgnoreCase(data1)){
				apply_List.get(i).click();
				break;
			}
		}
	}
	
}
