import java.util.*;

public class Main {
	static int N;
	static int[] d;
	static int cnt=0;
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		d = new int[N+1];
		
		Queen(0);
		System.out.println(cnt);
	}
	public static void Queen(int depth) { 			//depth:=행 
		if (depth == N) {
			cnt++;
			return;
		}
 
		for (int i = 0; i < N; i++) {
			d[depth] = i;
			if (Check(depth)) {
				Queen(depth + 1);
			}
		}
	}
	
	public static boolean Check(int col) {			//col:=열
		for (int i = 0; i < col; i++) {
			// 같은 행에 존재할경우 false return
			if (d[col] == d[i]) {
				return false;
			} 
			
			
			 //대각선상에 놓여있는 경우 false return
			else if (Math.abs(col - i) == Math.abs(d[col] - d[i])) {
				return false;
			}
		}
		
		return true;
	}
}