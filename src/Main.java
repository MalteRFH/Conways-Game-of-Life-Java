import java.util.Arrays;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Main {
    boolean[][] field, tempField;
    int height = 40, width = 100;
    public Main() throws InterruptedException {
        field = new boolean[height][width];
        tempField = new boolean[height][width];
        for(boolean[] booleans : field){
            Arrays.fill(booleans, false);
        }
//        field[17][49] = true;
//        field[17][50] = true;
//        field[17][51] = true;
//        field[18][49] = true;
//        field[18][51] = true;
//        field[19][49] = true;
//        field[19][51] = true;
//
//        field[23][49] = true;
//        field[23][50] = true;
//        field[23][51] = true;
//        field[22][49] = true;
//        field[22][51] = true;
//        field[21][49] = true;
//        field[21][51] = true;
        Random random = new Random();
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                field[i][j] = random.nextBoolean();
            }
        }
        while(true){
            display();
            Thread.sleep(300);
            calcStates();
            setField();
        }
    }
    private void calcStates(){
        int aliveCount;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                aliveCount = 0;
                for(int k = -1; k < 2; k++){
                    for(int l = -1; l < 2; l++) {
                        if(i+k > -1 && j+l > -1 && i+k < height && j+l < width){
                            if(field[i+k][j+l]) aliveCount++;
                        }
                    }
                }
                setTempField(aliveCount, i, j);
            }
        }
    }
    private void setTempField(int count, int y,int x){
        if(field[y][x]) count--;
        if(count == 2) tempField[y][x] = field[y][x];
        else tempField[y][x] = count == 3;
    }
    private void setField(){
        for(int i = 0; i < height; i++) {
            if (width >= 0) System.arraycopy(tempField[i], 0, field[i], 0, width);
        }
    }
    private void display(){
        System.out.println("\n\n\n\n\n\n\n");
        for (boolean[] booleans : field) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    System.out.print("█");
                } else {
                    System.out.print("⠀");
                }
            }
            System.out.println();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        new Main();
    }
}