package weixin.mapper;


import base.mybatis.BaseMapper;
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
}
