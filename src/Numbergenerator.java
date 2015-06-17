import java.util.Random;

/**
 * Created by Gerben on 17-6-2015.
 */
public class Numbergenerator {
    public static final int MAX_SIZE = 10000;
    boolean[] bin = new boolean[MAX_SIZE];


    public static BigNumber randomBinary(int size){
        size = Math.min(size, MAX_SIZE);
        boolean[] number = new boolean[size];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++){
            number[i] = random.nextBoolean();
        }
        return new BigNumber(number);
    }

    public static BigNumber interpretedAsBase(BigNumber n, BigNumber base){
        BigNumber out = new BigNumber();
        BigNumber current = new BigNumber(new boolean[]{true});
        for(int i = 0; i <= n.getHighestI(); i++){
            if(n.getNumber()[i]){
                out = out.add(current);
            }
            current = current.multiply(base);
        }
        return out;
    }

}
