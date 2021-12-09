package com.example.cricketapps;

public class AppModel {

    String appName;
    String appBundleName;
    String appId;
    String updatedDateTime;
    String timestamp;

    public AppModel() {
        this.appName = "";
        this.appBundleName = "";
        this.appId = "";
        this.updatedDateTime = "";
        this.timestamp = "";
    }

    public AppModel(String appName,String appBundleName, String appId, String updatedDateTime, String timestamp) {
        this.appName = appName;
        this.appBundleName = appBundleName;
        this.appId = appId;
        this.updatedDateTime = updatedDateTime;
        this.timestamp = timestamp;
    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppBundleName() {
        return appBundleName;
    }

    public void setAppBundleName(String appBundleName) {
        this.appBundleName = appBundleName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(String updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
