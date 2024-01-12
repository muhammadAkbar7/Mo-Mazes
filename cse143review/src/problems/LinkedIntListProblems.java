package problems;

import datastructures.LinkedIntList;
// Checkstyle will complain that this is an unused import until you use it in your code.
import datastructures.LinkedIntList.ListNode;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - do not call any methods on the `LinkedIntList` objects.
 * - do not construct new `ListNode` objects for `reverse3` or `firstToLast`
 *      (though you may have as many `ListNode` variables as you like).
 * - do not construct any external data structures such as arrays, queues, lists, etc.
 * - do not mutate the `data` field of any node; instead, change the list only by modifying
 *      links between nodes.
 */

public class LinkedIntListProblems {

    /**
     * Reverses the 3 elements in the `LinkedIntList` (assume there are exactly 3 elements).
     */
    public static void reverse3(LinkedIntList list) {
        ListNode prev = null;
        ListNode curr = list.front;
        ListNode next = null;
        while (curr != null) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        list.front = prev;
    }

    /**
     * Moves the first element of the input list to the back of the list.
     */
    public static void firstToLast(LinkedIntList list) {
        if (list.front != null && list.front.next != null) { // if list is empty or just one element
            ListNode prev = list.front;
            list.front = list.front.next;
            ListNode curr = list.front;
            while (curr.next != null) {
                curr = curr.next;
            }
            curr.next = prev;
            prev.next = null;
        }
    }

    /**
     * Returns a list consisting of the integers of a followed by the integers
     * of n. Does not modify items of A or B.
     */
    public static LinkedIntList concatenate(LinkedIntList a, LinkedIntList b) {
        // Hint: you'll need to use the 'new' keyword to construct new objects.
        if (a.front == null) {
            return b;
        } else if (b.front == null) {
            return a;
        } else if (a.front == null && b.front == null) {
            return a;
        } else {
            LinkedIntList newList = new LinkedIntList();
            ListNode first = new ListNode(a.front.data);
            newList.front = first;
            ListNode prev = newList.front; // concatenate to new list
            ListNode curr = a.front.next;
            while (curr != null) {
                ListNode val = new ListNode(curr.data);
                prev.next = val;
                prev = prev.next;
                curr = curr.next;
            }
            ListNode currB = b.front;
            while (currB != null) {
                ListNode val = new ListNode(currB.data);
                prev.next = val;
                prev = prev.next;
                currB = currB.next;
            }
            return newList;
        }
    }
}
