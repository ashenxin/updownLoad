package com.itstyle.es.area.entity;

public enum DatabaseType {
    /**
     *功能环境
     */
    function("function", "1"),
    /**
     * 回归环境
     */
    regression("regression", "2");

    DatabaseType(String name, String value){
        this.name = name;
        this.value = value;
    }

    private String name;
    private String value;

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }
}
