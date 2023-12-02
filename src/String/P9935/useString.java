package String.P9935;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class useString {

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/String/P9935/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String input = br.readLine();
        String target = br.readLine();

        while(input.contains(target)) {
            input = input.replaceAll(target, "");
        }

        if(input.isEmpty()) {
            System.out.println("FRULA");
            return ;
        }

        System.out.println(input);
    }
}
