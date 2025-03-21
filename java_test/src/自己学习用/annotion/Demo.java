package 自己学习用.annotion;

@MyTest4(aaa = 5, bbb = "bbb")
public class Demo {
    @MyTest4(aaa = 5.5, bbb = "bbc")
    public void test1() {
        System.out.println("aaa");
    }
}
