import java.util.Arrays;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopKFreqElements {
    class Solution {
        public int[] topKFrequent(int[] nums, int k) {
            Arrays.sort(nums);

            int current = 0;
            int count = 0;
            final NavigableSet<Elem> sorted = new TreeSet<Elem>();
            var i = 0;
            do {
                current = nums[i++];
                count++;
                if (i == nums.length || nums[i] != current) {
                    var e = new Elem();
                    e.count = count;
                    e.value = current;
                    sorted.add(e);
                    if (sorted.size() > k) {
                        sorted.pollFirst();
                    }
                    count = 0;
                }
            } while (i < nums.length);
            i = 0;
            var result = new int[sorted.size()];
            for (Elem e : sorted) {
                result[i++] = e.value;
            }
            return result;
        }
        class Elem implements Comparable<Elem> {
            public int count;
            public int value;

            @Override
            public int compareTo(Elem e) {
                int rv = Integer.compare(this.count, e.count);
                if (rv == 0) {
                    rv = Integer.compare(this.value, e.value);
                }
                return rv;
            }
        }
    }

    public static void main(String[] args) {
        var s = new TopKFreqElements().new Solution();
        System.out.println(Arrays.toString(s.topKFrequent(new int[] { 1, 1, 1, 2, 2, 3 }, 2)));
        System.out.println(Arrays.toString(s.topKFrequent(new int[] { 1 }, 1)));
        System.out.println(Arrays.toString(s.topKFrequent(new int[] { -1, -1 }, 1)));

    }
}
