/**
 * Created by kevin on 2017-06-14.
 */
public class UnfairWaitList<E> extends  WaitList<E>{

    /** Create an UnfairWaitList.
     *
     */
    public UnfairWaitList() {
        super();
    }

    /** Remove the specified element in the UnfairWaitList.
     *
     * @param element
     * The element removed from UnfairWaitList.
     */
    public void remove(E element) {
        this.content.remove();
    }

    /** Move a specified element to the end of the UnfairWaitList.
     *
     * @param element
     * The element moved to the end of the UnfairWaitList.
     */
    public void moveToBack(E element) {
        if (this.content.contains(element)) {
            this.content.remove(element);
            this.content.add(element);
        }
    }


}
