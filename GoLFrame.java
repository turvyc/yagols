import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * Defines the layout and event handling for the Game of Life frame.
 */
public class GoLFrame extends JFrame {
    // TODO: These should be changeable
    private static final int FRAME_WIDTH = 850;
    private static final int FRAME_HEIGHT = 600;
    private static String FRAME_TITLE = "Game of Life Simulator";
    private static final int CELL_SIZE = 20; // px
    private static final int GRID_SIZE = 20;

    // Speed slider settings (GPS = Generations Per Second)
    private static final int MIN_GPS = 0;
    private static final int MAX_GPS = 20;
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
    private Timer timer;

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

        createTimer();
    }

    private void createTimer() {
        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent event) {
                game.nextGeneration();
                updateGUI();
            }
        }
        ActionListener listener = new TimerListener();
        timer = new Timer(1000 / speedSlider.getValue(), listener);
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
        class ToroidalListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                // Do stuff
            }
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        MouseListener listener = new ToroidalListener();
        toroidalCheckBox.addMouseListener(listener);

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
        class SpeedListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                timer.stop();
            }
            public void mouseReleased(MouseEvent event) {
                if (speedSlider.getValue() == 0)
                    timer.stop();
                else {
                    timer.setDelay(1000 / speedSlider.getValue());
                    timer.start();
                }
            }
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        MouseListener listener = new SpeedListener();
        speedSlider.addMouseListener(listener);

        JPanel panel = new JPanel();
        panel.add(speedSlider);
        panel.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), "Speed"));
        return panel;
    }

    private JPanel createButtonPanel() {
        // Create the start button
        startButton = new JButton("Start");
        class StartListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                timer.start();
            }
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        MouseListener listener = new StartListener();
        startButton.addMouseListener(listener);

        // Create the stop button
        stopButton = new JButton("Stop");
        class stopListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                timer.stop();
            }
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        listener = new stopListener();
        stopButton.addMouseListener(listener);

        // Create the reset button
        resetButton = new JButton("Reset");
        class ResetListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                game.reset();
                updateGUI();
            }
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }
        listener = new ResetListener();
        resetButton.addMouseListener(listener);

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
        class PanelListener implements MouseListener {
            public void mousePressed(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                Point cell = getCell(x,y);
                game.toggleCell(cell);
                updateGUI();
            }
            public void mouseReleased(MouseEvent event) {}
            public void mouseClicked(MouseEvent event) {}
            public void mouseEntered(MouseEvent event) {}
            public void mouseExited(MouseEvent event) {}
        }

        MouseListener listener = new PanelListener();
        boardComponent.addMouseListener(listener);

        panel.add(boardComponent);
        return panel;
    }

    private void updateGUI() {
        populationLabel.setText(POPULATION_LABEL_TEXT 
                + game.getPopulation());
        generationLabel.setText(GENERATION_LABEL_TEXT 
                + game.getGeneration());
        boardComponent.repaint();
    }

    private Point getCell(int x, int y) {
        return new Point(x / CELL_SIZE, y / CELL_SIZE);
    }
}
