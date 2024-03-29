package com.onlinebank;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by p0wontnx on 1/25/16.
 */
public class Utils {


    /**
     * Ensure that all object fields annotated with {@link NotNull} are not null
     *
     * @param obj
     * @return
     */
    public static boolean isModelFieldNull(Object obj) {

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        return true;
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
        return false;
    }

    /**
     * Get name of fields from object model which are annotated with {@link NotNull} and having a null value
     *
     * @param obj
     * @return
     */
    public static List<String> getModelNullFields(Object obj) {
        List<String> nullFields = new ArrayList<String>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                try {
                    if (field.get(obj) == null) {
                        nullFields.add(field.getName());
                    }
                } catch (IllegalAccessException e) {
                }
            }
        }
        return nullFields;
    }

    /**
     * Generate a random string to perform tests and avoid unique fields constraints
     *
     * @param rng
     * @param characters
     * @param length
     * @return
     */
    public static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    /**
     * MD5 encrypter
     *
     * @return
     */
    public static String encryptPassword(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] encryptedPassword = md5.digest(password.getBytes("UTF-8"));
            return (new HexBinaryAdapter()).marshal(encryptedPassword);
        } catch (NoSuchAlgorithmException e) {
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

}
