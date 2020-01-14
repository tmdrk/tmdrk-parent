package org.tmdrk.toturial.news;

import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.tmdrk.protobuf.DataInfo;
import org.tmdrk.toturial.common.util.ListUtil;
import org.tmdrk.toturial.common.util.StringUtil;
import org.tmdrk.toturial.dataStructures.tree.bst.Student;

public class JdkStream {
	public static void main(String[] args) {
		List<String> nums = (List<String>) ListUtil.newArrayList();
		System.out.println(nums);
		List<String> stringCollection = new ArrayList<>();
		stringCollection.add("ddd2");
		stringCollection.add("aaa2");
		stringCollection.add("bbb1");
		stringCollection.add("aaa1");
		stringCollection.add("bbb3");
		stringCollection.add("ccc");
		stringCollection.add("bbb2");
		stringCollection.add("ddd1");
		stringCollection.stream().filter((s)->s.startsWith("b"));
		System.out.println(stringCollection.stream().filter((s)->s.startsWith("b")).collect(Collectors.toList()));
		Clock clock = Clock.systemDefaultZone();
		System.out.println(clock.millis());
		System.out.println(System.currentTimeMillis());
		
		stringCollection
	    .stream()
	    .map(StringUtil::firstUpperCase)
	    .sorted((a, b) -> b.compareTo(a))
	    .forEach(System.out::println);

		String namePrefix = "oka_";
		List<Student> list = new ArrayList<Student>();
		list.add(new Student("zhoujie",12));
		list.add(new Student("zhangsna",22));
		list.add(new Student("lisi",23));
		list.stream().forEach(stu->{
			System.out.println(namePrefix+stu.getName());
		});
        Stream<Student> z = list.stream().filter(stu -> stu.getName().startsWith("z"));
    }
}
