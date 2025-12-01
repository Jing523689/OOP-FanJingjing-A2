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