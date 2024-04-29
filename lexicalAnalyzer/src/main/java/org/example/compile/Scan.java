package org.example.compile;

import java.io.*;

import static org.example.compile.Globals.*;
import static org.example.compile.Globals.StateType.*;
import static org.example.compile.Globals.TokenType.*;

public class Scan {

    private static final int MAXTOKENLEN = 40;
    private static String lineBuf; // 读取的一行
    private static int linePos = 0;  // 读取的位置
    private static int bufSize = 0;  // 读取的长度
    private static boolean EOF_flag = false;  // 文件结束标志
    private static int lineno = 0;  // 当前读取的源码的行数
    private static BufferedReader source;  // 源码文件输入流
    private static OutputStream listing; /* 目标文件输出流 */
    private static final int EOF = -1;

    static {
        lineBuf = "";
    }

    // 构造函数，初始化源码和目标文件
    public Scan(InputStream source, OutputStream listingStream) {
        Scan.source = new BufferedReader(new InputStreamReader(source));
        listing = listingStream;
    }

    // 读取下一个字符
    private static int getNextChar() throws IOException {
        if (linePos >= bufSize) {
            lineno++;
            Scan.lineBuf = source.readLine();
            if (Scan.lineBuf == null) {
                EOF_flag = true;
                listing.write((lineno + ": EOF").getBytes());
                return -1;
            }
            listing.write(("\t" +  lineno + ": " + Scan.lineBuf + "\n").getBytes());
            bufSize = Scan.lineBuf.length();
            linePos = 0;
        }
        return Scan.lineBuf.charAt(linePos++);
    }

    // 如果不是文件结束 读取的位置减1
    private static void ungetNextChar() {
        if (!EOF_flag) {
            linePos--;
        }
    }

    // 查找保留字
    private static TokenType reservedLookup(String s) {
        for (int i = 0; i < MAXRESERVED; i++) {
            if (s.equals(reservedWords[i].str)) {
                return reservedWords[i].tok;
            }
        }
        return TokenType.ID;
    }

    // 词法分析
    public static TokenType getToken() throws IOException {
        StringBuilder tokenString = new StringBuilder();
        TokenType currentToken = ENDFILE;
        StateType state = START;
        int save;
        while (state != DONE){  // 当状态不是结束时
            try {
                int c = getNextChar(); // 读取下一个字符
                save = TRUE; // 保存
                switch (state)  // 根据状态
                {
                    case START:  // 开始
                        if (Character.isDigit(c))  // 如果是数字
                            state = INNUM;  // 状态为数字
                        else if (Character.isLetter(c))  // 如果是字母
                            state = INID;  // 状态为标识符
                        else if (c == '>')  // 如果是>
                            state = INMT;  // 状态为>
                        else if (c == '<')  // 如果是<
                            state = INLT; // 状态为<
                        else if (c == '=')  // 如果是=
                            state = INASSIGN;  // 状态为=
                        else if (c == '/')  // 如果是/
                            state = INDIV;  // 状态为/
                        else if ((c == ' ') || (c == '\t') || (c == '\n'))
                            save = FALSE;  // 空字符，不保存
                        else {
                            state = DONE;  // 结束
                            switch (c)  // 根据字符
                            {
                                case EOF -> {  // 文件结束
                                    save = FALSE;  // 不保存
                                    currentToken = ENDFILE;  // 文件结束
                                }  // 结束
                                case '+' -> currentToken = PLUS;  // +
                                case '-' -> currentToken = MINUS;  // -
                                case '(' -> currentToken = LPAREN;  // (
                                case ')' -> currentToken = RPAREN;  // )
                                case ';' -> currentToken = SEMI;  // ;
                                case ',' -> currentToken = COM;  // ,
                                case '[' -> currentToken = LBRACK;  // [
                                case ']' -> currentToken = RBRACK;  // ]
                                case '{' -> currentToken = LBRACES; // {
                                case '}' -> currentToken = RBRACES;  // }
                                case '*' -> currentToken = MUL;  // /*
                                default -> currentToken = ERROR;  // 错误
                            }
                        }
                        break;
                    case INNEQ:  // /!=
                        if (c == '=') {
                            save = TRUE;  // 保存
                            currentToken = NEQ;  // /!=
                            state = DONE;  // 结束
                        } else {
                            ungetNextChar();  // 退回一个字符
                            save = FALSE;  // 不保存
                            currentToken = ERROR;  // 错误
                        }
                    case INCOMMENT:  // /*
                        save = FALSE;  // 不保存
                        if (c == EOF)    // 文件结束
                        {
                            state = DONE;  // 结束
                            currentToken = ENDFILE; // 文件结束
                        } else if (c == '*')  // 如果是*
                            state = INCOMMENT1; // 状态为/*
                        break;  // 结束
                    case INCOMMENT1:  // /*
                        save = FALSE;  // 不保存
                        if (c == EOF) {
                            state = DONE;
                            currentToken = ENDFILE;
                        } else if (c == '/')    // 如果是/
                        {
                            state = START;  // 开始
                            tokenString = new StringBuilder();  // 置空
                        }
                        break;  // 结束
                    case INASSIGN:  // =
                        state = DONE;  // 结束
                        if (c == '=')  // 如果是=
                            currentToken = EQ;  // ==
                        else {
                            ungetNextChar();  // 退回一个字符
                            save = FALSE; // 不保存
                            currentToken = ASSIGN;  // =
                        }
                        break;
                    case INNUM:  // 数字
                        if (!Character.isDigit(c)) {
                            ungetNextChar();
                            save = FALSE;
                            state = DONE;
                            currentToken = NUM;
                        }
                        break;
                    case INID:   // 标识符
                        if (!Character.isLetter(c)) {
                            ungetNextChar();
                            save = FALSE;
                            state = DONE;
                            currentToken = ID;
                        } else if (linePos == bufSize) {
                            save = TRUE;
                            state = DONE;
                            currentToken = ID;
                        }
                        break;
                    case INDIV:
                        if (c == '*')
                            state = INCOMMENT;
                        else {
                            ungetNextChar();
                            save = FALSE;
                            state = DONE;
                            currentToken = DIV;
                        }
                        break;
                    case INLT:
                        state = DONE;
                        if (c == '=') {
                            save = TRUE;
                            currentToken = LET;
                        } else {
                            ungetNextChar();
                            save = FALSE;
                            currentToken = LT;
                        }
                        break;
                    case INMT:
                        state = DONE;
                        if (c == '=') {
                            save = TRUE;
                            currentToken = MET;
                        } else {
                            ungetNextChar();
                            save = FALSE;
                            currentToken = MT;
                        }
                        break;
                    case DONE:
                    default:
                        listing.write(("Scanner Bug: state= " + state + "\n").getBytes());
                        state = DONE;
                        currentToken = ERROR;
                        break;
                }
                if ((save == 1) && (tokenString.length() <= MAXTOKENLEN))
                    tokenString.append((char) c);  // 保存字符
                if (state == DONE) {
                    if (currentToken == ID)
                        currentToken = reservedLookup(tokenString.toString());  // 查找保留字
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!EOF_flag) {
            listing.write(("\t" +  lineno + ": ").getBytes());
        }
        if (currentToken == ID) {
            currentToken = reservedLookup(tokenString.toString());
        }
        if (tokenString.isEmpty()) {
            listing.write(("\t" + currentToken + "\n").getBytes());
        } else {
            listing.write(("\t" + currentToken + "," + tokenString.toString() + "\n").getBytes());
        }
        return currentToken;
    }

    // 保留字数组
    private static final ReservedWord[] reservedWords = {
            new ReservedWord("if", IF),
            new ReservedWord("else", ELSE),
            new ReservedWord("int", INT),
            new ReservedWord("return", RETURN),
            new ReservedWord("void", VOID),
            new ReservedWord("while", WHILE)
    };

    // 保留字结构体
    private static class ReservedWord {
        String str;
        TokenType tok;

        ReservedWord(String str, TokenType tok) {
            this.str = str;
            this.tok = tok;
        }
    }

}