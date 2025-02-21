package com.oven.encryjar;

import java.util.ArrayList;
import java.util.List;

public class EncryTest {

    public static void main(String[] args) {
        String targetJar = "/Users/oven/moonlight/V_Rain_02/hk/target/hk.jar";
        String password = "Cyeg4HM6gmg0nMEpkz7CwA==";
        List<String> packageList = new ArrayList<>();
        List<String> classpath = new ArrayList<>();
        packageList.add("com.rio.hk");
        classpath.add("/Users/oven/moonlight/V_Rain_02/hk/target/lib/");
        JarEncryptor encryptor = new JarEncryptor(targetJar, password.trim().toCharArray());
        encryptor.setCode(null);
        encryptor.setPackages(packageList);
        encryptor.setIncludeJars(new ArrayList<>());
        encryptor.setExcludeClass(new ArrayList<>());
        encryptor.setClassPath(classpath);
        encryptor.setCfgfiles(new ArrayList<>());
        String result = encryptor.doEncryptJar();
        System.out.println(result);
    }

}
