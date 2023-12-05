package String.P1181;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main {

    public static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        HashSet<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>();
        N = Integer.parseInt(br.readLine());
        for(int i=0; i<N; i++){
            set.add(br.readLine());
        }
        list.addAll(set);

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1.length() == o2.length()) {
                    return o1.compareTo(o2);
                }
                return o1.length() - o2.length();
            }
        });

        for(String l: list) {
            System.out.println(l);
        }

    }
}
