/**
 * Created by Gerben on 17-6-2015.
 */
public class Test {

    public static void main(String[] args){
        BigNumber z = Numbergenerator.interpretedAsBase(
                new BigNumber(new boolean[]{false, true, true}),
                new BigNumber(new boolean[]{false, true, false, true}
                ));
        System.out.println(z);
    }
}
