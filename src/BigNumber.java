/**
 * Created by Gerben on 17-6-2015.
 */
public class BigNumber {

    private int highestI = -1;
    private boolean[] number = new boolean[Numbergenerator.MAX_SIZE];


    public BigNumber(){}

    public BigNumber(boolean[] number){
        System.arraycopy(number, 0, this.number, 0, number.length);
        for(int i = number.length - 1; i != -1; i--){
            if(number[i]){
                this.highestI = i;
                break;
            }
        }
    }

    public BigNumber(int n){
        for( int i = 0; i < n; i++){
            addAt(0);
        }
    }

    public BigNumber add(BigNumber x) {
        BigNumber y = new BigNumber(number);
        y.setHighestI(Math.max(highestI, x.getHighestI()));
        for (int i = 0; i <= x.getHighestI(); i++) {
            if (x.getNumber()[i]) {
                y.addAt(i);
            }
        }

        return y;
    }

    public BigNumber multiply(BigNumber x){
        BigNumber y = new BigNumber();
        y.setHighestI(highestI + x.getHighestI());
        for (int i = 0; i <= x.getHighestI(); i++) {
            for(int j = 0; j <= highestI; j ++){
                if(x.getNumber()[i] && number[j]) {
                    y.addAt(i + j);
                }
            }
        }

        return y;
    }

    public void addAt(int index){
        boolean carry = true;
        while(carry){
            if (number[index]){
                number[index] = false;
                index += 1;
            } else {
                carry = false;
                number[index] = true;
            }
        }

        if(highestI < index){
            highestI = index;
        }
    }

    public boolean[] getNumber() {
        return number;
    }

    public int getHighestI() {
        return highestI;
    }

    public String toString(){
        String out = "";
        for (int i = highestI; i != -1; i--){
            out += number[i]?"1":"0";
        }
        return out;
    }

    public void setHighestI(int highestI) {
        this.highestI = highestI;
    }

    public int compare(BigNumber x){
        if(highestI != x.getHighestI()){
            return (highestI - x.getHighestI())/Math.abs(highestI - x.getHighestI());
        } else {
            for(int i = highestI; i > -1; i--){
                if(number[i] ^ x.getNumber()[i]){
                    return number[i]?1:-1;
                }
            }
            return 0;
        }
    }
}
