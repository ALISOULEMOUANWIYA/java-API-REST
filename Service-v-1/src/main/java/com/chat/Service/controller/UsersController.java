package com.chat.Service.controller;
/**
 * @author ali
 *
 */

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chat.Service.entity.UserValide;
import com.chat.Service.entity.UsersE;
import com.chat.Service.error.UsersNotFoundException;
import com.chat.Service.service.UserValideService;
import com.chat.Service.service.UsersEService;
import com.chat.Service.utils.EmailService;
import com.chat.Service.utils.ExcelService;
import com.chat.Service.utils.PdfService;
import com.chat.Service.utils.QrCodeService;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.google.zxing.WriterException;
import com.lowagie.text.DocumentException;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/user")
public class UsersController {
	
	private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/image/qrcode-spring.png";	
	private static final String QR_CODE_IMAGE = "image/qrcode-spring.png";

	private static final String imageLogo = "./src/main/resources/image/email_logo.png";
	
	private UsersEService usersEService;
    private EmailService emailService;
    private PdfService pdfService;
    private ExcelService excelService;
	
	private final Logger log = 
			LoggerFactory.getLogger(UsersController.class);

	@Autowired
	public UsersController(
		UsersEService usersEService,
		EmailService emailService,
		PdfService pdfService,
		ExcelService excelService
	){
		this.usersEService  = usersEService;
		this.emailService   = emailService;
		this.pdfService 	= pdfService;
		this.excelService   = excelService;
	}

	@PostMapping("/register")
	public void saveUser(
		@Valid @RequestBody UsersE  userC, 
		Locale locale, 
		HttpServletResponse response
	) throws MessagingException, 
		StreamWriteException, 
		DatabindException, 
		IOException 
	{
		usersEService.saveUser(userC, response);
		emailService.sendSimpleMessage(userC,imageLogo, locale, QR_CODE_IMAGE_PATH);
	}
	

	@PostMapping("/resendCode")
	public void reSendCode(
		@Valid @RequestBody UsersE  userC, 
		Locale locale, 
		HttpServletResponse response
	) throws MessagingException, 
	StreamWriteException, 
	DatabindException, 
	IOException 
	{
		emailService.reSendCodeUser(userC, locale, response, imageLogo, QR_CODE_IMAGE_PATH);
	}

	@PostMapping("/valideCompte")
	public void valideUser(@Valid @RequestBody UserValide  userV)  {
		UsersE getuser = usersEService.findByEmail(userV.getEmail());
		
		log.info("User : {}",  getuser.getName());
		
		emailService.valideCompte(userV);
	}
	
	@PostMapping("/savesListe")
	@PostAuthorize("hasAuthority('admin') or hasAuthority('secretaire')")
	public List<UsersE> saveUserListe(@Valid @RequestBody List<UsersE> userC) {
		log.info("Inside save User of User");
		return usersEService.saveUserListe(userC);
	}
	
	@GetMapping("/Liste")
	@PostAuthorize("hasAuthority('user')")
	public List<UsersE> fetchUser(){
		log.info("Inside fetch User of User");
		return usersEService.fetchUserE();
	}
	
	@GetMapping("/Searche/{nom}")
	@PostAuthorize("hasAuthority('user')")
	public List<UsersE> fetchUserByName(@PathVariable("nom") String nom) throws UsersNotFoundException
	{
		log.info("Inside fetch User of User");
		return usersEService.fetchUserByName(nom);
	}
	
	@GetMapping("/User/{nom}")
	@PostAuthorize("hasAuthority('user')")
	public UsersE findUserByName(@PathVariable("nom") String nom) throws UsersNotFoundException{
		log.info("Inside find User of User");
		return usersEService.findByName(nom);
	}
	
	@GetMapping("/getOne/{id}")
	@PostAuthorize("hasAuthority('admin')")
	public UsersE fetchUserById(@PathVariable("id") Long id) throws UsersNotFoundException {
		log.info("Inside fetch User of User");
		return usersEService.fetchUserById(id);
	}
		
	@DeleteMapping("/deleteOne/{id}")
	@PostAuthorize("hasAuthority('admin') or hasAuthority('secretaire')")
	public String deleteUserById(@PathVariable("id") Long id){
		log.info("Inside delete User of User");
		return usersEService.deleteUserById(id);
	}
	
	@PutMapping("/update/{id}")
	@PostAuthorize("hasAuthority('admin') or hasAuthority('secretaire')")
	public UsersE updateUser(@PathVariable("id") Long id, @RequestBody UsersE userC) {
		log.info("Inside update Userof User");
		return usersEService.updateUser(id, userC);
	}
	
	@GetMapping(path = "/profile")
	@PostAuthorize("hasAuthority('user')")
	public UsersE prifile(Principal principal) throws UsersNotFoundException{
		return usersEService.findByName(principal.getName());
	}
	
	//--------------------------------------------------------------------
	
    @GetMapping(path = "/email")
	//@PostAuthorize("hasAuthority('user')")
    public Mono<String> email() throws MessagingException{
    	log.info("Inside save User of Email");
		//return Mono.just("Email Sended ");
        return emailService.SimpleMessage("mouanwiya30@gmail.com",
                "Spring boot", "Bonjour, nous testons spirng mailer");
    }
    
    @GetMapping(path = "/qrCode", produces= MediaType.IMAGE_PNG_VALUE)
    public byte[] getQRCode(Model model) throws MessagingException{
        String medium="https://rahul26021999.medium.com/";
        String github="https://github.com/rahul26021999";
        List<UsersE> list = usersEService.fetchUserE();
        String data = "";
        for (UsersE user : list) {
            data = data + user.getPrenom() + " " + user.getName() + " " + user.getEmail() + "\n";
        }
        byte[] image = new byte[0];
        try {

            // Generate and Return Qr Code in Byte Array
            image = QrCodeService.getQRCodeImage(data,250,250);

            // Generate and Save Qr Code Image in static/image folder
            QrCodeService.generateQRCodeImage(data,250,250,QR_CODE_IMAGE_PATH);
            emailService.sendEmailWithAttachment("mouanwiya30@gmail.com",
                    "Spring boot", "Bonjour, nous testons spirng mailer", QR_CODE_IMAGE_PATH);
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        // Convert Byte Array into Base64 Encode String
        String qrcode = Base64.getEncoder().encodeToString(image);

        model.addAttribute("medium",medium);
        model.addAttribute("github",github);
        model.addAttribute("qrcode",qrcode);

        return image;
    }
    
    @GetMapping(path = "/pdf")
    public void createPdf(HttpServletResponse response) throws IOException, DocumentException {
        //create data
    	List<UsersE> list = usersEService.fetchUserE();
    	Map<String, Object> map = new HashMap<>();

    	
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        map.put("usersList", list);

        this.pdfService.export(response, map);
        emailService.email(
        		"mouanwiya30@gmail.com", 
        		"Spring boot", 
        		"Bonjour, nous testons spirng mailer", 
        		pdfService,
        		map,
        		response
        );
    }
    
    @GetMapping(path = "/excel")
    public void createExcel(HttpServletResponse response) throws Exception {
        //create data
    	List<UsersE> list = usersEService.fetchUserE();
    	Map<String, Object> map = new HashMap<>();

    	
        response.setContentType("application/xls");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=xls" + currentDateTime + ".xls";
        response.setHeader(headerKey, headerValue);
        map.put("usersList", list);

        this.excelService.buildExcelDocument(response, map);
        emailService.emailExel(
        		"mouanwiya30@gmail.com", 
        		"Spring boot", 
        		"Bonjour, nous testons spirng mailer", 
        		response,
        		map
        		
        );
    }


}
