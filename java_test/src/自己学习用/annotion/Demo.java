package 自己学习用.annotion;

import lombok.Getter;

@MyTest4(aaa = 5, bbb = "bbb")
@Getter
public class Demo {

    String string;

    Integer integer;

    @MyTest4(aaa = 5.5, bbb = "bbc")
    public void test1() {
        System.out.println("test`1");
    }

    public void test2() {
        System.out.println("test`2");
    }

    public void test3() {
        System.out.println("test`3");
    }

    @MyTest4(aaa = 5.5, bbb = "bbc")
    public void test4() {
        System.out.println("test`4");
    }

}
