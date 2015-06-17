import java.util.*;

/**
 * Created by Gerben on 17-6-2015.
 */
public class NumberFinder {

    private Integer base = 2;
    private int slavenum;
    private final Map<Integer, BigNumber> baseMap = new HashMap<>();
    private final Map<Integer, BigNumber> binMap = new HashMap<>();
    private final List<Map<Integer, BigNumber>> queue = new LinkedList<>();
    private CompareRunnable[] slaves;


    public static void main(String[] args){
        NumberFinder numberFinder = new NumberFinder(4);
        numberFinder.findForBases();
    }

    public NumberFinder(int slavenum) {
        baseMap.put(2, new BigNumber(new boolean[]{}));
        this.slavenum = slavenum;
        slaves = new CompareRunnable[slavenum];
        for(int i = 0; i < slavenum; i++){
            slaves[i] = new CompareRunnable(this);
            (new Thread(slaves[i])).start();
        }
    }

    public void findForBases(){
        int lastChanged = -1;
        int smallest = -1;
        BigNumber smallestN;
        while(true){
            synchronized (baseMap) {
                for (Integer key : baseMap.keySet()) {
                    binMap.put(key, Numbergenerator.interpretedAsBase(baseMap.get(key), new BigNumber(key)));
                }
            }

            smallestN = null;
            lastChanged = smallest;
            smallest = -1;

            synchronized (binMap) {
                for (Integer key : binMap.keySet()) {
                    if (smallestN == null || smallestN.compare(binMap.get(key)) == 1) {
                        smallest = key;
                        smallestN = binMap.get(key);
                    }
                }
            }


            synchronized (baseMap) {
                baseMap.get(smallest).addAt(0);
            }
            if (lastChanged != smallest){
                synchronized (binMap) {
                    queue.add(deepClone(binMap));
                }
            }
        }

    }

    public Map<Integer, BigNumber> deepClone(Map<Integer, BigNumber> map){
        Map<Integer, BigNumber> out = new HashMap<>();
        for(Integer key: map.keySet()){
            out.put(key, new BigNumber(map.get(key).getNumber()));
        }
        return out;
    }

    public List<Map<Integer, BigNumber>> getQueue() {
        return queue;
    }

    public void found(int i, BigNumber bigNumber) {
        if(base == i) {
            base += 1;
            synchronized (baseMap) {
                baseMap.put(base, new BigNumber());

                for (Integer key : baseMap.keySet()) {
                    baseMap.get(key).addAt(0);
                }
            }
        }
        System.out.println(String.format("Found a match on base 2 to %s: %s", i, bigNumber));

    }
}
