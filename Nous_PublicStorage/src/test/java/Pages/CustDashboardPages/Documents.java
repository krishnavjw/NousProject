package Pages.CustDashboardPages;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.relevantcodes.extentreports.LogStatus;

public class Documents {
	private WebDriver driver;
	
	@FindBy(xpath="//a[@id='addDocumentButton']")
	private WebElement addDocbtn;
	
	@FindBy(xpath="//div[@class='k-grid-header-wrap']/table/thead/tr/th[text()='Date Created']")
	public WebElement dateCreatedColName;
	
	@FindBy(xpath="//div[@class='k-grid-header-wrap']/table/thead/tr/th[text()='Created Time']")
	public WebElement createdTimeColName;
	
	@FindBy(xpath="//div[@class='k-grid-header-wrap']/table/thead/tr/th[text()='Document Type']")
	public WebElement documentTypeColName;
	
	@FindBy(xpath="//div[@class='k-grid-header-wrap']/table/thead/tr/th[text()='Space']")
	public WebElement SpaceColName;
	
	@FindBy(xpath="//div[@class='k-grid-header-wrap']/table/thead/tr/th[text()='Format']")
	public WebElement formatColName;
	
	@FindBy(xpath="//div[@id='documents-grid']//table//tr[1]/td[1]")
	private WebElement Curdate;
	
	@FindBy(xpath="//div[@id='documents-grid']//table//tr[1]/td[2]")
	private WebElement CurTime;
	
	@FindBy(xpath="//div[@id='documents-grid']//table//tr[1]/td[3]")
	private WebElement CurDocType;
	
	@FindBy(xpath="//div[@id='documents-grid']//table//tr[1]/td[4]")
	private WebElement CurSpace;
	
	@FindBy(xpath="//div[@id='documents-grid']//table//tr[1]/td[5]")
	private WebElement CurFormat;
	
	@FindBy(xpath="//div[@id='documents-grid']//table//tr[1]/td[6]/a")
	private WebElement CurviewBtn;


	public Documents(WebDriver driver)
	{
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
		
	}
	public boolean verify_AddDocument(){
		return addDocbtn.isDisplayed();
	}
	
	public void Clk_AddDocument(){
		addDocbtn.click();
	}
		
	
	public boolean verify_DateCreated()
	{
		return dateCreatedColName.isDisplayed();
	}
	
	public boolean verify_createdTime()
	{
		return createdTimeColName.isDisplayed();
	}
	
	public boolean verify_documentType()
	{
		return documentTypeColName.isDisplayed();
	}
	
	public boolean verify_Space()
	{
		return SpaceColName.isDisplayed();
	}
	
	
	public boolean verify_Format()
	{
		return formatColName.isDisplayed();
	}
	
	
	public String get_Date(){
		return Curdate.getText();
	}
	
	public String get_Time(){
		return CurTime.getText();
	}
	
	public String get_DocType(){
		return CurDocType.getText();
	}
	
	public String get_Space(){
		return CurSpace.getText();
	}
	
	public String get_Format(){
		return CurFormat.getText();
	}
	
	public void clk_ViewBtn(){
		CurviewBtn.click();
	}
	
	
	public void verify_data_DateCreated(String value)
	{
		List<WebElement> lst =driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']/table/tbody/tr/td[1]"));
		System.out.println("The list size:" +lst.size());
		Iterator<WebElement> itr= lst.iterator();
		while(itr.hasNext()){
			String  dateCreated=itr.next().getText();
			if(dateCreated.equals(value)){
				}
		}
	}
	
	public void verify_data_CreatedTime(String value)
	{
		List<WebElement> lst =driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']/table/tbody/tr/td[2]"));
		System.out.println("The list size:" +lst.size());
		Iterator<WebElement> itr= lst.iterator();
		while(itr.hasNext()){
			String  dateCreated=itr.next().getText();
			if(dateCreated.equals(value)){
				}
		}
	}
	
	
	public void verify_data_DocumentType(String value)
	{
		List<WebElement> lst =driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']/table/tbody/tr/td[3]"));
		System.out.println("The list size:" +lst.size());
		Iterator<WebElement> itr= lst.iterator();
		while(itr.hasNext()){
			String  dateCreated=itr.next().getText();
			if(dateCreated.equals(value)){
				}
		}
	}
	
	
	public void verify_data_Space(String value)
	{
		List<WebElement> lst =driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']/table/tbody/tr/td[4]"));
		System.out.println("The list size:" +lst.size());
		Iterator<WebElement> itr= lst.iterator();
		while(itr.hasNext()){
			String  dateCreated=itr.next().getText();
			if(dateCreated.equals(value)){
				}
		}
	}
	
	
	public void verify_data_Format(String value)
	{
		List<WebElement> lst =driver.findElements(By.xpath("//div[@class='k-grid-content ps-container']/table/tbody/tr/td[5]"));
		System.out.println("The list size:" +lst.size());
		Iterator<WebElement> itr= lst.iterator();
		while(itr.hasNext()){
			String  dateCreated=itr.next().getText();
			if(dateCreated.equals(value)){
				}
		}
	}
	
	
	
}
