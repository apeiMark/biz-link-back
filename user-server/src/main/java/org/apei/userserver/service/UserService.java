package org.apei.userserver.service;

import org.apei.bizcommon.entity.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public boolean login(User user){
        if(user!= null){
            return true;
        }else {
            return false;
        }
    }
}
