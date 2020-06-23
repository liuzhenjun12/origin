package weixin.impl;

import base.api.CommonResult;
import base.exception.BusinessException;
import base.mybatis.BaseMapper;
import base.mybatis.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weixin.mapper.SysLockTypeMapper;
import weixin.mapper.SysLockXlMapper;
import weixin.model.SysLockType;
import weixin.model.SysLockTypeExample;
import weixin.model.SysLockXl;
import weixin.service.LockTypeService;
import java.util.List;
@Slf4j
@Service
public class LockTypeServiceImpl extends BaseServiceImpl<SysLockType, Integer> implements LockTypeService {
    @Autowired
    SysLockTypeMapper lockTypeMapper;
    @Autowired
    SysLockXlMapper lockXlMapper;

    @Override
    public BaseMapper<SysLockType, Integer> getMappser() {
        return lockTypeMapper;
    }

    /**
     * 查询所有设备类型
     * @return
     */
    @Override
    public CommonResult getList() {
        List<SysLockType> types=lockTypeMapper.selectAll();
        if(!types.isEmpty()){
            return CommonResult.success(types);
        }
        return CommonResult.failed("没有数据");
    }

    @Override
    public CommonResult add(SysLockType type) throws BusinessException {
        try {
            SysLockTypeExample example = new SysLockTypeExample();
            example.createCriteria().andSbtypeEqualTo(type.getSbtype());
            List<SysLockType> lockXls = lockTypeMapper.selectByExample(example);
            if (!lockXls.isEmpty()) {
                return CommonResult.failed("类型名称已存在!");
            }
            SysLockXl ls = lockXlMapper.selectByPrimaryKey(type.getSbxlid());
            if(ls==null){
                return CommonResult.failed("设备系列不存在!");
            }
            type.setSbxlname(ls.getName());
            lockTypeMapper.insert(type);
            return CommonResult.success(null,"添加成功");
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }

    @Override
    public CommonResult update(SysLockType type) throws BusinessException {
        return null;
    }

    @Override
    public CommonResult delete(Integer id) throws BusinessException {
        try {
            lockTypeMapper.deleteByPrimaryKey(id);
            return CommonResult.success("删除成功");
        }catch (Exception e){
            throw new BusinessException(e.getMessage(),e.getCause());
        }
    }
}
