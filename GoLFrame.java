import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;

/**
 * Defines the layout and event handling for the Game of Life frame.
 */
public class GoLFrame extends JFrame {
    // TODO: These should be changeable
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static String FRAME_TITLE = "Game of Life Simulator";
    private static final int CELL_SIZE = 20; // px
    private static final int GRID_SIZE = 20;

    // Speed slider settings (GPS = Generations Per Second)
    private static final int MIN_GPS = 0;
    private static final int MAX_GPS = 50;
    private static final int INIT_GPS = 1;

    // GUI Components that require listeners
    private JLabel generationLabel;
    private JLabel populationLabel;
    private JCheckBox toroidalCheckBox;
    private JSlider speedSlider;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JPanel boardPanel;
    private BoardComponent boardComponent;

    // Label text
    private final String GENERATION_LABEL_TEXT = "Generation: ";
    private final String POPULATION_LABEL_TEXT = "Population: ";
    private final String TOROIDAL_TEXT = "Toroidal?";

    private GoLBoard game;

    public GoLFrame() {
        game = new GoLBoard(GRID_SIZE, GRID_SIZE);

        // Create the GUI
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(FRAME_TITLE);
        JPanel controlPanel = createControlPanel();
        boardPanel = createBoardPanel();
        add(controlPanel, BorderLayout.NORTH);
        add(boardPanel, BorderLayout.CENTER);
    }

    private JPanel createControlPanel() {
        JPanel generationPanel = createGenerationLabel();
        JPanel populationPanel = createPopulationLabel();
        JPanel toroidalCheckBoxPanel = createToroidalCheckBox();
        JPanel speedSliderPanel = createSpeedSlider();
        JPanel buttonPanel = createButtonPanel();

        JPanel panel = new JPanel();
        panel.add(generationPanel);
        panel.add(populationPanel);
        panel.add(toroidalCheckBoxPanel);
        panel.add(speedSliderPanel);
        panel.add(buttonPanel);

        return panel;
    }

    private JPanel createGenerationLabel() {
        generationLabel = new JLabel(GENERATION_LABEL_TEXT + 
                game.getGeneration());
        JPanel panel = new JPanel();
        panel.add(generationLabel);
        return panel;
    }

    private JPanel createPopulationLabel() {
        populationLabel = new JLabel(POPULATION_LABEL_TEXT + 
                game.getPopulation());
        JPanel panel = new JPanel();
        panel.add(populationLabel);
        return panel;
    }

    private JPanel createToroidalCheckBox() {
        toroidalCheckBox = new JCheckBox(TOROIDAL_TEXT);
        JPanel panel = new JPanel();
        panel.add(toroidalCheckBox);
        return panel;
    }

    private JPanel createSpeedSlider() {
        speedSlider = new JSlider(JSlider.HORIZONTAL, MIN_GPS, MAX_GPS,
                INIT_GPS);
        speedSlider.setMajorTickSpacing(10);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        JPanel panel = new JPanel();
        panel.add(speedSlider);
        panel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Speed"));
        return panel;
    }

    private JPanel createButtonPanel() {
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        resetButton = new JButton("Reset");
        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(resetButton);
        return panel;
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        boardComponent = new BoardComponent(CELL_SIZE, GRID_SIZE, game);
        int edgeSize = CELL_SIZE * GRID_SIZE + 1;
        boardComponent.setPreferredSize(new Dimension(edgeSize, edgeSize));

        // Add mouse click listener
        class MousePressListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                Point cell = getCell(x,y);
                game.toggleCell(cell);
                populationLabel.setText(POPULATION_LABEL_TEXT + game.getPopulation());
                boardComponent.repaint();
            }
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }

        MouseListener listener = new MousePressListener();
        boardComponent.addMouseListener(listener);

        panel.add(boardComponent);
        return panel;
    }

    private Point getCell(int x, int y) {
        return new Point(x / CELL_SIZE, y / CELL_SIZE);
    }
}
