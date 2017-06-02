package Lab2;

/**
 * Created by chenke15 on 25/05/17.
 */
public class President extends Person {

    private static int count;
    private int npres;

    public President(String first, String last, String title) {
        super(first, last, title);
        count += 1;
        this.npres = count;
    }

    @Override
    public String getHonorificName() {
        return "Mister President";
    }

    public String getJobDescription() {
        return "President of the United States #" + this.npres;
    }

}
