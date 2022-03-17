import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main{
	static int count = 0;
	static int[][] d;
	static boolean[] checked;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine()); 
		
		checked = new boolean[N+1];
		d = new int[N+1][N+1];
		StringTokenizer st;
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			d[a][b] = 1;
			d[b][a] = 1;
		}
		dfs(1);
		System.out.println(count);
	}
	public static void dfs(int idx) {
		checked[idx] = true;
		for(int i=1; i<d.length; i++) {
			if(d[idx][i] != 1 || checked[i]) {
				continue;
			}
			count ++;
			dfs(i);
		}
	}
}

