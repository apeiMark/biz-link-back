package org.apei.userserver.service.audit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apei.userserver.vo.user.Pagination;
import org.apei.userserver.entity.user.UserAuth;
import org.apei.userserver.mapper.user.UserAuthMapper;
import org.apei.userserver.vo.user.UserAuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author apeiMark
 * @Date 2024/7/13
 */
@Slf4j
@Service
public class AccountAuditService {
    private UserAuthMapper userAuthMapper;

    @Autowired
    public AccountAuditService(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

    public Map<String, Object> getAuditAccountPageList(Pagination param) {

        // 创建分页对象
        Page<UserAuth> page = new Page<>(param.getCurrent(), param.getPageSize());

        // 创建查询包装器
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();

        // 设置查询条件
        if (Objects.nonNull(param.getUid()) && !param.getUid().isEmpty()) {
            queryWrapper.eq("uid", param.getUid());
        }
        if (Objects.nonNull(param.getIdentityType()) && !param.getIdentityType().isEmpty()) {
            queryWrapper.eq("identity_type", param.getIdentityType());
        }
        if (Objects.nonNull(param.getIdentifier()) && !param.getIdentifier().isEmpty()) {
            queryWrapper.eq("identifier", param.getIdentifier());
        }
        if (param.getStatus() != null && !"".equals(param.getStatus())) {
            queryWrapper.eq("status", param.getStatus());
        }
        if (Objects.nonNull(param.getCreateTime()) && param.getCreateTime().length == 2) {
            queryWrapper.between("create_time", param.getCreateTime()[0], param.getCreateTime()[1]);
        }

        // 查询未被删除的记录
        queryWrapper.ne("is_deleted", 1);

        // 执行分页查询
        Page<UserAuth> resultPage = userAuthMapper.selectPage(page, queryWrapper);

        // 记录总数和当前页记录数
        log.debug("Total records: " + resultPage.getTotal());
        log.debug("Current page records: " + resultPage.getRecords().size());

        // Convert Page<UserAuth> to Page<UserAuthVO>
        Page<UserAuthVO> resultPageVO = new Page<>();
        resultPageVO.setCurrent(resultPage.getCurrent());
        resultPageVO.setSize(resultPage.getSize());
        resultPageVO.setTotal(resultPage.getTotal());
        resultPageVO.setRecords(convertUserAuthListToVO(resultPage.getRecords()));

        // 设置总记录数
        param.setTotal((int) resultPage.getTotal());

        // 构建返回数据
        Map<String, Object> data = new HashMap<>();
        data.put("list", resultPageVO.getRecords());
        data.put("total", param.getTotal());

        log.info("data(审核数据分页): " + data);

        // 返回查询结果
        return data;
    }


    // List<UserAuth> to List<UserAuthVO>
    private List<UserAuthVO> convertUserAuthListToVO(List<UserAuth> userAuthList) {
        return userAuthList.stream()
                .map(this::convertUserAuthToVO)
                .collect(Collectors.toList());
    }

    //  UserAuth to UserAuthVO
    private UserAuthVO convertUserAuthToVO(UserAuth userAuth) {
        UserAuthVO userAuthVO = new UserAuthVO();
        userAuthVO.setId(userAuth.getId());
        userAuthVO.setUid(String.valueOf(userAuth.getUid()));
        userAuthVO.setIdentityType(userAuth.getIdentityType());
        userAuthVO.setIdentifier(userAuth.getIdentifier());
        userAuthVO.setStatus(userAuth.getStatus());
        userAuthVO.setCreateTime(userAuth.getCreateTime());
        userAuthVO.setUpdateTime(userAuth.getUpdateTime());
        return userAuthVO;
    }

    @Transactional
    public void acceptAccountAudit(String uid) {
        UpdateWrapper<UserAuth> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid); // 将String类型的uid转换为Long类型
        UserAuth userAuth = new UserAuth();
        userAuth.setStatus(1); // 设置要更新的字段及值
        log.info("userAuth: "+userAuth);
        userAuthMapper.update(userAuth, updateWrapper);
    }


    @Transactional
    public void rejectAccountAudit(String uid) {
        UpdateWrapper<UserAuth> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", uid); // 设置更新条件
        UserAuth userAuth = new UserAuth();
        userAuth.setStatus(2); // 设置要更新的字段及值
        userAuthMapper.update(userAuth, updateWrapper);
    }
}
