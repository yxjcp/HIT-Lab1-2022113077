package P1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
public class MagicSquare {
    public static void main(String[] args) {
        //System.out.println("Current working directory: " + System.getProperty("user.dir"));
        String[] fileNames = {"src/P1/txt/1.txt", "src/P1/txt/2.txt", "src/P1/txt/3.txt", "src/P1/txt/4.txt", "src/P1/txt/5.txt"};
        // 遍历文件名数组，调用isLegalMagicSquare()函数
        for (String fileName : fileNames) {
            boolean Legal = isLegalMagicSquare(fileName);
            System.out.println(Legal);
        }
        String file6 = "src/P1/txt/6.txt";
        int n = 3;
        boolean ifGenerate = generateMagicSquare(n);
        if(ifGenerate) {
            boolean isLegalMagicSquare6 = isLegalMagicSquare(file6);
            System.out.println(isLegalMagicSquare6);
        }
    }
    public static boolean isLegalMagicSquare(String fileName) {
        try {
            // 创建文件读取对象
            BufferedReader myReader = new BufferedReader(new FileReader(fileName));
            ArrayList<Integer> list = new ArrayList<>();
            String myLine;
            //int[][] matrix = null;
            //int length = -1;
            int numRows = 0;
            int sum = 0;
            int numCols = 0;
            // 逐行读取文件内容并解析为整数列表
            while ((myLine = myReader.readLine()) != null) {
                ArrayList<Integer> row = parseLine(myLine,list);
                if (row == null) {
                    return false;
                }
                numRows++; // 每读取一行，增加行数
                if (numRows == 1) {
                    numCols = list.size(); // 第一行时，设置列数为当前列表的长度
                }else{
                    if (numCols != row.size()) {
                        System.err.println("错误: 不是方阵（每行个数未全部相等）");
                        myReader.close();
                        return false;
                    }
                }
            }
            myReader.close(); // 关闭文件读取对象

            // 检查矩阵规格是否为方阵
            if (!checkMatrixDimensions(numRows, numCols)) return false;

            // 检查矩阵中是否包含负数
            if (!checkMatrixValues(list)) return false;

            // 计算每行、每列、对角线的和
            sum = calculateMagicSum(list, numCols);

            // 检查行、列和对角线的和是否相等
            return checkSums(list, numCols, sum);

        } catch (IOException e) {
            // 处理文件读取过程中的异常
            System.err.println("错误: 文件读取失败");
            e.printStackTrace();
            return false;
        }
    }

    // 解析一行字符串并将其中的整数添加到列表中
    public static ArrayList<Integer> parseLine(String line, ArrayList<Integer> list) {
        String[] values = line.split("\t");
        ArrayList<Integer> row = new ArrayList<>();
        for (String value : values) {
            try {
                int integerValue = Integer.parseInt(value);
                list.add(integerValue);
                row.add(integerValue);
            }catch(NumberFormatException e){
                System.err.println("错误: 矩阵中包含非整型数字");
                return null;
            }
        }
        return row;
    }
    // 检查矩阵的行数和列数是否相等，即是否为方阵
    public static boolean checkMatrixDimensions(int numRows, int numCols) {
        if (numRows != numCols) {
            System.err.println("错误: 不是方阵（行列数不相等）");
            return false;
        }
        return true;
    }

    // 检查矩阵中是否包含负数
    public static boolean checkMatrixValues(ArrayList<Integer> list) {
        for (int value : list) {
            if (value <= 0) {
                System.err.println("错误: 矩阵中包含负数");
                return false;
            }
        }
        return true;
    }

    // 计算每行、每列、对角线的和
    public static int calculateMagicSum(ArrayList<Integer> list, int numCols) {
        int sum = 0;
        for (int i = 0; i < numCols; i++) {
            sum += list.get(i);
        }
        return sum;
    }

    // 检查行、列和对角线的和是否相等
    public static boolean checkSums(ArrayList<Integer> list, int numCols, int sum) {
        // 检查行、列的和是否为常数和
        for (int j = 0; j < numCols; j++) {
            int colSum = 0;
            int rowSum = 0;
            for (int i = 0; i < numCols; i++) {
                rowSum += list.get(i + numCols * j);
                colSum += list.get(i * numCols + j);
            }
            if (colSum != sum || rowSum != sum) {
                return false;
            }
        }

        // 检查主对角线的和是否为常数和
        int diagonalSum = 0;
        for (int i = 0; i < numCols; i++) {
            diagonalSum += list.get(i * numCols + i);
        }
        if (diagonalSum != sum) {
            return false;
        }

        // 检查副对角线的和是否为常数和
        int reverseDiagonalSum = 0;
        for (int i = 0; i < numCols; i++) {
            reverseDiagonalSum += list.get(i * numCols + (numCols - i - 1));
        }
        return reverseDiagonalSum == sum;
    }
    public static boolean generateMagicSquare(int n) {
        if (n % 2 == 0 || n <= 0) { // 检查n是否为偶数或负数
            System.out.println("输入的n不合法，请输入一个大于0的奇数。");
            return false; // 返回false表示执行失败，优雅退出
        }
        int magic[][] = new int[n][n];//创建一个n*n的矩阵并初始化
        int row = 0, col = n / 2, i, j, square = n * n;//初始化条件，row为0，col为n/2

        for (i = 1; i <= square; i++) {//开始循环
            magic[row][col] = i;//将i填入当前位置
            if (i % n == 0)//若i能被n整除
                row++;//行号+1
            else {
                if (row == 0)//若在第0行
                    row = n - 1;//移动到最后一行
                else
                    row--;//行号-1
                if (col == (n - 1))//在最后一列
                    col = 0;//移动到第一列
                else col++;//列号+1
            }
        }
        //循环打印出幻方
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++)
                System.out.print(magic[i][j] + "\t");
            System.out.println();
        }
        String filePath = "src/P1/txt/6.txt";
        try {
            FileWriter writer = new FileWriter(filePath);
            for (i = 0; i < magic.length; i++) {
                for (j = 0; j < magic[i].length; j++) {
                    writer.write(magic[i][j] + "\t");
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("幻方已成功写入文件：" + filePath);
        } catch (IOException e) {
            System.out.println("写入文件时出现错误：" + e.getMessage());
        }
        return true;
    }
}

