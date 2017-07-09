package com.example.programmer.tbeacloudbusiness.component.dropdownMenu;

public class KeyValueBean {
    private String id;
    private String name;

    public String getKey() {
        return id;
    }

    public void setKey(String key) {
        this.id = key;
    }

    public String getValue() {
        return name;
    }

    public void setValue(String value) {
        this.name = value;
    }

    public KeyValueBean(String key, String value) {
        super();
        this.id = key;
        this.name = value;
    }

    public KeyValueBean() {
        super();
    }


}
