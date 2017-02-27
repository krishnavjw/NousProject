package Pages.TaskManagement;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class ViewTaskMastersPage 

{
	
	WebDriver driver;
	
	
	@FindBy(xpath="//a[@id='submitButton']")
	private WebElement createTaskButton;
	
	@FindBy(xpath="//div[@id='createTaskTemplate']//span[text()='select']")
	private WebElement selectTaskTypeField;
	
	@FindBy(xpath="//select[@id='TaskTypeIdDdl']")
	private WebElement taskType;
	
	@FindBy(xpath="//div[@class='text-align-right']//a[contains(text(),'Create')]")
	private WebElement create_Button;
	
	
	public ViewTaskMastersPage(WebDriver driver) 
	{
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void click_CreateTask(){
		createTaskButton.click();
	}
	
	public void selectTaskOption(String value,WebDriver driver) throws InterruptedException{
	
		selectTaskTypeField.click();
		Thread.sleep(2000);
		
		/*Actions act=new Actions(driver);
		
		if(value.equals("Simple Task")){
			act.sendKeys(Keys.DOWN).build().perform();
			act.sendKeys(Keys.ENTER).build().perform();
		}
		*/
		List<WebElement> listTaskType = driver.findElements(By.xpath("//ul[@id='TaskTypeIdDdl_listbox']/li"));
		for(WebElement allTaskType:listTaskType){
			if(allTaskType.getText().equals("Simple Task")){
				allTaskType.click();
				break;
			}
		}
		
		
		Thread.sleep(3000);
		
		//create_Button.click();
	
	}
	
	public void clk_CreateBtn(){
		create_Button.click();
	}
	
	public void selectOptionTaskType(String value) throws InterruptedException{
		selectTaskTypeField.click();
		Thread.sleep(2000);
		List<WebElement> listTaskType = driver.findElements(By.xpath("//ul[@id='TaskTypeIdDdl_listbox']/li"));
		for(WebElement allTaskType:listTaskType){
			if(allTaskType.getText().equals("List Task")){
				allTaskType.click();
				break;
			}
		}
		
		
		Thread.sleep(3000);
		
		
	}
	
	
	public void selectTaskOptionAdhoc(String value,WebDriver driver) throws InterruptedException{
		
		selectTaskTypeField.click();
		Thread.sleep(2000);
		
		if(value.equals("Ad Hoc Task")){
			driver.findElement(By.xpath("//ul[@id='TaskTypeIdDdl_listbox']/li[text()='Ad Hoc Task']")).click();
		}
		
		Thread.sleep(3000);
	}
	
	
	
	
}
