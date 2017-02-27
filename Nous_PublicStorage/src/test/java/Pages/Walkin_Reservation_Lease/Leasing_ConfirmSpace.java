package Pages.Walkin_Reservation_Lease;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Leasing_ConfirmSpace {

	WebDriver driver;
	
	@FindBy(xpath="//div[@id='selectedUnitsFormToUpdate']//table/tbody")
	private WebElement space_select_table;
	
	@FindBy(linkText="Back To Dashboard")
	private WebElement back_dashboard;
	
	@FindBy(xpath="//span[text()='Add Another Space']")
	private WebElement add_another_space;
	
	@FindBy(id="cancelLeaseButton")
	private WebElement cancel_lease;
	
	@FindBy(id="saveandproceed")
	private WebElement save_proceed;
	
	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']//span[contains(text(),'Script')]")
	private WebElement link_script;
	
	@FindBy(xpath="//div[@id='customerscreenandscriptpanel']//span[contains(text(),'Customer Screen')]")
	private WebElement link_customer_screen;
	
	@FindBy(id="notes-add")
	private WebElement add_notes;
	
	@FindBy(xpath="//h3[text()='Leasing']")
	private WebElement title_LeasingPage;
	
	@FindBy(xpath="//div[@id='FutureMoveInDate']/span")
	private WebElement moveInDate_Leasing;
	
	@FindBy(xpath="//span[text()='SELECT']")
	private WebElement Sel_CancelReasn;

	@FindBy(id="employeeNumber")
	private WebElement Txt_EmpId;

	@FindBy(xpath="//a[contains(text(),'Yes')]")
	private WebElement Clk_YESBtn;
	
	@FindBy(id="confirmWithCustomer")
	private WebElement confirm_withCust;



	@FindBy(xpath="//div[@id='selectedUnits']//table//tbody/tr[1]/td[1]//label[@class='webchamp-radio-button confirmradiobutton']/span[@class='button']")
	private WebElement space1_radiobtn;

	
	public void click_BackToDashboard(){
		back_dashboard.click();
	}
	
	public void click_AddAnotherSpace(){
		add_another_space.click();
	}
	
	public void click_CancelLease(){
		cancel_lease.click();
	}
	
	public void click_SaveAndProceed(){
		save_proceed.click();
	}
	
	public void click_Script(){
		link_script.click();
	}
	
	public void click_CustomerScreen(){
		link_customer_screen.click();
	}
	
	public void click_AddNotes(){
		add_notes.click();
	}
	
	public boolean select_GivenSpace(String spaceNum){
		
		String getSpace = "";
		boolean spaceFound = false;
		List<WebElement> tableRows = space_select_table.findElements(By.tagName("tr"));
		for(WebElement element : tableRows){
			getSpace = element.findElement(By.xpath(".//td[4]")).getText();
			if(getSpace.equals(spaceNum)){
				element.findElement(By.xpath(".//td[1]//label/span[@class='button']")).click();
				spaceFound = true;
				break;
			}
		}
		
		return spaceFound;
	}

	public Leasing_ConfirmSpace(WebDriver driver)
	{
		PageFactory.initElements(driver,this);
	}
	public String getTitle_LeasingPage(){
		return title_LeasingPage.getText();
	}
	
	public void click_space_radiobtn1(){
		space1_radiobtn.click();
	}

	
	public void clk_ConfirmwtCust(){
		confirm_withCust.click();
	}
	
	public void click_MoveInDate(){
		moveInDate_Leasing.click();
	}
	
	public void Sel_ValFronCancelRsn(String data) throws InterruptedException
	{
		Sel_CancelReasn.click();
		Thread.sleep(3000);
		List<WebElement> ls = driver.findElements(By.xpath("//ul[@class='k-list k-reset ps-container ps-active-y']/li"));

		for(int i=0;i<ls.size();i++)
		{
			if(ls.get(i).getText().equals(data))
			{
				ls.get(i).click();
				break;
			}
		}
	}

	public void enter_EmpId(String empid)
	{
		Txt_EmpId.sendKeys(empid);
	}

	public void clk_onyesBtn()
	{
		Clk_YESBtn.click();
	}
	
	public boolean verify_leasingpage(){
		return title_LeasingPage.isDisplayed();
	}
	
	public void SelDropdown(){
		Sel_CancelReasn.click();
	}
}
