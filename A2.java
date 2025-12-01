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

/**
 * 游客类，继承自Person
 * 新增游客专属属性：游客ID、是否购买快速通行证
 */
public class Visitor extends Person {
    // 2个专属实例变量
    private String visitorId;
    private boolean hasFastPass;

    // 默认构造函数
    public Visitor() {
        super();
    }

    // 带参构造函数（初始化父类和子类属性）
    public Visitor(String name, int age, String phoneNumber, String visitorId, boolean hasFastPass) {
        super(name, age, phoneNumber);
        this.visitorId = visitorId;
        this.hasFastPass = hasFastPass;
    }

    // getter和setter方法
    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public boolean isHasFastPass() {
        return hasFastPass;
    }

    public void setHasFastPass(boolean hasFastPass) {
        this.hasFastPass = hasFastPass;
    }

    // 重写toString方法，包含游客专属信息
    @Override
    public String toString() {
        return super.toString() + "，游客ID：" + visitorId + "，快速通行证：" + (hasFastPass ? "有" : "无");
    }

    // 用于CSV文件读写的辅助方法（返回CSV格式字符串）
    public String toCsvString() {
        return getName() + "," + getAge() + "," + getPhoneNumber() + "," + visitorId + "," + hasFastPass;
    }

    // 从CSV字符串解析游客对象
    public static Visitor fromCsvString(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length != 5) {
            System.out.println("警告：CSV格式错误，跳过此行：" + csvLine);
            return null;
        }
        try {
            String name = parts[0];
            int age = Integer.parseInt(parts[1]);
            String phone = parts[2];
            String visitorId = parts[3];
            boolean hasFastPass = Boolean.parseBoolean(parts[4]);
            return new Visitor(name, age, phone, visitorId, hasFastPass);
        } catch (Exception e) {
            System.out.println("警告：解析CSV行失败：" + csvLine + "，错误：" + e.getMessage());
            return null;
        }
    }
    
    // 重写equals方法，用于集合比较
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Visitor visitor = (Visitor) obj;
        return visitorId.equals(visitor.visitorId);
    }
    
    @Override
    public int hashCode() {
        return visitorId.hashCode();
    }
}

/**
 * 游乐设施接口，定义核心行为规范
 */
public interface RideInterface {
    // 队列操作方法
    void addVisitorToQueue(Visitor visitor);
    void removeVisitorFromQueue();
    void printQueue();

    // 历史记录操作方法
    void addVisitorToHistory(Visitor visitor);
    boolean checkVisitorFromHistory(Visitor visitor);
    int numberOfVisitors();
    void printRideHistory();

    // 设施运行方法
    void runOneCycle();
}

import java.util.Comparator;

/**
 * 游客排序比较器，实现Comparator接口
 * 排序规则：先按年龄升序，年龄相同则按姓名字典序升序
 */
public class VisitorComparator implements Comparator<Visitor> {
    @Override
    public int compare(Visitor v1, Visitor v2) {
        // 第一排序条件：年龄
        int ageCompare = Integer.compare(v1.getAge(), v2.getAge());
        if (ageCompare != 0) {
            return ageCompare;
        }
        // 第二排序条件：姓名（字典序）
        return v1.getName().compareToIgnoreCase(v2.getName());
    }
}

import java.io.*;
import java.util.*;

/**
 * 游乐设施类，实现RideInterface接口
 * 管理设施的操作员、等待队列、历史记录、运行逻辑等
 */
public class Ride implements RideInterface {
    // 3个核心实例变量（名称、类型、操作员）
    private String rideName;
    private String rideType;
    private Employee operator; // 必须为Employee类型

    // Part3：等待队列（FIFO）
    private Queue<Visitor> waitingQueue;

    // Part4A：历史记录（LinkedList）
    private LinkedList<Visitor> rideHistory;

    // Part5：运行相关变量
    private int maxRider; // 单周期最大载客量
    private int numOfCycles; // 运行次数

    // 构造函数
    public Ride() {
        this.waitingQueue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
        this.numOfCycles = 0;
    }

    public Ride(String rideName, String rideType, Employee operator, int maxRider) {
        this.rideName = rideName;
        this.rideType = rideType;
        this.operator = operator;
        this.maxRider = Math.max(1, maxRider); // 确保至少1人
        this.waitingQueue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
        this.numOfCycles = 0;
    }

    // getter和setter方法
    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public String getRideType() {
        return rideType;
    }

    public void setRideType(String rideType) {
        this.rideType = rideType;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public int getMaxRider() {
        return maxRider;
    }

    public void setMaxRider(int maxRider) {
        this.maxRider = Math.max(1, maxRider); // 确保至少1人
    }

    public int getNumOfCycles() {
        return numOfCycles;
    }

    // Part3：队列操作方法实现
    @Override
    public void addVisitorToQueue(Visitor visitor) {
        if (visitor == null) {
            System.out.println("错误：游客对象不能为空，添加队列失败！");
            return;
        }
        waitingQueue.offer(visitor);
        System.out.println("成功添加游客【" + visitor.getName() + "】到【" + rideName + "】的等待队列");
    }

    @Override
    public void removeVisitorFromQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.println("错误：【" + rideName + "】的等待队列为空，移除失败！");
            return;
        }
        Visitor removed = waitingQueue.poll();
        System.out.println("成功从【" + rideName + "】的等待队列移除游客：" + removed.getName());
    }

    @Override
    public void printQueue() {
        System.out.println("\n【" + rideName + "】等待队列信息（当前队列长度：" + waitingQueue.size() + "）：");
        if (waitingQueue.isEmpty()) {
            System.out.println("队列无等待游客");
            return;
        }
        int index = 1;
        for (Visitor visitor : waitingQueue) {
            System.out.println(index + ". " + visitor);
            index++;
        }
        System.out.println();
    }

    // Part4A：历史记录操作方法实现
    @Override
    public void addVisitorToHistory(Visitor visitor) {
        if (visitor == null) {
            System.out.println("错误：游客对象不能为空，添加历史记录失败！");
            return;
        }
        rideHistory.add(visitor);
        System.out.println("成功添加游客【" + visitor.getName() + "】到【" + rideName + "】的历史记录");
    }

    @Override
    public boolean checkVisitorFromHistory(Visitor visitor) {
        if (visitor == null) {
            System.out.println("错误：游客对象不能为空，查询失败！");
            return false;
        }
        boolean exists = rideHistory.contains(visitor);
        if (exists) {
            System.out.println("查询结果：游客【" + visitor.getName() + "】曾乘坐过【" + rideName + "】");
        } else {
            System.out.println("查询结果：游客【" + visitor.getName() + "】未乘坐过【" + rideName + "】");
        }
        return exists;
    }

    @Override
    public int numberOfVisitors() {
        int count = rideHistory.size();
        System.out.println("【" + rideName + "】的历史乘坐人数：" + count);
        return count;
    }

    @Override
    public void printRideHistory() {
        System.out.println("\n【" + rideName + "】历史乘坐记录（总人数：" + rideHistory.size() + "）：");
        if (rideHistory.isEmpty()) {
            System.out.println("暂无历史乘坐记录");
            return;
        }
        // 必须使用Iterator遍历（否则无分）
        Iterator<Visitor> iterator = rideHistory.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.println(index + ". " + visitor);
            index++;
        }
        System.out.println();
    }

    // Part4B：历史记录排序方法
    public void sortRideHistory(Comparator<Visitor> comparator) {
        if (comparator == null) {
            System.out.println("错误：比较器不能为空，排序失败！");
            return;
        }
        if (rideHistory.isEmpty()) {
            System.out.println("错误：历史记录为空，排序失败！");
            return;
        }
        Collections.sort(rideHistory, comparator);
        System.out.println("成功对【" + rideName + "】的历史记录进行排序");
    }

    // Part5：运行一个设施周期
    @Override
    public void runOneCycle() {
        // 校验条件：无操作员
        if (operator == null) {
            System.out.println("错误：【" + rideName + "】未分配操作员，无法运行！");
            return;
        }
        // 校验条件：无等待游客
        if (waitingQueue.isEmpty()) {
            System.out.println("错误：【" + rideName + "】的等待队列为空，无法运行！");
            return;
        }

        System.out.println("\n【" + rideName + "】开始运行第" + (numOfCycles + 1) + "个周期（最大载客量：" + maxRider + "）");
        System.out.println("操作员：" + operator.getName() + "（" + operator.getPosition() + "）");
        
        int ridersThisCycle = 0;

        // 按maxRider数量从队列转移到历史记录
        while (!waitingQueue.isEmpty() && ridersThisCycle < maxRider) {
            Visitor visitor = waitingQueue.poll();
            rideHistory.add(visitor);
            System.out.println("游客【" + visitor.getName() + "】已上车");
            ridersThisCycle++;
        }

        numOfCycles++;
        System.out.println("【" + rideName + "】第" + numOfCycles + "个周期运行结束，本次载客：" + ridersThisCycle + "人");
    }

    // Part6：导出历史记录到CSV文件
    public void exportRideHistory(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("错误：文件路径不能为空，导出失败！");
            return;
        }
        if (rideHistory.isEmpty()) {
            System.out.println("错误：历史记录为空，无需导出！");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 遍历历史记录，写入CSV格式
            for (Visitor visitor : rideHistory) {
                writer.write(visitor.toCsvString());
                writer.newLine();
            }
            System.out.println("成功导出【" + rideName + "】的历史记录到文件：" + filePath);
            System.out.println("共导出 " + rideHistory.size() + " 条记录");
        } catch (IOException e) {
            System.out.println("错误：导出文件失败！原因：" + e.getMessage());
        }
    }

    // Part7：从CSV文件导入历史记录
    public void importRideHistory(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("错误：文件路径不能为空，导入失败！");
            return;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("错误：文件不存在（" + filePath + "），导入失败！");
            return;
        }

        int importedCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                Visitor visitor = Visitor.fromCsvString(line);
                if (visitor != null) {
                    rideHistory.add(visitor);
                    importedCount++;
                }
            }
            System.out.println("成功从文件【" + filePath + "】导入" + importedCount + "名游客到【" + rideName + "】的历史记录");
        } catch (IOException e) {
            System.out.println("错误：导入文件失败！原因：" + e.getMessage());
        }
    }
    
    // 获取等待队列大小
    public int getQueueSize() {
        return waitingQueue.size();
    }
    
    // 获取历史记录大小
    public int getHistorySize() {
        return rideHistory.size();
    }
}

import java.util.Scanner;

/**
 * 主类，包含程序入口和各部分功能演示方法
 */
public class AssignmentTwo {
    public static void main(String[] args) {
        AssignmentTwo demo = new AssignmentTwo();
        Scanner scanner = new Scanner(System.in);

        // 循环演示菜单
        while (true) {
            System.out.println("\n===== 主题公园游乐设施游客管理系统（PRVMS）=====");
            System.out.println("1. 演示Part3：等待队列管理");
            System.out.println("2. 演示Part4A：历史记录管理");
            System.out.println("3. 演示Part4B：历史记录排序");
            System.out.println("4. 演示Part5：运行设施周期");
            System.out.println("5. 演示Part6：导出历史记录到文件");
            System.out.println("6. 演示Part7：从文件导入历史记录");
            System.out.println("7. 完整演示所有功能");
            System.out.println("0. 退出系统");
            System.out.print("请选择功能（0-7）：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 清空缓冲区
            switch (choice) {
                case 1:
                    demo.partThree();
                    break;
                case 2:
                    demo.partFourA();
                    break;
                case 3:
                    demo.partFourB();
                    break;
                case 4:
                    demo.partFive();
                    break;
                case 5:
                    demo.partSix();
                    break;
                case 6:
                    demo.partSeven();
                    break;
                case 7:
                    demo.fullDemo();
                    break;
                case 0:
                    System.out.println("退出系统，感谢使用！");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    /**
     * Part3：等待队列演示（添加5名游客、移除1名、打印队列）
     */
    public void partThree() {
        System.out.println("\n===== 演示Part3：等待队列管理 =====");
        // 创建操作员
        Employee operator = new Employee("张三", 30, "13800138001", "EMP001", "过山车操作员");
        // 创建游乐设施
        Ride rollerCoaster = new Ride("云霄飞车", "刺激类", operator, 2);

        // 添加5名游客到队列
        System.out.println("\n--- 添加5名游客到队列 ---");
        rollerCoaster.addVisitorToQueue(new Visitor("Jack", 25, "13900139001", "VIS001", true));
        rollerCoaster.addVisitorToQueue(new Visitor("Sharon", 22, "13900139002", "VIS002", false));
        rollerCoaster.addVisitorToQueue(new Visitor("Benny", 30, "13900139003", "VIS003", true));
        rollerCoaster.addVisitorToQueue(new Visitor("Leo", 28, "13900139004", "VIS004", false));
        rollerCoaster.addVisitorToQueue(new Visitor("Lily", 24, "13900139005", "VIS005", true));

        // 打印队列
        System.out.println("\n--- 打印当前队列 ---");
        rollerCoaster.printQueue();

        // 移除1名游客
        System.out.println("\n--- 移除1名游客 ---");
        rollerCoaster.removeVisitorFromQueue();

        // 打印移除后的队列
        System.out.println("\n--- 打印移除后的队列 ---");
        rollerCoaster.printQueue();
        
        System.out.println("Part3 演示完成！");
    }

    /**
     * Part4A：历史记录演示（添加5名游客、检查存在性、打印数量和详情）
     */
    public void partFourA() {
        System.out.println("\n===== 演示Part4A：历史记录管理 =====");
        Employee operator = new Employee("王五", 28, "13800138002", "EMP003", "水上设施操作员");
        Ride thunderstorm = new Ride("雷霆风暴", "水上类", operator, 4);

        System.out.println("\n--- 添加5名游客到历史记录 ---");
        Visitor v1 = new Visitor("Tom", 27, "13700137001", "VIS006", true);
        Visitor v2 = new Visitor("Sherly", 23, "13700137002", "VIS007", false);
        Visitor v3 = new Visitor("Ben", 32, "13700137003", "VIS008", true);
        Visitor v4 = new Visitor("David", 29, "13700137004", "VIS009", false);
        Visitor v5 = new Visitor("Amy", 26, "13700137005", "VIS010", true);

        thunderstorm.addVisitorToHistory(v1);
        thunderstorm.addVisitorToHistory(v2);
        thunderstorm.addVisitorToHistory(v3);
        thunderstorm.addVisitorToHistory(v4);
        thunderstorm.addVisitorToHistory(v5);

        System.out.println("\n--- 检查游客是否在历史记录中 ---");
        thunderstorm.checkVisitorFromHistory(v2); // 存在
        thunderstorm.checkVisitorFromHistory(new Visitor("Mike", 20, "13700137006", "VIS011", false)); // 不存在

        System.out.println("\n--- 打印历史记录人数 ---");
        thunderstorm.numberOfVisitors();

        System.out.println("\n--- 打印历史记录详情（使用Iterator） ---");
        thunderstorm.printRideHistory();
        
        System.out.println("Part4A 演示完成！");
    }

    /**
     * Part4B：历史记录排序演示（添加5名游客、打印排序前后对比）
     */
    public void partFourB() {
        System.out.println("\n===== 演示Part4B：历史记录排序 =====");
        Employee operator = new Employee("赵六", 35, "13800138003", "EMP004", "观光设施操作员");
        Ride ferrisWheel = new Ride("摩天轮", "观光类", operator, 8);

        System.out.println("\n--- 添加5名游客（年龄和姓名无序） ---");
        ferrisWheel.addVisitorToHistory(new Visitor("Bob", 35, "13600136001", "VIS012", false));
        ferrisWheel.addVisitorToHistory(new Visitor("Alice", 22, "13600136002", "VIS013", true));
        ferrisWheel.addVisitorToHistory(new Visitor("Charlie", 28, "13600136003", "VIS014", false));
        ferrisWheel.addVisitorToHistory(new Visitor("Anna", 22, "13600136004", "VIS015", true));
        ferrisWheel.addVisitorToHistory(new Visitor("David", 30, "13600136005", "VIS016", false));

        // 打印排序前的历史记录
        System.out.println("\n--- 排序前的历史记录 ---");
        ferrisWheel.printRideHistory();

        // 使用自定义比较器排序（年龄升序，姓名升序）
        System.out.println("\n--- 使用Comparator进行排序（年龄升序，年龄相同按姓名升序） ---");
        ferrisWheel.sortRideHistory(new VisitorComparator());

        // 打印排序后的历史记录
        System.out.println("\n--- 排序后的历史记录 ---");
        ferrisWheel.printRideHistory();
        
        System.out.println("Part4B 演示完成！");
    }

    /**
     * Part5：运行设施周期演示（添加10名游客、运行1个周期、打印前后对比）
     */
    public void partFive() {
        System.out.println("\n===== 演示Part5：运行设施周期 =====");
        // 创建操作员
        Employee operator = new Employee("李四", 35, "13500135001", "EMP002", "旋转木马操作员");
        // 创建游乐设施（单周期最大载客量3人）
        Ride carousel = new Ride("旋转木马", "亲子类", operator, 3);

        System.out.println("\n--- 添加10名游客到队列 ---");
        // 添加10名游客到队列
        for (int i = 0; i < 10; i++) {
            carousel.addVisitorToQueue(new Visitor(
                    "游客" + (i + 1),
                    10 + i,
                    "1340013400" + (i + 1),
                    "VIS0" + (17 + i),
                    i % 2 == 0
            ));
        }

        // 打印运行前的队列
        System.out.println("\n--- 运行前的等待队列 ---");
        carousel.printQueue();

        System.out.println("\n--- 运行一个周期（最大载客3人） ---");
        // 运行一个周期
        carousel.runOneCycle();

        // 打印运行后的队列和历史记录
        System.out.println("\n--- 运行后的等待队列 ---");
        carousel.printQueue();

        System.out.println("\n--- 运行后的历史记录 ---");
        carousel.printRideHistory();
        
        System.out.println("Part5 演示完成！");
    }

    /**
     * Part6：导出历史记录到CSV文件演示
     */
    public void partSix() {
        System.out.println("\n===== 演示Part6：导出历史记录到文件 =====");
        Employee operator = new Employee("孙七", 32, "13800138004", "EMP005", "水上设施操作员");
        Ride logFlume = new Ride("激流勇进", "水上类", operator, 6);

        System.out.println("\n--- 添加5名游客到历史记录 ---");
        // 添加5名游客到历史记录
        logFlume.addVisitorToHistory(new Visitor("Emma", 24, "13300133001", "VIS027", true));
        logFlume.addVisitorToHistory(new Visitor("Olivia", 26, "13300133002", "VIS028", false));
        logFlume.addVisitorToHistory(new Visitor("Noah", 29, "13300133003", "VIS029", true));
        logFlume.addVisitorToHistory(new Visitor("Liam", 27, "13300133004", "VIS030", false));
        logFlume.addVisitorToHistory(new Visitor("Sophia", 23, "13300133005", "VIS031", true));

        // 打印当前历史记录
        System.out.println("\n--- 当前历史记录 ---");
        logFlume.printRideHistory();

        // 导出到CSV文件（默认保存到项目根目录）
        String filePath = "ride_history_export.csv";
        System.out.println("\n--- 导出历史记录到CSV文件 ---");
        logFlume.exportRideHistory(filePath);
        
        System.out.println("Part6 演示完成！文件已保存到：" + filePath);
    }

    /**
     * Part7：从CSV文件导入历史记录演示
     */
    public void partSeven() {
        System.out.println("\n===== 演示Part7：从文件导入历史记录 =====");
        Employee operator = new Employee("周八", 29, "13800138005", "EMP006", "测试设施操作员");
        Ride importRide = new Ride("导入测试设施", "测试类", operator, 5);

        System.out.println("\n--- 导入前历史记录 ---");
        importRide.printRideHistory();

        // 导入Part6生成的CSV文件
        String filePath = "ride_history_export.csv";
        System.out.println("\n--- 从CSV文件导入历史记录 ---");
        importRide.importRideHistory(filePath);

        // 验证导入结果
        System.out.println("\n--- 导入后历史记录 ---");
        importRide.numberOfVisitors();
        importRide.printRideHistory();
        
        System.out.println("Part7 演示完成！");
    }
    
    /**
     * 完整演示所有功能
     */
    public void fullDemo() {
        System.out.println("\n===== 完整演示所有功能 =====");
        System.out.println("=== 演示Part3：等待队列管理 ===");
        partThree();
        
        System.out.println("\n=== 演示Part4A：历史记录管理 ===");
        partFourA();
        
        System.out.println("\n=== 演示Part4B：历史记录排序 ===");
        partFourB();
        
        System.out.println("\n=== 演示Part5：运行设施周期 ===");
        partFive();
        
        System.out.println("\n=== 演示Part6：导出历史记录到文件 ===");
        partSix();
        
        System.out.println("\n=== 演示Part7：从文件导入历史记录 ===");
        partSeven();
        
        System.out.println("\n===== 所有功能演示完成 =====");
    }
}