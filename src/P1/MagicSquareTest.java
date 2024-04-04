package P1;

import P1.MagicSquare;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MagicSquareTest {

    // 测试 parseLine 方法，解析一行字符串并将其中的整数添加到列表中
    @Test
    public void testParseLine() {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        // 验证 parseLine 方法是否正确解析了字符串
        assertEquals(expected, MagicSquare.parseLine("1\t2", list));
        assertEquals(null, MagicSquare.parseLine("1\t2.5", list));
        assertEquals(null, MagicSquare.parseLine("1 2", list));
    }

    // 测试 checkMatrixDimensions 方法，检查矩阵的行数和列数是否相等，即是否为方阵
    @Test
    public void testCheckMatrixDimensions() {
        // 测试行列数相等的情况
        assertTrue(MagicSquare.checkMatrixDimensions(3, 3));
        // 测试行列数不相等的情况
        assertFalse(MagicSquare.checkMatrixDimensions(2, 4));
    }

    // 测试 checkMatrixValues 方法，检查矩阵中是否包含负数
    @Test
    public void testCheckMatrixValues() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        // 测试矩阵中不包含负数的情况
        assertTrue(MagicSquare.checkMatrixValues(list));
        // 测试矩阵中包含负数的情况
        list.add(-1);
        assertFalse(MagicSquare.checkMatrixValues(list));
    }

    // 测试 calculateMagicSum 方法，计算每行、每列、对角线的和
    @Test
    public void testCalculateMagicSum() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        // 测试计算和的正确性
        assertEquals(3, MagicSquare.calculateMagicSum(list, 2));
        list.add(3);
        assertEquals(6, MagicSquare.calculateMagicSum(list, 3));
    }

    // 测试 checkSums 方法，检查行、列和对角线的和是否相等
    @Test
    public void testCheckSums() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(7);
        list.add(6);
        list.add(9);
        list.add(5);
        list.add(1);
        list.add(4);
        list.add(3);
        list.add(8);
        // 测试和相等的情况
        assertTrue(MagicSquare.checkSums(list, 3, 15));
        // 测试和不相等的情况
        list.set(5, 5);
        assertFalse(MagicSquare.checkSums(list, 3, 15));
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        list.add(10);
        assertFalse(MagicSquare.checkSums(list, 4, 15));
    }
    // 测试 generateMagicSquare 方法，生成一个幻方并将其写入文件
    @Test
    public void testGenerateMagicSquare() {
        // 测试生成幻方的情况
        assertTrue(MagicSquare.generateMagicSquare(3));
        assertFalse(MagicSquare.generateMagicSquare(4));
        assertFalse(MagicSquare.generateMagicSquare(-1));
        assertFalse(MagicSquare.generateMagicSquare(-2));
    }
}