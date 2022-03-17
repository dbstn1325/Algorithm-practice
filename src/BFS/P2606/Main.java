import java.util.*;

public class Main{
	static int count = 0;
	static int[][] d;
	static boolean[] checked;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int M = sc.nextInt();
		
		checked = new boolean[N+1];
		d = new int[N+1][N+1];
		for(int i=0; i<M; i++) {
			int a = sc.nextInt();
			int b = sc.nextInt();
			d[a][b] = 1;
			d[b][a] = 1;
		}
		bfs(1);
	}
	
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		checked[start] = true;
		
		while(!q.isEmpty()) {
			int t = q.poll();

			for(int i=1; i<d.length; i++) {
				if(d[t][i] != 1 || checked[i]) {
					continue;
				}
				
				q.add(i);
				checked[i] = true;
				count ++;
			}
		}
		System.out.println(count);
 	}
}