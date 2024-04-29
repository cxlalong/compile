package org.example.compile;

public class Globals {

    // 定义布尔值常量
    public static final int FALSE = 0;
    public static final int TRUE = 1;

    // 关键字数的上限
    public static final int MAXRESERVED = 6;

    // 定义TokenType枚举
    public enum TokenType {
        ENDFILE,    // 文件结束
        ERROR,      // 错误
        IF,         // if
        ELSE,        // else
        INT,         // int
        RETURN,      // return
        VOID,        // void
        WHILE,       // while
        ID,          // 标识符
        NUM,         // 数字
        ASSIGN,      // 赋值
        EQ,          // ==
        PLUS,        // +
        MINUS,       // -
        MUL,         // *
        DIV,         // /
        LPAREN,      // (
        RPAREN,      // )
        SEMI,        // ;
        LT,          // <
        LET,         // <=
        MT,          // >
        MET,         // >=
        NEQ,         // !=
        COM,         // ,
        LBRACK,      // [
        RBRACK,      // ]
        LBRACES,     // {
        RBRACES,     // }
        LCOM,        // /*
        RCOM         // */
    }

    // 词法分析器的状态枚举
    public static enum StateType {
        START,  // 开始
        INLT,  // <
        INMT,  // >
        INASSIGN,  // =
        INDIV,  // /
        INCOMMENT,  // /*
        INCOMMENT1,  //   */
        INNEQ,  // /!=
        INNUM,  // 数字
        INID,  // 标识符
        DONE  // 结束
    }
}