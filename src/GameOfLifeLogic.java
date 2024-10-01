public class GameOfLifeLogic {
    private final int pixelNumber;
    private final boolean[][] cells;

    public GameOfLifeLogic(int pixelNumber) {
        this.pixelNumber = pixelNumber;
        this.cells = new boolean[pixelNumber][pixelNumber];
    }

    public void updateCells() {
        boolean[][] tempCells = new boolean[pixelNumber][pixelNumber];
        for (int i = 0; i < pixelNumber; i++) {
            for (int j = 0; j < pixelNumber; j++) {
                int liveNeighbors = countNeighbors(i, j);
                if(liveNeighbors == 2){
                    tempCells[i][j] = cells[i][j];
                } else {
                    tempCells[i][j] = (liveNeighbors == 3);
                }
            }
        }
        setCells(tempCells);
    }

    private int countNeighbors(int x, int y){
        int count = 0;
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (x + k >= 0 && x + k < pixelNumber && y + l >= 0 && y + l < pixelNumber) {
                    if ((k != 0 || l != 0) && cells[x + k][y + l]) count++;
                }
            }
        }
        return count;
    }

    public boolean[][] getCells() {
        boolean[][] tempCells = new boolean[pixelNumber][pixelNumber];
        for (int i = 0; i < pixelNumber; i++) {
            System.arraycopy(cells[i], 0, tempCells[i], 0, pixelNumber);
        }
        return tempCells;
    }

    public void setCells(boolean[][] cells) {
        for (int i = 0; i < pixelNumber; i++) {
            System.arraycopy(cells[i], 0, this.cells[i], 0, pixelNumber);
        }
    }
}