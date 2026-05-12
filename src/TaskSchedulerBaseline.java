package src;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TaskSchedulerBaseline extends JFrame {
    
    // Data storage
    private List<Task> tasks;
    private DefaultTableModel taskTableModel;
    private DefaultTableModel resultTableModel;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JLabel taskCountLabel;
    private JTextField searchField;
    
    // Color scheme - Dark theme with high contrast
    private static final Color BG_MAIN = new Color(30, 30, 40);
    private static final Color BG_PANEL = new Color(45, 45, 55);
    private static final Color BG_INPUT = new Color(60, 60, 75);
    private static final Color BG_BUTTON = new Color(70, 70, 90);
    private static final Color BG_HEADER = new Color(55, 55, 70);
    private static final Color BG_TABLE = new Color(50, 50, 65);
    
    // Button colors - Dark but with light text
    private static final Color BTN_GREEN = new Color(40, 120, 60);
    private static final Color BTN_ORANGE = new Color(180, 100, 35);
    private static final Color BTN_RED = new Color(160, 45, 45);
    private static final Color BTN_BLUE = new Color(45, 100, 160);
    private static final Color BTN_PURPLE = new Color(100, 55, 140);
    
    // Text colors - ALL LIGHT for visibility
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_LIGHT = new Color(230, 235, 245);
    private static final Color TEXT_YELLOW = new Color(255, 220, 100);
    private static final Color TEXT_GREEN = new Color(120, 220, 120);
    private static final Color BORDER_COLOR = new Color(80, 80, 100);
    
    public TaskSchedulerBaseline() {
        tasks = new ArrayList<>();
        setupFrame();
        buildUI();
        log("BASELINE SYSTEM READY - ArrayList Implementation");
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    private void setupFrame() {
        setTitle("BASELINE TASK SCHEDULER | O(n) Search | O(n²) Bubble Sort | FCFS");
        setSize(1400, 850);
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
    
    // ==================== HEADER ====================
    
    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_HEADER);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(0, 80));
        
        JPanel titleArea = new JPanel(new GridLayout(2, 1));
        titleArea.setBackground(BG_HEADER);
        titleArea.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        JLabel title = new JLabel("BASELINE SYSTEM - Simple ArrayList with Inefficient Algorithms");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_YELLOW);
        
        JLabel subtitle = new JLabel("Linear Search O(n)  |  Bubble Sort O(n²)  |  First-Come-First-Served Scheduling");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(TEXT_LIGHT);
        
        titleArea.add(title);
        titleArea.add(subtitle);
        
        header.add(titleArea, BorderLayout.WEST);
        return header;
    }
    
    // ==================== CONTROL PANEL ====================
    
    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG_MAIN);
        
        // Load Section
        content.add(createSectionHeader("LOAD TASKS"));
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
        
        // Search Section
        content.add(createSectionHeader("LINEAR SEARCH O(n)"));
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BG_PANEL);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(TEXT_WHITE);
        searchField.setBackground(BG_INPUT);
        searchField.setCaretColor(TEXT_WHITE);
        searchField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        searchField.setToolTipText("Enter Task ID to search (e.g., TASK0001)");
        
        JButton btnSearch = createButton("SEARCH", BTN_BLUE);
        btnSearch.addActionListener(e -> performSearch());
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(15));
        
        // Sort Section
        content.add(createSectionHeader("BUBBLE SORT O(n²)"));
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new GridLayout(2, 1, 0, 10));
        sortPanel.setBackground(BG_PANEL);
        sortPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btnSortDeadline = createButton("Sort by Deadline", BTN_PURPLE);
        JButton btnSortPriority = createButton("Sort by Priority", BTN_PURPLE);
        
        btnSortDeadline.addActionListener(e -> bubbleSortByDeadline());
        btnSortPriority.addActionListener(e -> bubbleSortByPriority());
        
        sortPanel.add(btnSortDeadline);
        sortPanel.add(btnSortPriority);
        content.add(sortPanel);
        content.add(Box.createVerticalStrut(15));
        
        // Info & Clear
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(BG_PANEL);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        taskCountLabel = new JLabel("Tasks: 0");
        taskCountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        taskCountLabel.setForeground(TEXT_WHITE);
        
        JButton btnClear = createButton("CLEAR ALL", BTN_RED);
        btnClear.addActionListener(e -> clearAllTasks());
        
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
    
    // ==================== RESULT PANEL ====================
    
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
        
        JTable table = new JTable(taskTableModel);
        styleTable(table);
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        
        JScrollPane scroll = new JScrollPane(table);
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
            "Performance Measurements - Real Execution Time (milliseconds)",
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
        
        statusLabel = new JLabel("  SYSTEM READY");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(TEXT_GREEN);
        bar.add(statusLabel, BorderLayout.WEST);
        
        JLabel helpLabel = new JLabel("1. Load Tasks  |  2. Search by ID  |  3. Sort by Deadline/Priority");
        helpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helpLabel.setForeground(TEXT_LIGHT);
        bar.add(helpLabel, BorderLayout.EAST);
        
        return bar;
    }
    
    // ==================== BUTTON STYLES ====================
    
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
        
        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_HEADER);
        header.setForeground(TEXT_YELLOW);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
    }
    
    // ==================== CORE FUNCTIONALITY ====================
    
    private void loadTasks(int count) {
        tasks.clear();
        
        long genStart = System.nanoTime();
        List<Task> generated = DataLoader.generateTasks(count);
        long genEnd = System.nanoTime();
        double genTime = (genEnd - genStart) / 1_000_000.0;
        
        long insertStart = System.nanoTime();
        DataLoader.loadIntoSystem(generated, tasks);
        long insertEnd = System.nanoTime();
        double insertTime = (insertEnd - insertStart) / 1_000_000.0;
        
        refreshTaskTable();
        taskCountLabel.setText("Tasks: " + tasks.size());
        
        double totalTime = genTime + insertTime;
        
        log(String.format("LOAD: %d tasks | Generation: %.3f ms | Insertion: %.3f ms | Total: %.3f ms", 
            count, genTime, insertTime, totalTime));
        
        addResultRow("LOAD & INSERT", count, totalTime, "ArrayList.add()", "O(1)");
        setStatus(String.format("Loaded %d tasks (%.3f ms total)", count, totalTime));
    }
    
    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty() || tasks.isEmpty()) {
            log("Search cancelled: " + (tasks.isEmpty() ? "No tasks loaded" : "Empty search term"));
            return;
        }
        
        long start = System.nanoTime();
        Task found = null;
        int comparisons = 0;
        
        for (Task t : tasks) {
            comparisons++;
            if (t.id.equalsIgnoreCase(query)) {
                found = t;
                break;
            }
        }
        
        long end = System.nanoTime();
        double searchTime = (end - start) / 1_000_000.0;
        
        if (found != null) {
            log(String.format("SEARCH FOUND: '%s' | Time: %.3f ms | Comparisons: %d | O(n) scan", 
                query, searchTime, comparisons));
        } else {
            log(String.format("SEARCH NOT FOUND: '%s' | Time: %.3f ms | Scanned all %d tasks", 
                query, searchTime, tasks.size()));
        }
        
        addResultRow("LINEAR SEARCH", tasks.size(), searchTime, "Linear Scan", "O(n)");
        setStatus(String.format("Search completed in %.3f ms", searchTime));
    }
    
    private void bubbleSortByDeadline() {
        if (tasks.isEmpty()) {
            log("Cannot sort: No tasks loaded");
            return;
        }
        
        List<Task> toSort = new ArrayList<>(tasks);
        
        long start = System.nanoTime();
        int n = toSort.size();
        int comparisons = 0;
        int swaps = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (toSort.get(j).deadline > toSort.get(j + 1).deadline) {
                    Task temp = toSort.get(j);
                    toSort.set(j, toSort.get(j + 1));
                    toSort.set(j + 1, temp);
                    swaps++;
                }
            }
        }
        
        long end = System.nanoTime();
        double sortTime = (end - start) / 1_000_000.0;
        
        refreshTaskTable(toSort);
        
        log(String.format("BUBBLE SORT (Deadline) | Time: %.3f ms | Comparisons: %d | Swaps: %d | O(n²): ~%d ops", 
            sortTime, comparisons, swaps, n * n));
        
        addResultRow("BUBBLE SORT (DEADLINE)", n, sortTime, "Bubble Sort", "O(n²)");
        setStatus(String.format("Sorted by deadline in %.3f ms", sortTime));
    }
    
    private void bubbleSortByPriority() {
        if (tasks.isEmpty()) {
            log("Cannot sort: No tasks loaded");
            return;
        }
        
        List<Task> toSort = new ArrayList<>(tasks);
        
        long start = System.nanoTime();
        int n = toSort.size();
        int comparisons = 0;
        int swaps = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparisons++;
                if (toSort.get(j).priority > toSort.get(j + 1).priority) {
                    Task temp = toSort.get(j);
                    toSort.set(j, toSort.get(j + 1));
                    toSort.set(j + 1, temp);
                    swaps++;
                }
            }
        }
        
        long end = System.nanoTime();
        double sortTime = (end - start) / 1_000_000.0;
        
        refreshTaskTable(toSort);
        
        log(String.format("BUBBLE SORT (Priority) | Time: %.3f ms | Comparisons: %d | Swaps: %d | O(n²): ~%d ops", 
            sortTime, comparisons, swaps, n * n));
        
        addResultRow("BUBBLE SORT (PRIORITY)", n, sortTime, "Bubble Sort", "O(n²)");
        setStatus(String.format("Sorted by priority in %.3f ms", sortTime));
    }
    
    private void refreshTaskTable() {
        refreshTaskTable(tasks);
    }
    
    private void refreshTaskTable(List<Task> taskList) {
        taskTableModel.setRowCount(0);
        for (Task t : taskList) {
            taskTableModel.addRow(new Object[]{
                t.id, 
                t.name,
                t.getPriorityLabel(),
                t.getFormattedDeadline(),
                t.completed ? "Done" : "Pending"
            });
        }
    }
    
    private void clearAllTasks() {
        if (tasks.isEmpty()) {
            log("No tasks to clear");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Delete ALL %d tasks? This cannot be undone.", tasks.size()),
            "Confirm Clear", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            int count = tasks.size();
            tasks.clear();
            refreshTaskTable();
            taskCountLabel.setText("Tasks: 0");
            log(String.format("Cleared all %d tasks", count));
            setStatus("All tasks cleared");
        }
    }
    
    private void addResultRow(String operation, int size, double time, String algorithm, String complexity) {
        resultTableModel.addRow(new Object[]{
            operation, size, String.format("%.4f", time), algorithm, complexity
        });
    }
    
    private void log(String msg) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        logArea.append("[" + timestamp + "] " + msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }
    
    private void setStatus(String msg) {
        statusLabel.setText("  " + msg);
    }
    
    public static void main(String[] args) {
        // Set FlatLaf or default look and feel that respects custom colors
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Force UI colors
        UIManager.put("Panel.background", BG_MAIN);
        UIManager.put("Table.background", BG_TABLE);
        UIManager.put("Table.foreground", TEXT_WHITE);
        UIManager.put("TableHeader.background", BG_HEADER);
        UIManager.put("TableHeader.foreground", TEXT_YELLOW);
        UIManager.put("TabbedPane.background", BG_MAIN);
        UIManager.put("TabbedPane.foreground", TEXT_WHITE);
        
        SwingUtilities.invokeLater(() -> new TaskSchedulerBaseline().setVisible(true));
    }
}