package financialmanager.guitest;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {


    String[] values = {"AB","BC","CD","AE"};
    boolean contains = Arrays.stream(values).anyMatch("CD"::equals);
        System.out.println(contains);
}}
