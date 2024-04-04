package P3;

import org.junit.Test;

import static org.junit.Assert.*;

public class FriendshipGraphTest {

    // 测试添加顶点方法
    @Test
    public void testAddVertex() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("Alice");
        Person person2 = new Person("Bob");

        graph.addVertex(person1);
        // 测试未添加的人的距离是否为-1
        assertTrue(graph.getDistance(person1, person2) == -1); // Alice和Bob不相连
        // 测试重复添加顶点
        graph.addVertex(person1);
        assertTrue(graph.getDistance(person1, person2) == -1); // Alice和Bob不相连
    }

    // 测试添加边方法
    @Test
    public void testAddEdge() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("Alice");
        Person person2 = new Person("Bob");
        Person person3 = new Person("Charlie");

        graph.addVertex(person1);
        graph.addVertex(person2);
        graph.addVertex(person3);

        graph.addEdge(person1, person2);
        // 测试添加边后的距离
        assertEquals(1, graph.getDistance(person1, person2)); // Alice和Bob相连，距离为1
        assertEquals(1, graph.getDistance(person2, person1)); // Alice和Bob相连，距离为1

        // 测试重复添加边
        graph.addEdge(person1, person2);
        assertEquals(1, graph.getDistance(person1, person2)); // Alice和Bob相连，距离为1
        assertEquals(1, graph.getDistance(person2, person1)); // Alice和Bob相连，距离为1

        // 测试添加另一条边
        graph.addEdge(person2, person3);
        assertEquals(2, graph.getDistance(person1, person3)); // Alice和Charlie相连，距离为2
        assertEquals(2, graph.getDistance(person3, person1)); // Alice和Charlie相连，距离为2
    }

    // 测试获取距离方法
    @Test
    public void testGetDistance() {
        FriendshipGraph graph = new FriendshipGraph();
        Person person1 = new Person("Alice");
        Person person2 = new Person("Bob");
        Person person3 = new Person("Charlie");

        graph.addVertex(person1);
        graph.addVertex(person2);
        graph.addVertex(person3);

        // 测试未添加边的情况
        assertEquals(-1, graph.getDistance(person1, person2)); // Alice和Bob未相连，返回-1

        // 测试未添加顶点的情况
        Person person4 = new Person("David");
        assertEquals(-1, graph.getDistance(person1, person4)); // David不存在于图中，返回-1

        // 测试自己到自己的距离为0
        assertEquals(0, graph.getDistance(person1, person1)); // Alice和自己距离为0

        // 测试Alice到Charlie的距离为2
        graph.addEdge(person1, person2);
        graph.addEdge(person2, person3);
        assertEquals(2, graph.getDistance(person1, person3)); // Alice和Charlie相连，距离为2
    }
}
