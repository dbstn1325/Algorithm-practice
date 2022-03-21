import java.util.*;

public class Main {
	static int M, N;
	
	static int[] dx = { -1, 1, 0, 0};
	static int[] dy = { 0, 0, -1, 1};
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		M = sc.nextInt();
		N = sc.nextInt();
		
		int[][] board = new int[N][M];
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				board[i][j] = sc.nextInt();
			}
		}
		//입력라인
		
		bfs(board);
		//다음좌표찾으러
	}
	
	public static void bfs(int[][] board) {
		Queue<Dot> q = new LinkedList<Dot>();
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] == 1) {
					q.add(new Dot(i,j));
				}
			}
		}
		//일단 1인애들다q에담고
		
		while(!q.isEmpty()) {
			Dot t = q.poll();
			
			for(int i=0; i<4; i++) {
				int nx = t.x + dx[i];
				int ny = t.y + dy[i];
				
				if(nx >= N || nx < 0 || ny >=M || ny < 0) {
					continue;
				}
				
				if(board[nx][ny] != 0) {
					continue;
				}
				
				q.add(new Dot(nx,ny));
				board[nx][ny] = board[t.x][t.y]+1;
			}
		}
		
		
		int max=0;
		for(int i=0; i<N; i++) {
			for(int j=0; j<M; j++) {
				if(board[i][j] == 0) {
					System.out.println(-1);
					System.exit(0);
				}
				max = Math.max(max, board[i][j]);
			}
		}
		System.out.println(max-1);	
		//처음->두번째 센건제외
		
	}
}
class Dot {
	int x;
	int y;
	
	Dot(int x, int y){
		this.x = x;
		this.y = y;
	}
}