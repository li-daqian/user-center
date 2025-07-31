package com.lidaqian.user_center.dto;

public enum AppStatus {
    ACTIVE("激活"),
    INACTIVE("未激活"),
    DISABLED("禁用"),
    ;

    private final String description;

    AppStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}