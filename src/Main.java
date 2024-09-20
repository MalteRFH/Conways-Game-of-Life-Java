import java.util.Arrays;

public class Main {
    boolean[][] field;
    int height = 10, width = 50;
    public Main(){
        field = new boolean[height][width];
        for(boolean[] booleans : field){
            Arrays.fill(booleans, false);
        }
    }

    private void display(boolean[][] disp){
        for (boolean[] booleans : disp) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    System.out.print("*");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}