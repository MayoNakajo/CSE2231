import java.util.Comparator;

import components.queue.Queue;
import components.queue.Queue1L;

/**
 * Layered implementations of secondary method {@code sort} for
 * {@code Queue<String>}.
 *
 * @param <T>
 *            type of {@code Queue} entries
 * @mathdefinitions <pre>
 * IS_TOTAL_PREORDER (
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y, z: T
 *   ((r(x, y) or r(y, x))  and
 *    (if (r(x, y) and r(y, z)) then r(x, z)))
 *
 * IS_SORTED (
 *   s: string of T,
 *   r: binary relation on T
 *  ) : boolean is
 *  for all x, y: T where (<x, y> is substring of s) (r(x, y))
 * </pre>
 */
public final class Queue1LSort4<T> extends Queue1L<T> {

    /**
     * No-argument constructor.
     */
    public Queue1LSort4() {
        super();
    }

    /**
     * Partitions {@code q} into two parts: entries no larger than
     * {@code partitioner} are put in {@code front}, and the rest are put in
     * {@code back}.
     *
     * @param <T>
     *            type of {@code Queue} entries
     * @param q
     *            the {@code Queue} to be partitioned
     * @param partitioner
     *            the partitioning value
     * @param front
     *            upon return, the entries no larger than {@code partitioner}
     * @param back
     *            upon return, the entries larger than {@code partitioner}
     * @param order
     *            ordering by which to separate entries
     * @clears q
     * @replaces front, back
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * perms(#q, front * back)  and
     * for all x: T where (<x> is substring of front)
     *  ([relation computed by order.compare method](x, partitioner))  and
     * for all x: T where (<x> is substring of back)
     *  (not [relation computed by order.compare method](x, partitioner))
     * </pre>
     */
    private static <T> void partition(Queue<T> q, T partitioner, Queue<T> front,
            Queue<T> back, Comparator<T> order) {
        assert q != null : "Violation of: q is not null";
        assert partitioner != null : "Violation of: partitioner is not null";
        assert front != null : "Violation of: front is not null";
        assert back != null : "Violation of: back is not null";
        assert order != null : "Violation of: order is not null";

        while (q.length() > 0) {
            T deq = q.dequeue();
            if (order.compare(partitioner, deq) >= 0) {
                front.enqueue(deq);
            } else {
                back.enqueue(deq);
            }
        }

//        Queue<T> tempF = q.newInstance();
//        Queue<T> tempB = q.newInstance();
//        if (q.length() > 1) {
//            T deq = q.dequeue();
//            if (order.compare(partitioner, deq) <= 0) {
//                front.enqueue(deq);
//            } else {
//                back.enqueue(deq);
//            }
//            if (front.length() > 1) {
//                partition(front, front.front(), tempF, tempB, order);
//            }
//            if (back.length() > 1) {
//                partition(back, back.front(), tempF, tempB, order);
//            }
//
//        }

    }

    @Override
    public void sort(Comparator<T> order) {
        assert order != null : "Violation of: order is not null";
        Queue<T> front = this.newInstance();
        Queue<T> back = this.newInstance();
        if (this.length() > 1) {

            /*
             * Dequeue the partitioning entry from this
             */T deq = this.dequeue();

            /*
             * Partition this into two queues as discussed above (you will need
             * to declare and initialize two new queues)
             */

            partition(this, deq, front, back, order);
            /*
             * Recursively sort the two queues
             */
            front.sort(order);
            back.sort(order);

            /*
             * Reconstruct this by combining the two sorted queues and the
             * partitioning entry in the proper order
             */
            this.append(front);
            this.enqueue(deq);
            this.append(back);

        }
    }

}
