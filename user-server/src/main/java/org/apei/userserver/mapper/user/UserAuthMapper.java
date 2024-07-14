package org.apei.userserver.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apei.userserver.entity.user.UserAuth;

/**
 * @Description UserAuth数据库映射
 * @Author apeiMark
 * @Date 2024/7/12
 */
@Mapper
public interface UserAuthMapper extends BaseMapper<UserAuth> {

}
