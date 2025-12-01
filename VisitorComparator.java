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