package org.apei.userserver.controller.audit;

import lombok.extern.slf4j.Slf4j;
import org.apei.bizcommon.constant.StatusCodeEnum;
import org.apei.userserver.vo.user.Pagination;
import org.apei.bizcommon.entity.Result;
import org.apei.userserver.service.audit.AccountAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description 账号审核控制层
 * @Author apeiMark
 * @Date 2024/7/13
 */
@Slf4j
@RestController
@RequestMapping("audit")
public class AccountAuditController {
    private final AccountAuditService accountAuditService;

    @Autowired
    public AccountAuditController(AccountAuditService accountAuditService) {
        this.accountAuditService = accountAuditService;
    }

    @PostMapping("/account")
    public Result AuditAccountPageList(@RequestBody Pagination param){
        try{
            log.info("param: "+param);
            Map<String,Object> data = accountAuditService.getAuditAccountPageList(param);
            return new Result(true, StatusCodeEnum.OK.getCode(),"待审核账号数据获取成功",data);
        }catch (Exception e){
            log.error("Error occurred while fetching account audit data", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    @GetMapping("/acceptAccount")
    public Result acceptAccount(@RequestParam String uid) {
        try {
            accountAuditService.acceptAccountAudit(uid);
            return new Result(true, StatusCodeEnum.OK.getCode(), "审核已通过");
        } catch (Exception e) {
            log.error("审核出错：", e);
            return new Result(false, StatusCodeEnum.OK.getCode(), e.getMessage());
        }
    }

    @GetMapping("/rejectAccount")
    public Result rejectAccount(@RequestParam String uid) {
        try {
            accountAuditService.rejectAccountAudit(uid);
            return new Result(true, StatusCodeEnum.OK.getCode(), "审核已驳回");
        } catch (Exception e) {
            log.error("审核出错：", e);
            return new Result(false, StatusCodeEnum.OK.getCode(), e.getMessage());
        }
    }

}
