package org.example.compile;

import org.example.compile.lexerService.LexerService;

import java.io.IOException;
import java.util.Scanner;

public class LexerApplication {
	public static void main(String[] args) {
		LexerService lexerService = new LexerService();
		Scanner in = new Scanner(System.in);
		System.out.println("请输入输入文件路径参数");
		String input = in.next();
		System.out.println("请输入输出文件路径参数");
		String output = in.next();
		try {
			lexerService.setPath(input, output);
			lexerService.lexingFile();
		} catch (IOException e) {
			System.out.println("文件地址有误");
			return;
		}
	}

}
