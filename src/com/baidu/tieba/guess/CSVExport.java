package com.baidu.tieba.guess;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CSVExport {
	
	public static void main(String[] args) throws Exception{
		Map<String, String> userHistory = new HashMap<String, String>();
		userHistory.put("paco", "10");
		userHistory.put("mata", "8");
		userHistory.put("silva", "90");
		System.out.println( CSVExport.export(userHistory ) );
	}

	public static String export(Map<String, String> userHistory) throws Exception{
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet = wb.createSheet("Sheet 1");

		 
		 int i = 0 ;
		 for (String key: userHistory.keySet()){
			 Row output = sheet.createRow(i++);
			
			 Cell keyCell = output.createCell(0);
			 keyCell.setCellValue(key);
			 Cell markCell = output.createCell(1);
			 markCell.setCellValue(userHistory.get(key));
		 }

		 File excelTemp  = File.createTempFile( UUID.randomUUID().toString() ,"workbook.xls");
	     FileOutputStream fileOut = new FileOutputStream( excelTemp);
	     wb.write(fileOut);
	     fileOut.close();
	     return excelTemp.getAbsolutePath();
	}
	
	public static String exportAll(Map<String, String> userHistory) throws Exception{
		 Workbook wb = new HSSFWorkbook();
		 Sheet sheet = wb.createSheet("Sheet 1");

		 
		 int i = 0 ;
		 for (String key: userHistory.keySet()){
			 Row output = sheet.createRow(i++);
			
			 Cell keyCell = output.createCell(0);
			 keyCell.setCellValue(key);
			 Cell markCell = output.createCell(1);
			 markCell.setCellValue(userHistory.get(key));
		 }

		 File excelTemp  = File.createTempFile( UUID.randomUUID().toString() ,"workbook.xls");
	     FileOutputStream fileOut = new FileOutputStream( excelTemp);
	     wb.write(fileOut);
	     fileOut.close();
	     return excelTemp.getAbsolutePath();
	}
}
