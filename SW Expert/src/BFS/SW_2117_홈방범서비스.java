package BFS;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class SW_2117_홈방범서비스 {
	static int T;
	static int N, M;
	static int map[][]; // 집 위치
	static int v[][]; // 방문 + 거리 표시
	static int dr[] = { -1, 1, 0, 0 };
	static int dc[] = { 0, 0, -1, 1 };
	static int ans;			// 답
	static int house_num;	// 집의 갯수.
	
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		T = sc.nextInt();
		for (int t = 1; t <= T; t++) {
			N = sc.nextInt();
			M = sc.nextInt();
			map = new int[N][N];
			ans = 0;
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j] = sc.nextInt();
				}
			}

			for (int i = 0; i < N; i++) {	// 모든 i,j에 대해서 bfs(거리 계산)
				for (int j = 0; j < N; j++) {
					house_num = 0;
					v = new int[N][N];
					que.add(new Point(i, j));
					v[i][j] = 1;
					bfs(i, j);
				}
			}
			
			System.out.println("#"+t+" "+ans);
		}
	}

	static Queue<Point> que = new LinkedList<Point>();

	static void bfs(int r, int c) {
		while (!que.isEmpty()) {
			int size = que.size();
			for (int i = 0; i < size; i++) {
				Point p = que.poll();
				int K = v[p.r][p.c];
				if(map[p.r][p.c]==1) {	// 해당 위치에 집이 있으면
					house_num++;
				}
				for (int d = 0; d < 4; d++) {
					int nr = p.r + dr[d];
					int nc = p.c + dc[d];

					if (nr < 0 || nc < 0 || nr >= N || nc >= N || v[nr][nc] > 0)
						continue;

					v[nr][nc] = K+1;
					que.add(new Point(nr, nc));

				}
				if(i==size-1) {	// 마지막 까지 왔으면 보안회사 이익 계산.
					int profit = ((house_num * M) - ((K*K)+((K-1)*(K-1))));
					if(profit>=0) { // "손해 보지 않는 한" 이므로 보안회사 이익이 마이너스만 아니면 됨.
						if(ans<house_num) ans = house_num;
					}
				}

			}
		}
	}
}

class Point {
	int r, c;

	public Point(int r, int c) {
		super();
		this.r = r;
		this.c = c;
	}

}