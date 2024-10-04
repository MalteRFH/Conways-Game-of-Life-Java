import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Arrays;

public class GameOfLifeGUI extends JFrame {
    private final int pixelSize;
    private final int pixelNumber;
    private boolean[][] cells;
    private boolean isRunning = false;
    private boolean step = false;
    private final DPanel dPanel;
    private final String fileFormat = ".dat";

    public GameOfLifeGUI(int pixelSize, int pixelNumber) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore){}
        this.pixelSize = pixelSize;
        this.pixelNumber = pixelNumber;
        this.cells = new boolean[pixelNumber][pixelNumber];
        setTitle("Conway's Game of Life");
        setSize(pixelNumber * pixelSize + 17,  pixelNumber * pixelSize + 80);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setIconImage(new ImageIcon("src/Game-of-Life-Icon.png").getImage());
        dPanel = new DPanel();
        add(dPanel, BorderLayout.CENTER);
        initButtons();
        MouseAdapter handler = new MouseAdapter() {
            boolean changeToo = false;
            @Override
            public void mousePressed(MouseEvent evt) {
                int x = evt.getX() / pixelSize;
                int y = evt.getY() / pixelSize;
                changeToo = !cells[x][y];
                changePixel(evt);
            }

            @Override
            public void mouseDragged(MouseEvent evt) {
                changePixel(evt);
            }

            private void changePixel(MouseEvent evt){
                int x = evt.getX() / pixelSize;
                int y = evt.getY() / pixelSize;
                if (x >= 0 && x < pixelNumber && y >= 0 && y < pixelNumber) {
                    cells[x][y] = changeToo;
                    dPanel.repaint();
                }
            }
        };
        dPanel.addMouseListener(handler);
        dPanel.addMouseMotionListener(handler);
    }

    private void initButtons() {
        JButton startButton = createButton("Start");
        JButton nextButton = createButton("Next");
        JButton randomButton = createButton("Random");
        JButton resetButton = createButton("Reset");
        JButton saveButton = createButton("Save");
        JButton loadButton = createButton("Load");

        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only " + fileFormat +" files", fileFormat.substring(1));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(restrict);

        startButton.addActionListener(evt -> {
            isRunning = !isRunning;
            startButton.setText(isRunning() ? "Stop" : "Start");
        });

        randomButton.addActionListener(evt -> {
            for (int i = 0; i < pixelNumber; i++) {
                for (int j = 0; j < pixelNumber; j++) {
                    cells[i][j] = Math.random() < 0.4;
                }
            }
            dPanel.repaint();
        });

        nextButton.addActionListener(evt -> step = true);

        saveButton.addActionListener(evt -> {
            try {
                saveData(chooser);
            } catch (Exception e) {
                System.err.println("Failed to save File");
            }
        });

        loadButton.addActionListener(evt -> {
            try {
                loadData(chooser);
            } catch (Exception e) {
                System.err.println("Failed to load File");
            }
        });

        resetButton.addActionListener(evt -> {
            for (int i = 0; i < pixelNumber; i++) {
                Arrays.fill(cells[i], false);
            }
            dPanel.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.darkGray);
        buttonPanel.add(startButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.NORTH);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.lightGray);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(100, 30));
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        return button;
    }

    private void saveData(JFileChooser chooser) throws IOException {
        chooser.showSaveDialog(this);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile().getAbsolutePath() + fileFormat));
        out.writeObject(cells);
        out.flush();
        out.close();
    }

    private void loadData(JFileChooser chooser) throws IOException, ClassNotFoundException {
        chooser.showOpenDialog(this);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile().getAbsolutePath()));
        cells = (boolean[][]) in.readObject();
        in.close();
        repaint();
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
        dPanel.repaint();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean getStep(){
        return step;
    }

    public void setStep(boolean step) {
        this.step = step;
    }

    class DPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            super.setBackground(Color.black);
            for(int i = 0; i < pixelNumber; i++){
                for(int j = 0; j < pixelNumber; j++){
                    if(cells[i][j]) g.setColor(Color.lightGray);
                    else g.setColor(Color.darkGray);
                    if(pixelSize > 1) g.fillRect(i * pixelSize + 1, j * pixelSize + 1, pixelSize - 1, pixelSize - 1);
                    else g.fillRect(i * pixelSize, j * pixelSize, pixelSize, pixelSize);
                }
            }
        }
    }
}