package GenericMethods;



import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;




import java.io.File;

import javax.imageio.ImageIO;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class NousListeners implements ITestListener 
{

	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) 
	{
		try
		{ 
			
		   Robot r = new Robot();
		   Dimension d= Toolkit.getDefaultToolkit().getScreenSize();
		   Rectangle rectagle=new Rectangle(d);
		   BufferedImage img=r.createScreenCapture(rectagle);
		   
		   ImageIO.write(img, "jpg", new File("./src/main/resources/ScreenShots/"+"screenshot_"+System.currentTimeMillis()+".jpg"));
		   
		  // ImageIO.write(img, "jpg", new File(".\\Screen-Shots\\"+"screenshot_"+System.currentTimeMillis()+".jpg"));
		}
		catch(Exception e)
		{
			
		}
		
		
	}

	public void onTestSkipped(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestSuccess(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	
}

