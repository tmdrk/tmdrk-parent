package org.tmdrk.toturial.collection.map;

import org.tmdrk.toturial.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Testmy
 *
 * @author Jie.Zhou
 * @date 2020/9/17 17:43
 */
public class Testmy {
    public static void main(String[] args) {
        List<User> oldList = new ArrayList<>();
        oldList.add(new User(1,"10001","120000","1"));
//        oldList.add(new User(2,"10002","120000","1"));
        List<User> newList = new ArrayList<>();
        newList.add(new User(1,"10001","120000","1"));
        newList.add(new User(3,"10003","120000","1"));
        Map<String, User> newMap = newList.stream().collect(Collectors.toMap(User::getUserName, bargainItem -> bargainItem));
        Map<String, User> oldMap = oldList.stream().collect(Collectors.toMap(User::getUserName, bargainItem -> bargainItem));

        List<String> userNames = newMap.values().stream().filter(user -> oldMap.get(user.getUserName()) == null).map(User::getUserName).collect(Collectors.toList());
        System.out.println(userNames);

        List<String> delNames = oldList.stream().filter(ouser -> newMap.get(ouser.getUserName()) == null).map(User::getUserName).collect(Collectors.toList());
        System.out.println(delNames);

    }
}
