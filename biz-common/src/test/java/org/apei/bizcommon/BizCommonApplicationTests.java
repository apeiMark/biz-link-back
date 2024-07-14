package org.apei.bizcommon;

import lombok.extern.slf4j.Slf4j;
import org.apei.bizcommon.util.IdGeneratorSnowflakeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
class BizCommonApplicationTests {

    @Test
    void IdGeneratorSnowflakUtilTest() {
        long snowflakeId = new IdGeneratorSnowflakeUtil().snowflakeId();
        log.info( "雪花算法ID：{}", snowflakeId );
    }

}
