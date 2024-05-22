package com.coollord22.melonfishin;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateGUI extends JDialog {
    public LocalDate startDate;
    public LocalDate endDate;

    public DateGUI() {
        setTitle("LurkBait Catch Date Range");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(525, 250));
        pack();
        setLocationRelativeTo(null);
        init();
    }

    private void init() {
        DatePicker datePicker1 = new DatePicker();
        DatePicker datePicker2 = new DatePicker();
        JLabel textTo = new JLabel("to");
        JLabel textError = new JLabel("Please ensure end date is AFTER start date!");
        textError.setForeground(new Color(50, 40, 115));
        textError.setVisible(false);
        textError.setFont(new Font(textError.getFont().getFontName(), Font.BOLD, 14));
        JButton cmd = new JButton("Save Date Range");

        LocalDate today = LocalDate.now();
        datePicker1.setDate(today.with(TemporalAdjusters.firstDayOfMonth()));
        datePicker2.setDate(today.with(TemporalAdjusters.lastDayOfMonth()));

        add(datePicker1);
        add(textTo);
        add(datePicker2);
        add(cmd);
        add(textError);

        datePicker1.addDateChangeListener(e -> {
            textError.setVisible(false);
            cmd.setEnabled(true);

            startDate = datePicker1.getDate();
            endDate = datePicker2.getDate();
            if(startDate != null && endDate != null) {
                if(!endDate.isAfter(startDate) && !endDate.isEqual(startDate)) {
                    cmd.setEnabled(false);
                    textError.setVisible(true);
                }
            }
        });

        datePicker2.addDateChangeListener(e -> {
            textError.setVisible(false);
            cmd.setEnabled(true);

            startDate = datePicker1.getDate();
            endDate = datePicker2.getDate();
            if(startDate != null && endDate != null) {
                if(!endDate.isAfter(startDate) && !endDate.isEqual(startDate)) {
                    cmd.setEnabled(false);
                    textError.setVisible(true);
                }
            }
        });

        cmd.addActionListener(e -> {
            startDate = datePicker1.getDate();
            endDate = datePicker2.getDate();
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                startDate = null;
                endDate = null;
                dispose();
            }
        });
    }
}
