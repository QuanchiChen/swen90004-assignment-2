/**
 * Represent a person in the wealth distribution model.
 *
 * @author team 3
 */
public class Person {
    // The class of the person
    private Class pClass;
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
        lifeExpectancy = Params.LIFE_EXPECTANCY_MIN + Util.random(Params.METABOLISM_MAX - Params.LIFE_EXPECTANCY_MIN + 1);
        age = Util.random(lifeExpectancy);
        metabolism = 1 + Util.random(Params.METABOLISM_MAX);
        wealth = metabolism + Util.random(50);
        vision = 1 + Util.random(Params.MAX_VISION);
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

    public enum Class {
        LOW, MID, UP
    }
}
