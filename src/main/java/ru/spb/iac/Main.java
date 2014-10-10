package ru.spb.iac;

import com.mongodb.Mongo;
import org.springframework.data.mongodb.core.MongoFactoryBean;

public class Main{
    public static void main(String[] args) {
        org.springframework.data.mongodb.core.MongoFactoryBean mfb = new MongoFactoryBean();
        mfb.setHost("");
        mfb.setPort(123);
        try {
            mfb.getObject().getDatabaseNames();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}