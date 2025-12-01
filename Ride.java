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