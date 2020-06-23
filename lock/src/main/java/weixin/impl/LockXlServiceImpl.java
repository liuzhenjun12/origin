package weixin.impl;

import base.api.CommonResult;
import base.exception.BusinessException;
import base.mybatis.BaseMapper;
import base.mybatis.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import weixin.mapper.SysLockTypeMapper;
import weixin.mapper.SysLockXlMapper;
import weixin.model.*;
import weixin.service.LockXlService;

import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class LockXlServiceImpl  extends BaseServiceImpl<SysLockXl, Integer> implements LockXlService {
    @Autowired
    SysLockXlMapper lockXlMapper;
    @Autowired
    SysLockTypeMapper typeMapper;
    @Override
    public BaseMapper<SysLockXl, Integer> getMappser() {
        return lockXlMapper;
    }

    /**
     * 查询所有设备系列
     * @return
     */
    @Override
    public CommonResult getList() {
        List<SysLockXl> lockXlList=lockXlMapper.selectAll();
        if(!lockXlList.isEmpty()){
            return CommonResult.success(lockXlList);
        }
        return CommonResult.failed("没有数据");
    }

    /**
     * 添加设备系列
     * @param lockXl
     * @return
     * @throws BusinessException
     */
    @Override
    public CommonResult add(SysLockXl lockXl) throws BusinessException {
        if(lockXl==null){
            return CommonResult.failed();
        }
        if(StringUtils.isEmpty(lockXl.getName())||StringUtils.isEmpty(lockXl.getMiaosu())){
            return CommonResult.failed();
        }
        try {
            SysLockXlExample example = new SysLockXlExample();
            example.createCriteria().andNameEqualTo(lockXl.getName());
            List<SysLockXl> lockXls = lockXlMapper.selectByExample(example);
            if (!lockXls.isEmpty()) {
                return CommonResult.failed("系列名称已存在!");
            }
            lockXlMapper.insert(lockXl);
            return CommonResult.success(null,"添加成功");
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }

    /**
     * 修改设备
     * @param lockXl
     * @return
     * @throws BusinessException
     */
    @Override
    public CommonResult update(SysLockXl lockXl) throws BusinessException {
        if(lockXl==null){
            return CommonResult.failed();
        }
        try {
            SysLockXlExample example = new SysLockXlExample();
            example.createCriteria().andNameEqualTo(lockXl.getName()).andIdNotEqualTo(lockXl.getId());
            List<SysLockXl> lockXls = lockXlMapper.selectByExample(example);
            if (!lockXls.isEmpty()) {
                return CommonResult.failed("系列名称已存在!");
            }
            lockXl.setUpdatedate(new Date());
            lockXlMapper.updateByPrimaryKey(lockXl);

            SysLockTypeExample examp = new SysLockTypeExample();
            examp.createCriteria().andSbxlidEqualTo(lockXl.getId());
            List<SysLockType> types = typeMapper.selectByExample(examp);
            if(!types.isEmpty()){
                for(SysLockType t:types){
                    t.setSbxlname(lockXl.getName());
                    typeMapper.updateByPrimaryKey(t);
                }
            }
            return CommonResult.success(null,"修改成功");
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public CommonResult delete(Integer id) throws BusinessException {
        try {
        lockXlMapper.deleteByPrimaryKey(id);
        return CommonResult.success("删除成功");
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }
}
