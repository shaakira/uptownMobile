package com.example.uptown.Model;

public class ScreenItem {
    String description;
    int Image;
    String heading;

    public ScreenItem(String description, int image,String heading) {
        this.description = description;
        Image = image;
        this.heading=heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
