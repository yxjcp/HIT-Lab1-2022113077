package P3;

public class Main {
    public static void main(String[] args) {
        // 创建一个新的FriendshipGraph实例
        FriendshipGraph graph = new FriendshipGraph();

        // 创建几个人实例
        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");

        // 将人添加到图中
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);

        // 添加人际关系
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);

        // 测试距离功能
        System.out.println("Distance between Rachel and Ross: " + graph.getDistance(rachel, ross));  // 应打印1
        System.out.println("Distance between Rachel and Ben: " + graph.getDistance(rachel, ben));    // 应打印2
        System.out.println("Distance between Rachel and Rachel: " + graph.getDistance(rachel, rachel)); // 应打印0
        System.out.println("Distance between Rachel and Kramer: " + graph.getDistance(rachel, kramer)); // 应打印-1
    }
}
