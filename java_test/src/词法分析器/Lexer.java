package 词法分析器;

import java.util.*;

public class Lexer {
    private String input;
    private int pos = 0;

    // 种别编码映射表
    private static final Map<String, Integer> KEYWORDS = new HashMap<>() {{
        put("begin", 1);
        put("end", 2);
        put("if", 3);
        put("then", 4);
        put("else", 5);
        put("while", 6);
        put("do", 7);
    }};
    private static final Map<String, Integer> OPERATORS = new HashMap<>() {{
        put("+", 13);
        put("-", 14);
        put("*", 15);
        put("/", 16);
        put("<", 19);
        put("<>", 18);
        put("<=", 19);
        put(":=", 21);
        put(":", 22);
    }};
    private static final Map<String, Integer> DELIMITERS = new HashMap<>() {{
        put(";", 23);
        put("(", 29);
        put(")", 30);
        put("[", 31);
        put("]", 32);
        put("{", 33);
        put("}", 34);


    }};
    private static final int IDENTIFIER_CODE = 10;
    private static final int INTEGER_CONST_CODE = 11;

    public Lexer(String input) {
        this.input = input;
    }

    public List<AbstractMap.SimpleEntry<Integer, String>> analyze() {
        List<AbstractMap.SimpleEntry<Integer, String>> tokens = new ArrayList<>();
        while (pos < input.length()) {
            char ch = input.charAt(pos);
            if (Character.isWhitespace(ch)) {
                pos++;
                continue;
            }
            if (Character.isLetter(ch)) {
                tokens.add(parseIdentifierOrKeyword());
            } else if (Character.isDigit(ch)) {
                tokens.add(parseInteger());
            } else if (isOperatorStart(ch)) {
                tokens.add(parseOperator());
            } else if (isOther(ch)) {
                tokens.add(parseOther());
            } else {
                try {
                    throw new RuntimeException("非法字符");
                } catch (RuntimeException e) {
                    pos++;
                    System.out.println("非法字符 ch=" + ch);
                }
            }
        }
        return tokens;
    }

    private AbstractMap.SimpleEntry<Integer, String> parseOther() {
        String ch = String.valueOf(input.charAt(pos++));
        return new AbstractMap.SimpleEntry<>(DELIMITERS.get(ch), ch);
    }

    private boolean isOther(char ch) {
        return ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '[' || ch == ']' || ch == ';';
    }

    // 解析标识符或关键字
    private AbstractMap.SimpleEntry<Integer, String> parseIdentifierOrKeyword() {
        int start = pos;
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            pos++;
        }
        String word = input.substring(start, pos);
        if (KEYWORDS.containsKey(word)) {
            return new AbstractMap.SimpleEntry<>(KEYWORDS.get(word), word);
        }
        return new AbstractMap.SimpleEntry<>(IDENTIFIER_CODE, word);
    }

    // 解析整型常量
    private AbstractMap.SimpleEntry<Integer, String> parseInteger() {
        int start = pos;
        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            pos++;
        }
        return new AbstractMap.SimpleEntry<>(INTEGER_CONST_CODE, input.substring(start, pos));
    }

    // 解析运算符（支持多字符如:=）
    private AbstractMap.SimpleEntry<Integer, String> parseOperator() {
        // 处理 := 和 :
        if (input.charAt(pos) == ':') {
            if (pos + 1 < input.length() && input.charAt(pos + 1) == '=') {
                String op = input.substring(pos, pos + 2);
                pos += 2;
                return new AbstractMap.SimpleEntry<>(21, op);
            } else {
                String op = String.valueOf(input.charAt(pos));
                pos++;
                return new AbstractMap.SimpleEntry<>(22, op);
            }
        }
        // 处理其他运算符
        String op = String.valueOf(input.charAt(pos));
        if (OPERATORS.containsKey(op)) {
            pos++;
            return new AbstractMap.SimpleEntry<>(OPERATORS.get(op), op);
        }
        throw new RuntimeException("未知运算符: " + op);
    }

    // 判断是否为运算符起始字符
    private boolean isOperatorStart(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '<' || c == ':' || c == '=';
    }

    public static void main(String[] args) {
        String source = "if (x < 5) then x := 10; else x := 20;";
        Lexer lexer = new Lexer(source);
        List<AbstractMap.SimpleEntry<Integer, String>> tokens = lexer.analyze();
        tokens.forEach(token -> System.out.printf("(%d, '%s')\n", token.getKey(), token.getValue()));
    }
}