package com.jwvdp.books.utils;

import java.awt.image.BufferedImage;
import java.util.*;

public class CodeUtil {

    private static final char[] CHAR_POOL = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final char[] LOW_CHAR_POOL = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final String DIGITS = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static final int BASE = DIGITS.length();  // 62
    private static final char[] NUMBER_POOL = "0123456789".toCharArray();

    public static ArrayList<String> generateUniqueStringsByChar(String prefix, int count, int length) {
        if (prefix == null)prefix="";
        if (count < 0 || length < prefix.length()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Set<String> uniqueStringSet = new HashSet<>();
        Random random = new Random();
        while (uniqueStringSet.size() < count) {
            StringBuilder sb = new StringBuilder(length);
            sb.append(prefix);
            // Fill the rest of the string with random characters from the pool
            for (int i = prefix.length(); i < length; i++) {
                sb.append(CHAR_POOL[random.nextInt(CHAR_POOL.length)]);
            }

            uniqueStringSet.add(sb.toString()); // HashSet takes care of uniqueness
        }

        // Convert the HashSet to an ArrayList
        return new ArrayList<>(uniqueStringSet);
    }


    public static ArrayList<String> generateUniqueStringsByLowChar(String prefix, int count, int length) {
        if (prefix == null)prefix="";
        if (count < 0 || length < prefix.length()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Set<String> uniqueStringSet = new HashSet<>();
        Random random = new Random();
        while (uniqueStringSet.size() < count) {
            StringBuilder sb = new StringBuilder(length);
            sb.append(prefix);
            // Fill the rest of the string with random characters from the pool
            for (int i = prefix.length(); i < length; i++) {
                sb.append(LOW_CHAR_POOL[random.nextInt(LOW_CHAR_POOL.length)]);
            }

            uniqueStringSet.add(sb.toString()); // HashSet takes care of uniqueness
        }

        // Convert the HashSet to an ArrayList
        return new ArrayList<>(uniqueStringSet);
    }

    public static List<List<String>> splitList(ArrayList<String> inputList, int a, int b) {
        // 检查输入列表不为空且a和b的总和不超过列表大小
        if (inputList == null || inputList.size() < a + b || a < 0 || b < 0) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        // 创建两个子列表
        List<String> listA = new ArrayList<>(inputList.subList(0, a));
        List<String> listB = new ArrayList<>(inputList.subList(a, a + b));
        // 创建一个包含两个子列表的列表
        List<List<String>> result = new ArrayList<>();
        result.add(listA);
        result.add(listB);
        return result;
    }

    public static ArrayList<String> generateUniqueStringsByNumber(String prefix, int count, int length) {
        if (prefix == null)prefix="";
        if (count < 0 || length < prefix.length()) {
            throw new IllegalArgumentException("Invalid arguments.");
        }

        Set<String> uniqueStringSet = new HashSet<>();
        Random random = new Random();
        while (uniqueStringSet.size() < count) {
            StringBuilder sb = new StringBuilder(length);
            sb.append(prefix);
            // Fill the rest of the string with random characters from the pool
            for (int i = prefix.length(); i < length; i++) {
                sb.append(NUMBER_POOL[random.nextInt(NUMBER_POOL.length)]);
            }

            uniqueStringSet.add(sb.toString()); // HashSet takes care of uniqueness
        }

        // Convert the HashSet to an ArrayList
        return new ArrayList<>(uniqueStringSet);
    }

//    public static String generateBatchCode() {
//        Random random = new Random();
//        StringBuilder batchCode = new StringBuilder(3);
//
//        for (int i = batchCode.length(); i < 3; i++) {
//             batchCode.append(CHAR_POOL[random.nextInt(CHAR_POOL.length)]) ;
//        }
//        return batchCode.toString();
//
//    }

    public static String generateBatchCode(String input) {
        if(null==input)input="0";
        char[] chars = input.toCharArray();
        int n = chars.length;
        // Start with carry as we want to add 1
        int carry = 1;
        // Iterate over the string from end to start
        for (int i = n - 1; i >= 0 && carry > 0; i--) {
            int index = DIGITS.indexOf(chars[i]);
            if (index == -1) {
                throw new IllegalArgumentException("Invalid character found: " + chars[i]);
            }

            int value = index + carry;
            // Determine if there's a carry for the next digit
            carry = value / BASE;
            // Update the current digit
            chars[i] = DIGITS.charAt(value % BASE);
        }

        // If there's still a carry, we need to add a new digit at the start
        if (carry > 0) {
            return 'A' + new String(chars);
        }

        String nextCode = new String(chars);
        for (int i = 0; i < 3; i++) {
            if(nextCode.length()<3)nextCode=nextCode+"0";
        }

        return nextCode;
    }

    public  String readBarcode(BufferedImage bufferedImage) throws Exception {
        String s = BarcodeReader.readBarcodeWithPreprocessing(bufferedImage);
        return  s;
    }
}
