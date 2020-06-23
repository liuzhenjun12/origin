package base.util.wx;

import org.apache.ibatis.session.RowBounds;

public class RowBoundUtil {
    /**
     * 根据分页属性 生成 RowBounds
     * @param page
     * @param rows
     * @return
     */
    public static RowBounds getRowBounds(Integer page, Integer rows){
        int offset = page * rows;
        RowBounds rowBounds = new RowBounds(offset,rows);
        return rowBounds;
    }
}
