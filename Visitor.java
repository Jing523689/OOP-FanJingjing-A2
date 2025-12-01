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