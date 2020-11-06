package org.tmdrk.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * LongestValidParentheses
 * 最长有效括号
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 *
 * 示例 1:
 *
 * 输入: "(()"
 * 输出: 2
 * 解释: 最长有效括号子串为 "()"
 * 示例 2:
 *
 * 输入: ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串为 "()()"
 *
 * @author Jie.Zhou
 * @date 2020/11/5 13:02
 */
public class LongestValidParentheses {
    public static void main(String[] args) {
        String s = ")()())(((()()()((()()(())";
        int i = longestValidParentheses(s);
        System.out.println(i);
    }
    public static int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack();
        Map<Integer,Integer> map = new HashMap<>();
        int max = 0;
        for(int i=0;i<s.length();i++){
            if('('==s.charAt(i)){
                stack.add(i);
            }else{
                if(stack.size()>0){
                    int num = 2;
                    Integer pop = stack.pop();
                    Integer pre = map.get(pop-1); //左括号之前的有效大小 ()()
                    num = pre==null?num:pre+num;
                    Integer in = map.get(i-1); //右括号之前的有效大小 (())
                    num = in==null?num:in+num;
                    max = Math.max(max,num);
                    map.put(i,num);
                }
            }
        }
        return max;
    }

    /**
     * 力扣解题，更简便
     */
//    public int longestValidParentheses(String s) {
//        int max = 0;
//        Stack<Integer> stack = new Stack<>();
//        stack.push(-1);
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i) == '(') {
//                stack.push(i);
//            } else {
//                stack.pop();
//                if (stack.empty()) {
//                    stack.push(i);
//                } else {
//                    max = Math.max(max, i - stack.peek());
//                }
//            }
//        }
//        return max;
//    }
}
