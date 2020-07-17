package weixin.v1.controller.web;

import base.aop.Log;
import base.api.CommonResult;
import base.constant.RedisKeyPrefixConst;
import base.exception.BusinessException;
import base.session.SessionInfo;
import base.util.File_Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import weixin.mapper.SysLockMapper;
import weixin.mapper.SysLockTypeMapper;
import weixin.mapper.SysLockXlMapper;
import weixin.model.*;
import weixin.service.LockTypeService;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/web/type")
public class TypeController {
    @Autowired
    LockTypeService typeService;
    @Autowired
    SysLockTypeMapper lockTypeMapper;
    @Autowired
    SysLockXlMapper lockXlMapper;
    @Autowired
    SysLockMapper sysLockMapper;

    @Value("${file.uploadFolder}")
    private String uploadFolder;
    private String staticAccessPath = "/upload/";

    /**
     * 查询所有设备类型
     * @return
     */
    @RequestMapping("/list")
    public CommonResult getList(){
        return typeService.getList();
    }

    /**
     * 添加设备类型
     * @return
     */
    @Log(desc = "添加设备类型",type = Log.LOG_TYPE.ADD)
    @RequestMapping("/add")
    public CommonResult add(HttpServletRequest request, @RequestParam("imgpath") MultipartFile imgpath, SysLockType lockType) throws BusinessException {
       log.info("lock:{}",lockType.toString());
        if(lockType==null){
            return CommonResult.failed("参数错误");
        }
        if(StringUtils.isEmpty(lockType.getSbtype())||StringUtils.isEmpty(lockType.getSbjc())||StringUtils.isEmpty(lockType.getSbxlid())){
            return CommonResult.failed("参数错误");
        }
        if(imgpath==null){
            return CommonResult.failed("图片样例不能为空");
        }
        String fileStr = File_Util.inputUploadFile(imgpath, uploadFolder,lockType.getSbtype());
        if(fileStr.equals("NOT_IMAGE")){
            return CommonResult.failed("图片上传失败");
        }else {
            SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(RedisKeyPrefixConst.SESSION_INFO);
//            String serverUrl=request.getRequestURL().toString().replace(request.getRequestURI(),"");
//            String url=serverUrl+staticAccessPath+ fileStr;
            String url="https://safe.jinchu.com.cn"+staticAccessPath+ fileStr;
            lockType.setCreatedate(new Date());
            lockType.setImg(url);
            lockType.setCreateby(sessionInfo.getLoginId());
            return typeService.add(lockType);
        }
    }

    /**
     * 修改设备类型
     * @return
     */
    @Log(desc = "修改设备类型",type = Log.LOG_TYPE.UPDATE)
    @RequestMapping("/update")
    public CommonResult update(HttpServletRequest request,@RequestParam(value = "file",required = false) MultipartFile file,Integer typeid,String sbtype2, String sbjc2, Integer sbxl2) throws BusinessException {
        if(StringUtils.isEmpty(sbtype2)||StringUtils.isEmpty(sbjc2)||StringUtils.isEmpty(sbxl2)||StringUtils.isEmpty(typeid)){
            return CommonResult.failed("参数错误");
        }
        SysLockType sys=lockTypeMapper.selectByPrimaryKey(typeid);
        if(sys==null){
            return CommonResult.failed("设备类型不存在");
        }
        if(!sbtype2.equals(sys.getSbtype())){
            return CommonResult.failed("不能修改设备类型");
        }
        SysLockXl ls = lockXlMapper.selectByPrimaryKey(sbxl2);
        if(ls==null){
            return CommonResult.failed("设备系列不存在!");
        }
        SysLockTypeExample example = new SysLockTypeExample();
        example.createCriteria().andSbtypeEqualTo(sbtype2).andIdNotEqualTo(typeid);
        List<SysLockType> types = lockTypeMapper.selectByExample(example);
        if (!types.isEmpty()){
            return CommonResult.failed("设备类型已存在");
        }
        String imgurl="";
        if(file!=null){
            String fileStr = File_Util.inputUploadFile(file, uploadFolder,sbtype2);
            if(!fileStr.equals("NOT_IMAGE")){
//                String serverUrl=request.getRequestURL().toString().replace(request.getRequestURI(),"");
//                imgurl=serverUrl+staticAccessPath+ fileStr;
                imgurl="https://safe.jinchu.com.cn"+staticAccessPath+ fileStr;
            }
        }
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(RedisKeyPrefixConst.SESSION_INFO);
        sys.setId(typeid);
        sys.setSbtype(sbtype2);
        sys.setSbjc(sbjc2);
        sys.setSbxlid(sbxl2);
        sys.setUpdateby(sessionInfo.getLoginId());
        sys.setUpdatedate(new Date());
        sys.setSbxlname(ls.getName());
        if(imgurl!=""){
            sys.setImg(imgurl);
            SysLockExample example1=new SysLockExample();
            example1.createCriteria().andModelIdEqualTo(sbtype2);
            List<SysLock> lock=sysLockMapper.selectByExample(example1);
            if(!lock.isEmpty()){
                for(SysLock s:lock){
                    s.setIcon(imgurl);
                    sysLockMapper.updateByPrimaryKey(s);
                }
            }
        }
        lockTypeMapper.updateByPrimaryKey(sys);
        return CommonResult.success(null,"修改成功");
    }

    /**
     * 删除设备类型
     * @param id
     * @return
     */
    @Log(desc = "删除设备类型",type = Log.LOG_TYPE.DEL)
    @RequestMapping("/delete")
    public CommonResult delete(Integer id) throws BusinessException {
        SysLockType type=lockTypeMapper.selectByPrimaryKey(id);
        if(type==null){
            return CommonResult.failed("数据不存在");
        }
        SysLockExample lockExample=new SysLockExample();
        lockExample.createCriteria().andModelIdEqualTo(type.getSbtype());
        List<SysLock> locks=sysLockMapper.selectByExample(lockExample);
        if(!locks.isEmpty()){
            return CommonResult.failed("设备类型已经关联设备");
        }
        return typeService.delete(id);
    }
}
