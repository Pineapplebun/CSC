package Lab2;

/**
 * Created by chenke15 on 25/05/17.
 */
public class Person {

    private String first;
    private String last;
    private String title;

    public Person(String first, String last, String title){
        this.first = first;
        this.last = last;
        this.title = title;
    }
    public String getHonorific() {
        return this.title;
    }
    public String getName() {
        return this.first + " " + this.last;
    }
    public String getHonorificName() {
        return this.getHonorific() + " " + this.getName();
    }
}
