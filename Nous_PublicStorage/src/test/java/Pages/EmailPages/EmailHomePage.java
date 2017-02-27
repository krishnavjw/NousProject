package Pages.EmailPages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.xpath.axes.SubContextList;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import GenericMethods.Generic_Class;

import com.relevantcodes.extentreports.LogStatus;

public class EmailHomePage {

	public EmailHomePage(WebDriver driver){
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div[contains(@class,'email-cornerstone')]")
	private WebElement header_Email;
	
	@FindBy(xpath="//div[@class='container email-menu']//button[@data-folder='Inbox']")
	private WebElement header_Inbox;
	
	@FindBy(xpath="//td[@class='subject']")
	private WebElement draftMailLink;
	
	@FindBy(xpath="//div[@class='container email-menu']//button[contains(text(),'Drafts')]")
	private WebElement draftsMenu;
	
	@FindBy(xpath="//div[@class='container email-menu']//button[contains(text(),'Sent')]")
	private WebElement sentMenu;
	
	@FindBy(xpath="//div[@id='readMessage']//div[text()='Sent:']")
	private WebElement sentEmailVerification;
	
	@FindBy(xpath="//span[@class='header-location-address']")
	private WebElement headerelements_Left;
	
	@FindBy(xpath="//div[@class='header-tray vertical-center']")
	private WebElement headerelements_Right;
	
	@FindBy(xpath="//span[@id='inboxUnreadCount']")
	private WebElement inboxUnreadCount;
	
	@FindBy(xpath="//button[contains(text(),'Inbox')]")
	private WebElement inboxFolder;
	
	@FindBy(xpath="//div[@class='email-menu-item border-bottom bold']/button[contains(text(),'Drafts')]")
	private WebElement draftsFolder;
	
	@FindBy(xpath="//button[contains(text(),'Sent')]")
	private WebElement sentFolder;
	
	@FindBy(xpath="//button[contains(text(),'Trash')]")
	private WebElement trashFolder;
	
	@FindBy(xpath="//span[@class='recipient-display-names']")
	private WebElement emailMessages;
	
	@FindBy(xpath="//span[contains(text(),'Filter:')]")
	private WebElement filterText;
	
	@FindBy(xpath="//span[text()='All']")
	private WebElement filterDropDown;
	
	@FindBy(xpath="//ul[li[text()='Unread']]")
	private WebElement filterDropDownValues;
	
	@FindBy(xpath="//span[text()='All']")
	private WebElement defaultSelectionfromDropDown;
	
	@FindBy(xpath="//input[@id='search']")
	private WebElement searchField;
	
	@FindBy(xpath="//button[@id='newmessage']")
	private WebElement newMessageButton;
	
	@FindBy(xpath="//a[@class='k-link']//div[contains(@class,'sprite-email-attachment')]")
	private WebElement attachmentColumn;
	
	@FindBy(xpath="//a[@class='k-link']//div[contains(@class,'sprite-action-needed')]")
	private WebElement flagColumn;
	
	@FindBy(xpath="//a[contains(text(),'From')]")
	private WebElement fromColumn;
	
	@FindBy(xpath="//a[contains(text(),'Subject')]")
	private WebElement subjectColumn;
	
	@FindBy(xpath="//a[contains(text(),'Date')]")
	private WebElement dateColumn;
	
	@FindBy(xpath="//td[@class='email-action']")
	private WebElement flagColumContent;
	
	@FindBy(xpath="//div[@class='sprite-action-needed-red']")
	private WebElement updatedFlag;
	
	@FindBy(xpath="//div[@class='sprite-email-attachment-black-transparent']")
	private WebElement attachmentColumnHeader;
	
	@FindBy(xpath="//span[@class='recipient-display-names']")
	private WebElement fromColumnContent;
	
	@FindBy(xpath="//div[@class='sprite-email-action-status']")
	private WebElement emailActionStatus;
	
	@FindBy(xpath="//td[@class='subject']")
	private WebElement subjectLineContent;
	
	@FindBy(xpath="//td[@class='date']//div[@class='floatleft']")
	private WebElement dateColumncontent;
	
	@FindBy(xpath="//span[@class='information-text']")
	private WebElement emailExpirationContent;
	
	@FindBy(xpath="//span[@class='recipient-display-names']")
	private WebElement locationNameinEmail;
	
	@FindBy(xpath="//td[@class='subject']")
	private WebElement subjectContent;
	
	@FindBy(xpath="//td[@class='from-address withData']/span[@class='recipient-display-names']")
	private WebElement fromContent;
	
	@FindBy(xpath="//td[@class='from-address withData']/span[@class='recipient-display-names']")
	List<WebElement> fromContentList;
	
	
	@FindBy(xpath="//div[@class='read-body']")
	private WebElement mailBody;
	
	@FindBy(xpath="//div[@class='email-command-row']/div[@class='read-commands js-read-commands']//button[@class='psbutton-normal large back-button js-back-button']")
	private WebElement backButton;
	
	
	@FindBy(xpath="//button[@class='delete-email-button']/span")
	private WebElement deleteIconforEmail;
	
	@FindBy(xpath="//tbody/tr[@class='email-row ']/td[@class='subject']")
	private WebElement mailinTrashFolder;
	
	@FindBy(xpath="//td[@class='date']/div/div[1]")
	List<WebElement> emailDates;
	
	
	@FindBy(xpath="//td[contains(text(),'test email')]")
	WebElement emailwithSubject;
	
	@FindBy(xpath="//div[@class='read-fields']/div[4]/div[2]")
	WebElement emailSubjectContent;
	
	@FindBy(id="search_target")
	WebElement search_button;
	
	public boolean exists_EmailHeader(){
		return header_Email.isDisplayed();
	}
	
	public boolean default_InboxFolder(){
		return header_Inbox.isEnabled();
	}

	public void openmailfromDraft() {
		draftMailLink.click();
		
	}

	public void openSentEmailsMenu() {
		sentMenu.click();
		
	}
	
	public void openmailfromSent(){
		draftMailLink.click();
	}
	
	public boolean verifySentMail()
	{
		return sentEmailVerification.isDisplayed();
	}
	
	public boolean isleftHeaderElementsDisplayed()
	{
		return headerelements_Left.isDisplayed();
	}
	
	public boolean isRightHeaderElementDisplayed()
	{
		return headerelements_Right.isDisplayed();
	}
	
	public boolean isInboxUnreadCountDisplayed()
	{
		return headerelements_Right.isDisplayed();	
	}
	
	public boolean isInboxFolderDisplayed()
	{
		return inboxFolder.isDisplayed();
	}
	
	public boolean isDraftsFolderDisplayed()
	{
		return draftsFolder.isDisplayed();
	}
	
	public boolean isSentFolderDisplayed()
	{
		return sentFolder.isDisplayed();
	}
	
	public boolean isTrashFolderDisplayed()
	{
		return trashFolder.isDisplayed();
	}
	
	public boolean isinboxFolderHighlighted(){
		return header_Inbox.isEnabled();
	}
	
	public void clickInboxFolder()
	{
		inboxFolder.click();
	}
	
	public void clickDraftsFolder()
	{
		draftsFolder.click();
	}
	
	public void clickSentEmailFolder()
	{
		sentFolder.click();
	}
	
	public void clickTrashFolder()
	{
		trashFolder.click();
	}
	
	public boolean isEmailMessagesDisplayed(){
		return emailMessages.isDisplayed();
	}
	
	public boolean isFilterTextDisplayed(){
		return filterText.isDisplayed();
	}
	
	public void click_FilterDropDown(){
		filterDropDown.click();
	}
	
	

	public boolean verifyFilterDropDownValues(WebDriver driver) {
		List<WebElement> dropdownValues=driver.findElements(By.xpath("//ul[li[text()='Unread']]//li"));
		int listSize=dropdownValues.size();
		System.out.println("List Size"+listSize);
		if(listSize==5)
			return true;
		else
			return false;
		
	}

	public boolean verifyDefaultSelectionFromDropDown() {
		return defaultSelectionfromDropDown.isDisplayed();
	}
	
	
	public boolean isSearchFieldDisplayed()
	{
		return searchField.isDisplayed();
	}
	
	public boolean isNewMessageButtonDisplayed()
	{
		return newMessageButton.isDisplayed();
	}
	
	public boolean verifyUnreadEmailCountInbox(WebDriver driver)
	{
		List<WebElement> unreadmailsCount=driver.findElements(By.xpath("//tbody/tr[@class='email-row  unread-email ']/td[@class='subject']"));
		int unreadMailsCount=unreadmailsCount.size();
		
	
		
		if(unreadMailsCount==0)
		{
			return true;
		}
		else
		{
			try{
			int valueNextToInbox=Integer.parseInt(inboxUnreadCount.getText());
				if(unreadMailsCount==valueNextToInbox)
				{
					return true;
				}
				else{
					return false;
				}
			}
			catch(Exception e){
				return true;
			}
		}
		
		
	}

	public boolean verifyColumnsinInboxPage() {
		
		return attachmentColumn.isDisplayed()&&flagColumn.isDisplayed()&&fromColumn.isDisplayed()&&subjectColumn.isDisplayed()&&dateColumn.isDisplayed();
		
	}
	
	
	
	public boolean verifySortingofDates(WebDriver driver) throws InterruptedException
	{
		 List<WebElement> dateValues=driver.findElements(By.xpath("//td[@class='email-action']"));
			
		   String[] dateValuesArray= new String[dateValues.size()];
			  
			  for (int i=0;i<dateValues.size();i++){
				  dateValuesArray[i]= dateValues.get(i).getText();
				  System.out.println("inserting into array---"+dateValuesArray[i]);
				  Thread.sleep(1000);
			  }
			  
			  Arrays.sort(dateValuesArray);
			  Thread.sleep(2000);
			  
			  for (int i=0;i<dateValues.size();i++){
					  if( dateValues.get(i).getText().equals(dateValuesArray[i])){
					  return true;
					  }else{
					  return false;
				  }
			  }
			 Thread.sleep(3000);
			return true;
		
	}
	
	
	public boolean verifySortingByFlag(WebDriver driver) throws InterruptedException
	{
	   List<WebElement> dateValues=driver.findElements(By.xpath("//td[@class='email-action']"));
		
	   String[] dateValuesArray= new String[dateValues.size()];
		  
		  for (int i=0;i<dateValues.size();i++){
			  dateValuesArray[i]= dateValues.get(i).getText();
			  System.out.println("inserting into array---"+dateValuesArray[i]);
			  Thread.sleep(1000);
		  }
		  
		  Arrays.sort(dateValuesArray);
		  Thread.sleep(2000);
		  
		  for (int i=0;i<dateValues.size();i++){
				  if( dateValues.get(i).getText().equals(dateValuesArray[i])){
				  return true;
				  }else{
				  return false;
			  }
		  }
		 Thread.sleep(3000);
		return true;
	}
	
	
	public boolean verifySortingByAttachment(WebDriver driver) throws InterruptedException 
	{
		List<WebElement> dateValues=driver.findElements(By.xpath("//div[@class='sprite-email-attachment-black']"));
		
		   String[] dateValuesArray= new String[dateValues.size()];
			  
			  for (int i=0;i<dateValues.size();i++){
				  dateValuesArray[i]= dateValues.get(i).getText();
				  System.out.println("inserting into array---"+dateValuesArray[i]);
				  Thread.sleep(1000);
			  }
			  
			  Arrays.sort(dateValuesArray);
			  Thread.sleep(2000);
			  
			  for (int i=0;i<dateValues.size();i++){
					  if( dateValues.get(i).getText().equals(dateValuesArray[i])){
					  return true;
					  }else{
					  return false;
				  }
			  }
			 Thread.sleep(3000);
			return true;
		
	}
	
	
	public boolean verifyFlagColumnContent() {
		return flagColumContent.isDisplayed();
	}
	
	public void click_flagColumnHeader(){
		flagColumn.click();
	}
	
	public void click_flagColumnContent(){
		flagColumContent.click();
	}

	public boolean verifyFlagStatusUpdate() {
		return updatedFlag.isDisplayed();
		
	}
	
	public boolean isAttachmentContentDisplayed(){
		return attachmentColumn.isDisplayed();
	}
	
	public void clickAttachmentColumnHeaderDisplayed(){
		attachmentColumnHeader.click();
	}

	public boolean isfromColumnContentDisplayed(){
		return fromColumnContent.isDisplayed();
	}
	
	public boolean isIconStatusDisplayed(){
		return emailActionStatus.isDisplayed();
	}
	
	
	public boolean isSubjectContentDisplayed(){
		return subjectLineContent.isDisplayed();
	}

	public boolean isDateColumnContentDisplayed() {
		return dateColumncontent.isDisplayed();
	}
	
	public boolean isEmailExpirationDisplayed(){
		return emailExpirationContent.isDisplayed();
	}
	
	public boolean islocationNumberDisplayed(){
		return locationNameinEmail.isDisplayed();
	}
	
	public boolean isSubjectDisplayed(){
		return subjectContent.isDisplayed();
	}
	
	public void enterSearchField(String str,WebDriver driver) throws InterruptedException{
		Actions act=new Actions(driver);
		
		searchField.clear();
		Thread.sleep(2000);
		searchField.sendKeys(str);
		Thread.sleep(2000);
		search_button.click();
		//act.moveToElement(searchField).sendKeys(Keys.ENTER).build().perform();
		//searchField.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
	}
	
	
	public boolean verifySearchedSubject(String str, WebDriver driver)
	{
	
		List<WebElement> subjContents=driver.findElements(By.xpath("//td[@class='subject']"));
		boolean res=true;
		for(int i=0;i<subjContents.size();i++)
		{
			String contText=subjContents.get(i).getText().toLowerCase();
			str=str.toLowerCase();
			if(contText.contains(str)){
			 res=res && true;}
			else{
				res=res && false;
				}
		}
		return res;
		
		
		
		
	}
	
	
	public int resultsCount(WebDriver driver){
		List<WebElement> subjContents=driver.findElements(By.xpath("//td[@class='subject']"));
		return subjContents.size();
		
	}
	
	
	public boolean verifySearchedFrom(String str)
	{
		List<WebElement> fromValue=fromContentList;
		boolean res=true;
		
		for(WebElement ele:fromValue)
			
		{

			String subjectText=ele.getText();
			if(subjectText.contains(str)){
				res=res && true;
			}
			else{
				res= res && false;
			}
			
			
		}
		return res;
		
	}
	

	
	
	
	public void openEmailMessage(){
		subjectContent.click();
	}
	
	public boolean verifyMailMessageDisplayed()
	{
		return mailBody.isDisplayed();
	}
	
	public void clickBackButton(){
		backButton.click();
	}
	
	
	public boolean verifyDeleteIconDisplayed(){
		return deleteIconforEmail.isDisplayed();
	}
	
	
	
	public String click_Delete(){
		deleteIconforEmail.click();
		return subjectContent.getText();
	}
	
	public boolean verifyMailinTrashFolder(String subject, WebDriver driver)
	{
		List<WebElement> mailsinTrashFolder=driver.findElements(By.xpath("//td[@class='subject']"));
		boolean result=false;
		for(int i=0;i<mailsinTrashFolder.size();i++){
			if(mailsinTrashFolder.get(i).getText().contains(subject))
				result=true;
			else
				continue;
		}
		return result;
		
	}
	
	public void movetoDeleteIcon(WebDriver driver){
		Actions act=new Actions(driver);
		act.moveToElement(deleteIconforEmail).build().perform();
	}
	
	
	public boolean verifyifOlderMessagesareDeleted(WebDriver driver) throws InterruptedException, ParseException{
		
		boolean res=false;
		
		Actions act=new Actions(driver);
		
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date currDate = new Date();
		System.out.println(dateFormat.format(currDate));
		String currDateValue=dateFormat.format(currDate).toString();
		String[] currentValue=currDateValue.split("/");
			
		System.out.println("month" + Integer.parseInt(currentValue[0]));
		System.out.println("date" + Integer.parseInt(currentValue[1]));
		System.out.println("year" + Integer.parseInt(currentValue[2]));
		
		
			
		
		for(int i=0;i<emailDates.size();i++){
			act.moveToElement(emailDates.get(i)).build().perform();
			Thread.sleep(1000);
			
			String emailDateValue=emailDates.get(i).getText();
			Date emailDa = new Date(emailDates.get(i).getText());
			String[] value=emailDateValue.split("/");
			String emailMonth=value[0];
			String emailDate=value[1];
			String emailYear=value[2];
			
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		//	LocalDateTime now = LocalDateTime.now();
			
			Date cdate = new Date();
					
			
			
			
			SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
			
			System.out.println("email value date " + emailDates.get(i).getText());
			
			Date d2;
			d2=format.parse(emailDates.get(i).getText());
			System.out.println("Date value displayed in the ui " + d2);
			
					
			
			 int diffInDays = (int) ((cdate.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
			
		
			
			
			if(diffInDays >14)
			{
				
				
				
				res= false;
			}
			
			else 
			{
				
								
				
				res=res&& true;
			}
			
		}
		
		return res;
	}
	
	
	public void openemailfromInbox() throws InterruptedException{
		emailwithSubject.click();
		Thread.sleep(3000);
	}
	
	
	public boolean verifyMailInbox(){
			try
			{
				if(emailSubjectContent.getText().contains("test email"))
				return true;
				else
				return false;
			}
			catch(Exception e)
			{
				return false;
			}
	}
	
	
}
