/**
 * Represent a patch in a NetLogo model.
 *
 * @author team 3: Quanchi Chen, Yijie Xie
 */
public class Patch {
    // The current amount of grain on this patch
    private int grain;
    // The maximum amount of grain this patch can hold
    private int maxGrain;

    public Patch() {
        init();
    }

    /**
     * Initialise a patch according to the setup-patches procedure.
     */
    private void init() {
        if (Util.randomFloat(100.0f) <= (float) Params.PERCENT_BEST_LAND) {
            maxGrain = Params.MAX_GRAIN;
            grain = maxGrain;
        } else {
            maxGrain = 0;
            grain = 0;
        }
    }

    public int getGrain() {
        return grain;
    }

    public void setGrain(int grain) {
        this.grain = grain;
    }

    public int getMaxGrain() {
        return maxGrain;
    }

    public void setMaxGrain(int maxGrain) {
        this.maxGrain = maxGrain;
    }
}
