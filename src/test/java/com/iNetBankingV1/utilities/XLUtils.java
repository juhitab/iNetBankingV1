package com.iNetBankingV1.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XLUtils {
 
	public String filepath;
	public FileInputStream fin;
	public FileOutputStream fout;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	
	public XLUtils(String filepath, String sheetname) throws IOException {
		this.filepath=filepath;
		fin = new FileInputStream(filepath);
		workbook =  new XSSFWorkbook(fin);
		sheet = workbook.getSheet(sheetname);
		
	}
	public int getRowCount() {
		return sheet.getLastRowNum();	
	}
	public int getCellCount(int rownum) {
		row = sheet.getRow(rownum);
		return row.getLastCellNum();	
	}
	public String getCellData(int rownum, int colnum) {
		row = sheet.getRow(rownum);
		cell = row.getCell(colnum);
		String cellData;
		try {
			DataFormatter formatter = new DataFormatter(); 	//Creates a formatter using the default locale.
			cellData = formatter.formatCellValue(cell);//Returns the formatted value of a cell as a String regardlessof the cell type. For null cell value, it returns empty string. If the Excel format pattern cannot be parsed then thecell value will be formatted using a default format. 
		}catch(Exception e) {
			cellData = "";
		}
		return cellData;
}
	public void setCellData(int rownum, int colnum, String data) throws IOException {
		row = sheet.getRow(rownum);
		cell = row.createCell(colnum); //create a new cell for the specified row in specified column
		cell.setCellValue(data); //data is passed as String
		fout = new FileOutputStream(filepath);
		workbook.write(fout);
		fout.close();
	}
	public void close() {
		try {
			workbook.close();
			fin.close();
		}catch(Exception e) {}
		
	}
}
