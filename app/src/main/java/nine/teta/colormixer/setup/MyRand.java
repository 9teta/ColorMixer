package nine.teta.colormixer.setup;

import java.util.List;
import java.util.Random;

import nine.teta.colormixer.adapter.FavouriteColorItemModel;

public class MyRand {

    private Random random;

    public MyRand(){
        random = new Random();
    }

    public  <T> T ran(List<T> list) {
        int size = list.size();
        int position = random.nextInt(size);
        return list.get(position);
    }

    public int ran(int from, int to) {
        int position = random.nextInt(to);
        int value = position + from;
        return value;
    }

    /** random color */
    public int rancol(List<FavouriteColorItemModel> allColors){
        return ran(allColors).getColor();
    }

    public int rancolButnotthis(List<FavouriteColorItemModel> allColors, int notThis){
        for (int i = 0; i < 1000; i++) {
            int thisColor = ran(allColors).getColor();
            if (thisColor != notThis){
                return thisColor;
            }
        }
        return notThis;
    }



    public int[] ranNums(int quantity, int from, int to){
        int[] result = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            result[i] = ran(from, to);
        }
        return result;
    }
    public double[] ranNums(int quantity, int range){
        double[] result = new double[quantity];
        for (int i = 0; i < quantity; i++) {
            result[i] = random.nextDouble() * 2;
        }
        return result;
    }


}
