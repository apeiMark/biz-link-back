package org.apei.userserver.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apei.userserver.entity.user.UserBase;

/**
 * @Description UserBase数据库映射
 * @Author apeiMark
 * @Date 2024/7/12
 */
@Mapper
public interface UserBaseMapper extends BaseMapper<UserBase> {

}
