package com.wbg.Excel.util;

public class Test {
    public static void main(String[] args) {
        String [][] ss=new String[3][2];
        ss[0][0]="北京";
        ss[0][1]="010";
        ss[1][0]="广州";
        ss[1][1]="020";
        ss[2][0]="珠海";
        ss[2][1]="0756";
        String sr="广州";
        for (int i = 0; i < ss.length; i++) {
            if(ss[i][0]==sr){
                System.out.println(ss[i][1]);
            }
        }
    }
}
