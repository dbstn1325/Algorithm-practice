import java.io.*;
import java.util.StringTokenizer;

public class Main {
    static int n, k;
    static int[] arr, d;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        arr = new int[n + 1];
        d = new int[k + 1];
        d[0] = 1;

        for(int i = 1 ; i <= n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
            for (int j = arr[i]; j <= k; j++)
                d[j] += d[j - arr[i]];
        }
        System.out.println(d[k]);
    }

}