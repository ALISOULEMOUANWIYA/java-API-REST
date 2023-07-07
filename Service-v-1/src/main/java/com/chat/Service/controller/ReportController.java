package com.chat.Service.controller;
/**
 * @author ali
 *
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import com.chat.Service.service.UsersEService;
import com.chat.Service.utils.EmailService;
import com.chat.Service.utils.ExcelService;
import com.chat.Service.utils.PdfService;
//import com.chat.Service.utils.QrCodeService;

import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Controller
@RequestMapping("api/v1/raport")
public class ReportController {

	private final Logger log = 
			LoggerFactory.getLogger(ReportController.class);
	
	 
	
    //private UsersEService userS;
    //private QrCodeService qrCodeService;
    private EmailService emailService;

    @Autowired
    public ReportController(EmailService emailService){
        this.emailService = emailService;
    }

    @GetMapping(value = "/excel")
    public Mono<ExcelService> createExcel(HttpServletRequest request, HttpServletResponse response) {
        //create data"/templates/email/"
       // List<UsersE> list = userS.fetchUserE();

        return Mono.just( new ExcelService());
    }
    
/* 
    @GetMapping(value = "/qrCode")
    @PostAuthorize("hasAuthority('user')")
    public byte[] qrCode()  {
        //create data
    	//BufferedImage img = null;
    	byte[] img;
    	int size = 400;
    	String imageFormat = "png";
        List<UsersE> list = userS.fetchUserE();
        String data = "";
        for (UsersE user : list) {
            data = data + user.getPrenom() + " " + user.getName() + " " + user.getEmail() + "\n";
        }
        try {
           // qrCodeService.generateQrCode(data); 
            img = qrCodeService.getQRCodeImage(data, size, size);
        } catch (Exception ex) {
            ex.printStackTrace(); 
        }

         emailService.sendEmailWithAttachment("mouanwiya30@gmail.com",
                "Spring boot", "Bonjour, nous testons spirng mailer", qrCodeService.generateQrCode(data));
        
        return img;
    }
    
*/
    
    
    @GetMapping(value = "/email")
    @PostAuthorize("hasAuthority('user')")
    public Mono<String> email() {
    	log.info("Inside save User of Email");
        return emailService.SimpleMessage("mouanwiya30@gmail.com",
                "Spring boot", "Bonjour, nous testons spirng mailer");
    }
    
    
    @GetMapping(value = "/pdf")
    public Mono<PdfService> createPdf(HttpServletRequest request, HttpServletResponse response) {
        request.getParameter("type");

        //create data
        //List<UsersE> list = userS.fetchUserE();

        return Mono.just(new PdfService());
    }
    
}
