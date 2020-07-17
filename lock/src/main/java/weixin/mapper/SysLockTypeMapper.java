package weixin.mapper;

import java.util.List;

import base.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;
import weixin.model.SysLock;
import weixin.model.SysLockType;
import weixin.model.SysLockTypeExample;

public interface SysLockTypeMapper extends BaseMapper<SysLockType,Integer> {

}
