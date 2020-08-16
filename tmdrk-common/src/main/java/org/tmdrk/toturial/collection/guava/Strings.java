package org.tmdrk.toturial.collection.guava;

import com.google.common.base.CaseFormat;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;

import java.util.Map;

/**
 * Strings
 *
 * @author Jie.Zhou
 * @date 2020/8/11 14:38
 */
public class Strings {
    public static void main(String[] args) {
        testSplitter();

        testJoiner();

        testCharMatcher();

        testCaseFormat();

        System.out.println(Range.closed(1, 3).contains(2));//return true
        System.out.println(Range.closedOpen(4, 4).isEmpty()); // returns true
        System.out.println(Range.openClosed(4, 4).isEmpty()); // returns true
        System.out.println(Range.closed(4, 4).isEmpty()); // returns false
        System.out.println(Range.open(4, 5).isEmpty()); // Range.open throws IllegalArgumentException
        System.out.println(Range.closed(3, 10).lowerEndpoint()); // returns 3
        System.out.println(Range.open(3, 10).lowerEndpoint()); // returns 3
        System.out.println(Range.closed(3, 10).lowerBoundType()); // returns CLOSED
        System.out.println(Range.open(3, 10).upperBoundType()); // returns OPEN
        System.out.println(Range.closedOpen(4, 4).isEmpty());
        System.out.println(Range.closedOpen(4, 4));
    }

    private static void testCaseFormat() {
        String constant_name = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME");// returns "constantName"
        System.out.println(constant_name);
    }

    private static void testCharMatcher() {
        String string = "  98sdf89s df9s8  df9sdsfhs/n";
        String noControl = CharMatcher.javaIsoControl().removeFrom(string); //移除control字符
        System.out.println(noControl);
        String theDigits = CharMatcher.digit().retainFrom(string); //只保留数字字符
        System.out.println(theDigits);
        String spaced = CharMatcher.whitespace().trimAndCollapseFrom(string, ' ');//去除两端的空格，并把中间的连续空格替换成单个空格
        System.out.println(spaced);
        String noDigits = CharMatcher.digit().replaceFrom(string, "*"); //用*号替换所有数字
        System.out.println(noDigits);
        String lowerAndDigit = CharMatcher.javaDigit().or(CharMatcher.javaLowerCase()).retainFrom(string);// 只保留数字和小写字母
        System.out.println(lowerAndDigit);

        CharMatcher charMatcher = CharMatcher.inRange('a', 'z');
        System.out.println(charMatcher.matches('A'));
        System.out.println(charMatcher.matchesAnyOf("ADJFGz"));
        //collapseFrom(CharSequence,   char)	把每组连续的匹配字符替换为特定字符。如WHITESPACE.collapseFrom(string, ‘ ‘)把字符串中的连续空白字符替换为单个空格。
        //matchesAllOf(CharSequence)	测试是否字符序列中的所有字符都匹配。
        //removeFrom(CharSequence)	从字符序列中移除所有匹配字符。
        //retainFrom(CharSequence)	在字符序列中保留匹配字符，移除其他字符。
        //trimFrom(CharSequence)	移除字符序列的前导匹配字符和尾部匹配字符。
        //replaceFrom(CharSequence,   CharSequence)	用特定字符序列替代匹配字符。
        CharMatcher.is('a').countIn("aaa"); // 3
        CharMatcher.is('a').indexIn("java"); // 1


    }

    private static void testJoiner() {
        Joiner joiner = Joiner.on("; ").skipNulls();
        String join = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println(join);
        Map<String,String> maps = Maps.newHashMap();
        maps.put("id", "1");
        maps.put("name", "2");
        String ss= Joiner.on("&").withKeyValueSeparator("=").join(maps);
        System.out.println(ss);
    }

    private static void testSplitter() {
        Iterable<String> split = Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux");
        System.out.println(Lists.newArrayList(split));
    }
}
