package org.example.compile.tokenType.TokenSub;

import org.example.compile.tokenType.TokenBase;
import org.example.compile.tokenType.tokenEnum.TokenType;

// 标识符
public class TokenIdentifier extends TokenBase {
    private String identifier;

    public TokenIdentifier(String id, int l, int p) {
        super(l, p);
        identifier = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public TokenType getTokenType() {
        return TokenType.ID;
    }

    @Override
    public String toString() {
        return "Identifier:    \t" + identifier;
    }
    
}
