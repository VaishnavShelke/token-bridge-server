package com.tokenmint.create;

public class TestClass01 {

	public static void main(String[] args) {
		
	}
}

class Solution {
	String aaa = null;
	String bbb = null;
	int[][] memo = null;
    public int numDistinct(String A, String B) {
    	int alen = A.length();
    	aaa = A;
    	bbb = B;
    	int blen = B.length();
    	memo = new int[alen][blen];
    	for(int a = 0; a<alen ; a++) {
    		for(int b = 0; b<blen ; b++) {
    			memo[a][b] = -1;
    		}
    	}
    	
    	int ans = nowicmbfa(alen-1,blen-1);
    	
    	return ans;
    }
	private int nowicmbfa(int a, int b) {
		if(b<0) {
			return 1;
		}
		if(a<0 || b<0) {
			return 0;
		}
		if(memo[a][b] != -1) {
			return memo[a][b];
		}
		int ans =0;
		if(aaa.charAt(a) == bbb.charAt(b)) {
			ans =  nowicmbfa(a-1,b-1)+ nowicmbfa(a-1, b);
		}else {
			ans =  nowicmbfa(a-1,b);
		}
		memo[a][b] = ans;
		return ans;
	}
}
