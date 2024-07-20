package org.apei.bizcommon;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apei.bizcommon.util.EncryptUtil;
import org.apei.bizcommon.util.IdGeneratorSnowflakeUtil;
import org.apei.bizcommon.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class BizCommonApplicationTests {

    @Test
    void IdGeneratorSnowflakUtilTest() {
        try {
            // 解析JWT
            Claims claims = JwtUtil.parseJWT("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiMGU3YjE1Ni1iNTcxLTRiYWQtODA3Yi1iMTA3NGRkZDA4ZDQiLCJzdWIiOiIxODEzMTc4OTQ1NjIxODUyMTYwIiwiaXNzIjoiYWRtaW4iLCJpYXQiOjE3MjE0NTY2NzYsImV4cCI6MTcyMTQ2MDI3Nn0.A8KZIXOk3kHglkFqGb3Y4sUpwfyC1BkhMieOio3xJSQ");
            String uid = claims.getSubject();
            System.out.println("Parsed UID: " + uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
