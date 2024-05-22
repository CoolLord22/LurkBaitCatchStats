package com.coollord22.melonfishin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Date startDate = null;
    public static Date endDate = null;
    public static HashMap<String, int[]> dataMap = new HashMap<>();
    public static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    public static JLabel fileNotFound;

    public static JTextField textFileLoc;
    public static JTextField textStartDate;
    public static JTextField textEndDate;

    public static void main(String[] args) {
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
        textFileLoc = new JTextField();
        textStartDate = new JTextField(15);
        textEndDate = new JTextField(15);

        fileNotFound = new JLabel("Warning, current file path not found!", SwingConstants.CENTER);
        fileNotFound.setForeground(new Color(50, 40, 115));
        fileNotFound.setVisible(false);
        fileNotFound.setFont(new Font(fileNotFound.getFont().getFontName(), Font.BOLD, 14));

        loadMainGUI();
    }

    private static void loadMainGUI() {
        JButton buttonTable = new JButton("Make Table");
        JButton buttonBrowse = new JButton("Browse...");

        textStartDate.setText("Start Date");
        textEndDate.setText("End Date");
        textStartDate.setEditable(false);
        textEndDate.setEditable(false);
        textStartDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textEndDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JLabel textTo = new JLabel("to");
        JButton buttonDates = new JButton("Set Dates");

        String path = "C:\\Users\\ash\\AppData\\LocalLow\\BLAMCAM Interactive\\LurkBait Twitch Fishing\\CatchData.txt";
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
                File f = new File(textFileLoc.getText());
                fileNotFound.setVisible(!f.exists());
                buttonTable.setEnabled(f.exists());
            }
        });
        textFileLoc.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textFileLoc.setColumns(55);
        textFileLoc.setText(path);

        JDialog mainGUI = new JDialog();
        mainGUI.setTitle("LurkBait Twitch Fishing Data Collector");
        mainGUI.setSize(750, 250);
        mainGUI.setLocationRelativeTo(null);
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
        c.insets = new Insets(3,3,3,3);
        mainGUI.add(textFileLoc, c);

        c.gridx = 4;
        c.gridy = 0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
        mainGUI.add(buttonBrowse, c);

        // Row 2
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
        mainGUI.add(textStartDate, c);

        c.gridx = 1;
        c.gridy = 1;
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
        c.insets = new Insets(3,3,3,3);
        mainGUI.add(buttonTable, c);

        // Row 3
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(3,3,3,3);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 5;
        mainGUI.add(fileNotFound, c);

        mainGUI.setVisible(true);

        buttonBrowse.addActionListener(e -> {
            JFileChooser fileGUI = new JFileChooser();
            fileGUI.showSaveDialog(null);
            try {
                textFileLoc.setText(fileGUI.getSelectedFile().getAbsolutePath());
            } catch (NullPointerException ignored) {}
        });

        buttonDates.addActionListener(e -> setDates());

        buttonTable.addActionListener(e -> {
            File f = new File(textFileLoc.getText());
            if(f.exists())
                tabulateData();
            else {
                fileNotFound.setVisible(true);
                buttonTable.setEnabled(false);
            }
        });
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
            file = new FileReader(textFileLoc.getText());
            features = gson.fromJson(file, Catch[].class);
            features = gson.fromJson(file, CatchData[].class);
            file.close();
        } catch (IOException ignored) {}

        dataMap = new HashMap<>();
        for(CatchData data : features) {
            if(startDate != null && endDate != null) {
                if(!data.date.after(startDate) || !data.date.before(endDate))
                    continue;
            }
            if(data.name.equalsIgnoreCase("stinky ticket")) {
                if(dataMap.containsKey(data.username)) {
                    dataMap.put(data.username, new int[] {dataMap.get(data.username)[0] + 1, dataMap.get(data.username)[1]});
                } else {
                    dataMap.put(data.username, new int[] {1, 0});
                }
            }
            if(data.name.equalsIgnoreCase("lucky ticket")) {
                if(dataMap.containsKey(data.username)) {
                    dataMap.put(data.username, new int[] {dataMap.get(data.username)[0], dataMap.get(data.username)[1] + 1});
                } else {
                    dataMap.put(data.username, new int[] {0, 1});
                }
            }
        }

        outputData();
    }

    private static void outputData() {
        DefaultTableModel model = new DefaultTableModel(new Object[] {"Username", "Stinky Tickets", "Lucky Tickets"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, int[]> entry : dataMap.entrySet()) {
            model.addRow(new Object[] { entry.getKey(), entry.getValue()[0], entry.getValue()[1]});
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