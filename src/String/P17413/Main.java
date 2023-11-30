package String.P17413;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/String/P17413/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Stack<Character> crossStack = new Stack<>();
        Stack<Character> stack = new Stack<>();
        String input = br.readLine();

        for(int i=0; i<input.length(); i++){
            char word = input.charAt(i);

            if(word == ' ' || word == '<') {
                boolean isNotCross = false;
                while(!stack.isEmpty()) {
                    isNotCross = true;
                    sb.append(stack.pop());
                }

                if(word == ' ' && isNotCross) {
                    sb.append(' ');
                }
            }

            if(word == '<') {
                sb.append('<');
                crossStack.push('<');
                continue;
            }

            if(!crossStack.isEmpty() && word != '>') {
                sb.append(word);
                continue;
            }

            if(word == '>') {
                crossStack.pop();
                sb.append('>');
                continue;
            }

            if(word != ' ') {
                stack.push(word);
            }
        }

        while(!stack.isEmpty()) {
            Character pop = stack.pop();
            sb.append(pop);
        }

        System.out.print(sb);
    }
}
