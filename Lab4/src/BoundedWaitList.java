import java.util.Collection;

public class BoundedWaitList<E> extends WaitList<E> {

    /** The max number of people.*/
    private int capacity;

    /** Create a new BoundedWaitList. */
    public BoundedWaitList(int capacity) {
        super();
        this.capacity = capacity;
    }

    /** Retrieve and return the capacity of the BoundedWaitList.
     *
     * @return
     * The capacity of the BoundedWaitList.
     */
    public int getCapacity() {
        return this.capacity;
    }


    @Override
    /** Add an element to the tail of the BoundedWaitList if not in full capacity.
     *
     * @param element
     *
     */
    public void add(E element) {
        if (this.content.size() < this.capacity) {
            this.content.add(element);
        }
    }

    @Override
    public String toString() {
        return this.content+ ", Capacity " + this.capacity;
    }
}