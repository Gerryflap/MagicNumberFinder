import java.util.Map;

/**
 * Created by Gerben on 18-6-2015.
 */
public class Job {

    private Map<Integer, BigNumber> baseMap;
    private Map<Integer, BigNumber> binMap;

    public Job(Map<Integer, BigNumber> baseMap, Map<Integer, BigNumber> binMap) {
        this.baseMap = baseMap;
        this.binMap = binMap;
    }

    public Map<Integer, BigNumber> getBaseMap() {
        return baseMap;
    }

    public Map<Integer, BigNumber> getBinMap() {
        return binMap;
    }
}

