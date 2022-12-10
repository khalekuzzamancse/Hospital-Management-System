package com.example.hospital.joydip.firebasetemplate;

public class DomainFeed {
   public String from, to, status;
   public String msg;

    @Override
    public String toString() {
        return "DomainFeed{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
