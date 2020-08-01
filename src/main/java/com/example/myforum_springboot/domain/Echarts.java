package com.example.myforum_springboot.domain;

public class Echarts {
    private String name;
    private long num;

    public Echarts(String name,long num){
        super();
        this.name = name;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "Echarts{" +
                "name='" + name + '\'' +
                ", num=" + num +
                '}';
    }
}
