import java.io.*
import java.util.*;

public class Exam1012 {

	static int M, N;
    static int K;
	static int[][] d;
	static boolean[][] checked;
	static int count;
	static int[] dx = { 0, -1, 0, 1 };
	static int[] dy = { 1, 0, -1, 0 };

	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int T = Integer.parseInt(br.readLine());

		for (int c = 0; c < T; c++) {
			StringTokenizer st = new StringTokenizer(br.readLine()," ");
			 M = Integer.parseInt(st.nextToken());
			 N = Integer.parseInt(st.nextToken());
			 K = Integer.parseInt(st.nextToken());
			d = new int[M][N];
			checked = new boolean[M][N];
			count=0;

			for (int i = 0; i < K; i++) {
				st=new StringTokenizer(br.readLine()," ");
				int p1 = Integer.parseInt(st.nextToken());
				int p2 = Integer.parseInt(st.nextToken());
				d[p1][p2] = 1;

			}

			for (int i = 0; i < M; i++) {
				for (int j = 0; j < N; j++) {
					if (d[i][j] == 1 && !checked[i][j]) {
						bfs(i, j);
						count++;
					}
				}
			}
			System.out.println(count);
		}
		
	}
    
    static void bfs(int x, int y) {
		qeue<int[]> q = new LinkedList<int[]>();
		q.add(new int[] { x, y });

		while (!q.isEmpty()) {
			x = q.peek()[0];
			y = q.peek()[1];
            
			checked[x][y] = true;
			q.poll();
			for (int i = 0; i < 4; i++) {
				int cx = x + dx[i];
				int cy = y + dy[i];

				if (cx >= 0 && cy >= 0 && cx < M && cy < N) {
					if (!checked[cx][cy] && d[cx][cy] == 1) {
						q.add(new int[] { cx, cy });
						checked[cx][cy] = true;
					}
				}

			}

		}
	}
}