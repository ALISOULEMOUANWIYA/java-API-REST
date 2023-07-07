package com.chat.Service.service;

import com.chat.Service.entity.UserValide;
import com.chat.Service.entity.UsersE;

/**
 * @author ali
 *
 */
public interface  UserValideService {
    public void saveCode(String code, String timeExp, String DateExp, UsersE userV);

    public UserValide findByEmail(String email);
}
