    import java.util.*;


public class MostFrequentEven { 
    public class Solution {
        public int mostFrequentEven(int[] nums) {
            Arrays.sort(nums);
            var current = nums[0] + 1;
            var count = 0;
            var most_frequent_count = 0;
            var most_frequent = -1;

            for (int i : nums) {
                if (i % 2 != 0) {
                    continue;
                }
                if (i != current) {
                    current = i;
                    count = 1;
                } else {
                    count++;
                }
                if (count > most_frequent_count) {
                    most_frequent = i;
                    most_frequent_count = count;
                }
            }
            return most_frequent;
        }

    }

    public void test(int...nums) {
        var s = new Solution();
        System.out.println("most frequent of " + Arrays.toString(nums) + ": " + s.mostFrequentEven(nums));
    }


    public static void main(String[] args) {
        var m = new MostFrequentEven();
        m.test(3,3,3);
        m.test(1,4,2,3,4);
        m.test(1,2,3,2,4,4);
    }

    
}