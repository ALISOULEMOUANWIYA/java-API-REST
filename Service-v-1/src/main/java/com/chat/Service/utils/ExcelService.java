package com.chat.Service.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.chat.Service.entity.UsersE;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
public class ExcelService {

	public void buildExcelDocument(HttpServletResponse response, 
			Map<String, Object> model) throws Exception {
		
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			@SuppressWarnings("unused")
			List<UsersE> list = (List<UsersE>) model.get("usersList");
			XSSFSheet sheet = workbook.createSheet("liste des users");
			//Entete de ligne
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("NOM");
			header.createCell(1).setCellValue("PRENOM");
			header.createCell(2).setCellValue("EMAIL");
			header.createCell(3).setCellValue("ETAT COMPTE");
			int rowNumber = 1;
			for(UsersE user : list) {
				Row row = sheet.createRow(rowNumber++);
				row.createCell(0).setCellValue(user.getName());
				row.createCell(1).setCellValue(user.getPrenom());
				row.createCell(2).setCellValue(user.getEmail());
				row.createCell(3).setCellValue(user.getSertified());
			}
		}
		
		
	}

}
