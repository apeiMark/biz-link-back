package org.apei.userserver.service.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apei.userserver.entity.user.UserAuth;
import org.apei.userserver.mapper.user.UserAuthMapper;
import org.apei.userserver.mapper.user.UserBaseMapper;
import org.apei.userserver.vo.login.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LARService {
    private UserAuthMapper userAuthMapper;

    @Autowired
    public LARService(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

    public UserAuth getUserAuth(String uid) {
        // 创建查询包装器
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq("uid", uid);
        // 使用 MyBatis Plus 提供的 selectList 方法查询

        return userAuthMapper.selectOne(queryWrapper);
    }


    public Long loginByUserName(LoginForm loginForm) {
        // 创建查询包装器
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq("identity_type", loginForm.getIdentityType())
                .eq("identifier", loginForm.getIdentifier());

        // 查询数据库中是否存在该用户
        UserAuth userAuth = userAuthMapper.selectOne(queryWrapper);

        if (userAuth == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检查用户状态
        if (userAuth.getStatus() == 0) {
            throw new RuntimeException("账号需经管理员审核，请耐心等待");
        } else if (userAuth.getStatus() == 2) {
            throw new RuntimeException("该用户账号已驳回请重新注册申请");
        }

        // 检查密码是否匹配
        if (!userAuth.getCertificate().equals(loginForm.getCertificate())) {
            throw new RuntimeException("密码错误");
        }

        // 返回用户ID
        return userAuth.getUid();
    }



    public Long loginByEmail(LoginForm loginForm) {
        throw new RuntimeException("暂未开放的登录方式");
    }

    public Long loginByUserMobileNumber(LoginForm loginForm) {
        throw new RuntimeException("暂未开放的登录方式");
    }

    public Long loginByUserQQ(LoginForm loginForm) {
        throw new RuntimeException("暂未开放的登录方式");
    }

    public Long loginBeWechart(LoginForm loginForm) {
        throw new RuntimeException("暂未开放的登录方式");
    }

    public Long loginByTencentWeibo(LoginForm loginForm) {
        throw new RuntimeException("暂未开放的登录方式");
    }

    public Long loginBySinaWeibo(LoginForm loginForm) {
        throw new RuntimeException("暂未开放的登录方式");
    }
}
