/**
 * Represent a patch in a NetLogo model.
 *
 * @author team 3
 */
public class Patch {
    // The current amount of grain on this patch
    private int grain;
    // The maximum amount of grain this patch can hold
    private int maxGrain;

    public int getGrain() {
        return grain;
    }

    public void setGrain(int grain) {
        this.grain = grain;
    }
}
