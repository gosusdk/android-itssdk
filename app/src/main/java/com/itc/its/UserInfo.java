package com.itc.its;

public class UserInfo {

    private String user_id;
    private String user_name;
    private String user_email;
    private String user_phone;
    private  String user_fullname;
    private  String user_birthday;
    private String user_gender;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_fullname() {
        return user_fullname;
    }

    public void setUser_fullname(String user_fullname) {
        this.user_fullname = user_fullname;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(String user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    //    public UserInfo(JSONObject json) {
//        try {
//            if (json.has("user_id") && !json.isNull("user_id"))
//                this.userId = json.getString("user_id");
//            if (json.has("user_name") && !json.isNull("user_name"))
//                this.userName = json.getString("user_name");
//            if (json.has("user_fullname") && !json.isNull("user_fullname"))
//                this.userFullName = json.getString("user_fullname");
//            if (json.has("user_email") && !json.isNull("user_email"))
//                this.email = json.getString("user_email");
//        }catch (Exception ignored)
//        {};
//    }

}
