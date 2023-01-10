    import java.util.*;


    class Solution {
        public List<Integer> majorityElement(int[] nums) {
            Arrays.sort(nums);
            var current = nums[0] + 1;
            var count = 0;
            var added = false;
            var min_count = nums.length / 3.0;
            List<Integer> result = new ArrayList<>();

            for (int i : nums) {
                if (i != current) {
                    current = i;
                    count = 1;
                    added = false;
                } else {
                    count++;
                }
                if (count > min_count && !added) {
                    result.add(i);
                    added = true;
                }
            }
            return result;
        }

        public static void main(String[] args) {
            var s = new Solution();
            System.out.println(s.majorityElement(new int[]{3,2,3}));

            System.out.println(s.majorityElement(new int[]{1,2,3, 4}));
            System.out.println(s.majorityElement(new int[]{1,2,3}));

        }
    }