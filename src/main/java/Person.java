/**
 * Represent a person in the wealth distribution model.
 *
 * @author team 3: Quanchi Chen
 */
public class Person {
    // The horizontal coordinate of the person
    private int x;
    // The vertical coordinate of the person
    private int y;
    // The maximum age the person can reach
    private int lifeExpectancy;
    // The age of the person
    private int age;
    // The amount of grain the person eats each time tick
    private int metabolism;
    //The accumulated amount of grain the person eats
    private int wealth;
    // The number of patches ahead the person can see
    private int vision;

    public Person() {
        init();
    }

    /**
     * Initialise the instance variables.
     */
    private void init() {
        x = Util.random(Params.MAX_COORDINATE + 1);
        y = Util.random(Params.MAX_COORDINATE + 1);

        reset(0);
        wealth = metabolism + Util.random(50);
    }

    /**
     * Reset the person's attributes.
     */
    public void reset(int mode) {
        lifeExpectancy = Params.LIFE_EXPECTANCY_MIN +
                Util.random(Params.LIFE_EXPECTANCY_MAX - Params.LIFE_EXPECTANCY_MIN + 1);
        age = Util.random(lifeExpectancy);
        metabolism = 1 + Util.random(Params.METABOLISM_MAX);
        vision = 1 + Util.random(Params.MAX_VISION);

        if (mode == 0)
            // In the original model, an offspring's wealth is unrelated to the parents' and randomly generated.
            wealth = metabolism + Util.random(50);
        // In the extended model, an offspring fully inherits parent's wealth.
        // Nothing needs to be done here
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLifeExpectancy() {
        return lifeExpectancy;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMetabolism() {
        return metabolism;
    }

    public int getWealth() {
        return wealth;
    }

    public void setWealth(int wealth) {
        this.wealth = wealth;
    }

    public int getVision() {
        return vision;
    }
}
