import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopKFreqElements {
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            Arrays.sort(nums);

            int current = nums[0] + 1;
            int count = 0;
            final var sorted = new TreeSet<Elem>();
            for (int i : nums) {
                if (i != current) {
                    if (count > 0) {
                        var e = new Elem();
                        e.count = count;
                        e.value = current;
                        sorted.add(e);
                        if (sorted.size() > k) {
                            sorted.remove(sorted.last());
                        }
                    }
                    count = 1;
                    current = i;
                } else {
                    count++;
                }
            }
            return sort;
        }

        
    }

    public static class Elem implements Comparable<Elem> {
        public int count;
        public int value;

        @Override
        public int compareTo(Elem e) {
            int rv = this.value - e.value;
            if (rv == 0) {
                rv = this.value - e.value;
            }
            return rv;
        }


    }
}
