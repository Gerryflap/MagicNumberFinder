/**
 * Created by Gerben on 17-6-2015.
 */
public class SpeedTest {

    public static void main(String[] args){
        BigNumber x;
        BigNumber y;
        long time;
        long dt;
        while(true){

            x = Numbergenerator.randomBinary(Numbergenerator.MAX_SIZE/2 - 1);
            y = Numbergenerator.randomBinary(Numbergenerator.MAX_SIZE/2 - 1);

            System.out.println("Numbers generated");
            time = System.nanoTime();

            x.multiply(y);
            dt = System.nanoTime() - time;
            System.out.println(dt);

        }

    }
}
