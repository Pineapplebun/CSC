package Lab2;

/**
 * Created by chenke15 on 25/05/17.
 */
public class Representative extends Person {

    public Representative(String first, String last, String title) {
        super(first, last, title);
    }

    @Override
    public String getHonorific() {
        return "The Right Honourable";
    }

    @Override
    public String getHonorificName() {
        return this.getHonorific() + " " + this.getName();
    }
}
