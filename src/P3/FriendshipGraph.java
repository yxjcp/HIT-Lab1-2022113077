package P3;

import java.util.*;

/**
 * 表示社交网络中的人际关系。
 */
public class FriendshipGraph {
    private Map<Person, Set<Person>> graph;

    public FriendshipGraph() {
        graph = new HashMap<>();
    }

    /**
     * 将一个顶点（人）添加到图中。
     */
    public void addVertex(Person person) {
        graph.putIfAbsent(person, new HashSet<>());
    }

    /**
     * 在两个人之间添加一条边（友谊关系）。
     */
    public void addEdge(Person person1, Person person2) {
        graph.get(person1).add(person2);
        graph.get(person2).add(person1);
    }

    /**
     * 使用广度优先搜索计算图中两个人之间的距离。
     * 如果两个人未连接，则返回-1。
     */
    public int getDistance(Person source, Person destination) {
        if (!graph.containsKey(source) || !graph.containsKey(destination)) {
            return -1;
        }

        Queue<Person> queue = new LinkedList<>();
        Set<Person> visited = new HashSet<>();
        Map<Person, Integer> distance = new HashMap<>();

        queue.add(source);
        visited.add(source);
        distance.put(source, 0);

        while (!queue.isEmpty()) {
            Person current = queue.poll();

            if (current.equals(destination)) {
                return distance.get(current);
            }

            for (Person neighbor : graph.get(current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    distance.put(neighbor, distance.get(current) + 1);
                }
            }
        }

        return -1; // 从源到目标不可达
    }
}
