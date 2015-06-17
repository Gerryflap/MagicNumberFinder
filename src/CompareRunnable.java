import java.util.Map;

/**
 * Created by Gerben on 17-6-2015.
 */
public class CompareRunnable implements Runnable {

    private NumberFinder master;

    public CompareRunnable(NumberFinder master) {
        this.master = master;
    }

    @Override
    public void run() {
        Map<Integer, BigNumber> part;
        boolean equal;
        while(true) {
            synchronized (master.getQueue()) {
                if (master.getQueue().size() != 0) {
                    part = master.getQueue().get(0);
                    master.getQueue().remove(0);
                } else {
                    part = null;
                }
            }
                if (part!=null){
                    equal = true;
                    for (Integer base : part.keySet()) {
                        equal = equal && (part.get(base).compare(part.get(2)) == 0);
                        if (!equal) {
                            break;
                        }
                    }

                    if (equal) {
                        master.found(part.size() + 1, part.get(2));
                    }


                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

}
