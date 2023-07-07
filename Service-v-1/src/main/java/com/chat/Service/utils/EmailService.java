package com.chat.Service.utils;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.chat.Service.config.EmailConfig;
import com.chat.Service.entity.UserSertified;
import com.chat.Service.entity.UserValide;
import com.chat.Service.entity.UsersE;
import com.chat.Service.security.JWTUtile;
import com.chat.Service.security.SECURITY_UTILS;
import com.chat.Service.service.RolesService;
import com.chat.Service.service.UserValideService;
import com.chat.Service.service.UsersEService;
import com.chat.Service.service.UsersSertifiedService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.WriterException;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import reactor.core.publisher.Mono;


@Component

public class EmailService  {

    private LocalTime myObj ;
	private LocalDateTime expirationDateTime;
	private LocalDate dateObjet;
    private DateTimeFormatter myFormatObj, DateObj ;  
    private static final String PNG_MIME = "image/png";
    private String TimeSend="", codeValidation="" ;  
	     
	private final Logger log = LoggerFactory.getLogger(EmailService.class);
	@Autowired
	private UserValideService userValideS;
	@Autowired
    private JavaMailSender emailSender;

	private RolesService rolesS;
	private UsersEService userServ;
	private UsersSertifiedService usersSS;
    private final EmailConfig emailConfig;
	private UserValideService userVS;
	private UUID uuid ;
	private TemplateEngine template;
    


	@Autowired
	public EmailService(
		EmailConfig emailConfig, 
		JavaMailSender emailSender,
		UserValideService userVS,
		TemplateEngine template,
		UsersEService userServ,
		RolesService rolesS,
		UsersSertifiedService usersSS
	) {
		this.emailConfig 	= emailConfig;
		this.emailSender 	= emailSender;
		this.userVS 	 	= userVS;
		this.template = template;
		this.userServ = userServ;
		this.rolesS = rolesS;
		this.usersSS = usersSS;
	}

    public void sendSimpleMessage(UsersE  userC,String imageLogo, Locale locale, String QR_CODE_IMAGE_PATH) throws MessagingException{
		
		String subject = "Mail Validation";
		String sambleur = "", sembleurFordata="";

		if (userServ.findByEmail(userC.getEmail()) != null) {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			FileSystemResource imagQRcode = new FileSystemResource(new File(QR_CODE_IMAGE_PATH));
			codeValidation = this.CodeValidation();

			sambleur = SECURITY_UTILS.CODE_FOR_USER(codeValidation);
			sembleurFordata = SECURITY_UTILS.CODE_IN_DATA(codeValidation);

			// Prepare the evaluation context			
			Map<String, Object> variables = new HashMap<>();
            variables.put("name", userC.getName() + " " + userC.getPrenom());
            variables.put("email", userC.getEmail());           
			variables.put("codeValidation", sambleur);
            variables.put("timeSend", this.GetTimeSend());  
			variables.put("imageResourceName", imageLogo);          
			variables.put("timeExp", this.GetTimeExpValidation());
			try {
             	QrCodeService.generateQRCodeImage(sambleur,250,250,QR_CODE_IMAGE_PATH);
			} catch (WriterException | IOException e) {
				e.printStackTrace();
			}

            
	
			helper.setFrom(emailConfig.getUsername());
			helper.setTo(userC.getEmail());
			helper.setSubject(subject);
			String html = this.template.process("welcome-email", new Context(locale, variables));
			helper.setText(html, true);
			String time, date;
			date = this.GetTimeExpValidation().substring(0, 10);
			time = this.GetTimeExpValidation().substring(11, 19);
			

			String imageGeneratQRcode  = imagQRcode.getPath().substring(19, (imagQRcode.getPath()).length());
			ClassPathResource clr = new ClassPathResource(imageGeneratQRcode);
    		helper.addInline("springLogo", clr, PNG_MIME);

			userVS.saveCode(sembleurFordata, time, date ,userC);
			emailSender.send(message);
		} 
    }


	public Mono<String> SimpleMessage(String to, String text, String subject ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailConfig.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
        return Mono.just("Email Sended ");
    }
    
    public  Mono<String> sendEmailWithAttachment(String toEmail,
            String body,
            String subject,
            String attachment
	) throws MessagingException {

			MimeMessage mimeMessage = emailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			
			FileSystemResource fileSystem = new FileSystemResource(new File(attachment));
			mimeMessageHelper.setFrom(emailConfig.getUsername());
			mimeMessageHelper.setTo(toEmail);
			mimeMessageHelper.setText(body);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.addAttachment(fileSystem.getFilename(),fileSystem);
			

			
			emailSender.send(mimeMessage);
			return Mono.just("Email Sended ");
	
	}
      
    public void email(String toEmail,
            String body,
            String subject, 
            PdfService pdfService, 
            Map<String, Object> map, 
            HttpServletResponse response) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 587);     
        Session session = Session.getDefaultInstance(properties, null);

        ByteArrayOutputStream outputStream = null;
 
        try {           
            //construct the text body part
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(body);

            //now write the PDF content to the output stream
            outputStream = new ByteArrayOutputStream();
            writePdf(outputStream, response, map);
            byte[] bytes = outputStream.toByteArray();

            //construct the pdf body part
            DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
            MimeBodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(dataSource));
            pdfBodyPart.setFileName("test.pdf");

			MimeMessage mimeMessage = emailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper
			= new MimeMessageHelper(mimeMessage, true);
			
			mimeMessageHelper.setFrom(emailConfig.getUsername());
			mimeMessageHelper.setTo(toEmail);
			mimeMessageHelper.setText(body);
			mimeMessageHelper.setSubject(subject);
			
			mimeMessageHelper.addAttachment("my.pdf", new ByteArrayResource(bytes) );
			emailSender.send(mimeMessage);
			log.info("Inside find User of User");
   
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            //clean off
            if(null != outputStream) {
                try { outputStream.close(); outputStream = null; }
                catch(Exception ex) { }
            }
        }
    }
    
    public void writePdf(
		ByteArrayOutputStream outputStream, 
		HttpServletResponse response, 
		Map<String, Object> model
	) throws Exception {
        
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);

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

    
    public void emailExel(
    		String toEmail,
            String body,
            String subject, 
    		HttpServletResponse response, 
			Map<String, Object> model) throws Exception {
    	
    	byte[] bytes =  buildExcelDocument(response, model);
    	
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper
		= new MimeMessageHelper(mimeMessage, true);
		
		mimeMessageHelper.setFrom(emailConfig.getUsername());
		mimeMessageHelper.setTo(toEmail);
		mimeMessageHelper.setText(body);
		mimeMessageHelper.setSubject(subject);
		
		mimeMessageHelper.addAttachment("my.xls", new ByteArrayDataSource(bytes, "application/vnd.ms-excel") );
		emailSender.send(mimeMessage);
		log.info("Inside find User of User");
    }

	public byte[] buildExcelDocument(HttpServletResponse response, 
			Map<String, Object> model) throws Exception {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
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
		
		// Write the output to a temporary excel file
		FileOutputStream fos = new FileOutputStream("temp.xls");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		workbook.write(bos); // write excel data to a byte array
		fos.close();

		// Now use your ByteArrayDataSource as
		byte[] bytes = bos.toByteArray();
		
		return bytes;
	}


	private String CodeValidation(){
		this.uuid = UUID.randomUUID();
		return (uuid.toString()).substring(0, (uuid.toString()).indexOf("-"));
	}
	private String GetTimeExpValidation() {
		myFormatObj = DateTimeFormatter.ofPattern(JWTUtile.FORMAT_DATE_TIME);
        expirationDateTime = LocalDateTime.now().plusMinutes(JWTUtile.VALIDATION_DURATION_MINUTES);
        String TimeExpValidation = expirationDateTime.format(myFormatObj);

		// it expir after 10min
		return TimeExpValidation;
	}

	private String GetDateTimeSertified() {
		myFormatObj = DateTimeFormatter.ofPattern(JWTUtile.FORMAT_DATE_TIME);
        expirationDateTime = LocalDateTime.now();
        String TimeExpValidation = expirationDateTime.format(myFormatObj);
		return TimeExpValidation;
	}
	
	public String valideCompte(@Valid UserValide userV) 
	{
		if (
			userValideS.findByEmail(userV.getEmail()) != null && userServ.findByEmail(userV.getEmail())  != null
		) {
			UserValide user = userValideS.findByEmail(userV.getEmail());
			if (this.VerifTimeSend(user.getTimeExp(), user.getDateExp()).compareToIgnoreCase(JWTUtile.VALIDE_CODE) == 0) {
				if 
				( (userV.getCode().replace(JWTUtile.SPECIAL_CHARACTERS_O, JWTUtile.SPECIAL_CHARACTERS_I)
				  .trim()).compareTo((user.getCode()).trim()) == 0 
				) 
				{
					UsersE getuser = userServ.findByEmail(userV.getEmail());
					getuser.setSertified(true);
					userServ.updateUser(getuser.getId(), getuser);
					rolesS.addRolesToPersonnes(userV.getEmail(), JWTUtile.ROLES_USER);
					UserSertified uss = new UserSertified(null, this.GetDateTimeSertified(), getuser.getEmail(), getuser.getName());
					usersSS.saveUser(uss);

					return JWTUtile.USER_SERTIFIED;
				} 
				else return JWTUtile.CODE_INVALIDE;
			}
			else return JWTUtile.EXPIRED_CODE;
		}
		else return JWTUtile.EMAIL_NOT_FOUND;
	}

    public void reSendCodeUser(
		@Valid UsersE userC, 
		Locale locale, 
		HttpServletResponse response,
		String imageLogo,	
		String QR_CODE_IMAGE_PATH
	) throws MessagingException, 
		StreamWriteException, 
		DatabindException, 
		IOException 
	{
		String subject = "Mail Validation";
		String sambleur = "", sembleurFordata="";
		if (userServ.findByEmail(userC.getEmail()) != null) {
			UsersE usersE = userServ.findByEmail(userC.getEmail());
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			FileSystemResource imagQRcode = new FileSystemResource(new File(QR_CODE_IMAGE_PATH));
			
			codeValidation = this.CodeValidation();
			sambleur = SECURITY_UTILS.CODE_FOR_USER(codeValidation);
			sembleurFordata = SECURITY_UTILS.CODE_IN_DATA(codeValidation);

			Map<String, Object> variables = new HashMap<>();
            variables.put("name", usersE.getName() + " " + usersE.getPrenom());
            variables.put("email", usersE.getEmail());           
			variables.put("codeValidation", sambleur);
            variables.put("timeSend", this.GetTimeSend());  
			variables.put("imageResourceName", imageLogo);          
			variables.put("timeExp", this.GetTimeExpValidation());
			try {
             	QrCodeService.generateQRCodeImage(sambleur,250,250, QR_CODE_IMAGE_PATH);
			} catch (WriterException | IOException e) {
				e.printStackTrace();
			}

            String html = this.template.process("welcome-email", new Context(locale, variables));
    		
			helper.setFrom(emailConfig.getUsername());
            helper.setTo(usersE.getEmail());
            helper.setSubject(subject);
            helper.setText(html, true);
			String time, date;
			date = this.GetTimeExpValidation().substring(0, 10);
			time = this.GetTimeExpValidation().substring(11, 19);

			String imageGeneratQRcode  = imagQRcode.getPath().substring(19, (imagQRcode.getPath()).length());
			ClassPathResource clr = new ClassPathResource(imageGeneratQRcode);
    		helper.addInline("springLogo", clr, PNG_MIME);

			userVS.saveCode(sembleurFordata, time, date ,userC);
			emailSender.send(message);
		}else{
			log.info("this Email "+userC.getEmail()+" does not existe");
			Map<String, String> massage = new HashMap<>();
			massage.put("Message-Error", "this Email "+userC.getEmail()+" does not existe");
			response.setContentType("application/json");
			new ObjectMapper().writeValue(response.getOutputStream(), massage);
		}



    }

	private String GetTimeSend() {
		myFormatObj = DateTimeFormatter.ofPattern(JWTUtile.FORMAT_TIME);
		myObj = LocalTime.now();
		TimeSend = myObj.format(myFormatObj);

		return TimeSend;
	}

	private String VerifTimeSend(String TimeExp, String dateExp) {
		myFormatObj = DateTimeFormatter.ofPattern(JWTUtile.FORMAT_TIME);
		myObj = LocalTime.now();
		String TimeSend = myObj.format(myFormatObj);

		DateObj = DateTimeFormatter.ofPattern(JWTUtile.FORMAT_DATE);
		dateObjet = LocalDate.now();
		String dateSend = dateObjet.format(DateObj);

		return this.CheckTime(TimeExp, dateExp, TimeSend, dateSend);
	}

	private String CheckTime(String TimeExp,String dateExp, String TimeSend,String dateSend){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(JWTUtile.FORMAT_DATE_TIME);

        LocalDateTime expirationDateTime = LocalDateTime.parse(dateExp + " " + TimeExp, formatter);
        LocalDateTime sendDateTime = LocalDateTime.parse(dateSend + " " + TimeSend, formatter);

        LocalDateTime expirationPlus10Min = expirationDateTime.plusMinutes(JWTUtile.VALIDATION_DURATION_MINUTES);

        if (sendDateTime.isAfter(expirationPlus10Min)) {
            return JWTUtile.EXPIRED_CODE;
        } else {
            return JWTUtile.VALIDE_CODE;
        }

	}



	
}
