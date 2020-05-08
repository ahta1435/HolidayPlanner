package com.example.holiday;

public class Places {
    String Name;
    String Images;
    String description;
    public Places(){

    }

    public Places(String name, String images, String description) {
        Name = name;
        Images = images;
        this.description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}