package com.chat.Service.utils;

import com.chat.Service.entity.UsersE;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletResponse;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class PdfService  {

	
    public void export(HttpServletResponse response, Map<String, Object> model) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);
        
        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(18);
        fontTitle.setColor(Color.WHITE);
        


        Paragraph paragraph = new Paragraph("Liste des Utilisateurs.", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        //----------------------
        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);

        
		List<UsersE> list = (List<UsersE>) model.get("usersList");
		
		PdfPTable table = new PdfPTable(4);//4 colonnes
		cell.setPhrase(new Phrase("Nom", fontTitle));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("PRENOM", fontTitle));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("EMAIL", fontTitle));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("ETAT COMPTE", fontTitle));
		table.addCell(cell);
		
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);
		
		for(UsersE user : list) {
			table.addCell(user.getName());
			table.addCell(user.getPrenom());
			table.addCell(user.getEmail());
			table.addCell(String.valueOf(user.getSertified()));
		}

        /*Paragraph paragraph2 = new Paragraph(table, fontParagraph);
        paragraph2.setAlignment(Paragraph.ALIGN_LEFT);*/

        document.add(paragraph);
        document.add(table);
        document.close();
    }
    



}
