package chaos.core.web.model;

import chaos.core.model.CaseRes;
import com.github.pagehelper.PageInfo;
import org.springframework.util.ObjectUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-04-10
 */
public class CaseResBST extends CaseRes {
    private List rows = new ArrayList<>();
    private long total = 0;

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public static CaseResBST getInstance() {
        return new CaseResBST();
    }


    public CaseResBST setPageInfo(PageInfo pageInfo) {
        if (ObjectUtils.isEmpty(pageInfo)) return this;
        this.rows = pageInfo.getList();
        this.total = pageInfo.getTotal();
        return this;
    }

//    @Override
//    public <T extends CaseRes> T setData(Object data) {
//        if (data instanceof PageInfo) {
//
//        }
//        this.rows = pageInfo.getList();
//        this.total = pageInfo.getTotal();
//        return super.setData(data);
//    }

    //
//    map.put("total", userList.getTotal());
//    map.put("rows", userList.getList());
}
