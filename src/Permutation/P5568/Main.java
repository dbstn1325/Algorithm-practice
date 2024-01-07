package Permutation.P5568;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    static int n, k;
    static int[] sel;
    static int[] num;
    static boolean[] visited;
    static HashSet<Integer> set = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/Permutation/P5568/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        k = Integer.parseInt(br.readLine());

        num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = Integer.parseInt(br.readLine());
        }

        sel = new int[k];
        visited = new boolean[100];

        permutation(0);
        System.out.println(set.size());
    }

    static void permutation(int depth) {
        if(depth == k) {
            String s = "";
            for(int val: sel) {
                s+=val;
            }
            set.add(Integer.parseInt(s));
            return;
        }

        for(int i=0; i<n; i++) {
            if(!visited[i]) {
                sel[depth] = num[i];
                visited[i] = true;
                permutation(depth + 1);
                visited[i] = false;
            }
        }

    }

}
