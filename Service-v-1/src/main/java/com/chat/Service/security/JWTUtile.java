package com.chat.Service.security;
/**
 * @author ali
 *
 */

public class JWTUtile {
	public static final String SECRET="(PouZii<->LoUrdre~SeCreT~NganTsou)->&´123´+`@#A`";
	public static final String AUTH_HEADER="Authorization";
	public static final long EXPIRE_ACCESS_TOKEN= 2*60*1000;
	public static final long EXPIRE_REFRESH_TOKEN = 15*60*1000;
	public static final String REFRESH_TOKEN = "/api/v1/refresh/Token";
	public static final String LOGIN = "/login";	
	public static final String REGISTER_USER = "/api/v1/user/register";
	public static final String RESEND_EMAIL_USER = "/api/v1/user/resendCode";
	public static final String VALIDE_USER = "/api/v1/user/valideCompte";
 	public static final long VALIDATION_DURATION_MINUTES = 10;
	public static final String BEARER = "Bearer ";
	public static final String FORMAT_DATE_TIME = "dd-MM-yyyy HH:mm:ss";
	public static final String FORMAT_DATE = "dd-MM-yyyy";
	public static final String FORMAT_TIME = "HH:mm:ss";
	public static final String EXPIRED_CODE = "Expired";
	public static final String EMAIL_NOT_FOUND = "not_found";
	public static final String VALIDE_CODE = "Valid";
	public static final String ROLES_USER = "user";
	public static final String USER_SERTIFIED = "sertified";
	public static final String CODE_INVALIDE = "invalide";
	public static final String SPECIAL_CHARACTERS_O = "~";
	public static final String SPECIAL_CHARACTERS_I = "^-^";
}
