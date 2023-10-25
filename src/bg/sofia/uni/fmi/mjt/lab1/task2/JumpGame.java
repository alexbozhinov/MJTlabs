package bg.sofia.uni.fmi.mjt.lab1.task2;

public class JumpGame {
    public static boolean canWin(int[] array){
        boolean win = true;
        int maxSteps = 0;

        for (int i = 0; i < array.length; i++) {
            if (i > maxSteps){ // arr[i] can't be reached
                win = false;
                break;
            }
            else{
                maxSteps = Math.max(maxSteps, i + array[i]); // current max steps, or current position + possible position
            }
        }
        return win;
    }

    public static void main(String[] args) {
        System.out.println("Can win: " + canWin(new int[]{2, 3, 1, 1, 0}));
        System.out.println("Can win: " + canWin(new int[]{3, 2, 1, 0, 0}));
    }
}

