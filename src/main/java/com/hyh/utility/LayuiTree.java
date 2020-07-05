package com.hyh.utility;

import java.util.List;

public class LayuiTree {
    private String title;//节点名称
    private String id;//节点唯一id
    private String field;//对应的实体类名称
    private boolean checked;//是否选中
    private boolean spread;//是否展开
    private String href;//点击的连接
    private boolean disabled;//是否为禁用状态
    private List<LayuiTree> children;//子节点

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isSpread() {
        return spread;
    }

    public void setSpread(boolean spread) {
        this.spread = spread;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public List<LayuiTree> getChildren() {
        return children;
    }

    public void setChildren(List<LayuiTree> children) {
        this.children = children;
    }
}
