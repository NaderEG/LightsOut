// Author: Gabriel Granata, Nader El-Ghotmi
// Student number: 300057462, 300051343
// Course: ITI 1121
// Assignment: 3
// Question: 2
/**
 * The interface <b>SolutionQueue</b> is a specialized
 * Queue which handles instances of Solution
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public interface Queue<E> {

	/**
     * Returns true if the Queue is currently empty
     * @return
     *		true if the queue is empty
     */
    boolean isEmpty();


	/**
     * Add the reference to Solution at the rear of
     * the queue. Assumes s is not null
     * @param s
     *		The (non null) reference to the new element
     */
    void enqueue(E s);

	/**
     * Removes the reference to Solution at the front of
     * the queue. Assumes the queue is not empty
     * @return
     *		The reference to removed Solution
     */
    E dequeue();
}
