/**
 * 员工类，继承自Person
 * 新增员工专属属性：员工ID、职位
 */
public class Employee extends Person {
    // 2个专属实例变量
    private String employeeId;
    private String position;

    // 默认构造函数
    public Employee() {
        super(); // 调用父类默认构造函数
    }

    // 带参构造函数（初始化父类和子类属性）
    public Employee(String name, int age, String phoneNumber, String employeeId, String position) {
        super(name, age, phoneNumber); // 调用父类带参构造函数
        this.employeeId = employeeId;
        this.position = position;
    }

    // getter和setter方法
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // 重写toString方法，包含员工专属信息
    @Override
    public String toString() {
        return super.toString() + "，员工ID：" + employeeId + "，职位：" + position;
    }
}