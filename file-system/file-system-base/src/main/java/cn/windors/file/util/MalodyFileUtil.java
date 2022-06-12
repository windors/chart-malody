package cn.windors.file.util;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Wind_Yuan
 */
public class MalodyFileUtil {
    public static boolean canSolve(String filename) {
        return filename.endsWith(".mc");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] words = s.split(" *");
        System.out.println(Arrays.toString(words));
        String word = words[words.length - 1];
        System.out.println(word.length());
    }
}