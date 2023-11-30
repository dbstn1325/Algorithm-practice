package String.P9093;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int num = Integer.parseInt(br.readLine());

        for(int i=0; i<num; i++) {
            String[] values = br.readLine().split(" ");
            for (int j = 0; j < values.length; j++) {
                calculateReverse(values[j]);
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public static void calculateReverse(String value) {
        Words words = new Words(value.toCharArray());
        List<Character> reverse = words.getReverse();
        showReverse(reverse);
    }

    private static void showReverse(List<Character> reverse) {
        String reverseResult = reverse.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        sb.append(reverseResult + " ");
    }

    public static class Words {
        char[] words;

        public Words(char[] words) {
            this.words = words;
        }

        public List<Character> getReverse() {
            List<Character> result = new ArrayList<>();
            for (char word : words) {
                result.add(word);
            }
            Collections.reverse(result);
            return result;
        }
    }
}
