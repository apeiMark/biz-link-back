package org.apei.feignserver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "user-server")
public interface UserClient {

    @GetMapping("/user/info")
    Object getStudent ();
}
