package P3;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersonTest {

    // 测试创建Person对象
    @Test
    public void testCreatePerson() {
        Person person = new Person("Alice");
        assertEquals("Alice", person.getName());
    }

    // 测试equals方法
    @Test
    public void testEquals() {
        Person person1 = new Person("Alice");
        Person person2 = new Person("Alice");
        Person person3 = new Person("Bob");

        assertTrue(person1.equals(person2)); // 同名的人应该相等
        assertFalse(person1.equals(person3)); // 不同名的人不相等
        assertFalse(person1.equals(null)); // 与null比较应返回false
        assertFalse(person1.equals("Alice")); // 与非Person对象比较应返回false
    }

    // 测试hashCode方法
    @Test
    public void testHashCode() {
        Person person1 = new Person("Alice");
        Person person2 = new Person("Alice");

        assertEquals(person1.hashCode(), person2.hashCode()); // 同名的人应该有相同的hashCode
    }
}
