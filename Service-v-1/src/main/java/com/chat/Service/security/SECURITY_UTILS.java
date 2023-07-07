package com.chat.Service.security;

public class SECURITY_UTILS {

    public static final String typeSenegale(String phS){
        String phone = "";
		for(int i = 0; i < phS.length(); i ++){
			if(i == 2){
				phone += "-"+String.valueOf(phS.charAt(i));
			}
			else if(i == 5){
				phone += "-"+String.valueOf(phS.charAt(i));
			}
			else if(i == 7){
				phone += "-"+String.valueOf(phS.charAt(i));
			}else{
				phone += String.valueOf(phS.charAt(i));
			}
		}
        return phone;
    }

    public static final String typeComorers(String phC){
        String phone = "";
		for(int i = 0; i < phC.length(); i ++){
			if(i == 3 ){
				phone += "-"+String.valueOf(phC.charAt(i));
			}
            else if(i == 5 ){
				phone += "-"+String.valueOf(phC.charAt(i));
			}else{
				phone += String.valueOf(phC.charAt(i));
			}
		}
        return phone;
    }

    public static final String CODE_IN_DATA(String code){
        String sembleurFordata = "";
		for(int i = 0; i < code.length(); i ++){
			if(i%2==0 && i>0){
				sembleurFordata += "^-^"+String.valueOf(code.charAt(i));
			}
			else{
				sembleurFordata += String.valueOf(code.charAt(i));
			}
		}
        return sembleurFordata;
    }

    public static final String CODE_FOR_USER(String code){
        String sembleur = "";
		for(int i = 0; i < code.length(); i ++){
			if(i%2==0 && i>0){
				sembleur += "~"+String.valueOf(code.charAt(i));
			}
			else{
				sembleur += String.valueOf(code.charAt(i));
			}
		}
        return sembleur;
    }
}
