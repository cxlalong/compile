package org.example.compile;

import java.io.*;
import java.util.Scanner;

import static org.example.compile.Globals.*;
public class LexicalAnalyzer {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String pgm = in.next();
        if (pgm.indexOf('.') == -1)
            pgm += ".c-"; // 源码文件名没有后缀的话，加上后缀

        try {
            InputStream source = new FileInputStream(pgm); // 打开源码文件
            if (source.available() == 0) {
                System.out.println("File: " + pgm + " not found");
                System.exit(1); // 源码文件打开失败
            }

            pgm = pgm.substring(0, pgm.lastIndexOf('.'));
            String listingFileName = pgm + ".txt"; // 目标文件名
            OutputStream listing = new FileOutputStream(listingFileName); // 打开目标文件

            listing.write(("C- Lexical Analyzer:" + "\n").getBytes());
            Scan scanner = new Scan(source, listing);
            while (scanner.getToken() != TokenType.ENDFILE);
            source.close(); // 关闭源码文件
            listing.close(); // 关闭目标文件

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}