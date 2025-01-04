import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import components.map.Map;
import components.naturalnumber.NaturalNumber;

/**
 * Simple class to experiment with the Java Collections Framework and how it
 * compares with the OSU CSE collection components.
 *
 * @author Put your name here
 *
 */
public final class JCFExplorations {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private JCFExplorations() {
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires [the salaries in map are positive] and raisePercent > 0
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    public static void giveRaise(components.map.Map<String, Integer> map, char initial,
            int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        int length = map.size();
        components.map.Map<String, Integer> temp = map.newInstance();
        for (int i = 0; i < length; i++) {
            Map.Pair<String, Integer> pair = map.removeAny();
            if (pair.key().charAt(0) == initial) {
                int newVal = pair.value() + (pair.value() * raisePercent / 100);
                temp.add(pair.key(), newVal);
            } else {
                temp.add(pair.key(), pair.value());
            }

        }

        map.transferFrom(temp);
    }

    /**
     * Raises the salary of all the employees in {@code map} whose name starts
     * with the given {@code initial} by the given {@code raisePercent}.
     *
     * @param map
     *            the name to salary map
     * @param initial
     *            the initial of names of employees to be given a raise
     * @param raisePercent
     *            the raise to be given as a percentage of the current salary
     * @updates map
     * @requires <pre>
     * [the salaries in map are positive] and raisePercent > 0 and
     * [the dynamic types of map and of all objects reachable from map
     *  (including any objects returned by operations (such as
     *  entrySet() and, from there, iterator()), and so on,
     *  recursively) support all optional operations]
     * </pre>
     * @ensures <pre>
     * DOMAIN(map) = DOMAIN(#map)  and
     * [the salaries of the employees in map whose names start with the given
     *  initial have been increased by raisePercent percent (and truncated to
     *  the nearest integer); all other employees have the same salary]
     * </pre>
     */
    public static void giveRaise(java.util.Map<String, Integer> map, char initial,
            int raisePercent) {
        assert map != null : "Violation of: map is not null";
        assert raisePercent > 0 : "Violation of: raisePercent > 0";

        Set<String> set = map.keySet();
        String[] arr = (String[]) set.toArray();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].charAt(0) == initial) {
                int val = map.get(arr[i]);
                int newVal = val + (val * raisePercent / 100);
                map.replace(arr[i], val, newVal);
            }
        }
    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     *
     * @param set
     *            the set whose elements to increment
     * @updates set
     * @ensures <pre>
     * [set is the set of integers that are elements of #set incremented by 1]
     * </pre>
     */
    public static void incrementAll(components.set.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        components.set.Set<NaturalNumber> temp = set.newInstance();
        while (set.size() > 0) {
            NaturalNumber remove = set.removeAny();
            remove.increment();
            temp.add(remove);
        }
        set.transferFrom(temp);

    }

    /**
     * Increments by 1 every element in the given {@code Set}.
     *
     * @param set
     *            the set whose elements to increment
     * @updates set
     * @requires <pre>
     * [the dynamic types of set and of all objects reachable from set
     *  (including any objects returned by operations (such as iterator()), and
     *  so on, recursively) support all optional operations]
     * </pre>
     * @ensures <pre>
     * [set is the set of integers that are elements of #set incremented by 1]
     * </pre>
     */
    public static void incrementAll(java.util.Set<NaturalNumber> set) {
        assert set != null : "Violation of: set is not null";

        Iterator<NaturalNumber> it = set.iterator();
        Set<NaturalNumber> temp = new HashSet<>();

        while (it.hasNext()) {
            NaturalNumber nextN = it.next();
            it.remove();
            nextN.increment();
            temp.add(nextN);
        }
        set.clear();
        set.addAll(temp);

        // using an array and for each loop
        NaturalNumber[] tempA = set.toArray(new NaturalNumber[0]);
        set.clear();
        for (NaturalNumber n : temp) {
            n.increment();
            set.add(n);
        }
    }

}