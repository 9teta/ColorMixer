package nine.teta.colormixer.setup;

import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public class LightningRays {

    private final int width;
    private final int height;
    private MyRand mr;

    public LightningRays(int width, int height){
        this.width = width;
        this.height = height;
        mr = new MyRand();
    }

    public List<Path> getRandRaysPaths(int x, int y){
        List<List<int[]>> randRays = getRandRays(x, y);
        List<Path> paths = new ArrayList<>();
        for (List<int[]> pathInList: randRays){
            Path path = new Path();
            for (int i = 0; i < pathInList.size(); i++) {
                if (i==0){
                    int[] point = pathInList.get(0);
                    path.moveTo(point[0], point[1]);
                }
                int[] point = pathInList.get(i);
                path.lineTo(point[0], point[1]);
            }
            paths.add(path);
        }
        return paths;
    }


    // 0, 0 ------ 100, 0
    // 100, 0 ------ 100, 100
    public List<List<int[]>> getRandRays(int x, int y){
        int numOfRays = mr.ran(2, 10);
        double angles[] = mr.ranNums(numOfRays, 2);
        List<List<int[]>> listOfRayPaths = new ArrayList<>();
        for (double angle : angles) {
            listOfRayPaths.add(singleRay(x, y, angle));
        }
        return listOfRayPaths;
    }


    private List<int[]> singleRay(int x, int y, double angle){
        int[] originPoint = new int[]{x,y};
        List<int[]> path = new ArrayList<>();
        path.add(originPoint);

        float[] pair = cosSinPair(angle);
        boolean isFinished = false;
        int lineTrigger = 1;
        while(!isFinished){
            isFinished = getNextPoint(path, pair, lineTrigger);
            lineTrigger = lineTrigger * -1;
        }
        return path;

    }

    private boolean getNextPoint(List<int[]> path, float[] pair, int lineTrigger){
        if (lineTrigger == 1) {
            int lineLength = mr.ran(50, 250);
            int[] startPoint = path.get(path.size() - 1);
            return straightLine(path, startPoint, pair, lineLength);
        } else if (lineTrigger == -1){
            int sideLength = mr.ran(10, 50);
            int[] startPoint = path.get(path.size() - 1);
            return sideLine(path, startPoint, pair, sideLength);
        }
        return true;
    }


    private boolean straightLine(List<int[]> path, int[] startPoint, float[] pair, int lineLength){
        int[] endPoint = getEndPoint(startPoint, pair, lineLength);
        if (isLineGoOffscreen(endPoint)){
            int[] newEndPoint = new int[2];
            String inter = whereLineGoOffscreen(endPoint);
            if ("x0".equals(inter)){
                int endY = getEndY(startPoint, pair, 0);
                newEndPoint[0] = 0;
                newEndPoint[1] = endY;
            } else if ("xm".equals(inter)){
                int endY = getEndY(startPoint, pair, width);
                newEndPoint[0] = width;
                newEndPoint[1] = endY;
            } else if ("y0".equals(inter)){
                int endX = getEndX(startPoint, pair, 0);
                newEndPoint[0] = endX;
                newEndPoint[1] = 0;
            } else if ("ym".equals(inter)){
                int endX = getEndX(startPoint, pair, height);
                newEndPoint[0] = endX;
                newEndPoint[1] = height;
            }
            path.add(newEndPoint);
            return true;
        }
        path.add(endPoint);
        return false;
    }

    private boolean sideLine(List<int[]> path, int[] startPoint, float[] pair, int sideLength){
        double newAngle = mr.ran(0, 2);
        float[] newPair = cosSinPair(newAngle);
        return straightLine(path, startPoint, newPair, sideLength);
    }

    private boolean isLineGoOffscreen(int[] endPoint){
        if (endPoint[0] < 0 || endPoint[0] > width) return true;
        if (endPoint[1] < 0 || endPoint[1] > height) return true;
        return false;
    }

    private String whereLineGoOffscreen(int[] endPoint){
        if (endPoint[0] < 0) return "x0";
        else if (endPoint[0] > width) return "xm";
        else if (endPoint[1] < 0) return "y0";
        else if (endPoint[1] > height) return "ym";
        else return "xm";
    }


    private int[] getEndPoint(int[] startPoint, float[] pair, int length){
        // -> = 0 = 2
        // ^ = 0.5
        // <- = 1
        // v = 1.5
        int nx = Math.round(pair[0] * length) + startPoint[0];
        int ny = Math.round(pair[1] * length) + startPoint[1];
        return  new int[]{nx,ny};
    }

    private int getEndX(int[] startPoint, float cosSinPair[], int endY){
        int length = Math.round( (endY - startPoint[1]) / cosSinPair[1] );
        int nx = Math.round(cosSinPair[0] * length) + startPoint[0];
        nx = nx < 0 ? 0 : nx;
        nx = nx > width ? width : nx;
        return nx;
    }

    private int getEndY(int[] startPoint, float cosSinPair[], int endX){
        int length = Math.round( (endX - startPoint[0]) / cosSinPair[0] );
        int ny = Math.round(cosSinPair[1] * length) + startPoint[1];
        ny = ny < 0 ? 0 : ny;
        ny = ny > height ? height : ny;
        return ny;
    }

    private float[] cosSinPair(double radian){
        float[] pair = new float[2];
        pair[0] = (float) Math.cos(Math.PI * radian);
        pair[1] = (float) Math.sin(Math.PI * radian);
        return pair;
    }

}
