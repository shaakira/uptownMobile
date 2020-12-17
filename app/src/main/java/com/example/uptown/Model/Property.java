package com.example.uptown.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Property implements Parcelable {

    private int id;
    private String heading;
    private String street;
    private String city;
    private String province;
    private String pType;
    private String description;
    private double rate;
    private String rateType;
    private float area;
    private int rooms;
    private int baths;
    private int garage;
    private String features;
    private String status;
    private String image1;
    private String image2;
    private String image3;
    private User user;

    public Property() {
    }
    public Property(String heading, String street, String city, String province, String pType) {
        this.heading = heading;
        this.street = street;
        this.city = city;
        this.province = province;
        this.pType = pType;
    }


    protected Property(Parcel in) {
        id = in.readInt();
        heading = in.readString();
        street = in.readString();
        city = in.readString();
        province = in.readString();
        pType = in.readString();
        description = in.readString();
        rate = in.readDouble();
        rateType = in.readString();
        area = in.readFloat();
        rooms = in.readInt();
        baths = in.readInt();
        garage = in.readInt();
        features = in.readString();
        status = in.readString();
        image1 = in.readString();
        image2 = in.readString();
        image3 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(heading);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(pType);
        dest.writeString(description);
        dest.writeDouble(rate);
        dest.writeString(rateType);
        dest.writeFloat(area);
        dest.writeInt(rooms);
        dest.writeInt(baths);
        dest.writeInt(garage);
        dest.writeString(features);
        dest.writeString(status);
        dest.writeString(image1);
        dest.writeString(image2);
        dest.writeString(image3);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getBaths() {
        return baths;
    }

    public void setBaths(int baths) {
        this.baths = baths;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        image1 = image1;
    }

    public int getGarage() {
        return garage;
    }

    public void setGarage(int garage) {
        this.garage = garage;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        image3 = image3;
    }
}
