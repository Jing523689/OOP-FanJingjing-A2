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