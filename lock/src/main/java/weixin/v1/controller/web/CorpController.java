package weixin.v1.controller.web;

import base.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weixin.mapper.SysCorpMapper;
import weixin.model.SysCorp;
import weixin.model.SysCorpExample;
import weixin.vo.page.Grid;
import weixin.vo.page.PageFilter;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/web/corp")
public class CorpController {
    @Autowired
    SysCorpMapper corpMapper;
    /**
     * 分页查询企业信息
     * @param name
     * @param ph
     * @return
     */
    @RequestMapping("/list")
    public Grid getList(String name, PageFilter ph) {
        log.info("info===>{}", name);
        Grid grid=new Grid();
        SysCorpExample example=new SysCorpExample();
        if(!StringUtils.isEmpty(name)){
            example.createCriteria().andCorpNameLike("%"+name+"%");
        }
        RowBounds rowBounds = new RowBounds(ph.getPage()-1,ph.getRows());
        List<SysCorp> list=corpMapper.selectByExampleAndRowBounds(example,rowBounds);
        grid.setRows(list);
        int count=corpMapper.selectCountByExample(example);
        grid.setTotal(count);
        return grid;
    }

    /**
     * 通过id获取企业信息
     * @param id
     * @return
     */
    @RequestMapping("/get")
    public CommonResult get(Integer id){
        try{
            SysCorp corp=corpMapper.selectByPrimaryKey(id);
            return CommonResult.success(corp);
        }catch (Exception e){
            return CommonResult.failed(e.getCause(),e.getMessage());
        }
    }
}
