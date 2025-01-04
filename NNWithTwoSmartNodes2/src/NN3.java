import java.util.Iterator;
import java.util.NoSuchElementException;
import components.naturalnumber.*;
import components.list.List;
import components.list.ListSecondary;

/**
 * {@code List} represented as a singly linked list, done "bare-handed", with
 * implementations of primary methods.
 *
 * <p>
 * Execution-time performance of all methods implemented in this class is O(1).
 * </p>
 *
 * @param <T>
 *            type of {@code List} entries
 * @convention <pre>
 * $this.leftLength >= 0  and
 * [$this.rightLength >= 0] and
 * [$this.preStart is not null]  and
 * [$this.lastLeft is not null]  and
 * [$this.finish is not null]  and
 * [$this.preStart points to the first node of a singly linked list
 *  containing $this.leftLength + $this.rightLength + 1 nodes]  and
 * [$this.lastLeft points to the ($this.leftLength + 1)-th node in
 *  that singly linked list]  and
 * [$this.finish points to the last node in that singly linked list]  and
 * [$this.finish.next is null]
 * </pre>
 * @correspondence <pre>
 * this =
 *  ([data in nodes starting at $this.preStart.next and running through
 *    $this.lastLeft],
 *   [data in nodes starting at $this.lastLeft.next and running through
 *    $this.finish])
 * </pre>
 */
public class NN3 implements NaturalNumberKernel {

    /**
     * Node class for singly linked list nodes.
     */
    private final class Node {

        /**
         * Data in node.
         */
        private int data;

        /**
         * Next node in singly linked list, or null.
         */
        private Node next;
        
        /**
         * Previous node in singly linked list, or null.
         */
        private Node prev;

    }

    /**
     * "Smart node" before front node of singly linked list.
     */
    private Node preStart;

    /**
     * Finish node of linked list.
     */
    private Node postFinish;

    /**
     * Length of this.right.
     */
    private int length;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.preStart = new Node();
        this.preStart.next = this.postFinish;
        this.postFinish.prev = this.preStart;
        this.length = 0;
    }

    /**
     * No-argument constructor.
     * @param i 
     */
    public NN3(int i) {
        this.createNewRep();
    }

    @Override
    public void clear() {
        this.createNewRep();
        
    }

    @Override
    public NaturalNumber newInstance() {
        return (new NaturalNumber1L()) ;
    }

    @Override
    public void transferFrom(NaturalNumber source) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int divideBy10() {
        int num = 0;
        if(this.preStart.next != this.postFinish) {
            Node last = this.postFinish.prev;
            num = last.data;
            this.postFinish.prev = last.prev;
            last.prev.next = this.postFinish;
            this.length--;
        }
        return num;
    }

    @Override
    public boolean isZero() {
        return this.preStart == this.postFinish;
    }

    @Override
    public void multiplyBy10(int k) {
        Node n = new Node();
        n.data = k;
        if(this.preStart.next == this.postFinish && k > 0) {
            // empty NN and k > 0
            n.next = this.postFinish;
            n.prev = this.preStart;
            this.preStart.next = n;
            this.postFinish.prev = n;
            this.length++;
        } else if(this.preStart.next != this.postFinish) {
            // non empty NN
            n.next = this.postFinish;
            n.prev = this.postFinish.prev;
            this.postFinish.prev = n;
            n.prev.next = n;
            this.length++;
        }
        
    }

   

    
    

}
