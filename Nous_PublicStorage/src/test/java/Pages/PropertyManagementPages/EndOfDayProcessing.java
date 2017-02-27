package Pages.PropertyManagementPages;

import java.sql.Driver;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.thoughtworks.selenium.webdriven.commands.DragAndDrop;


public class EndOfDayProcessing {

	@FindBy(xpath="//div[@id='eodProcessing']//h3[text()='End Of Day Processing']")
	private WebElement page_Title;

	@FindBy(xpath="//div[@id='eodProcessingGrid']//table//thead/tr/th[2][text()='Payment Type']")
	private WebElement paymentType;

	@FindBy(xpath="//div[@id='eodProcessingGrid']//table//thead/tr/th[3][text()='Transactions']")
	private WebElement transactions;

	@FindBy(xpath="//div[@id='eodProcessingGrid']//table//thead/tr/th[4][text()='System Total']")
	private WebElement systemTotal;

	@FindBy(xpath="//div[@id='eodProcessingGrid']//table//thead/tr/th[5][text()='Manual Count']")
	private WebElement Manualcount;

	@FindBy(xpath="//div[@id='eodProcessingGrid']//table//thead/tr/th[7][text()='Comments']")
	private WebElement comments;

	private WebDriver driver;




	public EndOfDayProcessing(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	public boolean verify_page_Title(){
		return page_Title.isDisplayed();
	}

	public boolean verify_paymentType(){
		return paymentType.isDisplayed();
	}

	public boolean verify_transactions(){
		return transactions.isDisplayed();
	}

	public boolean verify_systemTotal(){
		return systemTotal.isDisplayed();
	}

	public boolean verify_Manualcount(){
		return Manualcount.isDisplayed();
	}

	public boolean verify_comments(){
		return comments.isDisplayed();
	}



	public String get_paymentType(){
		return paymentType.getText();
	}

	public String get_transactions(){
		return transactions.getText();
	}

	public String get_systemTotal(){
		return systemTotal.getText();
	}

	public String get_Manualcount(){
		return Manualcount.getText();
	}

	public String  get_comments(){
		return comments.getText();
	}

	public String data_PaymentType(){
		String payment1 = null;
		List<WebElement> lst=driver.findElements(By.xpath("//div[@id='eodProcessingGrid']//table//tbody/tr/td[2]/span"));
		for(WebElement payment:lst){
			payment1=payment.getText();
			System.out.println(payment.getText());}
		
		return payment1;
		}

		public String data_Transactions(){
			String transaction1 = null;
			List<WebElement> lst=driver.findElements(By.xpath("//div[@id='eodProcessingGrid']//table//tbody/tr/td[3]"));
			for(WebElement transaction:lst){
				transaction1=transaction.getText();
				System.out.println(transaction.getText());}
			return transaction1;
			}

			public String data_SystemTotal(){
				String systotal=null;
				List<WebElement> lst=driver.findElements(By.xpath("//div[@id='eodProcessingGrid']//table//tbody/tr/td[4][@id='data-systemtotal']"));
				for(WebElement SystemTotal:lst){
					systotal=SystemTotal.getText();
					System.out.println(SystemTotal.getText());}
				return systotal;
				}

				public String data_ManualCount(){
					String Manualcnt=null;
					List<WebElement> lst=driver.findElements(By.xpath("//div[@id='eodProcessingGrid']//table//tbody/tr/td[5][@id='Cash']"));
					for(WebElement ManualCount:lst){
						Manualcnt=ManualCount.getText();
						System.out.println(ManualCount.getText());}
					return Manualcnt;
					}

					public String data_Comments(){
						String comment=null;
						List<WebElement> lst=driver.findElements(By.xpath("//div[@id='eodProcessingGrid']//table//tbody/tr/td[7]"));
						for(WebElement Comments:lst){
							comment=Comments.getText();
							System.out.println(Comments.getText());
						}
						return comment;

					}


				}
