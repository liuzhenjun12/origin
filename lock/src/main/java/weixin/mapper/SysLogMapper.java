package weixin.mapper;


import base.mybatis.BaseMapper;
import io.swagger.models.auth.In;
import weixin.model.SysLog;

import java.util.List;

public interface SysLogMapper extends BaseMapper<SysLog,Integer> {
    /**
     * 通过企业id、用户id、日期获取日志列表
     * @param corpid
     * @param userid
     * @param date
     * @return
     */
    public List<SysLog> getLogsByuserid(String corpid, String userid, String methodtype,String date);

    /**
     * 统计当天开锁次数
     * @param corpid
     * @param userid
     * @param methodtype
     * @param date
     * @return
     */
    public int getLogsByuseridCount(String corpid, String userid, String methodtype,String date);

    public List<SysLog> getPageLogsByuserid(String corpid, String userid, Integer page, Integer rows);
}
