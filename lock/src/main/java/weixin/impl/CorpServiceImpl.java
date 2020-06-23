package weixin.impl;

import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.mybatis.BaseMapper;
import base.mybatis.BaseServiceImpl;
import base.util.RedisOpsUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.mapper.SysCorpMapper;
import weixin.mapper.SysDepartMapper;
import weixin.model.SysCorp;
import weixin.model.SysDepart;
import weixin.service.CorpService;
import weixin.vo.department.Depart;
import weixin.vo.department.Department;
import weixin.vo.permanentCode.DealerCorpInfo;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class CorpServiceImpl   extends BaseServiceImpl<SysCorp, Integer> implements CorpService {
    @Autowired
    SysCorpMapper corpMapper;
    @Autowired
    SysDepartMapper departMapper;
    @Autowired
    RedisOpsUtil redisOpsUtil;
    @Override
    public BaseMapper<SysCorp, Integer> getMappser() {
        return corpMapper;
    }

    /**
     * 添加企业
     * @param corpid
     * @return
     */
    @Override
    public CommonResult addCorp(String corpid) {
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.PERMANENT_CODE+corpid)){
            return CommonResult.failed(null,"访问token不存在");
        }
        DealerCorpInfo dealerCorpInfo=redisOpsUtil.get(RedisKeyPrefixConst.PERMANENT_CODE+corpid,DealerCorpInfo.class);
        SysCorp corp=new SysCorp();
        corp.setCorpid(dealerCorpInfo.getAuth_corp_info().getCorpid());
        corp.setCorpName(dealerCorpInfo.getAuth_corp_info().getCorp_name());
        corp.setCorpFullName(dealerCorpInfo.getAuth_corp_info().getCorp_full_name());
        corp.setAgentid(dealerCorpInfo.getAuth_info().getAgent().get(0).getAgentid());
        corp.setCorpIndustry(dealerCorpInfo.getAuth_corp_info().getCorp_industry());
        corp.setCorpScale(dealerCorpInfo.getAuth_corp_info().getCorp_scale());
        corp.setCorpSquareLogoUrl(dealerCorpInfo.getAuth_corp_info().getCorp_square_logo_url());
        corp.setCorpWxqrcode(dealerCorpInfo.getAuth_corp_info().getCorp_wxqrcode());
        corp.setCreateDate(new Date());
        corp.setCreateBy(dealerCorpInfo.getAuth_user_info().getUserid());
        corpMapper.insert(corp);
        redisOpsUtil.set(RedisKeyPrefixConst.CORP_INFO+corpid,corp);
        return CommonResult.success(null,"添加企业成功");
    }

    /**
     * 添加部门
     * @param corpid
     * @return
     */
    @Override
    public CommonResult addDrep(String corpid) {
        if(!redisOpsUtil.hasKey(RedisKeyPrefixConst.ACCESS_TOKEN+corpid)){
            return CommonResult.failed("访问token不存在");
        }
        String token=redisOpsUtil.get(RedisKeyPrefixConst.ACCESS_TOKEN+corpid);
        try {
            String  str = Request.Get("https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token="+token)
                    .execute()
                    .returnContent()
                    .asString(Charset.forName("UTF-8"));
            log.info("amdin==>{}",str);
            Gson gson = new Gson();
            Depart depart =gson.fromJson(str, Depart.class);
            if(depart!=null){
                if(!depart.getDepartment().isEmpty()){
                    List<Department> list=depart.getDepartment();
                    for(Department t:list){
                        log.info("t==>{}",t.toString());
                        SysDepart de=new SysDepart();
                        de.setName(t.getName());
                        de.setNameEn(t.getName_en());
                        de.setDeparid(t.getId());
                        de.setParentid(t.getParentid());
                        departMapper.insert(de);
                    }
                }
            }
        } catch (Exception e) {
            return CommonResult.failed("抛出Exception异常==>"+e.getMessage());
        }
        return CommonResult.success("添加部门成功");
    }
}
