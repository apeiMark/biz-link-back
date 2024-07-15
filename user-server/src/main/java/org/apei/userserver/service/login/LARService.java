package org.apei.userserver.service.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apei.bizcommon.util.IdGeneratorSnowflakeUtil;
import org.apei.userserver.entity.user.UserAuth;
import org.apei.userserver.entity.user.UserBase;
import org.apei.userserver.mapper.user.UserAuthMapper;
import org.apei.userserver.mapper.user.UserBaseMapper;
import org.apei.userserver.vo.login.LoginForm;
import org.apei.userserver.vo.login.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LARService {
    private UserAuthMapper userAuthMapper;
    private UserBaseMapper userBaseMapper;

    @Autowired
    public LARService(UserAuthMapper userAuthMapper,UserBaseMapper userBaseMapper) {
        this.userAuthMapper = userAuthMapper;
        this.userBaseMapper = userBaseMapper;
    }

    /**
    * @description: 根据用户名登录
    * @param loginForm
    * @return java.lang.Long
    * @author apeiMark
    * @date 2024/7/16
    */

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

    /**
     * @description: 用户名注册
     * @param registerForm
     * @return java.lang.Long
     * @author apeiMark
     * @date 2024/7/16
     */
    public Long registerByUserName(RegisterForm registerForm) {
        // 创建查询包装器
        QueryWrapper<UserAuth> queryWrapper = new QueryWrapper<>();
        // 设置查询条件
        queryWrapper.eq("identifier", registerForm.getIdentifier());

        // 查询数据库中是否存在该用户
        List<UserAuth> userAuthList = userAuthMapper.selectList(queryWrapper);

        Long uid;
        if (userAuthList != null && !userAuthList.isEmpty()) {
            // 检查是否存在与当前注册方式相同的记录
            for (UserAuth userAuth : userAuthList) {
                if (userAuth.getIdentityType().equals(registerForm.getIdentityType())) {
                    throw new RuntimeException("该账号已存在，请直接登录");
                }
            }
            // 如果存在相同 identifier 但不同 identityType 的记录，使用现有的 uid
            uid = userAuthList.get(0).getUid();
        } else {
            // 如果不存在相同 identifier 的记录，生成新的 uid
            uid = new IdGeneratorSnowflakeUtil().snowflakeId();
        }

        // 创建新的 UserAuth 实体
        UserAuth newUserAuth = new UserAuth();
        newUserAuth.setUid(uid);
        newUserAuth.setIdentityType(registerForm.getIdentityType());
        newUserAuth.setIdentifier(registerForm.getIdentifier());
        newUserAuth.setCertificate(registerForm.getCertificate());
        // 插入新的 UserAuth 记录
        userAuthMapper.insert(newUserAuth);

        // 查询 user_base 表中是否存在该 uid
        UserBase userBase = userBaseMapper.selectById(uid);
        if (userBase == null) {
            // 创建新的 UserBase 实体
            UserBase newUserBase = new UserBase();
            newUserBase.setUid(uid);
            // 插入新的 UserBase 记录
            userBaseMapper.insert(newUserBase);
        }

        return uid;
    }


    public Long registerByEmail(RegisterForm registerForm) {
        throw new RuntimeException("暂未开放的注册方式");
    }

    public Long registerByUserMobileNumber(RegisterForm registerForm) {
        throw new RuntimeException("暂未开放的注册方式");
    }

    public Long registerByUserQQ(RegisterForm registerForm) {
        throw new RuntimeException("暂未开放的注册方式");
    }

    public Long registerBeWechart(RegisterForm registerForm) {
        throw new RuntimeException("暂未开放的注册方式");
    }

    public Long registerByTencentWeibo(RegisterForm registerForm) {
        throw new RuntimeException("暂未开放的注册方式");
    }

    public Long registerBySinaWeibo(RegisterForm registerForm) {
        throw new RuntimeException("暂未开放的注册方式");
    }
}
