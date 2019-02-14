package com.ipubu.demo;

/**
 * @ClassName Acfd
 * @Description
 * @Author jzy
 */
public class Acfd {
	public static void main(String[] args) {
		for(int i=0; i<5; i++){
		   if(i==0)
		      continue;
		   if (i == 3)
			   break;
		   System.out.println(i);
		}
	}

}
