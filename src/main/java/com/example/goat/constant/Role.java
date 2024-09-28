package com.example.goat.constant;

public enum Role {

    USER , ADMIN;

    //String 값을 Enum으로 변환하는 메소드
    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("역할이 유효하지 않습니다 : " + value);
    }
}
