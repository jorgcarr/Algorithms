import java.util.*;
import java.io.*;

public class BacketMatches {





        public static int BracketMatcher(String str) {
            // code goes here
            Stack<Integer> stack = new Stack<Integer>();

            for (int i = 0; i < str.length(); i++) {

                if(str.charAt(i) == '(') {
                    stack.push(i);
                } else if(str.charAt(i) == ')') {
                    if(stack.isEmpty()) {
                        return 0;
                    }
                    stack.pop();
                }
            }

            if(stack.isEmpty()) {
                return 1;
            }

            return 0;

        }

        public static void main (String[] args) {
            // keep this function call here
            Scanner s = new Scanner(System.in);
            System.out.print(BracketMatcher(s.nextLine()));
        }

    }

