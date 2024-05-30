package org.example.compile;


import org.example.compile.lexerService.LexerService;
import org.example.compile.parserImpl.Parser;
import org.example.compile.parserTree.TreeNode;

import java.io.IOException;
import java.util.Scanner;

public class ParserApplication {

	public static void main(String[] args) throws IOException {
		TreeNode res = null;
		LexerService lexer = new LexerService();
		Parser parser = new Parser(lexer);
		Scanner in = new Scanner(System.in);
		System.out.println("请输入输入文件路径参数");
		String input = in.next();
		System.out.println("请输入输出文件路径参数");
		String output = in.next();
		try {
			lexer.setPath(input, output);
			res = parser.parse();
			if (res != null) {
				res.show();
			}
		} catch (IOException e) {
			System.out.println("文件地址有误");
			return;
		}
	}
}
