package DFS;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SW_1949_등산로조정 {

	static int T;
	static int N, K;
	static int[][] map;
	static boolean[][] v;
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 }; // 상하좌우
	static int highest = 0; // 가장 높은 봉우리 높이
	static int ans;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		T = sc.nextInt();
		for (int t = 1; t <= T; t++) {
			N = sc.nextInt();
			K = sc.nextInt();
			map = new int[N][N];
			v = new boolean[N][N];
			highest = 0;
			ans = 0;
			List<Point> blist = new ArrayList<>();

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					map[i][j] = sc.nextInt();
					if (highest < map[i][j])
						highest = map[i][j];
				}
			}

			/* 가장 높은 봉우리 위치 blist에 넣기 */
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (map[i][j] == highest) {
						blist.add(new Point(i, j));
					}
				}
			}
			
			/* 가장 높은 봉우리들 dfs */
			for (int i = 0; i < blist.size(); i++) {
				int r = blist.get(i).r;
				int c = blist.get(i).c;
				dfs(r, c, false, 1);
			}

			System.out.println("#" + t + " " + ans);
		}
	}

	public static void dfs(int r, int c, boolean trim, int distance) {
		if (ans < distance)
			ans = distance;
		v[r][c] = true;
		for (int d = 0; d < 4; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];

			if (nr < 0 || nr >= N || nc < 0 || nc >= N || v[nr][nc])
				continue;
			if (map[nr][nc] < map[r][c]) { // 현재 봉우리보다 다음 봉우리가 낮은 경우 바로 이동 
				dfs(nr, nc, trim, distance + 1);
			} else if (!trim) {  
				for (int j = 1; j <= K; j++) {// 최대 공사 가능 깊이가 K 이므로 하나씩 검사해서 비교.
					if (map[nr][nc] - j < map[r][c]) {
						map[nr][nc] = map[nr][nc] - j;
						dfs(nr, nc, true, distance + 1);
						map[nr][nc] = map[nr][nc] + j;
					}
				}
			}
		}
		v[r][c] = false;

	}
}

class Point {
	int r, c;

	public Point() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Point(int r, int c) {
		super();
		this.r = r;
		this.c = c;
	}

}
