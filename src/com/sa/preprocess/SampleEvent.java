package com.sa.preprocess;

public class SampleEvent 
{
    private int id;
    private int count;
    private String name;
    
    public SampleEvent(int id, int count, String name) {
        this.id = id;
        this.count = count;
        this.name = name;
    }
    
    public int getId(){ return id; }
    public int getCount(){ return count; }
    public String getName(){ return name; }
}