package lab4;

/**
 *
 * @author Mary
 */
public enum Delimiters {
    TAB('\t'),
    COMMA(','),
    AT_SIGN('@'),
    POUND_SIGN('#'),
    DOLLAR_SIGN('$'),
    PERCENT_SIGN('%'),
    CARET('^'),
    AMPERSAND('&'), 
    ASTERISK('*'),
    HYPHEN('-'),
    UNDERSCORE('_'),
    VERTICAL_BAR('|'),
    EQUAL_SIGN('='),
    PLUS_SIGN('+'),
    LESS_THAN_SIGN('<'),
    GREATER_THAN_SIGN('>'),
    COLON(':'),
    SEMI_COLON(';'),
    PERIOD('.'),
    EXLAMATION_POINT('!'), 
    QUESTION_MARK('?');
    
    private char value;

    private Delimiters(char value) {
        this.value = value;
    }
    
    public char getValue() {
        return value;
    }
}
