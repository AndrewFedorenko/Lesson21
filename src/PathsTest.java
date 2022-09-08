import java.util.ArrayList;
import java.util.Arrays;

public class PathsTest {
    static final int xMax = 3;
    static final int yMax = 3;
    static final int zMax = 3;

    static ArrayList<Thread> threadArray = new ArrayList<>();

    public static void main(String[] args) {
        // first anonymous runnable class with simple path calculation
        Runnable runSimple = new Runnable() {
            final int [][][]array = new int[xMax][yMax][zMax];
            @Override
            public void run()
                {for (int x = 0; x < xMax; x++) {
                    for (int y = 0; y < yMax; y++) {
                        for (int z = 0; z < zMax; z++) {
                            if(x==0&&y==0||z==0&&y==0||x==0&&z==0){
                                array[x][y][z] = 1;
                            }
                            else {
                                array[x][y][z] = array[x][y][z] +
                                        (x==0||array[x][y][z]==1?0:array[x-1][y][z]) +
                                        (y==0||array[x][y][z]==1?0:array[x][y-1][z]) +
                                        (z==0||array[x][y][z]==1?0:array[x][y][z-1]);}
                        }
                    }
                };

                    printArray("Simple", array);
                }
        };
        // second anonymous runnable class with recursion path calculation
        Runnable runRecursion = new Runnable() {
            final int [][][]array = new int[xMax][yMax][zMax];
            @Override
            public void run()
                {pathsRecursion(xMax-1, yMax-1, zMax-1);
                    printArray("Recursion",array);}
            int pathsRecursion(int x, int y, int z){
                if (array[x][y][z] != 0) {
                    return array[x][y][z];
                }
                if(x==0&&y==0||z==0&&y==0||x==0&&z==0){
                    array[x][y][z] = 1;
                }
                else {
                    array[x][y][z] =
                            (x==0||array[x][y][z]==1?0:pathsRecursion(x-1, y, z)) +
                                    (y==0||array[x][y][z]==1?0:pathsRecursion(x, y-1, z)) +
                                    (z==0||array[x][y][z]==1?0:pathsRecursion(x, y, z-1));
                }
                return array[x][y][z];
            }
        };
        // add threads to start
        threadArray.add(0, new Thread(runSimple));
        threadArray.add(1, new Thread(runRecursion));
        for (Thread thread:threadArray){
            thread.start();
        };
        for (Thread thread:threadArray){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
    static synchronized void printArray(String descr, int[][][] array){
        {
        System.out.println(descr + " path");
        for (int x = 0; x < xMax; x++) {
            System.out.println(descr + " x = " + x);
            for (int y = 0; y < yMax; y++) {
                System.out.println(Arrays.toString(array[x][y]));
            }
        }
        }
    }
}

