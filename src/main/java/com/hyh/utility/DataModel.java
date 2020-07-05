package com.hyh.utility;

public class DataModel<T> {
	
	private T data;
	/**
	 * 兼容datatable分页
	 */
	private Integer recordsTotal;    
    private Integer recordsFiltered; 
    private Integer draw;
    /**
     * 错误提示
     */
    private String prompt;
    /**
     * 堆栈
     */
    private StackTraceElement[] errorTrace;
    private boolean success;
    
    public DataModel() { }
    
    public DataModel(StackTraceElement[] errorTrace, String prompt, Integer recordsTotal, Integer draw, boolean success, T data) {
        this.errorTrace = errorTrace;
        this.prompt = prompt;
        this.success = success;
        this.data = data;
        this.setDraw(draw);
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    /**
     * 无分页构造函数
     *
     * @param errorTrace
     * @param prompt
     * @param success
     * @param data
     */
    public DataModel(StackTraceElement[] errorTrace, String prompt, boolean success, T data) {
        this.errorTrace = errorTrace;
        this.prompt = prompt;
        this.success = success;
        this.data = data;
    }

    /**
     * 成功且有结果集的DataModal,不支持分页
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> DataModel<T> getSucDataModal(T data) {
        DataModel<T> d = new DataModel<T>(null, null, true, data);
        return d;
    }

    /**
     * 成功且有结果集的DataModal,支持分页
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> DataModel<T> getSucDataModal(T data, Integer recordsTotal, Integer draw) {
        DataModel<T> d = new DataModel<T>(null, null, recordsTotal, draw, true, data);
        return d;
    }

    /**
     * 成功但没有结果集的DataModal
     *
     * @param <T>
     * @return
     */
    public static <T> DataModel<T> getNotDataSucDataModal() {
        DataModel<T> d = new DataModel<T>(null, null, true, null);
        return d;
    }

    /**
     * 失败的DataModal
     *
     * @param prompt
     * @param errorTrace
     * @param <T>
     * @return
     */
    public static <T> DataModel<T> getFailDataModal(String prompt, StackTraceElement[] errorTrace) {
        DataModel<T> d = new DataModel<T>(errorTrace, prompt, false, null);
        return d;
    }

    /**
     * 失败的DataModal
     *
     * @param prompt
     * @param <T>
     * @return
     */
    public static <T> DataModel<T> getFailDataModalNotException(String prompt) {
        DataModel<T> d = new DataModel<T>(null, prompt, false, null);
        return d;
    }
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsFiltered = recordsTotal;
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public StackTraceElement[] getErrorTrace() {
		return errorTrace;
	}

	public void setErrorTrace(StackTraceElement[] errorTrace) {
		this.errorTrace = errorTrace;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	

}
