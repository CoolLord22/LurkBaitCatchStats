package com.coollord22.melonfishin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class Main {
    public static Date startDate = null;
    public static Date endDate = null;
    public static List<String> customCatches;
    public static List<String> trackedCatchTypes;
    public static HashMap<String, HashMap<String, Integer>> dataMap;
    public static SimpleDateFormat df;
    public static JLabel fileNotFound;
    public static JTextField textFileLoc;
    public static JTextField textStartDate;
    public static JTextField textEndDate;
    public static JButton buttonTable;
    public static DefaultListModel<String> customCatchesList;

    public static void main(String[] args) {
        setUITheme();
        customCatches = new ArrayList<>();
        trackedCatchTypes = new ArrayList<>();
        textFileLoc = new JTextField();
        textStartDate = new JTextField(15);
        textEndDate = new JTextField(15);
        dataMap = new HashMap<>();
        df = new SimpleDateFormat("MM/dd/yyyy");
        customCatchesList = new DefaultListModel<>();

        loadMainGUI();
    }

    private static void setUITheme() {
        try {
            UIManager.put("control", new Color(124, 113, 241));
            UIManager.put("info", new Color(164, 131, 246));
            UIManager.put("nimbusLightBackground", new Color(167, 155, 255));
            UIManager.put("nimbusBase", new Color(46, 17, 178));
            UIManager.put("nimbusFocus", new Color(126, 117, 248));
            UIManager.put("nimbusSelectionBackground", new Color(85, 53, 210));
            UIManager.put("nimbusSelectedText", new Color(157, 139, 241));
            UIManager.put("text", new Color(44, 12, 108));
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |  InstantiationException ignored) {}
    }

    private static void loadMainGUI() {
        createStaticUIElements();
        // Other UI elements
        JButton buttonBrowse = new JButton("Browse...");
        JButton buttonDates = new JButton("Set Dates");
        JLabel textTo = new JLabel("to");

        JList<String> customCatchesSelector = new JList<>(customCatchesList);
        customCatchesSelector.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if(super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                }
                else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        // Creating the main GUI
        JDialog mainGUI = new JDialog();
        mainGUI.setTitle("LurkBait Twitch Fishing Data Collector");
        mainGUI.setPreferredSize(new Dimension(550, 375));
        mainGUI.setLayout(new GridBagLayout());
        mainGUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        GridBagConstraints c = new GridBagConstraints();

        // Row 1
        c.gridx = 0;
        c.gridwidth = 4;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,0,3,3);
        mainGUI.add(textFileLoc, c);

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,0);
        mainGUI.add(buttonBrowse, c);

        // Row 2
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,0,3,3);
        mainGUI.add(textStartDate, c);

        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(0,0,0,0);
        mainGUI.add(textTo, c);

        c.gridx = 2;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
        mainGUI.add(textEndDate, c);

        c.gridx = 3;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
        mainGUI.add(buttonDates, c);

        c.gridx = 4;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,0);
        mainGUI.add(buttonTable, c);

        // Row 3
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 5;
        mainGUI.add(fileNotFound, c);

        // Row 4
        c.gridx = 0;
        c.gridy = 4;
        mainGUI.add(customCatchesSelector, c);
        c.insets = new Insets(10,3,3,3);


        // Making GUI Visible
        mainGUI.pack();
        mainGUI.setLocationRelativeTo(null);
        mainGUI.setVisible(true);

        buttonBrowse.addActionListener(e -> {
            JFileChooser fileGUI = new JFileChooser();
            fileGUI.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileGUI.setAcceptAllFileFilterUsed(false);
            fileGUI.showSaveDialog(null);
            try {
                textFileLoc.setText(fileGUI.getSelectedFile().getAbsolutePath());
            } catch (NullPointerException ignored) {}
        });

        customCatchesSelector.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                trackedCatchTypes.clear();
                for(Object element : ((JList<?>) e.getSource()).getSelectedValuesList()) {
                    trackedCatchTypes.add((String) element);
                }
                buttonTable.setEnabled(!trackedCatchTypes.isEmpty());
            }
        });

        buttonDates.addActionListener(e -> setDates());

        buttonTable.addActionListener(e -> tabulateData());
    }

    private static void createStaticUIElements() {
        buttonTable = new JButton("Make Table");

        fileNotFound = new JLabel("Warning, current file directory not found!", SwingConstants.CENTER);
        fileNotFound.setForeground(new Color(50, 40, 115));
        fileNotFound.setVisible(false);
        fileNotFound.setFont(new Font(fileNotFound.getFont().getFontName(), Font.BOLD, 14));

        textStartDate.setText("Start Date");
        textEndDate.setText("End Date");
        textStartDate.setEditable(false);
        textEndDate.setEditable(false);
        textStartDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textEndDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        textFileLoc.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                customCatchesList.clear();
                trackedCatchTypes.clear();
                File f = new File(textFileLoc.getText());
                if(f.exists() && f.isDirectory()) {
                    File catches = new File(textFileLoc.getText() + File.separator + "CatchData.txt");
                    File customCatches = new File(textFileLoc.getText() + File.separator + "CustomCatches.txt");
                    if(!catches.exists() || !customCatches.exists()) {
                        fileNotFound.setText("Warning, CatchData/CustomCatches files not found in directory!");
                        fileNotFound.setVisible(true);
                        buttonTable.setEnabled(false);
                    } else {
                        fileNotFound.setVisible(false);
                        populateJList();
                    }
                } else {
                    fileNotFound.setText("Warning, current file directory not found!");
                    fileNotFound.setVisible(true);
                    buttonTable.setEnabled(false);
                }
            }
        });
        textFileLoc.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textFileLoc.setColumns(55);
        textFileLoc.setText("C:\\Users\\ash\\AppData\\LocalLow\\BLAMCAM Interactive\\LurkBait Twitch Fishing\\");
    }

    private static void populateJList() {
        FileReader file;
        try {
            file = new FileReader(textFileLoc.getText() + File.separator + "CustomCatches.txt");
            JsonReader jsonReader = new JsonReader(file);
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {
                jsonReader.skipValue(); // skip over the name
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    String name = jsonReader.nextName();
                    if(name.equalsIgnoreCase("FullName"))
                        customCatches.add(jsonReader.nextString());
                    else jsonReader.skipValue();
                }
                jsonReader.endObject();
            }
            jsonReader.endObject();
            jsonReader.close();

        } catch (IOException ignored) {}

        if(customCatches != null && !customCatches.isEmpty()) {
            for(String catchType : customCatches)
                customCatchesList.addElement(catchType);
        }
    }

    private static void setDates() {
        DateGUI dg = new DateGUI();
        dg.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        dg.setVisible(true);
        if(dg.startDate == null || dg.endDate == null) {
            startDate = null;
            endDate = null;
            textStartDate.setText("Start Date");
            textEndDate.setText("End Date");
            return;
        }
        startDate = Date.from(dg.startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        endDate = Date.from(dg.endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        textStartDate.setText(df.format(startDate));
        textEndDate.setText(df.format(endDate));
    }

    private static void tabulateData() {
        Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();
        FileReader file;
        CatchData[] features = new CatchData[0];
        try {
            file = new FileReader(textFileLoc.getText() + File.separator + "CatchData.txt");
            features = gson.fromJson(file, CatchData[].class);
            file.close();
        } catch (IOException ignored) {}

        dataMap = new HashMap<>();
        for(CatchData data : features) {
            if(startDate != null && endDate != null) {
                if(!data.date.after(startDate) || !data.date.before(endDate))
                    continue;
            }
            if(trackedCatchTypes.contains(data.name)) {
                HashMap<String, Integer> userCatchData = new HashMap<>();
                if(dataMap.containsKey(data.username)) {
                    userCatchData = dataMap.get(data.username);
                    if(userCatchData.containsKey(data.name))
                        userCatchData.put(data.name, userCatchData.get(data.name) + 1);
                    else userCatchData.put(data.name, 1);
                } else {
                    userCatchData.put(data.name, 1);
                }
                dataMap.put(data.username, userCatchData);
            }
        }
        outputData();
    }

    private static void outputData() {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? String.class : Integer.class;
            }
        };
        model.addColumn("Username");
        for(String catchType : trackedCatchTypes) {
            model.addColumn(catchType);
        }
        for (Map.Entry<String, HashMap<String, Integer>> entry : dataMap.entrySet()) {
            List<Object> catchValues = new ArrayList<>();
            catchValues.add(entry.getKey());
            for(String catchType : trackedCatchTypes) {
                catchValues.add(entry.getValue().getOrDefault(catchType, 0));
            }
            model.addRow(catchValues.toArray());
        }
        JTable table = new JTable(model);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);
        table.getTableHeader().setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane spTable = new JScrollPane(table);
        JFrame frame = new JFrame("All Time Catch Data");
        if(startDate != null) {
            frame.setTitle("Catch Data from " + df.format(startDate) + " to " + df.format(endDate));
        }
        frame.add(spTable);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}