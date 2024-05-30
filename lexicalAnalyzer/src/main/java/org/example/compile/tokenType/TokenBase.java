package org.example.compile.tokenType;

import org.example.compile.tokenType.tokenEnum.TokenType;

// Token基类
public abstract class TokenBase {
    private int line;
    private int pos;

    public TokenBase() {
        line = 0;
        pos = 0;
    }

    public TokenBase(int l, int p) {
        line = l;
        pos = p;
    }

    public abstract TokenType getTokenType();

    @Override
    public abstract String toString();

    public int getLine() {
        return line;
    }

    public int getPos() {
        return pos;
    }
}
