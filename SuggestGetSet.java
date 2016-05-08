//This is getter Setter Method for the Auto Complete

package com.stock_search.arjun.stockmarketviewer;



public class SuggestGetSet {

    String id,name,ex;
    public SuggestGetSet(String id, String name, String ex){
        this.setId(id);
        this.setName(name);
        this.setEx(ex);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

}