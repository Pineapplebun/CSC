package Lab2;

/**
 * Created by chenke15 on 25/05/17.
 */
public class Demo {

    public static void main(String[] args) {
        Person p1 = new Person("Kevin", "Chen", "Mr.");
        Representative r1 = new Representative("Kevin", "Chen", "Mr.");
        Judge j1 = new Judge("Kevin", "Chen", "Mr.");
        President z1 = new President("Kevin", "Chen", "Mr.");
        System.out.println(p1.getHonorificName());
        System.out.println(r1.getHonorificName());
        System.out.println(j1.getHonorificName());
        System.out.println(z1.getHonorificName());


        President z2 = new President("Rima", "Elsayed", "Ms.");
        System.out.println(z2.getJobDescription());
        System.out.println(z1.getJobDescription());
    }
}
