package com.example.cricketapps;

public class BannerModel {
    String bannerName;
    String appName;
    String phoneNumber;
    String watsappUrl;
    String smallBanner;
    String bigBanner;
    String bannerId;
    String updatedDateTime;
    String timestamp;

    public BannerModel() {
        this.bannerName = "";
        this.appName = "";
        this.phoneNumber = "";
        this.watsappUrl = "";
        this.smallBanner = "";
        this.bigBanner = "";
        this.bannerId = "";
        this.timestamp = "";
        this.updatedDateTime = "";
    }

    public BannerModel(String bannerName, String appName, String phoneNumber, String watsappUrl, String smallBanner, String bigBanner, String bannerId, String timestamp, String updatedDateTime) {
        this.bannerName = bannerName;
        this.appName = appName;
        this.phoneNumber = phoneNumber;
        this.watsappUrl = watsappUrl;
        this.smallBanner = smallBanner;
        this.bigBanner = bigBanner;
        this.bannerId = bannerId;
        this.timestamp = timestamp;
        this.updatedDateTime = updatedDateTime;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWatsappUrl() {
        return watsappUrl;
    }

    public void setWatsappUrl(String watsappUrl) {
        this.watsappUrl = watsappUrl;
    }

    public String getSmallBanner() {
        return smallBanner;
    }

    public void setSmallBanner(String smallBanner) {
        this.smallBanner = smallBanner;
    }

    public String getBigBanner() {
        return bigBanner;
    }

    public void setBigBanner(String bigBanner) {
        this.bigBanner = bigBanner;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    //    public Model(String bannerName, String appName, String phoneNumber, String watsappUrl, String smallBanner, String bigBanner, String bannerId) {
//        this.bannerName = bannerName;
//        this.appName = appName;
//        this.phoneNumber = phoneNumber;
//        this.watsappUrl = watsappUrl;
//        this.smallBanner = smallBanner;
//        this.bigBanner = bigBanner;
//        this.bannerId = bannerId;
//    }
}
