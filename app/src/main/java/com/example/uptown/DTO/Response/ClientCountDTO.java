package com.example.uptown.DTO.Response;

public class ClientCountDTO {
    private int activeUserCount;
    private int blacklistedUserCount;

    public ClientCountDTO() {
    }

    public ClientCountDTO(int activeUserCount, int blacklistedUserCount) {
        this.activeUserCount = activeUserCount;
        this.blacklistedUserCount = blacklistedUserCount;
    }

    public int getActiveUserCount() {
        return activeUserCount;
    }

    public void setActiveUserCount(int activeUserCount) {
        this.activeUserCount = activeUserCount;
    }

    public int getBlacklistedUserCount() {
        return blacklistedUserCount;
    }

    public void setBlacklistedUserCount(int blacklistedUserCount) {
        this.blacklistedUserCount = blacklistedUserCount;
    }
}
