package Lab2;

/**
 * Created by chenke15 on 25/05/17.
 */
public class Judge extends Person{
    public Judge(String first, String last, String title) {
        super(first, last, title);
    }

    @Override
    public String getHonorific() {
        return "The Honourable";
    }
    @Override
    public String getHonorificName() {
        return this.getHonorific() + " " + this.getName();
    }
}
