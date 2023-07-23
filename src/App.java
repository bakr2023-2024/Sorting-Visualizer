import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeSupport;

public class App extends JFrame {
    public static class Screen extends JComponent implements Sorts.Observer {
        private int[] arr;
        private int[] copy;
        private int first = -1;
        private int second = -1;
        private boolean done = false;
        private int comparisons = 0;
        private int swaps = 0;
        private int access = 0;
        private double timeTaken = 0;
        private int space = 0;

        public Screen(int[] arr) {
            this.arr = arr;
            this.copy = this.arr.clone();
        }

        private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            pcs.addPropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            pcs.removePropertyChangeListener(listener);
        }

        @Override
        public void updateScreen(int[] arr, int i, int j, int comp, int swap, int acces, int spac) {
            this.arr = arr;
            this.first = i;
            this.second = j;
            int oldComparisons = this.comparisons;
            this.comparisons = comp;
            pcs.firePropertyChange("comparisons", oldComparisons, this.comparisons);
            int oldSwaps = this.swaps;
            this.swaps = swap;
            pcs.firePropertyChange("swaps", oldSwaps, this.swaps);
            int oldAccess = this.access;
            this.access = acces;
            pcs.firePropertyChange("access", oldAccess, this.access);
            int oldSpace = this.space;
            this.space = spac;
            pcs.firePropertyChange("space", oldSpace, this.space);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    repaint();
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(960, 720);
        }

        public void sort(String method, Runnable stats) {
            done = false;
            Sorts.keepSorting = true;
            Sorts sorter = new Sorts();
            sorter.addObserver(this);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    double start = System.currentTimeMillis();
                    sorter.sort(arr, method);
                    space = sorter.space;
                    timeTaken = System.currentTimeMillis() - start;
                    stats.run();
                    done = true;
                    Sorts.keepSorting = false;
                    comparisons = 0;
                    swaps = 0;
                    access = 0;
                    timeTaken = 0;
                    space = 0;
                }
            }).start();
        }

        @Override
        public void displaySortedArray() {
            this.first = -1;
            this.second = -1;
            for (int i = 0; i < arr.length; i++) {
                doneIndex = i;
                repaint();
                Sorts.delay();
            }
        }

        int doneIndex = -1;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            double columnWidth = Math.max(1, (double) width / arr.length);
            int max = Arrays.stream(arr).max().getAsInt();
            double columnHeight = Math.max(1, (double) height);
            for (int i = 0; i < arr.length; i++) {
                if (i == first || i == second) {
                    g.setColor(Color.RED);
                } else if (i <= doneIndex) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.WHITE);
                }
                int x = (int) Math.round(i * columnWidth);
                int y = (int) Math.round(height - (double) arr[i] / max * columnHeight);
                int w = (int) Math.round(columnWidth);
                int h = (int) Math.round((double) arr[i] / max * columnHeight);
                h = Math.min(h, height - y);
                g.fillRect(x, y, w, h);
            }
        }

        public void setArray(int[] array) {
            this.arr = array.clone();
            this.copy = arr.clone();
            this.comparisons = 0;
            this.swaps = 0;
            repaint();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("screen");
        frame.setVisible(true);
        frame.setSize(1280, 720);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel leftPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        frame.add(leftPanel, BorderLayout.WEST);
        leftPanel.setBackground(Color.BLACK);
        int[] arr = new int[512];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 1000) + 1;
        }
        Screen screen = new Screen(arr);
        leftPanel.add(screen, BorderLayout.WEST);
        screen.revalidate();
        screen.repaint();
        rightPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        frame.add(rightPanel, BorderLayout.EAST);
        JTextArea title = new JTextArea("Sorting Algorithms\nVisualizer");
        title.setBackground(frame.getBackground());
        title.setFont(new Font("Papyrus", Font.BOLD, 25));
        JLabel comparisonsLabel = new JLabel("Comparisons: 0");
        JLabel swapsLabel = new JLabel("Swaps: 0");
        JLabel accessLabel = new JLabel("Array Accesses: 0");
        JLabel timeLabel = new JLabel("Time Taken: 0 ms");
        JLabel spaceLabel = new JLabel("Space Taken: 0 memory cell(s)");
        screen.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("comparisons")) {
                    int numComparisons = (int) evt.getNewValue();
                    comparisonsLabel.setText("Comparisons: " + numComparisons);
                } else if (evt.getPropertyName().equals("swaps")) {
                    int numSwaps = (int) evt.getNewValue();
                    swapsLabel.setText("Swaps: " + numSwaps);
                } else if (evt.getPropertyName().equals("access")) {
                    int numAccess = (int) evt.getNewValue();
                    accessLabel.setText("Array Accesses: " + numAccess);
                } else if (evt.getPropertyName().equals("space")) {
                    int numSpace = (int) evt.getNewValue();
                    spaceLabel.setText("Space Taken: " + numSpace + " memory cell(s)");
                }
            }
        });
        c.weighty = 1;
        rightPanel.add(title, c);
        c.gridy = 1;
        c.weighty = 0;
        JLabel algorithms = new JLabel("Sorting Algorithms");
        rightPanel.add(algorithms, c);

        String[] methods = { "bubble", "selection", "insertion", "merge", "quick",
                "shell", "radix", "heap", "comb", "cycle",
                "cocktail", "pancake", "bogo", "gnome",
                "stooge", "oddeven", "bitonic" };
        String[] timeComplexities = { "O(n^2)", "O(n^2)", "O(n^2)", "O(n log n)", "O(n log n)", "O(n log n)",
                "O(nk)", "O(n log n)", "O(n^2)", "O(n^2)", "O(n^2)", "O(n^2)",
                "O((n+1)!)", "O(n^2)", "O(n^(log 3 / log 1.5))", "O(n^2)", "O(n log^2 n)" };
        String[] spaceComplexities = { "O(1)", "O(1)", "O(1)", "O(n)", "O(log n)",
                "O(1)", "O(n + k)", "O(1)", "O(1)", "O(1)", "O(1)",
                "O(1)", "O(1)", "O(1)", "O(1)", "O(1)", "O(n)" };
        JComboBox<String> methodComboBox = new JComboBox<>(methods);
        c.gridy = 2;
        rightPanel.add(methodComboBox, c);
        c.gridy = 3;
        JPanel statistics = new JPanel();
        statistics.setLayout(new BoxLayout(statistics, BoxLayout.Y_AXIS));
        statistics.add(comparisonsLabel);
        statistics.add(swapsLabel);
        statistics.add(accessLabel);
        statistics.add(timeLabel);
        statistics.add(spaceLabel);
        JLabel timeComplexity = new JLabel("Time Complexity: ");
        JLabel spaceComplexity = new JLabel("Space Complexity: ");
        statistics.add(timeComplexity);
        statistics.add(spaceComplexity);
        statistics.add(new JLabel("Array Size: " + arr.length));
        c.weighty = 1;
        rightPanel.add(statistics, c);
        c.weighty = 0;
        methodComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idx = methodComboBox.getSelectedIndex();
                timeComplexity.setText("Time Complexity: " + timeComplexities[idx]);
                spaceComplexity.setText("Space Complexity: " + spaceComplexities[idx]);
            }
        });
        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sortButton.getText().equals("Sort")) {
                    sortButton.setText("Stop");
                    screen.sort((String) methodComboBox.getSelectedItem(), new Runnable() {
                        @Override
                        public void run() {
                            timeLabel.setText(String.format("Time Taken: %.3f ms", screen.timeTaken));
                        }
                    });
                } else if (sortButton.getText().equals("Stop")) {
                    sortButton.setText("Reset");
                    screen.done = true;
                    Sorts.keepSorting = false;
                } else if (sortButton.getText().equals("Reset")) {
                    sortButton.setText("Sort");
                    screen.setArray(screen.copy);
                    screen.first = -1;
                    screen.second = -1;
                    screen.doneIndex = -1;
                    swapsLabel.setText("Swaps: " + 0);
                    comparisonsLabel.setText("Comparisons: " + 0);
                    accessLabel.setText("Array Accesses: " + 0);
                    timeLabel.setText("Time Taken: 0 ms");
                    spaceLabel.setText("Space Taken: 0 memory cell(s)");
                    screen.revalidate();
                    screen.repaint();
                }
            }
        });
        screen.addPropertyChangeListener("done", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ((boolean) evt.getNewValue()) {
                    sortButton.setText("Reset");
                }
            }
        });
        String[] columnNames = { "Algorithm", "Best Time Complexity", "Average Time Complexity",
                "Worst Time Complexity", "Space Complexity", "Stable?", "Adaptive?", "Best Suited For",
                "Sorting Method" };
        Object[][] data = {
                { "Bubble Sort", "O(n)", "O(n^2)", "O(n^2)", "O(1)", "Yes", "Yes", "Small or nearly sorted data",
                        "Comparison-based, Exchanging" },
                { "Selection Sort", "O(n^2)", "O(n^2)", "O(n^2)", "O(1)", "No", "No", "Small data sets",
                        "Comparison-based, Selection" },
                { "Insertion Sort", "O(n)", "O(n^2)", "O(n^2)", "O(1)", "Yes", "Yes", "Small or nearly sorted data",
                        "Comparison-based, Insertion" },
                { "Merge Sort", "O(n log n)", "O(n log n)", "O(n log n)", "O(n)", "Yes", "No", "Large data sets",
                        "Comparison-based, Merging" },
                { "Quick Sort", "O(n log n)", "O(n log n)", "O(n^2)", "O(log n)", "No", "No", "Large data sets",
                        "Comparison-based, Partitioning" },
                { "Shell Sort", "O(n log n)", "O(n(log n)^2)", "O(n(log n)^2)", "O(1)", "No", "Yes",
                        "Medium to large data sets", "Comparison-based, Insertion" },
                { "Radix Sort", "O(nk)", "O(nk)", "O(nk)", "O(n+k)", "Yes", "No",
                        "Large data sets with fixed number of digits/characters", "Non-comparison-based" },
                { "Heap Sort", "O(n log n)", "O(n log n)", "O(n log n)", "O(1)", "No", "No", "Large data sets",
                        "Comparison-based, Selection" },
                { "Comb Sort", "O(n log n)", "Ω(N^2/2^p)", "O(n^2)", "O(1)", "No", "Yes", "Medium to large data sets",
                        "Comparison-based, Exchanging" },
                { "Cycle Sort", "Θ(n^2)", "Θ(n^2)", "Θ(n^2)", "O(1)", "Yes", "No",
                        "Small data sets with limited range of values", "Comparison-based, Selection" },
                { "Cocktail Sort", "O(n^2)", "O(n^2)", "O(n^2)", "O(1)", "Yes", "Yes",
                        "Small to medium data sets or nearly sorted data", "Comparison-based, Exchanging" },
                { "Pancake Sort", "O(n^2)", "O(n^2)", "O(n^2)", "O(1)", "No", "No", "Small data sets",
                        "Comparison-based, Selection" },
                { "Bogo Sort", "O(n)", "O((n+1)!)", "Unbounded", "O(1)", "No", "No", "Not practical for any data set",
                        "Randomized" },
                { "Gnome Sort", "O(n^2)", "O(n^2)", "O(n^2)", "O(1)", "Yes", "Yes", "Small or nearly sorted data",
                        "Comparison-based, Insertion" },
                { "Stooge Sort", "O(n^(log 3 / log 1.5))", "O(n^(log 3 / log 1.5))", "O(n^(log 3 / log 1.5))", "O(n)",
                        "No", "No", "Not practical for any data set", "Comparison-based, Partitioning" },
                { "OddEven Sort", "O(n)", "O(n^2)", "O(n^2)", "O(1)", "Yes", "No", "Small or nearly sorted data",
                        "Comparison-based, Exchanging" },
                { "Bitonic Sort", "O(n log^2 n)", "O(n log^2 n)", "O(n log^2 n)", "O(log n)", "No", "No",
                        "Large data sets with power of 2 elements", "Comparison-based, Partitioning" }
        };
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1100, 300));
        JButton showTableButton = new JButton("Show Table");
        showTableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, scrollPane);
            }
        });
        c.gridy = 4;
        rightPanel.add(showTableButton, c);
        c.gridy = 5;
        c.weighty = 1;
        rightPanel.add(sortButton, c);
        frame.pack();
    }
}
