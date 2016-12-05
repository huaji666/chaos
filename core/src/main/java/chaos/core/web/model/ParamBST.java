package chaos.core.web.model;

import chaos.core.utils.ConvertUtil;
import chaos.core.utils.json.JsonUtils;
import chaos.core.utils.object.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import chaos.core.web.api.annoatation.ApiField;
import org.springframework.util.StringUtils;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-26
 */
public class ParamBST {

    @ApiField("limit/limit分页//0")
    private int limit = 10;
    @ApiField("offset/limit分页://0")
    private int offset = 0;
    private String order;
    private String sort;

    private SearchBST search;

    /**
     * 普通分页模式
     */
    @ApiField("pageType/page分页://false")
    private boolean pageType;
    @ApiField("pageCount/page分页://10")
    private int pageCount = 10;
    @ApiField("page/page分页:当前页码//1")
    private int page = -1;

    public ParamBST() {
    }

//    /**
//     * 搜索封装
//     *
//     * @return
//     */
//    @ApiField("id/search参数:id//")
//    private long id;
//    @ApiField("field/search参数:field字段名称//")
//    private String field;
//    @ApiField("newValue/search参数:newValue字段的值//")
//    private String newValue;


    public int getPage() {
        if (page == 0) page = 1;
        return page;
    }

    /**
     * <pre>
     *     page 模式分页
     *     只需要传 当前页码
     * </pre>
     *
     * @return
     */
    public boolean isPageType() {
        if (getPage() > 1) pageType = true;
        return pageType;
    }

    public void setPageType(boolean pageType) {
        this.pageType = pageType;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public int getPageCount() {
        if (pageCount == 0) pageCount = 10;
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * <pre>
     *     排序
     * </pre>
     *
     * @return
     */
    public String getOrderWhere() {
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            return ConvertUtil.toTableStyle(sort) + " " + order;
        }
        return "";
    }

    /**
     * <pre>
     *     默认排序
     *     id desc = select * from XXX where id desc
     * </pre>
     *
     * @param defaultStr 默认排序
     * @return
     */
    public String getOrderWhere(String defaultStr) {
        if (ObjectUtils.isEmpty(getOrderWhere())) return defaultStr;
        return getOrderWhere();
    }


    public boolean isOrder() {
        return !StringUtils.isEmpty(getOrderWhere());
    }

    public int getLimit() {
        if (isPageType()) limit = getPageCount();
        return limit;
    }

    public void setLimit(int limit) {
        if (limit == 0) return;
        this.limit = limit;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    public SearchBST getSearch() {
        SearchBST searchBST = JsonUtils.toObj(search, SearchBST.class);
        return searchBST;
    }

    /**
     * 包含搜索内容
     *
     * @return
     */
    @JsonIgnore
    public boolean isSearch() {
        if (ObjectUtils.isEmpty(search)) return false;
//        SearchBST searchBST = JsonUtils.toObj(search, SearchBST.class);
//        if (ObjectUtils.isEmpty(searchBST)) return false;
        if (ObjectUtils.isEmpty(search.getField())) return false;
        if (ObjectUtils.isEmpty(search.getNewValue())) return false;
        return true;
    }

    /**
     * 拼接添加条件
     * <pre>
     *     whrer id = 123456
     *     whrer name LINK CONCAT('%参数%')
     * </pre>
     *
     * @return
     */
    @JsonIgnore
    public String getSearchWhere() {
        return getSearchWhere(null);
    }

    /**
     * 在搜索条件后 增加 自定义条件
     * <pre>
     *     whrer id = 123456 jion
     *     whrer name LINK CONCAT('%参数%') jion
     *
     *     注意：
     *     字段中如果包含 Str、All 会被去掉，这个字段需要和数据库字段吻合。
     * </pre>
     *
     * @param jion
     * @return
     */
    @JsonIgnore
    public String getSearchWhere(String jion) {
        if (ObjectUtils.isEmpty(getSearch())) return "";

        StringBuilder sb = new StringBuilder();
        sb.append(" where ");
        String newValue = getSearch().getNewValue();
        String field = getSearch().getField();
        if (field.contains("id")) {
            sb.append(field).append(" = ").append("'").append(newValue).append("'").append(" ");
        } else {
            sb.append(field).append(" LIKE ").append("CONCAT('%").append(newValue).append("%')").append(" ");
        }
        if (!ObjectUtils.isEmpty(jion)) sb.append(jion);
        return sb.toString();
    }

    @JsonDeserialize(as = SearchBST.class)
    public void setSearch(SearchBST search) {
        this.search = search;
    }

    public int getOffset() {
        if (isPageType()) offset = getPage() * getPageCount() - getLimit();
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * 请使用 getOrderWhere 排序
     *
     * @return
     */
    @Deprecated
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


//    public String getField() {
//        return field;
//    }
//
//    public void setField(String field) {
//        this.field = field;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getNewValue() {
//        return newValue;
//    }
//
//    public void setNewValue(String newValue) {
//        this.newValue = newValue;
//    }
}
