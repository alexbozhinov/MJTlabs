package bg.sofia.uni.fmi.mjt.lab1.task3;

public class BrokenKeyboard {
    public static int calculateFullyTypedWords(String message, String brokenKeys){
        message = message.strip().replaceAll("\\s+", " ");
        if (message.isEmpty()){
            return 0;
        }

        String[] words = message.split(" ");
        if (brokenKeys.isEmpty()){
            return words.length;
        }

        int wordsCount = 0;
        char[] brokenArray = brokenKeys.toCharArray();

        for (String word: words) {
            if (word.isEmpty()){
                continue;
            }
            boolean containsBroken = true;

            for (char letter: brokenArray) {
                containsBroken= false;

                if (word.contains(String.valueOf(letter))){
                    containsBroken = true;
                    break;
                }
            }
            if (!containsBroken){
                ++wordsCount;
            }
        }
        return wordsCount;
    }

    public static void main(String[] args) {
        System.out.println("Number of written words: " + calculateFullyTypedWords("i love mjt", "qsf3o") );
        System.out.println("Number of written words: " + calculateFullyTypedWords("secret      message info      ", "sms") );
        System.out.println("Number of written words: " + calculateFullyTypedWords("dve po 2 4isto novi beli kecove", "o2sf") );
        System.out.println("Number of written words: " + calculateFullyTypedWords("     ", "asd") );
        System.out.println("Number of written words: " + calculateFullyTypedWords(" - 1 @ - 4", "s") );
    }
}

