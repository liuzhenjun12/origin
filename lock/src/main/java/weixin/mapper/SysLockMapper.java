package weixin.mapper;


import base.mybatis.BaseMapper;
import org.apache.ibatis.annotations.Param;
import weixin.model.SysLock;

public interface SysLockMapper extends BaseMapper<SysLock,Integer> {
    /**
     * 检查锁蓝牙地址是否存在
     * @param mac
     * @return
     */
    int checkMac(@Param("mac")String mac);
}