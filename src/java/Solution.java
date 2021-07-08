// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {

    public static void main(String[] args) {
        Solution s = new Solution();
        //System.out.println(s.solution(new int[]{-1, 1, 3, 3, 3, 2, 3, 2, 1, 0}));
        System.out.println(s.solution(new int[]{1,1,1}));

    }
    public int solution(int[] A) {
        int result = 0;
        int lastVelocity = -999999;
        int velocityChanges = 0;
        for(int i = 0; i< A.length; i++) {
                int periodLength = 1;
                if(i+1 < A.length) {
                    int velocity =  A[i] - A[i+1];
                    for(int j = i; j<A.length; j++) {
                        int nextId = j+1;
                        if(nextId < A.length) {

                            int nestVelocity= A[j] - A[nextId];

                            if(velocity == nestVelocity) {
                                periodLength = periodLength+1;
                            } else {
                                break;
                            }
                            if(periodLength == 3) {

                                result = result+1;
                                if(lastVelocity == nestVelocity) {
                                    result = result+1;
                                    lastVelocity = -999999;
                                } else {
                                    velocityChanges = velocityChanges +1;
                                    lastVelocity =nestVelocity;
                                }
                                break;
                            }
                        }

                    }
                }



        }
        if(velocityChanges ==0) {
            result = result+1;
        }
        if(result == 1) {
            result = result+1;
        }
        return result;
    }
}
