package org.apei.fileserver.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apei.fileserver.entity.Document;
import org.apei.fileserver.mapper.document.DocumentMapper;
import org.apei.fileserver.mapper.library.LibraryMapper;
import org.apei.fileserver.vo.file.DocumentVO;
import org.apei.fileserver.vo.file.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author apeiMark
 * @Date 2024/7/21
 */
@Slf4j
@Service
public class DocumentService {
    private DocumentMapper documentMapper;
    private LibraryMapper libraryMapper;

    @Autowired
    public DocumentService(DocumentMapper documentMapper, LibraryMapper libraryMapper) {
        this.documentMapper = documentMapper;
        this.libraryMapper = libraryMapper;
    }

    public void uploadDocument(String libraryId, MultipartFile file) {
        // 定义存储文件的路径
        String basePath = "D:\\企业智联开发版\\biz-link\\public\\";
        String filePath = basePath + file.getOriginalFilename();

        // 创建文件对象
        File dest = new File(filePath);

        try {
            // 保存文件到指定路径
            file.transferTo(dest);
            log.info("文件上传成功，路径: {}", filePath);

            // 获取文件信息
            String fileName = file.getOriginalFilename();
            long fileSize = file.getSize();
            String fileType = getFileType(fileName);

            // 去掉文件名的后缀
            String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));

            // 生成可供前端下载的URL
            String fileUrl = "/public/" + file.getOriginalFilename();

            // 将文件信息存储到数据库
            Document document = new Document();
            document.setName(fileNameWithoutExtension);
            document.setDocumentSize((int) Math.ceil(fileSize / (1024.0 * 1024.0))); // 转换为MB并四舍五入
            document.setDocumentType(fileType);
            document.setLibraryId(Long.parseLong(libraryId));
            document.setSrc(fileUrl);
            document.setCreateTime(new Timestamp(System.currentTimeMillis()));

            documentMapper.insert(document);
            log.info("文件信息存储成功，数据库ID: {}", document.getId());
        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败", e);
        }
    }

    private String getFileType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "PDF";
            case "txt":
                return "TXT";
            case "ppt":
                return "PPT";
            case "docx":
                return "DOCX";
            case "xlsx":
                return "XLSX";
            case "doc":
                return "DOC";
            case "pptx":
                return "PPTX";
            default:
                return "OTHERS";
        }
    }

    public Map<String, Object> getDocumentList(Pagination param) {
        // 创建分页对象
        Page<Document> page = new Page<>(param.getCurrent(), param.getPageSize());
        // 创建查询包装器
        QueryWrapper<Document> queryWrapper = new QueryWrapper<>();


        // 设置查询条件
        if (Objects.nonNull(param.getId()) && !param.getId().isEmpty()) {
            queryWrapper.eq("id", param.getId());
        }
        if (Objects.nonNull(param.getName()) && !param.getName().isEmpty()) {
            queryWrapper.eq("name", param.getName());
        }
        if (Objects.nonNull(param.getDocumentType()) && !param.getDocumentType().isEmpty()) {
            queryWrapper.eq("document_type", param.getDocumentType());
        }
        if (Objects.nonNull(param.getLibraryId()) && !param.getLibraryId().isEmpty()) {
            queryWrapper.eq("library_id", param.getLibraryId());
        }
        if (Objects.nonNull(param.getCreateTime()) && param.getCreateTime().length == 2) {
            queryWrapper.between("create_time", param.getCreateTime()[0], param.getCreateTime()[1]);
        }

        // 执行分页查询
        Page<Document> resultPage = documentMapper.selectPage(page, queryWrapper);

        // 记录总数和当前页记录数
        log.debug("Total records: " + resultPage.getTotal());
        log.debug("Current page records: " + resultPage.getRecords().size());

        // Convert Page<UserAuth> to Page<UserAuthVO>
        Page<DocumentVO> resultPageVO = new Page<>();
        resultPageVO.setCurrent(resultPage.getCurrent());
        resultPageVO.setSize(resultPage.getSize());
        resultPageVO.setTotal(resultPage.getTotal());
        resultPageVO.setRecords(convertDocumentListToVO(resultPage.getRecords()));

        // 设置总记录数
        param.setTotal((int) resultPage.getTotal());

        // 构建返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("list", resultPageVO.getRecords());
        data.put("total", param.getTotal());

        log.info("data(审核数据分页): " + data);

        return data;
    }


    // List<UserAuth> to List<UserAuthVO>
    private List<DocumentVO> convertDocumentListToVO(List<Document> userAuthList) {
        return userAuthList.stream()
                .map(this::convertDocumentToVO)
                .collect(Collectors.toList());
    }

    //  Document to DocumentVO
    private DocumentVO convertDocumentToVO(Document document) {
        DocumentVO documentVO = new DocumentVO();
        documentVO.setId(String.valueOf(document.getId()));
        documentVO.setDocumentType(document.getDocumentType());
        documentVO.setDocumentSize(document.getDocumentSize());
        documentVO.setName(document.getName());
        documentVO.setSrc(document.getSrc());
        documentVO.setLibraryId(String.valueOf(document.getLibraryId()));
        documentVO.setCreateTime(String.valueOf(document.getCreateTime()));
        return documentVO;
    }

    public void deleteDocument(String id) {
        log.info("删除文档，ID: {}", id);
        boolean result = documentMapper.deleteById(Long.valueOf(id)) > 0;
        if (result) {
            log.info("文档删除成功，ID: {}", id);
        } else {
            log.error("文档删除失败，ID: {}", id);
        }
    }
}
