package Pages.AWB2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;


public class AuctionBuyerCreateNotepage {
	
	private WebDriver driver;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header note-category']/span/span[contains(text(),'SELECT')]")
	private WebElement catagarydropdown;
	
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header note-application']/span/span[contains(text(),'SELECT')]")
	private WebElement applynotedropdown;
	
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header note-application']/span/span[2]")
	private WebElement applynotearrowmark;
	
	@FindBy(xpath="//span[@class='k-widget k-dropdown k-header note-category validation-error']/span/span[2]")
	private WebElement catagoryarrowmark;
	
	@FindBy(xpath="//textarea[@id='noteText']")
	private WebElement notetextbox;
	
	
	@FindBy(xpath="//div[@id='customerNoteDialog']//a[contains(text(),'Create Note')]")
	private WebElement createnotebtn;
	
	@FindBy(xpath="//a[contains(text(),'Cancel')]")
	private WebElement cancelbtn;
	
	
	@FindBy(xpath="//div[contains(text(),'Please enter a note')]")
	private WebElement createnoteerrormsg;
	
	
	@FindBy(id="employeeNumber")
	private WebElement empnum;
	
	
	@FindBy(xpath="//span[contains(text(),'Please enter an employee number')]")
	private WebElement emperrormsg;
	
	
	@FindBy(xpath="//h2[@class='notes-history-header half-padding']")
	private WebElement recenthistory;
	
	@FindBy(xpath="//div[@class='notes-grid k-grid k-widget']//th[contains(text(),'Date')]")
	private WebElement header_date;
	
	@FindBy(xpath="//div[@class='notes-grid k-grid k-widget']//th[contains(text(),'Time')]")
	private WebElement header_time;
	
	@FindBy(xpath="//div[@class='notes-grid k-grid k-widget']//th[contains(text(),'Category')]")
	private WebElement header_Category;
	
	@FindBy(xpath="//div[@class='notes-grid k-grid k-widget']//th[contains(text(),'Notetaker')]")
	private WebElement header_Notetaker;
	
	@FindBy(xpath="//div[@class='notes-grid k-grid k-widget']//th[contains(text(),'Note')]")
	private WebElement header_Note;
	
	@FindBy(xpath="//div[@class='notes-grid k-grid k-widget']//th[contains(text(),'Space')]")
	private WebElement header_Space;
	
	
	public AuctionBuyerCreateNotepage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isdisplay_default(){
		return catagarydropdown.isDisplayed();
	} 
	
	
	public boolean isdisplay_default_applynote(){
		return applynotedropdown.isDisplayed();
	} 
	
	public boolean isdisplay_notetext(){
		return notetextbox.isDisplayed();
	} 
	public boolean isdisplay_emptextbox(){
		return empnum.isDisplayed();
	}

	public boolean isdisplay_notetexterrormsg(){
		return createnoteerrormsg.isDisplayed();
	} 
	
	public boolean isdisplay_createbtn(){
		return createnotebtn.isDisplayed();
	} 
	
	public boolean isdisplay_cancelbtn(){
		return cancelbtn.isDisplayed();
	} 
	
	
	public boolean isdisplay_emptexterrormsg(){
		return emperrormsg.isDisplayed();
	} 
	
	
	public boolean isdisplay_recenthistory(){
		return recenthistory.isDisplayed();
	} 
	
	public void clk_categorydropdown(){
		 catagarydropdown.click();
	} 
	
	
	public void clk_applynotedropdown(){
		applynotedropdown.click();
	} 
	
	
	public void click_createnote(){
		createnotebtn.click();
	} 
	
	public boolean isdisplay_headerdate(){
		return header_date.isDisplayed();
	} 
	
	public boolean isdisplay_headertime(){
		return header_time.isDisplayed();
	} 
	public boolean isdisplay_headercategory(){
		return header_Category.isDisplayed();
	} 
	public boolean isdisplay_headernote(){
		return header_Note.isDisplayed();
	} 
	public boolean isdisplay_headernotetaker(){
		return header_Notetaker.isDisplayed();
	} 
	public boolean isdisplay_headerspace(){
		return header_Space.isDisplayed();
	} 
	
	
	public void enter_notetext(String note){
		 notetextbox.sendKeys(note);
	} 
	
	public void enter_empnum(String empnumdata){
		empnum.sendKeys(empnumdata);
	} 

	public void click_applynotearrow(){
		applynotearrowmark.click();
	} 
	public void click_catagoryarrow(){
		catagoryarrowmark.click();
	}
	
	public void clk_cancel(){
		cancelbtn.click();
	} 
	
}
