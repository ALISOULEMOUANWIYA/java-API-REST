package com.chat.Service.service;

/**
 * @author ali
 *
 */
import java.util.Objects;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.Service.entity.UserValide;
import com.chat.Service.entity.UsersE;
import com.chat.Service.repository.UserValideRepository;


@Service
@Transactional
public class UserValideImplement implements UserValideService {
    private UserValideRepository UserRV ;
    private final Logger log = LoggerFactory.getLogger(UserValideImplement.class);
    
    @Autowired
    public UserValideImplement(UserValideRepository UserRV){
        this.UserRV = UserRV;
    }

    @Override
    public void saveCode(String code, String timeExp, String DateExp, UsersE userC) {
        if (UserRV.findByEmail(userC.getEmail()) != null) {
            UserValide user = UserRV.findByEmail(userC.getEmail());

            if (Objects.nonNull(code) && !"".equalsIgnoreCase(code)) {
                user.setCode(code);
            }

            if (Objects.nonNull(timeExp) && !"".equalsIgnoreCase(timeExp)) {
                user.setTimeExp(timeExp);
            }

            if (Objects.nonNull(DateExp) && !"".equalsIgnoreCase(DateExp)) {
                user.setDateExp(DateExp);
            }

            if (Objects.nonNull(userC.getEmail()) && !"".equalsIgnoreCase(userC.getEmail())) {
                user.setEmail(userC.getEmail());
            }

            log.info("Update code and time Expire for this Email "+userC.getEmail());
            UserRV.save(user);
        } else {
            Long id = null;
            this.add(id, code,  timeExp,DateExp,  userC);
        }
    }

    private void add(Long id, String code, String timeExp, String DateExp, UsersE userC){
            log.info("Add Email validation "+userC.getEmail());
            UserValide user = new UserValide(id, code, timeExp, DateExp, userC.getEmail());
            UserRV.save(user);
    }

    @Override
    public UserValide findByEmail(String email) {
        return UserRV.findByEmail(email);
    }
    
}
