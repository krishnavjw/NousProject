package GenericMethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;
import org.testng.Reporter;

public class Excel 
{


	public static int getRowNum(String path, int sheetNum)
	{
		int rowcount=0;
		try{
			FileInputStream fis= new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			rowcount= wb.getSheetAt(sheetNum).getLastRowNum();

		}
		catch(Exception e)
		{

		}
		return rowcount;



	}

	public static int getColNum(String path, int sheetNum,int rowNum)
	{
		int colcount=0;
		try{
			FileInputStream fis= new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			colcount= wb.getSheetAt(sheetNum).getRow(rowNum).getLastCellNum();
			//wb.getSheetAt(sheetNum).getRow(rowNum).

		}
		catch(Exception e)
		{

		}
		return colcount;



	}





	public static String getCellValue_ByIndex(String path, int sheetNum, int rowNum, int colNum)
	{
		String rowval=null;
		try{
			FileInputStream fis= new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			rowval= wb.getSheetAt(sheetNum).getRow(rowNum).getCell(colNum).toString();

		}
		catch(Exception e)
		{

		}


		return rowval;


	}

	//-----------------------------------------------------------------------------------------------
	//Method : getCellValue_ByColName
	//Description : This method is used to Retrieve the value from the excel sheet on the basis of test case name and header.
	//Author : Testing Team


	public static String getCellValue_ByColName(String path, int sheetNum, String TestCase_Name, String colName)
	{

		FileInputStream fis;
		Workbook wb=null;
		int rowcnt=0;
		int colcnt=0;
		try{
			fis= new FileInputStream(path);
			wb= WorkbookFactory.create(fis);
			int rowNum= wb.getSheetAt(sheetNum).getLastRowNum();
			for(int i=1; i<=rowNum;i++)
			{
				if(TestCase_Name.equalsIgnoreCase(wb.getSheetAt(sheetNum).getRow(i).getCell(0).toString()))
				{
					rowcnt=i;
					break;
				}

			}
			int colNum=wb.getSheetAt(sheetNum).getRow(0).getLastCellNum();
			for(int j=0; j<colNum; j++)
			{
				if(colName.equalsIgnoreCase(wb.getSheetAt(sheetNum).getRow(0).getCell(j).toString()));
				{
					colcnt=j;
					break;
				}
			}



		}
		catch(Exception e)
		{

		}

		return wb.getSheetAt(sheetNum).getRow(rowcnt).getCell(colcnt).toString();

	}



	//-----------------------------------------------------------------------------------------------
	//Method : setCellValue
	//Description : This method is used to set the value in the excel sheet
	//Author : Testing Team


	public static void setCellValue(String path, String sheet, int rowNum, int colNum, String Result)
	{
		try{
			FileInputStream fis= new FileInputStream(path);
			Workbook wb= WorkbookFactory.create(fis);
			Cell cell=wb.getSheet(sheet).getRow(rowNum).createCell(colNum);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(Result);

			FileOutputStream fos= new FileOutputStream(path);
			wb.write(fos);
			fos.close();	

		}

		catch(Exception e)
		{

		}
	}    


	//-----------------------------------------------------------------------------------------------
	//Method : getCellValue_inlist
	//Description : This method is used to Retrieve the value from the excel sheet on the basis of test case name
	//Author : Testing Team


	public static Object[][] getCellValue_inlist(String path_excel,String moduleName,String sheetname, String TestCase_Name)
	{

		FileInputStream fis;
		Workbook wb=null;
		int rowcnt=1;
		int colcnt=0;

		try{
			fis= new FileInputStream(path_excel);
			wb= WorkbookFactory.create(fis);
		}
		catch(Exception e){
		}

		int rowNum= wb.getSheet(sheetname).getLastRowNum();


		int colstartNumber=rowcnt;
		int totalcolNum=wb.getSheet(sheetname).getRow(colstartNumber).getLastCellNum();


		int TCrownum=0;
		for(int i=1; i<=rowNum;i++)
		{
			if(wb.getSheet(sheetname).getRow(i).getCell(1).toString().equals(TestCase_Name))
			{
				TCrownum=i;
				break;

			}

		}

		int TCdatarow=TCrownum;

		int totalcoldataNum=wb.getSheet(sheetname).getRow(TCdatarow).getLastCellNum();


		System.out.println("the TC total data rows-----------"+totalcoldataNum);

		Object[][] data= new Object[1][1];
		int index=0;
		Hashtable<String,String> ht= null;

		for(int rNum=1; rNum<2; rNum++){

			ht=new Hashtable<String,String>();

			for(int colnum=1; colnum<totalcoldataNum; colnum++){

				ht.put(wb.getSheet(sheetname).getRow(1).getCell(colnum).toString(),
						wb.getSheet(sheetname).getRow(TCdatarow).getCell(colnum).toString());  

			}

			ht.put(moduleName,wb.getSheet(sheetname).getRow(0).getCell(1).toString());

			data[index][0]=ht;

			index++;

		}

		return data; 


	}



	//    public static String getCellData(String path,String sheetName,int colNum,int rowNum) 
	//    {
	//    	FileInputStream fis;
	//    	try{
	//            fis= new FileInputStream(path_excel);
	//          }
	//          catch(Exception e){
	//          }
	//           Workbook wb= WorkbookFactory.create(fis);
	//           
	//           try{
	//                  if(rowNum <=0)
	//                        return "";
	//           
	//           int index = wb.getSheetIndex(sheetName);
	//
	//           if(index==-1)
	//                  return "";
	//           
	//    
	//           sheet = wb.getSheetAt(index);
	//           row = sheet.getRow(rowNum);
	//           if(row==null)
	//                  return "";
	//           cell = row.getCell(colNum);
	//           if(!(cell==null))
	//                  return cell.toString();
	//           
	//
	//      else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
	//          return "";
	//      else 
	//             return String.valueOf(cell.getBooleanCellValue());
	//           }
	//           catch(Exception e){
	//                  
	//                  e.printStackTrace();
	//                  return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
	//           }
	//    }


	//-----------------------------------------------------------------------------------------------
	//Method : setCellValBasedOnTcname
	//Description : This method is used to set the result against the test case after execution of test case.
	//Author : Testing Team

	public static void setCellValBasedOnTcname(String path, String sheet, String tcname, String colHeader, String Result)
	{

		
		FileInputStream fis;
		Workbook wb=null;
		int rowcnt=0;
		int colcnt=0;
		try{
			fis= new FileInputStream(path);
			wb= WorkbookFactory.create(fis);
			int rowNum= wb.getSheet(sheet).getLastRowNum();
			for(int i=2; i<=rowNum;i++)
			{
				if(tcname.equalsIgnoreCase(wb.getSheet(sheet).getRow(i).getCell(1).toString()))
				{
					rowcnt=i;
					break;
				}

			}
			int colNum=wb.getSheet(sheet).getRow(0).getLastCellNum();
			for(int j=0; j<colNum; j++)
			{
				if(colHeader.equalsIgnoreCase(wb.getSheet(sheet).getRow(1).getCell(j).toString()));
				{
					colcnt=j;
					break;
				}
			}

			Cell cell=wb.getSheet(sheet).getRow(rowcnt).createCell(colcnt);
			cell.setCellType(cell.CELL_TYPE_STRING);
			cell.setCellValue(Result);


			FileOutputStream fos= new FileOutputStream(path);
			wb.write(fos);
			fos.close();

		}
		catch(Exception e)
		{

		}

	
	}    

	public static void msExcel_Copy_File(String sSrc_File , String sDest_File)
	{

		Workbook workbook1=null;
		XSSFWorkbook workbook2=new XSSFWorkbook();
		try{

			FileInputStream fis=new FileInputStream(new File(sSrc_File));
			workbook1=WorkbookFactory.create(fis);
		}
		catch(Exception e)
		{

		}
		int sheetNum=workbook1.getNumberOfSheets();
		Reporter.log(""+sheetNum,true);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(sDest_File));
		} catch (FileNotFoundException e1) {

			e1.printStackTrace();
		}
		for(int i=0; i<sheetNum;i++)
		{
			String sheetName=workbook1.getSheetAt(i).getSheetName();
			Reporter.log(sheetName,true);
			//Create the Sheet in Dest Workbook
			XSSFSheet sh=workbook2.createSheet(sheetName);

			int rowcnt=workbook1.getSheetAt(i).getLastRowNum();
			for(int j=0; j<=rowcnt; j++)
			{
				XSSFRow rw=sh.createRow(j);
				if(j==0){
					XSSFCellStyle moduleStyle = workbook2.createCellStyle();
					moduleStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
					moduleStyle.setFillForegroundColor(HSSFColor.INDIGO.index);
					moduleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				
				}else if(j==1){
					XSSFCellStyle colHeaderStyle = workbook2.createCellStyle();
					colHeaderStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
					colHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				}
					
				int colcnt=workbook1.getSheetAt(i).getRow(j).getLastCellNum();
				for(int k=0;k<colcnt;k++)
				{
					if(k==3)
					{
						break;

					}
					else
					{
						String value= workbook1.getSheetAt(i).getRow(j).getCell(k).toString();
						//Cell cell=sh.getRow(rowcnt).createCell(colcnt);
						Reporter.log(value,true);
						
						//XSSFRow rw=sh.createRow(j);
						XSSFCell cell=rw.createCell(k);
						cell.setCellType(cell.CELL_TYPE_STRING);
						cell.setCellValue(value);
						
//						if((j==0&&k==0)||(j==0&&k==1)){
//							XSSFCellStyle moduleStyle = workbook2.createCellStyle();
//							moduleStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
//							moduleStyle.setFillForegroundColor(HSSFColor.INDIGO.index);
//							moduleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//							
//						}else if((j==1&&k==0)||(j==1&&k==1)||(j==1&&k==2)){
//							
//							XSSFCellStyle colHeaderStyle = workbook2.createCellStyle();
//							colHeaderStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
//							colHeaderStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//						}
						
						if(k==0&&value=="Pass"){
							XSSFCellStyle PassStyle = workbook2.createCellStyle();
							PassStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
							PassStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
							
						}else if(k==0&&value=="Fail"){
							
							XSSFCellStyle PassStyle = workbook2.createCellStyle();
							PassStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
							PassStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						}else if((k==0&&value=="Skip")){
							
							XSSFCellStyle PassStyle = workbook2.createCellStyle();
							PassStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
							PassStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
						}
						//cell.setCellValue(value);
						
						// Auto-size the columns.
						sh.autoSizeColumn(0);
						sh.autoSizeColumn(1);
					}
				}

			}
		}

		try {
			workbook2.write(fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	
	public static void copyHtmlFile(File source, File dest) throws IOException {
	    FileInputStream is = null;
	    FileOutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}
}


