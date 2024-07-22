package org.apei.fileserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.apei.bizcommon.constant.StatusCodeEnum;
import org.apei.bizcommon.entity.Result;
import org.apei.fileserver.service.DocumentService;
import org.apei.fileserver.vo.file.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Description
 * @Author apeiMark
 * @Date 2024/7/21
 */
@Slf4j
@RestController
@RequestMapping(value = "/document")
public class DocumentController {
    private DocumentService documentService;

    @Autowired

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/tmp")
    public Result uploadDocumentToTmp(@RequestPart("file") MultipartFile file){
        try{
            return new Result(true, StatusCodeEnum.OK.getCode(),"文件缓存成功");
        }catch (Exception e){
            log.error("文件缓存异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    @PostMapping("/upload")
    public Result uploadDocument(@RequestParam("libraryId") String libraryId,@RequestPart("file") MultipartFile file){
        try{
            log.info("libraryId: ",libraryId);
            documentService.uploadDocument(libraryId,file);
            return new Result(true, StatusCodeEnum.OK.getCode(),"文件缓存成功");
        }catch (Exception e){
            log.error("文件缓存异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }

    @PostMapping("/list")
    public Result getDocumentList(@RequestBody Pagination param){
        try{
            log.info("param: ",param);
            Map<String,Object> data = documentService.getDocumentList(param);
            return new Result(true, StatusCodeEnum.OK.getCode(),"获取文件列表成功",data);
        }catch (Exception e){
            log.error("文件列表获取异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(),e.getMessage());
        }
    }


    @GetMapping("/delete")
    public Result deleteDocument(@RequestParam String id) {
        try {
            documentService.deleteDocument(id);
            return new Result(true, StatusCodeEnum.OK.getCode(), "文档已删除");
        } catch (Exception e) {
            log.error("文档删除异常", e);
            return new Result(false, StatusCodeEnum.OK.getCode(), e.getMessage());
        }
    }

}
