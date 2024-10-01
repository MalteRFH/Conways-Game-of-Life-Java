import javax.swing.*;

public class Main {
    static int pixelSize = 8;
    static int pixelNumber = 100;
    static GameOfLifeLogic logic;
    static GameOfLifeGUI gui;

    private static void initGUI() {
        gui = new GameOfLifeGUI(pixelSize, pixelNumber);
        logic = new GameOfLifeLogic(pixelNumber);

        gui.setVisible(true);
    }

    private static void runGame(){
        Timer timer = new Timer(100, e -> {
            if (gui.isRunning()) {
                logic.setCells(gui.getCells());
                logic.updateCells();
                gui.setCells(logic.getCells());
            } else if (gui.getStep()){
                logic.setCells(gui.getCells());
                logic.updateCells();
                gui.setCells(logic.getCells());
                gui.setStep(false);
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        initGUI();
        runGame();
    }
}