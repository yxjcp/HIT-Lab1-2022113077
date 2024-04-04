/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.*;

import static java.util.Collections.swap;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        if(sideLength<=0) {
            throw new RuntimeException("请输入大于0的sideLength");
        }else{
            for(int i = 0; i < 4; i++) {
                turtle.forward(sideLength);
                turtle.turn(90);
            }
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        if(sides <=2){
            throw new RuntimeException("多边形边数不可能为小于2的数");
        }
        double sumAngle = (sides - 2)*180.0;
        double Angle = sumAngle/sides;
        return Angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */


    public static int calculatePolygonSidesFromAngle(double angle) {
        if(angle >=180){
            throw new RuntimeException("输入角度违规");
        }
        int side = (int)Math.round((360.0/(180.0-angle)));
        return side;

    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {

        for(int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        if(currentBearing>=0&&currentBearing<360) {// 计算当前位置到目标位置的方向向量
            // 计算当前位置到目标位置的方向向量
            double dx = targetX - currentX;
            double dy = targetY - currentY;

            // 计算目标方向与y轴正向的夹角（以顺时针为正方向）
            double targetBearing = Math.toDegrees(Math.atan2(dx, dy));
            if (targetBearing < 0) {
                targetBearing += 360; // 将角度转换为[0, 360)范围内
            }

            // 计算需要旋转的角度
            double rotationAngle = targetBearing - currentBearing;

            // 确保旋转角度在[0, 360)范围内
            rotationAngle %= 360;//a % b = a - (a / b) * b
            if (rotationAngle < 0) {
                rotationAngle += 360;
            }

            return rotationAngle;
        }

        else {
            throw new RuntimeException("初始角度错误");
        }
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        if (xCoords.size() != yCoords.size()) {
            throw new RuntimeException("implement me!");
        } else {
            List<Double> bearings = new ArrayList<>();
            int currentX = xCoords.get(0);
            int currentY = yCoords.get(0);
            double bearing = 0.0;
            for (int i = 1; i < xCoords.size(); i++) {
                bearing = calculateBearingToPoint(bearing, xCoords.get(i-1), yCoords.get(i-1), xCoords.get(i), yCoords.get(i));
                currentX = xCoords.get(i);
                currentY = yCoords.get(i);
                bearings.add(bearing);
            }
            return  bearings;

        }
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.

     // @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    private static double dist(Point A, Point B) {
        double deltaX = B.x() - A.x();
        double deltaY = B.y() - A.y();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    private static double cross(Point O, Point A, Point B) {
        return (A.x() - O.x()) * (B.y() - O.y()) - (A.y() - O.y()) * (B.x() - O.x());
    }
    public static Set<Point> convexHull(Set<Point> points) {
        // 如果点的数量小于等于3，直接返回原始点集合，因为它们已经构成了凸包
        if(points.size() <= 3) return points;
        // 将点集合转换为列表以便操作
        ArrayList<Point> pointList = new ArrayList<Point>();
        int index = -1;
        // 找到具有最低 y 坐标的点（如果存在相同 y 坐标，则按最低 x 坐标来决定）
        for(Point p: points) {
            pointList.add(p);
            // 找到初始最低点的索引
            if(index == -1) index = 0;
            else {
                Point miny = pointList.get(index);
                // 检查是否有更低的点
                if (p.y() < miny.y() || (p.y() == miny.y() && p.x() < miny.x())) {
                    index = pointList.size() - 1;
                }
            }
        }
        // 获取基准点
        Point base = pointList.get(index);
        // 根据基准点对点进行排序，顺时针方向
        pointList.sort(new Comparator<Point>() {
            @Override
            public int compare(Point A, Point B) {
                if(A.x() == base.x() && A.y() == base.y()) return -1;
                if(B.x() == base.x() && B.y() == base.y()) return 1;

                // 计算叉乘
                double crossVal = cross(base, A, B);
                if(crossVal == 0) {
                    // 如果叉乘结果为0，则按距离基准点的距离排序
                    if(dist(base, A) < dist(base, B)) return -1;
                    return 1;
                }
                if(crossVal > 0) return -1; // 逆时针方向
                return 1; // 顺时针方向
            }
        });
        // 找到第一个不共线的点
        int ptr = pointList.size() - 1;
        while(ptr >= 0 && cross(base, pointList.get(pointList.size() - 1), pointList.get(ptr)) == 0) ptr--;
        // 将共线的部分反转
        for(int p = ptr + 1, q = pointList.size() - 1; p < q; p++, q--) {
            swap(pointList, p, q);
        }
        // 删除多余的点，保留前三个点
        while(pointList.size() > ptr + 2) pointList.remove(pointList.size() - 1);

        // 使用栈来构建凸包
        Stack<Point> S = new Stack<Point>();

        S.push(pointList.get(0));
        S.push(pointList.get(1));

        // 遍历剩余的点，添加到栈中构建凸包
        for(int i = 2; i < pointList.size(); i++) {
            while(S.size() >= 2) {
                Point top = S.pop();
                Point nextTop = S.peek();
                // 如果当前点在构建的凸包的顺时针方向上，则将栈顶元素弹出，直到当前点位于栈顶和次栈顶之间
                if(cross(nextTop, top, pointList.get(i)) > 0) {
                    S.push(top);
                    break;
                }
            }
            S.push(pointList.get(i));
        }
        // 将栈中的点转换为集合并返回
        return new HashSet<Point>(S);
    }
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        drawFractalPattern(turtle, 5, 200);
    }
    public static void drawFractalPattern(Turtle turtle, int levels, double length) {
        if (levels == 0) {
            return;
        }

        // 绘制当前层级的图案
        for (int i = 0; i < 6; i++) {
            drawBranch(turtle, length);
            turtle.turn(60);
        }

        // 递归绘制下一层级的图案
        for (int i = 0; i < 6; i++) {
            drawFractalPattern(turtle, levels - 1, length * 0.5);
            turtle.turn(60);
        }
    }
    public static void drawBranch(Turtle turtle, double length) {
        if (length < 10) {
            return;
        }

        turtle.forward((int) length);
        turtle.turn(30);
        drawBranch(turtle, length * 0.7);
        turtle.turn(-60);
        drawBranch(turtle, length * 0.7);
        turtle.turn(30);
        turtle.forward((int) -length);
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawSquare(turtle, 40);
        // draw the window
        turtle.draw();
        drawPersonalArt(turtle);
    }

}
