package com.example.hospital.joydip.firebasetemplate;

public class DomainUserInfo {
    public String name,phoneNo,email,userType, availableTime, specialization, dept;
    @Override
    public String toString() {
        return "DomainUserInfo{" +
                "name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", userType='" + userType + '\'' +
                ", className='" + availableTime + '\'' +
                ", subject='" + specialization + '\'' +
                ", district='" + dept + '\'' +
                '}';
    }

}
