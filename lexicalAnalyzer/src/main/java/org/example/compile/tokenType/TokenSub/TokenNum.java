package org.example.compile.tokenType.TokenSub;

import org.example.compile.tokenType.TokenBase;
import org.example.compile.tokenType.tokenEnum.TokenType;

// 数字
public class TokenNum extends TokenBase {

    private String number;

    public TokenNum(String num, int l, int p) {
        super(l, p);
        number = num;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.NUM;
    }

    @Override
    public String toString() {
        return "Number:    \t" + number;
    }

}
