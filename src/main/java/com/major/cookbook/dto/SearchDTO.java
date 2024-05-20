package com.major.cookbook.dto;

public class SearchDTO {
    
    private String search;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public SearchDTO(String search) {
        this.search = search;
    }
    
    public SearchDTO(){

    }

    @Override
    public String toString() {
        return search;
    }    
}
