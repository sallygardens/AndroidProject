package com.dujiaosongmu.music.db;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String age;
    private String area;
    private String fav_music;
    private String fav_instrument;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFav_music() {
        return fav_music;
    }

    public void setFav_music(String fav_music) {
        this.fav_music = fav_music;
    }

    public String getFav_instrument() {
        return fav_instrument;
    }

    public void setFav_instrument(String fav_instrument) {
        this.fav_instrument = fav_instrument;
    }
    public void save(User user){

    }
}

