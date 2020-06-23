package base.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 查询返回json格式依照ui默认属性名称
 */
@Data
public class ReType implements Serializable{
  /**状态*/
  public int code=0;
  /**状态信息*/
  public String msg="";
  /**数据总数*/
  public long count;
  /**页码*/
  public long pageNum;

  public List<?> data;

  public ReType() {
  }

  public ReType(long count, List<?> data) {
    this.count = count;
    this.data = data;
  }

  public ReType(long count,long pageNum, List<?> data) {
    this.count = count;
    this.pageNum=pageNum;
    this.data = data;
  }

  /**
   * 动态添加属性 map 用法可以参考 activiti 模块中 com.len.JsonTest 测试类中用法
   * @param count
   * @param data
   * @param map
   * @param node 绑定节点字符串 这样可以更加灵活
   * @return
   */
  public static String jsonStrng(long count,List<?> data,Map<String, Map<String,Object>> map,String node){
    JSONArray jsonArray=JSONArray.parseArray(JSON.toJSONString(data));
    JSONObject object=new JSONObject();
    for(int i=0;i<jsonArray.size();i++){
      JSONObject jsonData = (JSONObject) jsonArray.get(i);
      jsonData.putAll(map.get(jsonData.get(node)));
    }
    object.put("count",count);
    object.put("data",jsonArray);
    object.put("code",0);
    object.put("msg","");
    return object.toJSONString();
  }

public int getCode() {
	return code;
}

public void setCode(int code) {
	this.code = code;
}

public String getMsg() {
	return msg;
}

public void setMsg(String msg) {
	this.msg = msg;
}

public long getCount() {
	return count;
}

public void setCount(long count) {
	this.count = count;
}

public long getPageNum() {
	return pageNum;
}

public void setPageNum(long pageNum) {
	this.pageNum = pageNum;
}

public List<?> getData() {
	return data;
}

public void setData(List<?> data) {
	this.data = data;
}
  
  
}
