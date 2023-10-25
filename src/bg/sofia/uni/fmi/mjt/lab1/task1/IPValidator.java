package bg.sofia.uni.fmi.mjt.lab1.task1;

public class IPValidator {
    public static boolean validateIPv4Address(String str) {
        if (str.length() < 7) {
            return false;
        }

        if (str.charAt(0) == '.' || str.charAt(str.length()-1) == '.'){
            return false;
        }

        char[] chars = str.toCharArray();
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == '.' && chars[i-1] == '.'){
                return false;
            }
        }

        String[] tokens = str.split("\\.");

        if (tokens.length != 4){
            return false;
        }

        for (String token: tokens) {
            if (token.length() > 3){
                return false;
            }
            char[] digits = token.toCharArray();

            for(char digit: digits){
                if (!Character.isDigit(digit)) {
                    return false;
                }
            }

            if ('0' == digits[0] && digits.length > 1){
                return false;
            }

            int number = Integer.parseInt(token);
            if (number < 0 || number > 255){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println("Valid IP: " + validateIPv4Address("192.168.1.1"));
        System.out.println("Valid IP: " + validateIPv4Address("192.168.1.0"));
        System.out.println("Valid IP: " + validateIPv4Address("192.168.1.00"));
        System.out.println("Valid IP: " + validateIPv4Address("192.168@1.1"));
    }
}

