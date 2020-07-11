package com.course.server.util.Test;

import static java.lang.System.exit;

/**
 * @author yk
 * @version 1.0
 * @date 2020/7/9 17:01
 */
public class PrimeNumber {
    //判断一个数是不是素数
    public static void isPrime(Integer num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) {
                System.out.println("不是素数");
                break;
            }
        }
        System.out.println("是素数哦");
    }

    //输出一百以内的素数
    public static void AllPrime(Integer num) {
        for (int i = 2; i <=num; i++) {
            int flag=1;
            for (int j = 2;j<num;j++){
                if (i%j==0){
                    flag++;
                }
            }
            //只是被除2次的
            if (flag==2){
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        AllPrime(17);
    }
}
