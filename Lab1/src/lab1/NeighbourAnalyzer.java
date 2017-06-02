package lab1;

/**
 * Created by chenke15 on 18/05/17.
 */
public class NeighbourAnalyzer {
    public static void main(String[] args) {
        String[] names = new String[] {"Rima", "Henry", "Jinsoo"};
        Integer[] nums = new Integer[] {16, 16, 0};
        String[] frosty = new String[] {"Strawberry", "Vanilla", "Strawberry Cheesecake"};

        for (int i=0; i < names.length; i++) {
            for (int j=i+1; j < names.length; j++) {
                if (nums[i].equals(nums[j])) {
                    System.out.format("%s and %s like the same number %s!\n", names[i],names[j],nums[i].toString());
                }
                else {
                    System.out.format("%s likes %s and %s likes %s!\n", names[i], nums[i].toString(), names[j], nums[j].toString());
                }
            }
        }
    }
}
