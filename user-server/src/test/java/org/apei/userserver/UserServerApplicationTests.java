package org.apei.userserver;

import lombok.extern.slf4j.Slf4j;
import org.apei.userserver.entity.user.UserAuth;
import org.apei.userserver.service.login.LARService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class UserServerApplicationTests {

    @Autowired
    private LARService LARService;

    @Test
    void selectUserAuthByUid() {
        UserAuth userAuth = LARService.getUserAuth("1811645877530525696");
        log.info("userAuth: "+userAuth);
    }

}
