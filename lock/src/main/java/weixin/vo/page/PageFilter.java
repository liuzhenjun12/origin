package weixin.vo.page;

import lombok.Data;

@Data
public class PageFilter implements java.io.Serializable {
    private static final long serialVersionUID = -3541144590435319390L;
    private int page;// 当前页
	private int rows;// 每页显示记录数
	private String sort;// 排序字段
	private String order;// asc/desc

}
