package com.example.walkwithme;

public class WalkItem {
    /* 아이템의 정보를 담기 위한 클래스 */

    int img_pic;
    String name;
    String type;


    public int getImg_pic(){
        return img_pic;
    }
    public void setImg_pic(int img_pic){
        this.img_pic=img_pic;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
}
