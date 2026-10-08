/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erich.worktracker.v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

/**
 *
 * @author erich
 */
public class TrackerFrame extends JFrame{
    //labels
    private JLabel lblTimer;
    //textfields
    private JTextField txtHours;
    private JTextField txtMinutes;
    //buttons
    private JButton btnTrack;
    private JButton btnStart;
    private JButton btnStop;
    //panels
    private JPanel mainPanel;
    
    
    //logic
    private Timer guiTimer;
    private WorkManager manager;
    private DataBase dbManager;
    private WorkSessionDAO sessionDao;
    
    private int currentSessionId = -1;
    
    public TrackerFrame() {
        InitUI();
        
        this.setTitle("Work tracker v2.0");
        this.pack();
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void InitUI() {
        //label
        this.lblTimer = new JLabel("00:00:00", SwingConstants.CENTER);
        //text field
        this.txtHours = new JTextField("", 4);
        this.txtMinutes = new JTextField("", 4);
        //buttons
        this.btnTrack = new JButton("Work");
        this.btnStart = new JButton("Start");
        this.btnStop = new JButton("Break");
        //panels
        JPanel panelBtn = new JPanel();
        JPanel panelInput = new JPanel();
        
        // DESIGN CHANGES AND METHODS
        this.lblTimer.setFont(new Font("Monospaced", Font.BOLD, 50));
        
        this.txtHours.putClientProperty("JTextField.placeholderText", "hours");
        this.txtMinutes.putClientProperty("JTextField.placeholderText", "minutes");
        
        this.btnTrack.setPreferredSize(new Dimension(90, 50));
        this.btnTrack.setForeground(Color.MAGENTA);
        this.btnStart.setFocusable(false);
        this.btnStart.setEnabled(false);
        this.btnStop.setEnabled(false);
        this.btnStop.setFocusable(false);
        this.btnStart.setForeground(java.awt.Color.decode("#2ECC71"));
        this.btnStop.setForeground(java.awt.Color.decode("#942A18"));
        
        //panels
        
        panelBtn.add(this.btnStop);
        panelBtn.add(this.btnStart);
        
        
        panelInput.add(this.btnTrack);
        panelInput.add(this.txtHours);
        panelInput.add(this.txtMinutes);
        
        
        this.btnTrack.addActionListener(e-> handleTrackAction());
        this.btnStop.addActionListener(e -> handleStopAction());
        this.btnStart.addActionListener(e -> handleStartAction());
        
        this.setLayout(new BorderLayout());
        this.add(this.lblTimer, BorderLayout.CENTER);
        this.add(panelBtn, BorderLayout.SOUTH);
        this.add(panelInput, BorderLayout.NORTH);
        
    }
    
    private void handleTrackAction() {
        try {
            String hText = txtHours.getText().trim();
            String mText = txtMinutes.getText().trim();
            
            int h = Integer.parseInt(hText);
            int m = Integer.parseInt(mText);
            
            this.manager = new WorkManager(h, m);
            this.dbManager = new DataBase();
            this.sessionDao = new WorkSessionDAO(dbManager);
            this.currentSessionId = this.sessionDao.startSession(manager.getDate(), manager.getStartTime());

            if (this.currentSessionId == -1) {
                System.err.println("no id");
            }
                    
            
            
            
            txtHours.setEditable(false);
            txtMinutes.setEditable(false);
            this.btnTrack.setEnabled(false);
            this.btnStop.setEnabled(true);

            this.guiTimer = new Timer(1000, e -> {
                if(manager.isFinished()) {
                    guiTimer.stop();
                    
                    this.sessionDao.endSession(manager.getFinishTime(), this.currentSessionId);
                    JOptionPane.showMessageDialog(this, "GOOD JOB!");
                    this.lblTimer.setForeground(Color.cyan);
                    this.btnStart.setEnabled(false);
                    this.btnStop.setEnabled(false);
                    
                    this.lblTimer.setText("DONE!");
                    return;
                }
                String timeText = manager.getTime();
                this.lblTimer.setText(timeText);
            });
            
            guiTimer.start();
       
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "input numbers");
        }
    }
    
    private void handleStopAction() {
        if(guiTimer != null) {
            guiTimer.stop();
            manager.getBreak();
            this.btnStop.setEnabled(false);
            this.lblTimer.setForeground(java.awt.Color.decode("#942A18"));
            this.btnStart.setEnabled(true);
            System.out.println("stop");
        }
    }
    
    private void handleStartAction() {
        if(guiTimer != null) {
            guiTimer.start();
            
            this.sessionDao.endBreak(manager.endBreak(), this.currentSessionId);
            this.btnStart.setEnabled(false);
            this.lblTimer.setForeground(Color.WHITE);
            this.btnStop.setEnabled(true);
            System.out.println("restart");
        }
    }
    
    
    
}
