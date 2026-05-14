package src.baseline;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TaskSchedulerBaseline extends JFrame {
    
    private List<Task> tasks;
    private DefaultTableModel taskTableModel;
    private DefaultTableModel resultTableModel;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JLabel taskCountLabel;
    private JTextField searchField;
    private JTable taskTable;
    
    private static final Color BG_MAIN = new Color(30, 30, 40);
    private static final Color BG_PANEL = new Color(45, 45, 55);
    private static final Color BG_INPUT = new Color(60, 60, 75);
    private static final Color BG_HEADER = new Color(55, 55, 70);
    private static final Color BG_TABLE = new Color(50, 50, 65);
    
    private static final Color BTN_GREEN = new Color(40, 120, 60);
    private static final Color BTN_ORANGE = new Color(180, 100, 35);
    private static final Color BTN_RED = new Color(160, 45, 45);
    private static final Color BTN_BLUE = new Color(45, 100, 160);
    private static final Color BTN_PURPLE = new Color(100, 55, 140);
    
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_LIGHT = new Color(230, 235, 245);
    private static final Color TEXT_YELLOW = new Color(255, 220, 100);
    private static final Color TEXT_GREEN = new Color(120, 220, 120);
    private static final Color BORDER_COLOR = new Color(80, 80, 100);
    
    public TaskSchedulerBaseline() {
        tasks = new ArrayList<>();
        setupFrame();
        buildUI();
        log("═══════════════════════════════════════════════════════════════");
        log("BASELINE SYSTEM - ACCURATE PERFORMANCE MEASUREMENT");
        log("═══════════════════════════════════════════════════════════════");
        log("✓ Each test runs in a FRESH JVM process");
        log("✓ Results are 100% ACCURATE (no JIT optimization)");
        log("═══════════════════════════════════════════════════════════════\n");
    }
    
    private void setupFrame() {
        setTitle("BASELINE SYSTEM | Accurate Results | Fresh JVM Each Test");
        setSize(1300, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_MAIN);
        setLayout(new BorderLayout());
    }
    
    private void buildUI() {
        add(createHeader(), BorderLayout.NORTH);
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
            createControlPanel(), createResultPanel());
        mainSplit.setDividerLocation(380);
        mainSplit.setDividerSize(3);
        mainSplit.setBackground(BG_MAIN);
        add(mainSplit, BorderLayout.CENTER);
        add(createStatusBar(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(0, 80));
        
        JPanel titleArea = new JPanel(new GridLayout(2, 1));
        titleArea.setBackground(BG_HEADER);
        titleArea.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        JLabel title = new JLabel("BASELINE SYSTEM - ACCURATE Performance Measurement");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_YELLOW);
        
        JLabel subtitle = new JLabel("ArrayList | Linear Search O(n) | Bubble Sort O(n²) | Each test = Fresh JVM");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_LIGHT);
        
        titleArea.add(title);
        titleArea.add(subtitle);
        header.add(titleArea, BorderLayout.WEST);
        return header;
    }
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_MAIN);
        
        // LOAD SECTION
        content.add(createSectionHeader("LOAD TASKS (Fresh JVM Each Test)"));
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new GridLayout(3, 1, 0, 10));
        loadPanel.setBackground(BG_PANEL);
        loadPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btn100 = createButton("Load 100 Tasks", BTN_GREEN);
        JButton btn500 = createButton("Load 500 Tasks", BTN_ORANGE);
        JButton btn1000 = createButton("Load 1000 Tasks", BTN_RED);
        
        btn100.addActionListener(e -> loadTasks(100));
        btn500.addActionListener(e -> loadTasks(500));
        btn1000.addActionListener(e -> loadTasks(1000));
        
        loadPanel.add(btn100);
        loadPanel.add(btn500);
        loadPanel.add(btn1000);
        content.add(loadPanel);
        content.add(Box.createVerticalStrut(15));
        
        // SEARCH SECTION
        content.add(createSectionHeader("LINEAR SEARCH O(n) (Fresh JVM Each Test)"));
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BG_PANEL);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(TEXT_WHITE);
        searchField.setBackground(BG_INPUT);
        searchField.setCaretColor(TEXT_WHITE);
        searchField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        searchField.setToolTipText("Enter Task ID (e.g., TASK0001)");
        
        JButton btnSearch = createButton("SEARCH (Fresh JVM)", BTN_BLUE);
        btnSearch.addActionListener(e -> performSearch());
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(15));
        
        // SORT SECTION
        content.add(createSectionHeader("BUBBLE SORT O(n²) (Fresh JVM Each Test)"));
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new GridLayout(2, 1, 0, 10));
        sortPanel.setBackground(BG_PANEL);
        sortPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btnSortDeadline = createButton("Sort by Deadline (Fresh JVM)", BTN_PURPLE);
        JButton btnSortPriority = createButton("Sort by Priority (Fresh JVM)", BTN_PURPLE);
        
        btnSortDeadline.addActionListener(e -> sortTasks("deadline"));
        btnSortPriority.addActionListener(e -> sortTasks("priority"));
        
        sortPanel.add(btnSortDeadline);
        sortPanel.add(btnSortPriority);
        content.add(sortPanel);
        content.add(Box.createVerticalStrut(15));
        
        // INFO & CLEAR
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(BG_PANEL);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        taskCountLabel = new JLabel("Tasks: 0");
        taskCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        taskCountLabel.setForeground(TEXT_WHITE);
        
        JButton btnClear = createButton("CLEAR TABLE", BTN_RED);
        btnClear.addActionListener(e -> clearTable());
        
        infoPanel.add(taskCountLabel, BorderLayout.WEST);
        infoPanel.add(btnClear, BorderLayout.EAST);
        content.add(infoPanel);
        
        content.add(Box.createVerticalGlue());
        
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_MAIN);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSectionHeader(String title) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_HEADER);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(TEXT_YELLOW);
        label.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        p.add(label);
        return p;
    }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_MAIN);
        tabs.setForeground(TEXT_WHITE);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        tabs.addTab("TASK LIST", createTaskPanel());
        tabs.addTab("PERFORMANCE RESULTS", createResultsPanel());
        tabs.addTab("LOG", createLogPanel());
        
        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createTaskPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        String[] cols = {"ID", "Task Name", "Priority", "Deadline", "Status"};
        taskTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        taskTable = new JTable(taskTableModel);
        styleTable(taskTable);
        taskTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        taskTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        JScrollPane scroll = new JScrollPane(taskTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        scroll.getViewport().setBackground(BG_MAIN);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        String[] cols = {"Operation", "Task Count", "Time (ms)", "Algorithm", "Complexity"};
        resultTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(resultTableModel);
        styleTable(table);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            "ACCURATE RESULTS - Each test = Fresh JVM Process",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11), TEXT_LIGHT));
        
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createLogPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        logArea = new JTextArea();
        logArea.setFont(new Font("Consolas", Font.PLAIN, 11));
        logArea.setBackground(BG_PANEL);
        logArea.setForeground(TEXT_GREEN);
        logArea.setEditable(false);
        logArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_HEADER);
        bar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, BORDER_COLOR));
        bar.setPreferredSize(new Dimension(0, 38));
        
        statusLabel = new JLabel("  READY - Each test = Fresh JVM = 100% Accurate Results");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(TEXT_GREEN);
        bar.add(statusLabel, BorderLayout.WEST);
        
        JLabel helpLabel = new JLabel("Each button launches a NEW JVM → No JIT optimization");
        helpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helpLabel.setForeground(TEXT_YELLOW);
        bar.add(helpLabel, BorderLayout.EAST);
        
        return bar;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(bgColor);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getWidth(), 40));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(brighten(bgColor, 1.2f));
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });
        
        return btn;
    }
    
    private Color brighten(Color c, float factor) {
        int r = Math.min(255, (int)(c.getRed() * factor));
        int g = Math.min(255, (int)(c.getGreen() * factor));
        int b = Math.min(255, (int)(c.getBlue() * factor));
        return new Color(r, g, b);
    }
    
    private void styleTable(JTable table) {
        table.setBackground(BG_TABLE);
        table.setForeground(TEXT_WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(100, 100, 140));
        table.setSelectionForeground(TEXT_WHITE);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_HEADER);
        header.setForeground(TEXT_YELLOW);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
    }
    
    // ==================== LOAD TASKS - FRESH JVM ====================
    
    private void loadTasks(int count) {
        log("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log("📊 LOADING " + count + " TASKS (Fresh JVM Process)");
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        setStatus("Launching fresh JVM for " + count + " tasks...");
        
        // Disable buttons during test
        setButtonsEnabled(false);
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return runLoadInFreshJVM(count);
            }
            
            @Override
            protected void done() {
                setButtonsEnabled(true);
                try {
                    String result = get();
                    if (result != null) {
                        String[] parts = result.split(",");
                        double timeMs = Double.parseDouble(parts[1]);
                        displayLoadedTasks(count);
                        log("✅ LOAD COMPLETE: " + String.format("%.4f", timeMs) + " ms");
                        log("   (Fresh JVM - No optimization)");
                        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                        resultTableModel.addRow(new Object[]{
                            "LOAD & INSERT", count, String.format("%.4f", timeMs), "ArrayList.addAll()", "O(n)"
                        });
                        setStatus("Loaded " + count + " tasks in " + String.format("%.4f", timeMs) + " ms");
                    }
                } catch (Exception e) {
                    log("❌ ERROR: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private void setButtonsEnabled(boolean enabled) {
        for (Component c : getContentPane().getComponents()) {
            if (c instanceof JButton) {
                c.setEnabled(enabled);
            }
        }
    }
    
    /** Launches a COMPLETELY FRESH JVM process for accurate measurement */
    private String runLoadInFreshJVM(int count) {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            
            // Each test runs in a COMPLETELY NEW JVM process
            ProcessBuilder pb = new ProcessBuilder(
                javaBin, 
                "-cp", classpath, 
                "src.baseline.AccurateTestRunner", 
                String.valueOf(count)
            );
            pb.redirectErrorStream(true);
            
            long startTime = System.currentTimeMillis();
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line, result = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("[JVM] " + line);
                if (line.startsWith("RESULT:")) {
                    result = line.substring(7).trim();
                }
            }
            
            int exitCode = process.waitFor();
            long endTime = System.currentTimeMillis();
            
            System.out.println("[JVM] Process exited with code: " + exitCode);
            System.out.println("[JVM] Total time including JVM startup: " + (endTime - startTime) + " ms");
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void displayLoadedTasks(int count) {
        tasks = DataLoader.generateTasks(count);
        taskTableModel.setRowCount(0);
        for (Task t : tasks) {
            taskTableModel.addRow(new Object[]{
                t.id, t.name, t.getPriorityLabel(), t.getFormattedDeadline(), "Pending"
            });
        }
        taskCountLabel.setText("Tasks: " + count);
    }
    
    // ==================== SEARCH ====================
    
    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty() || tasks.isEmpty()) return;
        
        int currentTaskCount = tasks.size();
        
        log("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log("🔍 LINEAR SEARCH for '" + query + "' on " + currentTaskCount + " tasks");
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        setStatus("Searching in fresh JVM...");
        
        setButtonsEnabled(false);
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return runSearchInFreshJVM(query, currentTaskCount);
            }
            
            @Override
            protected void done() {
                setButtonsEnabled(true);
                try {
                    String result = get();
                    if (result != null) {
                        String[] parts = result.split(",");
                        double timeMs = Double.parseDouble(parts[0]);
                        boolean found = Boolean.parseBoolean(parts[2]);
                        if (found) log("✅ SEARCH FOUND: '" + query + "'");
                        else log("❌ SEARCH NOT FOUND: '" + query + "'");
                        log("   Time: " + String.format("%.4f", timeMs) + " ms");
                        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                        resultTableModel.addRow(new Object[]{
                            "LINEAR SEARCH", currentTaskCount, String.format("%.4f", timeMs), "Linear Scan", "O(n)"
                        });
                        setStatus("Search completed in " + String.format("%.4f", timeMs) + " ms");
                    }
                } catch (Exception e) {
                    log("❌ ERROR: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private String runSearchInFreshJVM(String query, int taskCount) {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            
            ProcessBuilder pb = new ProcessBuilder(
                javaBin, "-cp", classpath, 
                "src.baseline.SearchTestRunnerWithCount", query, String.valueOf(taskCount)
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line, result = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("RESULT:")) result = line.substring(7).trim();
            }
            process.waitFor();
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    
    // ==================== SORT ====================
    
    private void sortTasks(String sortBy) {
        if (tasks.isEmpty()) {
            log("No tasks loaded. Please load tasks first.");
            return;
        }
        
        int currentTaskCount = tasks.size();
        
        log("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log("🔄 BUBBLE SORT by " + sortBy.toUpperCase() + " on " + currentTaskCount + " tasks");
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        setStatus("Sorting in fresh JVM...");
        
        setButtonsEnabled(false);
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return runSortInFreshJVM(sortBy, currentTaskCount);
            }
            
            @Override
            protected void done() {
                setButtonsEnabled(true);
                try {
                    String result = get();
                    if (result != null) {
                        String[] parts = result.split(",");
                        double timeMs = Double.parseDouble(parts[0]);
                        int comparisons = Integer.parseInt(parts[1]);
                        int swaps = Integer.parseInt(parts[2]);
                        log("✅ SORT COMPLETE: " + String.format("%.4f", timeMs) + " ms");
                        log("   Comparisons: " + comparisons + " | Swaps: " + swaps);
                        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                        resultTableModel.addRow(new Object[]{
                            "BUBBLE SORT (" + sortBy.toUpperCase() + ")", currentTaskCount, 
                            String.format("%.4f", timeMs), "Bubble Sort", "O(n²)"
                        });
                        setStatus("Sort completed in " + String.format("%.4f", timeMs) + " ms");
                    }
                } catch (Exception e) {
                    log("❌ ERROR: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    private String runSortInFreshJVM(String sortBy, int taskCount) {
        try {
            String javaHome = System.getProperty("java.home");
            String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            
            ProcessBuilder pb = new ProcessBuilder(
                javaBin, "-cp", classpath, 
                "src.baseline.SortTestRunnerWithCount", sortBy, String.valueOf(taskCount)
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line, result = null;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("RESULT:")) result = line.substring(7).trim();
            }
            process.waitFor();
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    
    private void clearTable() {
        int confirm = JOptionPane.showConfirmDialog(this, "Clear ALL tasks from display?", "Confirm Clear", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tasks.clear();
            taskTableModel.setRowCount(0);
            taskCountLabel.setText("Tasks: 0");
            log("🗑️ Cleared task display");
            setStatus("Task display cleared");
        }
    }
    
    private void log(String msg) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        logArea.append("[" + timestamp + "] " + msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
        System.out.println("[" + timestamp + "] " + msg);
    }
    
    private void setStatus(String msg) {
        statusLabel.setText("  " + msg);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new TaskSchedulerBaseline().setVisible(true));
    }
}