import java.util.*;

/**
 * Created by Gerben on 17-6-2015.
 */
public class NumberFinder {

    public static final int STARTING_BASE = 3;

    private Integer base = STARTING_BASE;
    private int slavenum;
    private final Map<Integer, BigNumber> baseMap = new HashMap<>();
    private final Map<Integer, BigNumber> binMap = new HashMap<>();
    private final List<Job> queue = new LinkedList<>();
    private CompareRunnable[] slaves;
    public BigNumber smallestN;


    public static void main(String[] args){
        NumberFinder numberFinder = new NumberFinder(4);
        numberFinder.findForBasesSimple();
    }

    public NumberFinder(int slavenum) {
        baseMap.put(base, new BigNumber(new boolean[]{}));
        this.slavenum = slavenum;

    }


    public void findForBasesSimple(){
        new Thread(new Printer(this)).start();
        int lastChanged = -1;
        int smallest = -1;
        boolean equal;
        boolean base5Equal;
        while(true){
            synchronized (baseMap) {
                for (Integer key : baseMap.keySet()) {
                    binMap.put(key, Numbergenerator.interpretedAsBase(baseMap.get(key), new BigNumber(key)));
                }
            }

            smallestN = null;
            lastChanged = smallest;
            smallest = -1;
            equal = true;

            synchronized (binMap) {
                for (Integer key : binMap.keySet()) {
                    if(smallestN == null){
                        smallest = key;
                        smallestN = binMap.get(key);
                    } else {
                        byte compare = (byte) smallestN.compare(binMap.get(key));
                        if (compare == 1) {
                            smallest = key;
                            smallestN = binMap.get(key);
                            equal = false;

                        } else if(compare == -1){
                            equal = false;
                        }
                    }
                }
            }

            if(equal){
                System.out.println(String.format("Found a match on base 2 to %s: %s", base, binMap.get(STARTING_BASE)));
                base += 1;
                baseMap.put(base, new BigNumber());
                for (Integer key : baseMap.keySet()) {
                    baseMap.get(key).addAt(0);
                }
            }




            synchronized (baseMap) {
                baseMap.get(smallest).addAt(0);
            }
        }
    }

    public void findForBases(){
        slaves = new CompareRunnable[slavenum];
        for(int i = 0; i < slavenum; i++){
            slaves[i] = new CompareRunnable(this);
            (new Thread(slaves[i])).start();
        }
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
                    System.out.println(smallestN);
                    queue.add(new Job(deepClone(baseMap), deepClone(binMap)));
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

    public List<Job> getQueue() {
        return queue;
    }

    public void found(int i, Job job) {
        if(base == i) {
            base += 1;
            synchronized (baseMap) {
                for(Integer key: job.getBaseMap().keySet()){

                    baseMap.put(key, job.getBaseMap().get(key));
                    if(key == i) {
                        baseMap.get(key).addAt(0);
                    }
                }

                baseMap.put(base, new BigNumber());

                for (Integer key : baseMap.keySet()) {
                    baseMap.get(key).addAt(0);
                }
            }
        }
        System.out.println(String.format("Found a match on base 2 to %s: %s", i, job.getBinMap().get(STARTING_BASE)));
    }
}
