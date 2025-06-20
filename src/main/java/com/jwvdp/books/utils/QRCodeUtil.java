package com.jwvdp.books.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class QRCodeUtil {

    public static String decodeByPython(String filePath) {
        Process proc;
        try {
            String exe = "C:/dev_env/python/3.12.3/python.exe";
            String py = "C:/dev_project/trust-code/lib/python/decode.py";
            proc = Runtime.getRuntime().exec(new String[]{exe, py, filePath});
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line = null;
            while ((line = stdInput.readLine()) != null) {
//                System.out.println("____________________:"+line);
                if(null!=line&&!"".equals(line))  return line;
            }

            while ((line = stdError.readLine()) != null) {
//                System.out.println("____________________:"+line);
                return null;
            }

            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

        return  null;
    }

    public static void main(String[] args) {
        decodeByPython("C:\\work_space\\xinma\\2704480002.jpg");
    }
}
