package com.example.uptown.DTO.Response;

import com.example.uptown.Model.User;

import java.util.List;

public class AdminDashboardDTO {
    private List<User> topAgents;
    private int propCount;
    private int agentCount;
    private int customerCount;
    private int ownerCount;

    public AdminDashboardDTO() {
    }

    public AdminDashboardDTO(List<User> topAgents, int propCount, int agentCount, int customerCount, int ownerCount) {
        this.topAgents = topAgents;
        this.propCount = propCount;
        this.agentCount = agentCount;
        this.customerCount = customerCount;
        this.ownerCount = ownerCount;
    }

    public List<User> getTopAgents() {
        return topAgents;
    }

    public void setTopAgents(List<User> topAgents) {
        this.topAgents = topAgents;
    }

    public int getPropCount() {
        return propCount;
    }

    public void setPropCount(int propCount) {
        this.propCount = propCount;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(int agentCount) {
        this.agentCount = agentCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getOwnerCount() {
        return ownerCount;
    }

    public void setOwnerCount(int ownerCount) {
        this.ownerCount = ownerCount;
    }
}
