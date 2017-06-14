import java.util.Collection;

public interface IWaitList<E> {
    /** Adds an element to a class that implements WaitList.
     *
     * @param element
     * The element that is added.
     */
    public void add(E element);

    /** Removes and returns an element to a class that implements IWaitList.
     *
     * @return
     * The removed element.
     */
    public E remove();

    /** Returns true iff the IWaitList contains the element.
     *
     * @param element
     * The element that may or may not be in the IWaitList class.
     *
     * @return
     * true if contains element.
     */
    public boolean contains(E element);

    /** Returns true if the IWaitList contains all the elements inside of this Collection.
     *
     *
     * @param c
     * The Collection of elements.
     * @return
     * true if all elements in the Collection is contained in the IWaitList.
     */
    public boolean containsAll(Collection<E> c);

    /** Returns true if the IWaitList is empty.
     *
     * @return
     * true if the IWaitList is empty.
     */
    public boolean isEmpty();
}