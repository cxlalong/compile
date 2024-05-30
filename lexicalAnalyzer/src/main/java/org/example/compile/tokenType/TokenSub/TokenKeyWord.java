package org.example.compile.tokenType.TokenSub;

import org.example.compile.tokenType.TokenBase;
import org.example.compile.tokenType.tokenEnum.KeywordType;
import org.example.compile.tokenType.tokenEnum.TokenType;

// 关键字
public class TokenKeyWord extends TokenBase {

    private KeywordType keywordType;

    public TokenKeyWord(KeywordType t, int l, int p) {
        super(l, p);
        keywordType = t;
    }

    public KeywordType getKeywordType() {
        return keywordType;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.KEYWORD;
    }

    @Override
    public String toString() {
        return "Keyword:    \t" + KeywordType.KeywordType2String(keywordType);
    }

}
