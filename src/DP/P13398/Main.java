package DP.P13398;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int[] a = new int[n+1];
		for(int i=1; i<=n; i++) {
			a[i] = sc.nextInt();
		}
		
		int[] d = new int[n+1];
		d[1] = a[1];
		int[] d2 = new int[n+1];

		//n은 10만이하, O(N)으로 풀어야한다 => DP로 접근해서 풀었다.
		
	
		//기존에 생각했었던 방법
//		if(d[i-1] + a[i] < d[i]) {//d[i]가음수 일 경우를 계산해서 풀려했다.
//		a[i] = d[i-1];
//	}
		//음수를골라내서 구하는게아니라 오름차순 DP와 내림차순 DP를 적극 활용해서 구하면 되는 문제이다.
		
		
		//i-1  i  i+1  중에 i가 중요.
		//d[i] = i번째로끝나는 연속합
		//d2[i] = i번째로시작하는 연속합

		//bottom-top 방식
		int ans = d[1];
		for(int i=2; i<=n; i++) {
			d[i] = Math.max(d[i-1]+a[i], a[i]);
			
			ans = Math.max(ans, d[i]);
		}
		
		//top-bottom 방식
		d2[n] = a[n];
		for(int i=n-1; i>=1; i--) {
			d2[i] = Math.max(d2[i+1]+a[i], a[i]);
		}
		
		
		
		
		for(int i=2; i<=n-1; i++) {
			//i를 기준으로 i-1로끝나는 연속합과 i+1로시작하는연속합의 최대합을 구해준다.
			ans = ans > d[i-1]+d2[i+1] ? ans : d[i-1]+d2[i+1]; 
		}
		System.out.println(ans);
	}
}
