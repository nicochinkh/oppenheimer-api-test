package utils;

import java.util.Random;

public class TestUtils {

    public static String getRandomHeroName(int length) {
        String str = "abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getRandomGender() {
        Random random = new Random();
        int s = random.nextInt(10);
        if (s > 5) {
            return "MALE";
        } else {
            return "FEMALE";
        }
    }

}
