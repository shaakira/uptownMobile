package com.example.uptown.DTO.Response;

public class AuthResponse {
    private Integer id;
    private String userName;
    private String email;
    private String uType;
    private String status;

    public AuthResponse(Integer id,String userName, String email, String uType, String status) {
        this.id=id;
        this.userName = userName;
        this.email = email;
        this.uType = uType;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuType() {
        return uType;
    }

    public void setuType(String uType) {
        this.uType = uType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
