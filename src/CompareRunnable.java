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
        Job job = null;
        Map<Integer, BigNumber> part;
        boolean equal;
        while(true) {
            synchronized (master.getQueue()) {
                if (master.getQueue().size() != 0) {
                    job = master.getQueue().get(0);
                    master.getQueue().remove(0);
                } else {
                    part = null;
                }
            }
                if (job != null){
                    part = job.getBinMap();
                    //System.out.println("Starting with: "+part );
                    equal = true;
                    for (Integer base : part.keySet()) {
                        equal = equal && (part.get(base).compare(part.get(NumberFinder.STARTING_BASE)) == 0);
                        if (!equal) {
                            break;
                        }
                    }

                    if (equal) {
                        master.found(part.size() + NumberFinder.STARTING_BASE - 1, job);
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
