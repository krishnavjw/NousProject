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


public class AuctionBuyerDAshBoard {
	
	private WebDriver driver;
	
	@FindBy(xpath="//h1[@class='customer-name bold']/span")
	private WebElement Buyername;
	
	@FindBy(xpath="(//span[contains(text(),'Auction Buyer')])[1]")
	private WebElement Buyertypedisplay;
	
	@FindBy(xpath="//span[contains(text(),'Auction Buyer')]/following-sibling::span[2]")
	private WebElement BuyeraccNum;
	
	@FindBy(xpath="//a[@href='/AuctionBuyer/BackToDashboard']")
	private WebElement BAckbtn;
	
	@FindBy(xpath="//div[contains(text(),' Address:')]")
	private WebElement displayaddresstag;
	
	
	@FindBy(xpath="//div[@class='addresses-container clearfix-container']//span[contains(text(),' Official')]")
	private WebElement official_address;
	
	
	@FindBy(xpath="//div[@class='addresses-container clearfix-container']//span[contains(text(),' Unofficial')]")
	private WebElement Unofficial_address;
	
	
	@FindBy(xpath="//div[@class='phones-container clearfix-container']//span[contains(text(),' home - Preferred - Official')]")
	private WebElement home_phone;
	
	
	@FindBy(xpath="//div[@class='phones-container clearfix-container']//span[contains(text(),' work - Official')]")
	private WebElement work_phone;
	
	
	@FindBy(xpath="//div[@class='emails-container padding-bottom clearfix-container']/div[2]")
	private WebElement email;
	
	
	@FindBy(xpath="//div[@class='identifications-container half-padding-bottom clearfix-container']/div[2]")
	private WebElement Identification;
	
	
	@FindBy(xpath="//a[contains(text(),'Create Note')]")
	private WebElement createnotebtn;
	
	@FindBy(xpath="//span[contains(text(),'Create Note')]")
	private WebElement createnotetitle;
	
	
	@FindBy(xpath="//a[contains(text(),'Edit Buyer Details')]")
	private WebElement  Edit_Buyer_Details;
	
	@FindBy(xpath="//a[contains(text(),'Edit Tax Exemption')]")
	private WebElement  Edit_tax_excempt;
	
	@FindBy(xpath="//div[@class='addresses-container clearfix-container']/div[2]/div/div/div[1]")
	private WebElement  adress1;
	
	@FindBy(xpath="//div[@class='emails-container padding-bottom clearfix-container']/div[2]")
	private WebElement  emailupdate;
	
	@FindBy(xpath="//span[contains(text(),'Documents')]")
	private WebElement  document_tab;
	
	@FindBy(xpath="//div[contains(text(),'Total Due Now')]")
	private WebElement total_due;
	
	
	
	public AuctionBuyerDAshBoard(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public String get_Buyername(){
		return Buyername.getText();
	}
	
	public String get_Buyerdisplaytype(){
		return Buyertypedisplay.getText();
	}
 
	public String get_Buyeraccnum(){
		return BuyeraccNum.getText();
	}   
	
	
	public void click_backbtn(){
		BAckbtn.click();
	} 
	
	public boolean isdisplayaddress(){
		return displayaddresstag.isDisplayed();
	} 
	
	
	public boolean isdisplayaddress_official(){
		return official_address.isDisplayed();
	} 
	
	
	public boolean isdisplayaddress_unofficial(){
		return Unofficial_address.isDisplayed();
	} 

	public boolean isdisplayphone_home(){
		return home_phone.isDisplayed();
	} 

	public boolean isdisplayphone_work(){
		return work_phone.isDisplayed();
	} 
	
	public boolean isdisplaycreatenotebtn(){
		return createnotebtn.isDisplayed();
	} 
	
	public String get_email(){
		return email.getText();
	} 
	
	public String get_identification(){
		return Identification.getText();
	} 
	
	public void click_createnote(){
		createnotebtn.click();
	} 
	
	public boolean isdisplaycreatenote_title(){
		return createnotetitle.isDisplayed();
	} 
	
	public void click_editbuyer(){
		Edit_Buyer_Details.click();
	} 
	
	public String get_adress(){
		return adress1.getText();
	}  
	
	
	public String get_emailupdate(){
		return emailupdate.getText();
	}  
	
	
	public void click_edittax(){
		Edit_tax_excempt.click();
	} 
	
	public void click_document(){
		document_tab.click();
	}
	
	public boolean isdisplaytotal_due(){
		return total_due.isDisplayed();
	} 
	
}
