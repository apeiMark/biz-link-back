package org.apei.fileserver.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apei.feignserver.client.UserClient;
import org.apei.feignserver.entity.user.UserBaseVO;
import org.apei.feignserver.entity.user.UserInfoByIdRequest;
import org.apei.fileserver.entity.Library;
import org.apei.fileserver.mapper.library.LibraryMapper;
import org.apei.fileserver.vo.library.SelectOptionData;
import org.apei.fileserver.vo.response.LibraryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 文档库业务处理
 * @Author apeiMark
 * @Date 2024/7/21
 */
@Slf4j
@Service
public class LibraryService {
    private LibraryMapper libraryMapper;
    private UserClient userClient;

    @Autowired
    public LibraryService(LibraryMapper libraryMapper, UserClient userClient) {
        this.libraryMapper = libraryMapper;
        this.userClient = userClient;
    }

    public void createFileLibrary(LibraryResponse createLibraryResponse) {
        // 将 CreateLibraryResponse 转换为 Library 实体
        Library library = new Library();
        library.setTitle(createLibraryResponse.getTitle());
        library.setDescription(createLibraryResponse.getDescription());
        library.setCreaterUid(Long.valueOf(createLibraryResponse.getCreateUid()));
        // 插入库表
        libraryMapper.insert(library);
    }

    public void editFileLibrary(LibraryResponse editLibraryResponse) {
        // 根据 ID 查询现有的文档库
        Library library = libraryMapper.selectById(editLibraryResponse.getId());
        if (library == null) {
            log.error("文档库ID为 {} 的记录不存在", editLibraryResponse.getId());
            throw new RuntimeException("文档库记录不存在");
        }

        // 更新需要修改的字段
        library.setTitle(editLibraryResponse.getTitle());
        library.setDescription(editLibraryResponse.getDescription());

        // 执行更新操作
        libraryMapper.updateById(library);
    }

    public Map<String, Object> getFileLibraryListByCreaterID(String uid) {
        // 构建 QueryWrapper 条件
        QueryWrapper<Library> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creater_uid", Long.valueOf(uid));

        // 获取文档库列表
        List<Library> libraries = libraryMapper.selectList(queryWrapper);

        // 构建返回数据
        Map<String, Object> data = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        // 数据格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Library library : libraries) {
            Map<String, Object> libraryMap = new HashMap<>();
            libraryMap.put("id", library.getId().toString());
            libraryMap.put("title", library.getTitle());
            libraryMap.put("createTime", dateFormat.format(library.getCreateTime()));
            libraryMap.put("description", library.getDescription());

            // 构建 data 部分
            List<Map<String, String>> dataList = new ArrayList<>();
            dataList.add(createDataEntry("文档数", String.valueOf(library.getTotal())));
            dataList.add(createDataEntry("参与人数", String.valueOf(library.getParticipants())));
            UserInfoByIdRequest userInfoByIdRequest = new UserInfoByIdRequest();
            userInfoByIdRequest.setUid(uid);
            UserBaseVO userInfo = userClient.getUserInfo(userInfoByIdRequest);
            dataList.add(createDataEntry("拥有者", userInfo.getNickName())); // 或者是实际的拥有者名称

            libraryMap.put("data", dataList);
            list.add(libraryMap);
        }

        data.put("list", list);
        data.put("total", libraries.size());

        return data;
    }

    private Map<String, String> createDataEntry(String label, String value) {
        Map<String, String> entry = new HashMap<>();
        entry.put("label", label);
        entry.put("value", value);
        return entry;
    }

    public void deleteFileLibrary(String id) {
        log.info("删除文档库，ID: {}", id);
        boolean result = libraryMapper.deleteById(Long.valueOf(id)) > 0;
        if (result) {
            log.info("文档库删除成功，ID: {}", id);
        } else {
            log.error("文档库删除失败，ID: {}", id);
        }
    }

    public Map<String, Object> getLibraryOptionList(String uid) {
        // 构建 QueryWrapper 条件
        QueryWrapper<Library> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("creater_uid", Long.valueOf(uid));

        // 获取文档库列表
        List<Library> libraries = libraryMapper.selectList(queryWrapper);

        // 构建返回数据
        Map<String, Object> data = new HashMap<>();
        List<SelectOptionData> options = new ArrayList<>();

        // 构建下拉选项列表
        for (Library library : libraries) {
            SelectOptionData option = new SelectOptionData();
            option.setLabel(library.getTitle());
            option.setValue(library.getId().toString());
            options.add(option);
        }

        data.put("list", options);
        data.put("total", libraries.size());

        return data;
    }
}
