package String.P9935;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/String/P9935/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String input = br.readLine();
        String target = br.readLine();

        Stack<Character> stack = new Stack<>();

        int correct = 0;
        int len = target.length();
        for(char ch: input.toCharArray()) {
            stack.push(ch);

            if(stack.size() >= len) {
                for(int i=len-1; i>=0; i--){
                    if(target.charAt(i) == stack.get(stack.size() - len + i)) {
                        correct++;
                    }
                }

                if(correct == len) {
                    for(int i=0; i<len; i++){
                        stack.pop();
                    }
                }

                correct = 0;
            }
        }

        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        if(sb.length() == 0) {
            System.out.println("FRULA");
            return ;
        }

        System.out.println(sb.reverse());

    }

}
