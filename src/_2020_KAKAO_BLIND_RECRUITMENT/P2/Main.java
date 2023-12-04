package _2020_KAKAO_BLIND_RECRUITMENT.P2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/_2020_KAKAO_BLIND_RECRUITMENT/P2/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        solution(br.readLine());
    }

    public static String solution(String p) {
        String answer = "";

        if(p.isEmpty()) {
            return "";
        }

        answer = choiceUAndV(p);
//        System.out.println(answer);
        return answer;
    }

    public static String choiceUAndV(String s) {

        if(s.equals("")) {
            return s;
        }

        int leftCount = 0;
        int rightCount = 0;
        int outIdx = 0;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i) == ')') {
                leftCount++;
            }
            if(s.charAt(i) == '(') {
                rightCount++;
            }

            if(leftCount == rightCount) {
                outIdx = i+1;
                break;
            }
        }

        String u = s.substring(0, outIdx);
        String v = s.substring(outIdx, s.length());

//        System.out.println(u);
//        System.out.println(v);

        if(isCorrect(u)) {
            return u + choiceUAndV(v);
        }

        String tmp = "";
        tmp+= "(";
        tmp += choiceUAndV(v);
        tmp += ")";
        tmp += reverse(u.substring(1, u.length()-1));

        return tmp;
    }

    public static boolean isCorrect(String s) {
        Stack<String> stack = new Stack<>();

        for(int i=0; i<s.length(); i++){
            String pushItem = String.valueOf(s.charAt(i));
            if(!stack.isEmpty()) {
                String peek = stack.peek();

                if(peek.equals("(") && !peek.equals(pushItem)) {
                    stack.pop();
                    continue;
                }
            }
            stack.push(pushItem);
        }

        if(stack.isEmpty()) {
            return true;
        }

        return false;
    }

    public static String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<s.length(); i++) {
            if(s.charAt(i) == ')') {
                sb.append("(");
            }
            if(s.charAt(i) == '(') {
                sb.append(")");
            }
        }

        return sb.toString();
    }
}
