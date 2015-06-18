/**
 * Created by Gerben on 18-6-2015.
 */
public class Printer implements Runnable {

    private NumberFinder master;

    public Printer(NumberFinder master) {
        this.master = master;
    }

    @Override
    public void run() {
        while(true){
            System.out.println("Currently at "+master.smallestN);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
