package src.optimized;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/** OPTIMIZED GUI - Each test = Fresh JVM + JIT disabled = Real times */
public class TaskSchedulerOptimized extends JFrame {
    
    private List<Task> tasks;
    private DefaultTableModel taskTableModel;
    private DefaultTableModel resultTableModel;
    private DefaultTableModel scheduleTableModel;
    private JTextArea logArea;
    private JLabel statusLabel;
    private JLabel taskCountLabel;
    private JTextField searchField;
    private JTable taskTable;
    
    // Dark theme colors - ALL DEFINED
    private static final Color BG_MAIN = new Color(30, 30, 40);
    private static final Color BG_PANEL = new Color(45, 45, 55);
    private static final Color BG_INPUT = new Color(60, 60, 75);
    private static final Color BG_HEADER = new Color(55, 55, 70);
    private static final Color BG_TABLE = new Color(50, 50, 65);
    private static final Color BORDER_COLOR = new Color(80, 80, 100);
    
    // Button colors
    private static final Color BTN_GREEN = new Color(40, 120, 60);
    private static final Color BTN_ORANGE = new Color(180, 100, 35);
    private static final Color BTN_RED = new Color(160, 45, 45);
    private static final Color BTN_BLUE = new Color(45, 100, 160);
    private static final Color BTN_PURPLE = new Color(100, 55, 140);
    private static final Color BTN_CYAN = new Color(45, 140, 140);
    private static final Color BTN_PINK = new Color(180, 70, 120);
    
    // Text colors - ALL DEFINED
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_LIGHT = new Color(230, 235, 245);  // ← DEFINED
    private static final Color TEXT_YELLOW = new Color(255, 220, 100);
    private static final Color TEXT_GREEN = new Color(120, 220, 120);
    private static final Color TEXT_CYAN = new Color(100, 220, 220);
    
    public TaskSchedulerOptimized() {
        tasks = new ArrayList<>();
        setupFrame();
        buildUI();
        log("OPTIMIZED SYSTEM - Real Times | Fresh JVM + JIT Disabled");
    }
    
    private void setupFrame() {
        setTitle("OPTIMIZED SYSTEM | O(1) Search | O(n log n) Sort");
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
        
        JLabel title = new JLabel("OPTIMIZED SYSTEM - REAL Performance Measurement");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(TEXT_YELLOW);
        
        JLabel subtitle = new JLabel("HashMap O(1) | MinHeap O(log n) | MergeSort O(n log n) | Topological Sort O(V+E)");
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
        content.add(createSectionHeader("LOAD TASKS (Fresh JVM + JIT Disabled)"));
        JPanel loadPanel = new JPanel();
        loadPanel.setLayout(new GridLayout(2, 2, 10, 8));
        loadPanel.setBackground(BG_PANEL);
        loadPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btn100 = createButton("Load 100 Tasks", BTN_GREEN);
        JButton btn500 = createButton("Load 500 Tasks", BTN_ORANGE);
        JButton btn1000 = createButton("Load 1000 Tasks", BTN_RED);
        JButton btnDeps = createButton("Load 500 (with Dependencies)", BTN_PINK);
        
        btn100.addActionListener(e -> runTest(100, "load"));
        btn500.addActionListener(e -> runTest(500, "load"));
        btn1000.addActionListener(e -> runTest(1000, "load"));
        btnDeps.addActionListener(e -> runTest(500, "load_deps"));
        
        loadPanel.add(btn100);
        loadPanel.add(btn500);
        loadPanel.add(btn1000);
        loadPanel.add(btnDeps);
        content.add(loadPanel);
        content.add(Box.createVerticalStrut(15));
        
        // SEARCH SECTION
        content.add(createSectionHeader("HASH MAP SEARCH O(1)"));
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(BG_PANEL);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(TEXT_WHITE);
        searchField.setBackground(BG_INPUT);
        searchField.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 1));
        searchField.addActionListener(e -> performSearch());
        
        JButton btnSearch = createButton("SEARCH (O(1))", BTN_CYAN);
        btnSearch.addActionListener(e -> performSearch());
        
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        content.add(searchPanel);
        content.add(Box.createVerticalStrut(15));
        
        // SORT SECTION
        content.add(createSectionHeader("MERGE SORT O(n log n)"));
        JPanel sortPanel = new JPanel();
        sortPanel.setLayout(new GridLayout(2, 1, 0, 8));
        sortPanel.setBackground(BG_PANEL);
        sortPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btnSortDeadline = createButton("Sort by Deadline", BTN_PURPLE);
        JButton btnSortPriority = createButton("Sort by Priority", BTN_PURPLE);
        
        btnSortDeadline.addActionListener(e -> runTest(500, "sort_deadline"));
        btnSortPriority.addActionListener(e -> runTest(500, "sort_priority"));
        
        sortPanel.add(btnSortDeadline);
        sortPanel.add(btnSortPriority);
        content.add(sortPanel);
        content.add(Box.createVerticalStrut(15));
        
        // ADVANCED ALGORITHMS
        content.add(createSectionHeader("ADVANCED ALGORITHMS"));
        JPanel advancedPanel = new JPanel();
        advancedPanel.setLayout(new GridLayout(2, 1, 0, 8));
        advancedPanel.setBackground(BG_PANEL);
        advancedPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JButton btnHeap = createButton("Heap Schedule (Priority Order)", BTN_BLUE);
        JButton btnTopo = createButton("Topological Sort (Dependency Order)", BTN_CYAN);
        
        btnHeap.addActionListener(e -> generateHeapSchedule());
        btnTopo.addActionListener(e -> generateTopologicalSchedule());
        
        advancedPanel.add(btnHeap);
        advancedPanel.add(btnTopo);
        content.add(advancedPanel);
        content.add(Box.createVerticalStrut(15));
        
        // INFO & CLEAR
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
        tabs.addTab("SCHEDULE", createSchedulePanel());
        tabs.addTab("PERFORMANCE RESULTS", createResultsPanel());
        tabs.addTab("LOG", createLogPanel());
        
        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createTaskPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        String[] cols = {"ID", "Task Name", "Priority", "Deadline", "Prerequisites", "Status"};
        taskTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        taskTable = new JTable(taskTableModel);
        styleTable(taskTable);
        
        JScrollPane scroll = new JScrollPane(taskTable);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createSchedulePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_MAIN);
        
        String[] cols = {"Order", "Task ID", "Task Name", "Priority", "Deadline", "Prerequisites"};
        scheduleTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable scheduleTable = new JTable(scheduleTableModel);
        styleTable(scheduleTable);
        
        JScrollPane scroll = new JScrollPane(scheduleTable);
        scroll.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(BORDER_COLOR, 1),
            "Optimized Task Execution Order",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 11), TEXT_CYAN));
        
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
            "REAL TIMES - Fresh JVM + JIT Disabled (milliseconds)",
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
        
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_HEADER);
        bar.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, BORDER_COLOR));
        bar.setPreferredSize(new Dimension(0, 38));
        
        statusLabel = new JLabel("  READY - Each test = Fresh JVM + JIT disabled = Real times");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        statusLabel.setForeground(TEXT_GREEN);
        bar.add(statusLabel, BorderLayout.WEST);
        
        JLabel helpLabel = new JLabel("Load with Dependencies → Topological Sort shows prerequisite order");
        helpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helpLabel.setForeground(TEXT_YELLOW);
        bar.add(helpLabel, BorderLayout.EAST);
        
        return bar;
    }
    
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(TEXT_WHITE);
        btn.setBackground(bgColor);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(btn.getWidth(), 38));
        return btn;
    }
    
    private void styleTable(JTable table) {
        table.setBackground(BG_TABLE);
        table.setForeground(TEXT_WHITE);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        table.setRowHeight(28);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(new Color(100, 100, 140));
        table.setSelectionForeground(TEXT_WHITE);
        table.setShowVerticalLines(false);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(BG_HEADER);
        header.setForeground(TEXT_YELLOW);
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, BORDER_COLOR));
    }
    
    // ==================== RUN TESTS IN FRESH JVM ====================
    
    private void runTest(int taskCount, String operation) {
        log("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        log("📊 " + operation.toUpperCase() + " on " + taskCount + " tasks (Fresh JVM)");
        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        setStatus("Running " + operation + "...");
        
        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return runInSeparateJVM(taskCount, operation);
            }
            @Override
            protected void done() {
                try {
                    String result = get();
                    if (result != null) {
                        String[] parts = result.split(",");
                        double timeMs = Double.parseDouble(parts[1]);
                        
                        String displayName = getDisplayName(operation);
                        log("✅ COMPLETE: " + String.format("%.4f", timeMs) + " ms");
                        log("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
                        
                        resultTableModel.addRow(new Object[]{
                            displayName, taskCount, String.format("%.4f", timeMs), 
                            getAlgorithm(operation), getComplexity(operation)
                        });
                        
                        if (operation.equals("load") || operation.equals("load_deps")) {
                            displayLoadedTasks(taskCount, operation.equals("load_deps"));
                        }
                        setStatus("✓ " + operation + " completed in " + String.format("%.4f", timeMs) + " ms");
                    }
                } catch (Exception e) { log("ERROR: " + e.getMessage()); }
            }
        };
        worker.execute();
    }
    
    /** CRITICAL: -Xint disables JIT compilation for REAL execution time */
    private String runInSeparateJVM(int taskCount, String operation) {
        try {
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            String classpath = System.getProperty("java.class.path");
            
            ProcessBuilder pb = new ProcessBuilder(
                javaBin, "-Xint", "-cp", classpath, 
                "src.optimized.OptimizedTestRunner", String.valueOf(taskCount), operation
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
        } catch (Exception e) { return null; }
    }
    
    private String getDisplayName(String operation) {
        switch (operation) {
            case "load": return "OPTIMIZED LOAD";
            case "load_deps": return "LOAD WITH DEPS";
            case "sort_deadline": return "MERGE SORT (DEADLINE)";
            case "sort_priority": return "MERGE SORT (PRIORITY)";
            default: return operation.toUpperCase();
        }
    }
    
    private String getAlgorithm(String operation) {
        switch (operation) {
            case "load": return "HashMap + Heap";
            case "load_deps": return "HashMap + Heap + Graph";
            case "sort_deadline": return "Merge Sort";
            case "sort_priority": return "Merge Sort";
            default: return "Unknown";
        }
    }
    
    private String getComplexity(String operation) {
        switch (operation) {
            case "load": return "O(1)+O(log n)";
            case "load_deps": return "O(1)+O(log n)";
            case "sort_deadline": return "O(n log n)";
            case "sort_priority": return "O(n log n)";
            default: return "Unknown";
        }
    }
    
    private void displayLoadedTasks(int count, boolean withDependencies) {
        tasks = withDependencies ? 
            OptimizedDataLoader.generateTasksWithDependencies(count, 30) : 
            OptimizedDataLoader.generateTasks(count);
        
        taskTableModel.setRowCount(0);
        for (Task t : tasks) {
            String prereqs = t.prerequisites.isEmpty() ? "None" : String.join(", ", t.prerequisites);
            taskTableModel.addRow(new Object[]{
                t.id, t.name, t.getPriorityLabel(), t.getFormattedDeadline(), prereqs, "Pending"
            });
        }
        taskCountLabel.setText("Tasks: " + tasks.size());
    }
    
    // ==================== SEARCH ====================
    
    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty() || tasks.isEmpty()) return;
        
        Map<String, Task> searchMap = new HashMap<>();
        for (Task t : tasks) searchMap.put(t.id, t);
        
        long startTime = System.nanoTime();
        Task found = searchMap.get(query);
        double searchTime = (System.nanoTime() - startTime) / 1_000_000.0;
        
        if (found != null) {
            log("✅ HASHMAP SEARCH FOUND: '" + query + "' | Time: " + String.format("%.4f", searchTime) + " ms");
            for (int i = 0; i < taskTableModel.getRowCount(); i++) {
                if (taskTableModel.getValueAt(i, 0).toString().equalsIgnoreCase(query)) {
                    taskTable.setRowSelectionInterval(i, i);
                    break;
                }
            }
        } else {
            log("❌ HASHMAP SEARCH NOT FOUND: '" + query + "' | Time: " + String.format("%.4f", searchTime) + " ms");
        }
        
        resultTableModel.addRow(new Object[]{
            "HASHMAP SEARCH", tasks.size(), String.format("%.4f", searchTime), "HashMap.get()", "O(1) avg"
        });
        setStatus("Search completed in " + String.format("%.4f", searchTime) + " ms");
    }
    
    // ==================== HEAP SCHEDULE ====================
    
    private void generateHeapSchedule() {
        if (tasks.isEmpty()) { log("No tasks loaded"); return; }
        
        log("📊 HEAP SCHEDULE (Priority Order) for " + tasks.size() + " tasks");
        setStatus("Generating heap schedule...");
        
        long startTime = System.nanoTime();
        
        MinHeap<Task> heap = new MinHeap<>();
        for (Task t : tasks) heap.insert(t);
        
        scheduleTableModel.setRowCount(0);
        int order = 1;
        
        while (!heap.isEmpty()) {
            Task next = heap.extractMin();
            String prereqs = next.prerequisites.isEmpty() ? "None" : String.join(", ", next.prerequisites);
            scheduleTableModel.addRow(new Object[]{
                order++, next.id, next.name, next.getPriorityLabel(), next.getFormattedDeadline(), prereqs
            });
        }
        
        long endTime = System.nanoTime();
        double scheduleTime = (endTime - startTime) / 1_000_000.0;
        
        log("✅ HEAP SCHEDULE: " + String.format("%.4f", scheduleTime) + " ms");
        resultTableModel.addRow(new Object[]{
            "HEAP SCHEDULE", tasks.size(), String.format("%.4f", scheduleTime), "MinHeap Extract", "O(n log n)"
        });
        
        JTabbedPane tabs = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(1);
        tabs.setSelectedIndex(1);
    }
    
    // ==================== TOPOLOGICAL SORT ====================
    
    private void generateTopologicalSchedule() {
        if (tasks.isEmpty()) { log("No tasks loaded"); return; }
        
        log("📊 TOPOLOGICAL SORT (Dependency Order) for " + tasks.size() + " tasks");
        setStatus("Generating topological sort...");
        
        Graph<String> graph = new Graph<>();
        Map<String, Task> taskMap = new HashMap<>();
        
        for (Task t : tasks) {
            graph.addVertex(t.id);
            taskMap.put(t.id, t);
        }
        
        for (Task t : tasks) {
            for (String prereq : t.prerequisites) {
                graph.addEdge(prereq, t.id);
            }
        }
        
        if (graph.hasCycle()) {
            log("❌ CYCLE DETECTED! Cannot generate topological order.");
            JOptionPane.showMessageDialog(this, "Circular dependency detected!", "Cycle Detected", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        long startTime = System.nanoTime();
        List<String> topoOrder = graph.topologicalSort();
        long endTime = System.nanoTime();
        double sortTime = (endTime - startTime) / 1_000_000.0;
        
        scheduleTableModel.setRowCount(0);
        int order = 1;
        for (String taskId : topoOrder) {
            Task t = taskMap.get(taskId);
            if (t != null) {
                String prereqs = t.prerequisites.isEmpty() ? "None" : String.join(", ", t.prerequisites);
                scheduleTableModel.addRow(new Object[]{
                    order++, t.id, t.name, t.getPriorityLabel(), t.getFormattedDeadline(), prereqs
                });
            }
        }
        
        log("✅ TOPOLOGICAL SORT: " + String.format("%.4f", sortTime) + " ms");
        resultTableModel.addRow(new Object[]{
            "TOPOLOGICAL SORT", tasks.size(), String.format("%.4f", sortTime), "Kahn's Algorithm", "O(V+E)"
        });
        
        JTabbedPane tabs = (JTabbedPane) ((JPanel) getContentPane().getComponent(1)).getComponent(1);
        tabs.setSelectedIndex(1);
    }
    
    private void clearAllTasks() {
        int confirm = JOptionPane.showConfirmDialog(this, "Clear ALL tasks?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            tasks.clear();
            taskTableModel.setRowCount(0);
            scheduleTableModel.setRowCount(0);
            taskCountLabel.setText("Tasks: 0");
            log("🗑️ Cleared task display");
        }
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
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } 
        catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new TaskSchedulerOptimized().setVisible(true));
    }
}