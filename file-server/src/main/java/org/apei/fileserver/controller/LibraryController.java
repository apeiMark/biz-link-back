package org.apei.fileserver.controller;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apei.bizcommon.constant.StatusCodeEnum;
import org.apei.bizcommon.util.JwtUtil;
import org.apei.fileserver.service.LibraryService;
import org.apei.fileserver.vo.response.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apei.bizcommon.entity.Result;

import java.util.Map;

/**
 * @Description 文档库管理
 * @Author apeiMark
 * @Date 2024/7/21
 */
@Slf4j
@RestController
@RequestMapping(value = "/library")
public class LibraryController {

    private LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }


    /**
     * @description: 获取文档库列表
     * @return org.apei.bizcommon.entity.Result
     * @author apeiMark
     * @date 2024/7/21
     */

    @PostMapping("/list")
    public Result getFileLibraryList(@RequestHeader("Authorization") String authorizationHeader){
        try{
            log.info("Authorization: "+authorizationHeader);
            // 移除 "Bearer " 前缀
            if (authorizationHeader.startsWith("Bearer ")) {
                log.info("token以Bearer 开头");
                authorizationHeader = authorizationHeader.substring(7);
            }
            Claims claims = JwtUtil.parseJWT(authorizationHeader);
            log.info("token: " + authorizationHeader);
            String uid = claims.getSubject();
            log.info("Parsed UID: " + uid);
            Map<String, Object> data = libraryService.getFileLibraryListByCreaterID(uid);
            return new Result(true, StatusCodeEnum.OK.getCode(),"获取文档库列表成功",data);
        }catch (Exception e){
            log.error("获取文档库列表异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    /**
     * @description: 创建文档库
     * @param libraryResponse
     * @return javax.xml.transform.Result
     * @author apeiMark
     * @date 2024/7/21
     */
    @PostMapping("/create")
    public Result createFileLibrary(@RequestBody LibraryResponse libraryResponse, @RequestHeader("Authorization") String authorizationHeader){
        try{
            log.info("createLibraryResponse: "+libraryResponse);
            log.info("Authorization: "+authorizationHeader);
            // 移除 "Bearer " 前缀
            if (authorizationHeader.startsWith("Bearer ")) {
                log.info("token以Bearer 开头");
                authorizationHeader = authorizationHeader.substring(7);
            }
            Claims claims = JwtUtil.parseJWT(authorizationHeader);
            log.info("token: " + authorizationHeader);
            String uid = claims.getSubject();
            log.info("Parsed UID: " + uid);
            libraryResponse.setCreateUid(uid);
            log.info("createLibraryResponse: " + libraryResponse);
            libraryService.createFileLibrary(libraryResponse);
            return new Result(true, StatusCodeEnum.OK.getCode(),"创建文档库成功");
        }catch (Exception e){
            log.error("创建文档库异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    /**
    * @description: 编辑文档库
    * @param libraryResponse
    * @return org.apei.bizcommon.entity.Result
    * @author apeiMark
    * @date 2024/7/21
    */

    @PostMapping("/edit")
    public Result editFileLibrary(@RequestBody LibraryResponse libraryResponse, @RequestHeader("Authorization") String authorizationHeader){
        try{
            libraryService.editFileLibrary(libraryResponse);
            return new Result(true, StatusCodeEnum.OK.getCode(),"编辑文档库成功");
        }catch (Exception e){
            log.error("编辑文档库异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    @GetMapping("/delete")
    public Result deleteFileLibrary(@RequestParam String id) {
        try {
            libraryService.deleteFileLibrary(id);
            return new Result(true, StatusCodeEnum.OK.getCode(), "文档库已删除");
        } catch (Exception e) {
            log.error("文档库删除异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(), e.getMessage());
        }
    }

    /**
     * @description: 获取文档库列表
     * @return org.apei.bizcommon.entity.Result
     * @author apeiMark
     * @date 2024/7/21
     */

    @GetMapping("/optionList")
    public Result getLibraryOptionList(@RequestHeader("Authorization") String authorizationHeader){
        try{
            log.info("Authorization: "+authorizationHeader);
            // 移除 "Bearer " 前缀
            if (authorizationHeader.startsWith("Bearer ")) {
                log.info("token以Bearer 开头");
                authorizationHeader = authorizationHeader.substring(7);
            }
            Claims claims = JwtUtil.parseJWT(authorizationHeader);
            log.info("token: " + authorizationHeader);
            String uid = claims.getSubject();
            log.info("Parsed UID: " + uid);
            Map<String, Object> data = libraryService.getLibraryOptionList(uid);
            return new Result(true, StatusCodeEnum.OK.getCode(),"获取文档库列表成功",data);
        }catch (Exception e){
            log.error("获取文档库列表异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

}
