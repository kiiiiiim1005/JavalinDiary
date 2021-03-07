package com.gmail.kiiiiiim1005.diary.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean isEmailPattern(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean emptyCheck(String... strings) {
        for (String string : strings) {
            if(string == null || string.isEmpty()) return true;
        }
        return false;
    }

    public static String sha256(String msg)  throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        return byteToHexString(md.digest());
    }

    public static String byteToHexString(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for(byte b : data) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private final static Pattern scriptTagPattern = Pattern.compile("<[\\s\\t]*\\/script[\\s\\t]*>");
    public static String removeScriptTags(String str) {
        Matcher matcher = scriptTagPattern.matcher(str);
        final StringBuilder sb = new StringBuilder(str);
        while (matcher.find()) {
            int start = matcher.start();
            sb.deleteCharAt(start);
            sb.insert(start, "&lt;");
        }
        return sb.toString();
    }

}
