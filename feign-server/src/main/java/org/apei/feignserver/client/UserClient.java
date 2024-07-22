package org.apei.feignserver.client;

import org.apei.feignserver.entity.user.UserBaseVO;
import org.apei.feignserver.entity.user.UserInfoByIdRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(value = "user-server")
public interface UserClient {

    @PostMapping("/infoById")
    UserBaseVO getUserInfo(@RequestBody UserInfoByIdRequest userInfoByIdRequest);
}
