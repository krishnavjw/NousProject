package Pages.TaskManagement;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateTaskPage 
{
	
	public CreateTaskPage(WebDriver driver)
	{
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@id='Title']")
	private WebElement taskName;
	
	@FindBy(xpath="//textarea[@id='Description']")
	private WebElement taskDescription;
	
	@FindBy(xpath="//span[text()='High Priority Task']")
	private WebElement selectPriorityListBoxField;
	
	@FindBy(xpath="//span[text()='Daily']")
	private WebElement selectSchedulerFrequencyField;
	
	@FindBy(xpath="//ul[@id='TaskPriorityId_listbox']/li")
	List<WebElement> priorityListValues;
	
	@FindBy(xpath="//input[@id='TimeToComplete']")
	private WebElement timetoComplete;
	
	@FindBy(xpath="//input[@id='StartTimeHh']")
	private WebElement startTimeHour;
	
	@FindBy(xpath="//input[@id='StartTimeMm']")
	private WebElement startTimeMinute;
	
	@FindBy(xpath="//ul[@id='TaskFrequencyId_listbox']/li")
	List<WebElement> taskFrequencylistValues;
	
	@FindBy(xpath="//label[input[@name='Monday']]/span[@class='button']")
	private WebElement deliveryOptionDayCheckboxes;
	
	@FindBy(xpath="//span[text()='No Group Selected']")
	private WebElement propertyGroupField;
	
	@FindBy(xpath="//ul[@id='PropertyGroupId_listbox']/li")
	List<WebElement> propertyGroupListValues;
	
	@FindBy(xpath="//span[text()='Regional Manager']")
	private WebElement assignToField;
	
	@FindBy(xpath="//ul[@id='TaskAssignmentTypeId_listbox']/li")
	List<WebElement> assignToListValues;
	
	@FindBy(xpath="//a[@id='submitButton']")
	private WebElement create_button;
	

	@FindBy(xpath="//div[@id='StartDt']/span")
	private WebElement SchedulerStartDate_Calander;
	
	
	@FindBy(xpath="//input[@id='employeeNumber']")
	private WebElement EmpID;
	
	@FindBy(xpath="//a[contains(text(),'OK')]")
	private WebElement Ok_btn;
	
	@FindBy(xpath="//a[@id='submitButton']")
	private WebElement submitBtn;
	
	
	@FindBy(id="TaskMasterItems_0_Content")
	private WebElement taskItemcontent1;
	
	@FindBy(id="TaskMasterItems_1_Content")
	private WebElement taskItemcontent2;
	
	@FindBy(id="TaskMasterItems_2_Content")
	private WebElement taskItemcontent3;
	
	@FindBy(xpath="(//form[@id='viewTaskMasterForm']//span[contains(text(),'+ Add')])[2]")
	private WebElement addBtn;
	
	@FindBy(xpath="//span[text()='Medium Priority Task']")
	private WebElement selectprioritylist;
	
	@FindBy(xpath="//form[@id='createTaskForm']/div[1]//span[text()='Select']")
	private WebElement selectprioritylistadhoc;
	
	@FindBy(xpath="//label[@id='TimeToCompleteDays']/span[@class='button']")
	private WebElement daysRadiobtn;
	
	@FindBy(xpath="//label[@id='AssignToRole']/span[@class='button']")
	private WebElement roleRadioBtn;
	
	@FindBy(xpath="(//form[@id='createTaskForm']/div[2]//span[contains(text(),'Select')])[1]")
	private WebElement propgrouplistadhoc;
	
	@FindBy(xpath="//ul[@id='LocationTypeId_listbox']/li")
	List<WebElement> propgrouplistadhocvalues;
	
	@FindBy(xpath="//form[@id='createTaskForm']//div[@id='AssignToRoleIdFields']//span[contains(text(),'Select')]")
	private WebElement AssignToDropdownAdhoc;
	
	@FindBy(id="LocationCode")
	private WebElement groupText;

	
	
	
	
	public void enterTaskName(String Name){
		taskName.sendKeys(Name);
	}
	
	public void enterTaskDescription(String Description) throws InterruptedException{
		taskDescription.clear();
		Thread.sleep(2000);
		taskDescription.sendKeys(Description);
	}
	
	public void selectPriority(String priority) throws InterruptedException{
		selectPriorityListBoxField.click();
		Thread.sleep(2000);
		for(int i=0;i<priorityListValues.size();i++)
		{
			if(priorityListValues.get(i).getText().equalsIgnoreCase(priority))
			{
				priorityListValues.get(i).click();
			}
		}
		Thread.sleep(2000);
		
		
	}
	
	
	
	
	
	public void selectRadioButtonValue(String value,WebDriver driver){
		WebElement radioButton=driver.findElement(By.xpath("//label[span[text()='"+value+"']]/span[@class='button']"));
		radioButton.click();
		
	}
	
	
	public void enterTimetoComplete(String value) throws InterruptedException{
		timetoComplete.clear();
		Thread.sleep(2000);
		timetoComplete.sendKeys(value);
	}

	
	public void enterStartTime(String hour,String minute) throws InterruptedException{
		startTimeHour.clear();
		Thread.sleep(3000);
		startTimeHour.sendKeys(hour);
		Thread.sleep(1000);
		startTimeMinute.clear();
		Thread.sleep(1000);
		startTimeMinute.sendKeys(minute);
		Thread.sleep(1000);
	}
	
	
	public void selectSchedulerFrequency(String frequency) throws InterruptedException{
		selectSchedulerFrequencyField.click();
		Thread.sleep(1000);
		
		for(int i=0;i<taskFrequencylistValues.size();i++)
		{
			if(taskFrequencylistValues.get(i).getText().equalsIgnoreCase(frequency))
			{
				taskFrequencylistValues.get(i).click();
			}
		}
		Thread.sleep(1000);
		
		
		
	}
	
	
	public void selectDeliveryOptionDay(String dayvalues,WebDriver driver){
		List<String> daysList = Arrays.asList(dayvalues.split(","));
		
		for (int i=0;i<daysList.size();i++){
		WebElement selectdeliveryOptionDay=driver.findElement(By.xpath("//label[input[@name='"+daysList.get(i)+"']]/span[@class='button']"));
		selectdeliveryOptionDay.click();
		}
	}
	
	
	
	public void selectPropertyGroup(String frequency) throws InterruptedException{
		propertyGroupField.click();
		Thread.sleep(1000);
		
		for(int i=0;i<propertyGroupListValues.size();i++)
		{
			if(propertyGroupListValues.get(i).getText().equalsIgnoreCase(frequency))
			{
				propertyGroupListValues.get(i).click();
			}
		}
		Thread.sleep(1000);
	}
	
	
	public void selectAssignToValue(String frequency) throws InterruptedException{
		assignToField.click();
		Thread.sleep(1000);
		
		for(int i=0;i<assignToListValues.size();i++)
		{
			if(assignToListValues.get(i).getText().equalsIgnoreCase(frequency))
			{
				assignToListValues.get(i).click();
			}
		}
		Thread.sleep(1000);
	}

	public void clickCreateButton(WebDriver driver) {
		Actions act=new Actions(driver);
		act.moveToElement(create_button).click().build().perform();
	}
	
	
	
	public void click_SchedulerStartDate_Calander(WebDriver driver) {
		SchedulerStartDate_Calander.click();
	}
	
	public void enter_EmpID(String Empid){
		
		EmpID.sendKeys(Empid);
	}

	
	
	public void click_Ok_btn(){
		Ok_btn.click();
	}
	
	
	
	public void click_submitBtn(){
		submitBtn.click();
	}
	
	public void enter_TaskItem1(String value){
		taskItemcontent1.sendKeys(value);
	}
	
	public void enter_TaskItem2(String value){
		taskItemcontent2.sendKeys(value);
	}
	
	public void enter_TaskItem3(String value){
		taskItemcontent3.sendKeys(value);
	}
	
	

	
	public void clk_AddBtn(){
		addBtn.click();
	}
	
	public void selectHighPriority(String priority) throws InterruptedException{
		selectprioritylist.click();
		Thread.sleep(3000);
		
		for(int i=0;i<priorityListValues.size();i++)
		{
			if(priorityListValues.get(i).getText().equalsIgnoreCase(priority))
			{
				priorityListValues.get(i).click();
			}
		}
		Thread.sleep(2000);
		
	}
	
	public void selectPriorityListAdhoc(String priority) throws InterruptedException{
		selectprioritylistadhoc.click();
		Thread.sleep(3000);
		
		for(int i=0;i<priorityListValues.size();i++)
		{
			if(priorityListValues.get(i).getText().equalsIgnoreCase(priority))
			{
				priorityListValues.get(i).click();
			}
		}
		Thread.sleep(2000);
		
	}
	
	public void clk_DaysRadioBtn(){
		daysRadiobtn.click();
	}
	
	public void clk_RoleRadioBtn(){
		roleRadioBtn.click();
	}
	
	
	public void selectPropertyGroupAdhoc(String property) throws InterruptedException{
		propgrouplistadhoc.click();
		Thread.sleep(3000);
		
		for(int i=0;i<propgrouplistadhocvalues.size();i++)
		{
			if(propgrouplistadhocvalues.get(i).getText().equalsIgnoreCase(property))
			{
				propgrouplistadhocvalues.get(i).click();
			}
		}
		Thread.sleep(2000);
		
	}
	
	public void selectAssignToValueAdhoc(String frequency) throws InterruptedException{
		AssignToDropdownAdhoc.click();
		Thread.sleep(1000);
		
		for(int i=0;i<assignToListValues.size();i++)
		{
			if(assignToListValues.get(i).getText().equalsIgnoreCase(frequency))
			{
				assignToListValues.get(i).click();
			}
		}
		Thread.sleep(1000);
	}
	
	public void enterGroupid(String sitenumber){
		groupText.sendKeys(sitenumber);
	}

	
	
	
	
	
	
	
	
	
	
}
