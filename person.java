/**
 * 抽象父类，封装人员共有的属性和行为
 * 不可实例化，仅作为Employee和Visitor的父类
 */
public abstract class Person {
    // 3个实例变量（姓名、年龄、联系电话）
    protected String name;  // 改为protected，便于子类访问
    private int age;
    private String phoneNumber;

    // 默认构造函数
    public Person() {}

    // 带参构造函数
    public Person(String name, int age, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // getter和setter方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // 重写toString方法，便于打印人员信息
    @Override
    public String toString() {
        return "姓名：" + name + "，年龄：" + age + "，联系电话：" + phoneNumber;
    }
}