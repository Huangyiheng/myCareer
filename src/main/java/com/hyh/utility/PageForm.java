package com.hyh.utility;


import java.util.Date;

/**
 * datatable兼容用分页
 * @author wxy
 *
 */
public class PageForm {
	
	/**
	 * datatable 分页
	 * 
	 * 
	 * 
	 * 
	 */
	private Integer start;//当前页数起始行
    private Integer length;//每页长度
    private Integer pageNum;//当前页
    private String orderStr;
    private String orderDir;
    private Integer draw;//ajax异步验证,接收到什么值就传回什么值
    private String error;//异常提示,服务器异常后,填入提示
    private Date createstart; //方便查询创建时间的的起止时间
    private Date createend;//便查询创建时间的的截止时间

	public Date getCreatestart() {
		return createstart;
	}

	public void setCreatestart(Date createstart) {
		this.createstart = createstart;
	}

	public Date getCreateend() {
		return createend;
	}

	public void setCreateend(Date createend) {
		this.createend = createend;
	}

	/**
     * @return pageNum
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * @param pageNum 要设置的 pageNum
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * @return error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error 要设置的 error
     */
    public void setError(String error) {
        this.error = error;
    }
    //用于前端排序
    /**
     * mybatis 分页
     */
   /* private RowBounds rowBounds;*/
    /**
     * 通用的关键字检索项
     */
    private String generalKeyword;
    /**
     * 通用的标签检索项
     */
    private Byte generalTag;
	/**
	 * 通用检索开始时间
	 */
	private Long generalDateStart;
	/**
	 * 通用检索结束时间
	 */
	private Long generalDateEnd;
	/**
	 * 通用检索id
	 */
	private Long generalId;
	
	


	public Long getGeneralId() {
		return generalId;
	}

	public void setGeneralId(Long generalId) {
		this.generalId = generalId;
	}

	public Long getGeneralDateStart() {
		return generalDateStart;
	}

	public void setGeneralDateStart(Long generalDateStart) {
		this.generalDateStart = generalDateStart;
	}

	public Long getGeneralDateEnd() {
		return generalDateEnd;
	}

	public void setGeneralDateEnd(Long generalDateEnd) {
		this.generalDateEnd = generalDateEnd;
	}
	
    public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getOrderStr() {
		return orderStr;
	}
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
	public String getOrderDir() {
		return orderDir;
	}
	public void setOrderDir(String orderDir) {
		this.orderDir = orderDir;
	}
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	/*public RowBounds getRowBounds() {
		return (rowBounds == null && start != null && length != null) ?
				new RowBounds(start, length) : rowBounds;
	}
	public void setRowBounds(RowBounds rowBounds) {
		this.rowBounds = rowBounds;
	} */
	public String getOrderBy() {
	    return orderStr+" "+orderDir;
		//return StringUtils.isNotBlank(orderStr)&&StringUtils.isNotBlank(orderDir)? orderStr+" "+orderDir:null;
	}
	public String getGeneralKeyword() {
		return generalKeyword;
	}
	public void setGeneralKeyword(String generalKeyword) {
		this.generalKeyword = generalKeyword;
	}
	public Byte getGeneralTag() {
		return generalTag;
	}
	public void setGeneralTag(Byte generalTag) {
		this.generalTag = generalTag;
	}
}
