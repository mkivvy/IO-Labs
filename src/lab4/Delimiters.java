package lab4;

/**
 * Delimiters is the enum class containing acceptable values for using the 
 * CSVPlusFormatter class.
 * The character value of the enum can be retrieved using the method getValue().
 * 
 * @author Mary King, mking@my.wctc.edu
 * @version 1.0
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
    
    /**
     * Returns the character value of the enum name.
     * 
     * @return the character value assigned to the enum name
     */
    public final char getValue() {
        return value;
    }
}
