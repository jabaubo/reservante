/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Interfaces.dialogs;

import com.jabaubo.proyecto_reservas.Clases.CeldaCalendario;
import com.jabaubo.proyecto_reservas.Clases.Vacaciones;
import com.jabaubo.proyecto_reservas.Interfaces.InterfazPrincipal;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.ColorUIResource;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author pokem
 */
public class InsertarVacacion extends javax.swing.JDialog {

    private Vacaciones vacacion;
    private boolean editando = false;
    private Calendar calendar1;
    private Calendar calendar2;
    private CeldaCalendario seleccionada1;
    private CeldaCalendario seleccionada2;
    private InterfazPrincipal interfazPrincipal;

    /**
     * Creates new form InsertarSalon
     */
    public InsertarVacacion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.interfazPrincipal = (InterfazPrincipal) parent;
        if (interfazPrincipal == null) {
            System.exit(200);
        }
        initComponents();
        calendar1 = Calendar.getInstance();
        calendar2 = Calendar.getInstance();
        celdaLunes.setTitle(true);
        celdaMartes.setTitle(true);
        celdaMiercoles.setTitle(true);
        celdaJueves.setTitle(true);
        celdaViernes.setTitle(true);
        celdaSabado.setTitle(true);
        celdaDomingo.setTitle(true);

        celdaLunes1.setTitle(true);
        celdaMartes1.setTitle(true);
        celdaMiercoles1.setTitle(true);
        celdaJueves1.setTitle(true);
        celdaViernes1.setTitle(true);
        celdaSabado1.setTitle(true);
        celdaDomingo1.setTitle(true);
        setDate(calendar1, panelCalendario1, jlMes1);
        setDate(calendar2, panelCalendario2, jlMes2);
    }

    public InsertarVacacion(java.awt.Frame parent, boolean modal, Vacaciones v) {
        super(parent, modal);
        this.vacacion = v;
        this.editando = true;
        this.interfazPrincipal = (InterfazPrincipal) parent;
        if (interfazPrincipal == null) {
            System.exit(200);
        }
        initComponents();
        calendar1 = Calendar.getInstance();
        System.out.println(v.getInicio().toEpochDay());
        calendar1.setTimeInMillis(v.getInicio().toEpochDay() * 24 * 3600000);
        calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(v.getFin().toEpochDay() * 24 * 3600000);
        System.out.println(v.getFin().toEpochDay());
        celdaLunes.setTitle(true);
        celdaMartes.setTitle(true);
        celdaMiercoles.setTitle(true);
        celdaJueves.setTitle(true);
        celdaViernes.setTitle(true);
        celdaSabado.setTitle(true);
        celdaDomingo.setTitle(true);

        celdaLunes1.setTitle(true);
        celdaMartes1.setTitle(true);
        celdaMiercoles1.setTitle(true);
        celdaJueves1.setTitle(true);
        celdaViernes1.setTitle(true);
        celdaSabado1.setTitle(true);
        celdaDomingo1.setTitle(true);
        setDate(calendar1, panelCalendario1, jlMes1);
        setDate(calendar2, panelCalendario2, jlMes2);
        jtfNombre.setText(v.getNombre());
    }

    public void setDate(Calendar calendarValues, JPanel panelCalendario, JLabel etiqueta) {
        Calendar calendar = (Calendar) calendarValues.clone();
        switch (calendarValues.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                etiqueta.setText("Enero de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.FEBRUARY:
                etiqueta.setText("Febrero de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.MARCH:
                etiqueta.setText("Marzo de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.APRIL:
                etiqueta.setText("Abril de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.MAY:
                etiqueta.setText("Mayo de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.JUNE:
                etiqueta.setText("Junio de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.JULY:
                etiqueta.setText("Julio de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.AUGUST:
                etiqueta.setText("Agosto de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.SEPTEMBER:
                etiqueta.setText("Septiembre de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.OCTOBER:
                etiqueta.setText("Octubre de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.NOVEMBER:
                etiqueta.setText("Noviembre de " + calendar.get(Calendar.YEAR));
                break;
            case Calendar.DECEMBER:
                etiqueta.setText("Diciembre de " + calendar.get(Calendar.YEAR));
                break;
            default:
                etiqueta.setText("Error al obtener el mes");
                break;
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int start = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (start < 0) {
            calendar.add(Calendar.DATE, -7);
        }
        calendar.add(Calendar.DATE, -start);
        for (Component com : panelCalendario.getComponents()) {
            CeldaCalendario c = (CeldaCalendario) com;
            if (!c.isTitle()) {
                c.setFocusPainted(true);
                c.setText(calendar.get(Calendar.DATE) + "");
                c.setDate(calendar.getTime());
                if (calendar.get(Calendar.DAY_OF_YEAR) == calendarValues.get(Calendar.DAY_OF_YEAR)) {
                    c.setBackground(Color.RED);
                    if (seleccionada1 != null) {
                        seleccionada2 = c;
                    } else {
                        seleccionada1 = c;
                    }
                } else {
                    c.setBackground(new ColorUIResource(243, 244, 248));
                }
                c.currentMonth(calendar.get(Calendar.MONTH) == calendarValues.get(Calendar.MONTH));
                calendar.add(Calendar.DATE, 1);
            } else {
                c.setBackground(new ColorUIResource(109, 34, 109));
                c.setForeground(Color.WHITE);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jlTitulo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jtfNombre = new javax.swing.JTextField();
        panelCalendario1 = new javax.swing.JPanel();
        celdaLunes = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaMartes = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaMiercoles = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaJueves = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaViernes = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaSabado = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaDomingo = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario8 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario9 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario10 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario11 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario12 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario13 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario14 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario15 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario16 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario17 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario18 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario19 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario20 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario21 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario22 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario23 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario24 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario25 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario26 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario27 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario28 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario29 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario30 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario31 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario32 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario33 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario34 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario35 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario36 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario37 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario38 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario39 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario40 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario41 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario42 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario43 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario44 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario45 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario46 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario47 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario48 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario49 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        jPanel2 = new javax.swing.JPanel();
        jlMes1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelCalendario2 = new javax.swing.JPanel();
        celdaLunes1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaMartes1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaMiercoles1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaJueves1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaViernes1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaSabado1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaDomingo1 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario50 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario51 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario52 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario53 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario54 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario55 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario56 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario57 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario58 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario59 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario60 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario61 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario62 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario63 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario64 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario65 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario66 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario67 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario68 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario69 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario70 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario71 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario72 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario73 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario74 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario75 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario76 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario77 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario78 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario79 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario80 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario81 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario82 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario83 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario84 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario85 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario86 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario87 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario88 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario89 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario90 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        celdaCalendario91 = new com.jabaubo.proyecto_reservas.Clases.CeldaCalendario();
        jButton4 = new javax.swing.JButton();
        jlMes2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jlTitulo.setBackground(new java.awt.Color(109, 34, 109));
        jlTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitulo.setText("FECHA : 30-04-2024 TRAMO 12:30");
        jlTitulo.setOpaque(true);

        jLabel1.setText("Nombre");

        jtfNombre.setText("jTextField1");
        jtfNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfNombreActionPerformed(evt);
            }
        });

        panelCalendario1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray));
        panelCalendario1.setOpaque(false);
        panelCalendario1.setLayout(new java.awt.GridLayout(7, 7));

        celdaLunes.setBackground(new java.awt.Color(255, 255, 102));
        celdaLunes.setText("L");
        celdaLunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaLunesActionPerformed(evt);
            }
        });
        panelCalendario1.add(celdaLunes);

        celdaMartes.setBackground(new java.awt.Color(255, 255, 102));
        celdaMartes.setText("M");
        celdaMartes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaMartesActionPerformed(evt);
            }
        });
        panelCalendario1.add(celdaMartes);

        celdaMiercoles.setBackground(new java.awt.Color(255, 255, 102));
        celdaMiercoles.setText("X");
        panelCalendario1.add(celdaMiercoles);

        celdaJueves.setBackground(new java.awt.Color(255, 255, 102));
        celdaJueves.setText("J");
        panelCalendario1.add(celdaJueves);

        celdaViernes.setBackground(new java.awt.Color(255, 255, 102));
        celdaViernes.setText("V");
        panelCalendario1.add(celdaViernes);

        celdaSabado.setBackground(new java.awt.Color(255, 255, 102));
        celdaSabado.setText("S");
        panelCalendario1.add(celdaSabado);

        celdaDomingo.setBackground(new java.awt.Color(255, 255, 102));
        celdaDomingo.setText("D");
        celdaDomingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaDomingoActionPerformed(evt);
            }
        });
        panelCalendario1.add(celdaDomingo);

        celdaCalendario8.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario8.setToolTipText("Selecione el dia");
        celdaCalendario8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario8onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario8);

        celdaCalendario9.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario9.setToolTipText("Selecione el dia");
        celdaCalendario9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario9onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario9);

        celdaCalendario10.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario10.setToolTipText("Selecione el dia");
        celdaCalendario10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario10onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario10);

        celdaCalendario11.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario11.setToolTipText("Selecione el dia");
        celdaCalendario11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario11onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario11);

        celdaCalendario12.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario12.setToolTipText("Selecione el dia");
        celdaCalendario12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario12onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario12);

        celdaCalendario13.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario13.setToolTipText("Selecione el dia");
        celdaCalendario13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario13onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario13);

        celdaCalendario14.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario14.setToolTipText("Selecione el dia");
        celdaCalendario14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario14onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario14);

        celdaCalendario15.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario15.setToolTipText("Selecione el dia");
        celdaCalendario15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario15onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario15);

        celdaCalendario16.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario16.setToolTipText("Selecione el dia");
        celdaCalendario16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario16onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario16);

        celdaCalendario17.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario17.setToolTipText("Selecione el dia");
        celdaCalendario17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario17onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario17);

        celdaCalendario18.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario18.setToolTipText("Selecione el dia");
        celdaCalendario18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario18onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario18);

        celdaCalendario19.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario19.setToolTipText("Selecione el dia");
        celdaCalendario19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario19onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario19);

        celdaCalendario20.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario20.setToolTipText("Selecione el dia");
        celdaCalendario20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario20onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario20);

        celdaCalendario21.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario21.setToolTipText("Selecione el dia");
        celdaCalendario21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario21onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario21);

        celdaCalendario22.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario22.setToolTipText("Selecione el dia");
        celdaCalendario22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario22onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario22);

        celdaCalendario23.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario23.setToolTipText("Selecione el dia");
        celdaCalendario23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario23onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario23);

        celdaCalendario24.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario24.setToolTipText("Selecione el dia");
        celdaCalendario24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario24onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario24);

        celdaCalendario25.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario25.setToolTipText("Selecione el dia");
        celdaCalendario25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario25onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario25);

        celdaCalendario26.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario26.setToolTipText("Selecione el dia");
        celdaCalendario26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario26onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario26);

        celdaCalendario27.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario27.setToolTipText("Selecione el dia");
        celdaCalendario27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario27onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario27);

        celdaCalendario28.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario28.setToolTipText("Selecione el dia");
        celdaCalendario28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario28onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario28);

        celdaCalendario29.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario29.setToolTipText("Selecione el dia");
        celdaCalendario29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario29onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario29);

        celdaCalendario30.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario30.setToolTipText("Selecione el dia");
        celdaCalendario30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario30onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario30);

        celdaCalendario31.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario31.setToolTipText("Selecione el dia");
        celdaCalendario31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario31onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario31);

        celdaCalendario32.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario32.setToolTipText("Selecione el dia");
        celdaCalendario32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario32onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario32);

        celdaCalendario33.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario33.setToolTipText("Selecione el dia");
        celdaCalendario33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario33onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario33);

        celdaCalendario34.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario34.setToolTipText("Selecione el dia");
        celdaCalendario34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario34onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario34);

        celdaCalendario35.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario35.setToolTipText("Selecione el dia");
        celdaCalendario35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario35onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario35);

        celdaCalendario36.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario36.setToolTipText("Selecione el dia");
        celdaCalendario36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario36onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario36);

        celdaCalendario37.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario37.setToolTipText("Selecione el dia");
        celdaCalendario37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario37onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario37);

        celdaCalendario38.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario38.setToolTipText("Selecione el dia");
        celdaCalendario38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario38onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario38);

        celdaCalendario39.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario39.setToolTipText("Selecione el dia");
        celdaCalendario39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario39onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario39);

        celdaCalendario40.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario40.setToolTipText("Selecione el dia");
        celdaCalendario40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario40onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario40);

        celdaCalendario41.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario41.setToolTipText("Selecione el dia");
        celdaCalendario41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario41onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario41);

        celdaCalendario42.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario42.setToolTipText("Selecione el dia");
        celdaCalendario42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario42onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario42);

        celdaCalendario43.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario43.setToolTipText("Selecione el dia");
        celdaCalendario43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario43onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario43);

        celdaCalendario44.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario44.setToolTipText("Selecione el dia");
        celdaCalendario44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario44onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario44);

        celdaCalendario45.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario45.setToolTipText("Selecione el dia");
        celdaCalendario45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario45onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario45);

        celdaCalendario46.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario46.setToolTipText("Selecione el dia");
        celdaCalendario46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario46onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario46);

        celdaCalendario47.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario47.setToolTipText("Selecione el dia");
        celdaCalendario47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario47onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario47);

        celdaCalendario48.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario48.setToolTipText("Selecione el dia");
        celdaCalendario48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario48onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario48);

        celdaCalendario49.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario49.setToolTipText("Selecione el dia");
        celdaCalendario49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario49onClickDia(evt);
            }
        });
        panelCalendario1.add(celdaCalendario49);

        jlMes1.setText("jLabel2");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton1");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jlMes1, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlMes1)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        panelCalendario2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray));
        panelCalendario2.setOpaque(false);
        panelCalendario2.setLayout(new java.awt.GridLayout(7, 7));

        celdaLunes1.setBackground(new java.awt.Color(255, 255, 102));
        celdaLunes1.setText("L");
        celdaLunes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaLunes1ActionPerformed(evt);
            }
        });
        panelCalendario2.add(celdaLunes1);

        celdaMartes1.setBackground(new java.awt.Color(255, 255, 102));
        celdaMartes1.setText("M");
        celdaMartes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaMartes1ActionPerformed(evt);
            }
        });
        panelCalendario2.add(celdaMartes1);

        celdaMiercoles1.setBackground(new java.awt.Color(255, 255, 102));
        celdaMiercoles1.setText("X");
        panelCalendario2.add(celdaMiercoles1);

        celdaJueves1.setBackground(new java.awt.Color(255, 255, 102));
        celdaJueves1.setText("J");
        panelCalendario2.add(celdaJueves1);

        celdaViernes1.setBackground(new java.awt.Color(255, 255, 102));
        celdaViernes1.setText("V");
        panelCalendario2.add(celdaViernes1);

        celdaSabado1.setBackground(new java.awt.Color(255, 255, 102));
        celdaSabado1.setText("S");
        panelCalendario2.add(celdaSabado1);

        celdaDomingo1.setBackground(new java.awt.Color(255, 255, 102));
        celdaDomingo1.setText("D");
        celdaDomingo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaDomingo1ActionPerformed(evt);
            }
        });
        panelCalendario2.add(celdaDomingo1);

        celdaCalendario50.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario50.setToolTipText("Selecione el dia");
        celdaCalendario50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCalendario2(evt);
            }
        });
        panelCalendario2.add(celdaCalendario50);

        celdaCalendario51.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario51.setToolTipText("Selecione el dia");
        celdaCalendario51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario51onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario51);

        celdaCalendario52.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario52.setToolTipText("Selecione el dia");
        celdaCalendario52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCalendario2(evt);
            }
        });
        panelCalendario2.add(celdaCalendario52);

        celdaCalendario53.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario53.setToolTipText("Selecione el dia");
        celdaCalendario53.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario53onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario53);

        celdaCalendario54.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario54.setToolTipText("Selecione el dia");
        celdaCalendario54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario54onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario54);

        celdaCalendario55.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario55.setToolTipText("Selecione el dia");
        celdaCalendario55.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario55onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario55);

        celdaCalendario56.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario56.setToolTipText("Selecione el dia");
        celdaCalendario56.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario56onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario56);

        celdaCalendario57.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario57.setToolTipText("Selecione el dia");
        celdaCalendario57.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario57onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario57);

        celdaCalendario58.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario58.setToolTipText("Selecione el dia");
        celdaCalendario58.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario58onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario58);

        celdaCalendario59.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario59.setToolTipText("Selecione el dia");
        celdaCalendario59.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario59onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario59);

        celdaCalendario60.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario60.setToolTipText("Selecione el dia");
        celdaCalendario60.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario60onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario60);

        celdaCalendario61.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario61.setToolTipText("Selecione el dia");
        celdaCalendario61.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario61onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario61);

        celdaCalendario62.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario62.setToolTipText("Selecione el dia");
        celdaCalendario62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario62onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario62);

        celdaCalendario63.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario63.setToolTipText("Selecione el dia");
        celdaCalendario63.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario63onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario63);

        celdaCalendario64.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario64.setToolTipText("Selecione el dia");
        celdaCalendario64.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario64onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario64);

        celdaCalendario65.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario65.setToolTipText("Selecione el dia");
        celdaCalendario65.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario65onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario65);

        celdaCalendario66.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario66.setToolTipText("Selecione el dia");
        celdaCalendario66.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario66onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario66);

        celdaCalendario67.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario67.setToolTipText("Selecione el dia");
        celdaCalendario67.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario67onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario67);

        celdaCalendario68.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario68.setToolTipText("Selecione el dia");
        celdaCalendario68.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario68onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario68);

        celdaCalendario69.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario69.setToolTipText("Selecione el dia");
        celdaCalendario69.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario69onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario69);

        celdaCalendario70.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario70.setToolTipText("Selecione el dia");
        celdaCalendario70.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario70onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario70);

        celdaCalendario71.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario71.setToolTipText("Selecione el dia");
        celdaCalendario71.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario71onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario71);

        celdaCalendario72.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario72.setToolTipText("Selecione el dia");
        celdaCalendario72.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario72onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario72);

        celdaCalendario73.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario73.setToolTipText("Selecione el dia");
        celdaCalendario73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario73onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario73);

        celdaCalendario74.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario74.setToolTipText("Selecione el dia");
        celdaCalendario74.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario74onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario74);

        celdaCalendario75.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario75.setToolTipText("Selecione el dia");
        celdaCalendario75.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario75onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario75);

        celdaCalendario76.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario76.setToolTipText("Selecione el dia");
        celdaCalendario76.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario76onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario76);

        celdaCalendario77.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario77.setToolTipText("Selecione el dia");
        celdaCalendario77.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario77onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario77);

        celdaCalendario78.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario78.setToolTipText("Selecione el dia");
        celdaCalendario78.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario78onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario78);

        celdaCalendario79.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario79.setToolTipText("Selecione el dia");
        celdaCalendario79.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario79onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario79);

        celdaCalendario80.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario80.setToolTipText("Selecione el dia");
        celdaCalendario80.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario80onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario80);

        celdaCalendario81.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario81.setToolTipText("Selecione el dia");
        celdaCalendario81.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario81onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario81);

        celdaCalendario82.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario82.setToolTipText("Selecione el dia");
        celdaCalendario82.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario82onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario82);

        celdaCalendario83.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario83.setToolTipText("Selecione el dia");
        celdaCalendario83.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario83onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario83);

        celdaCalendario84.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario84.setToolTipText("Selecione el dia");
        celdaCalendario84.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario84onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario84);

        celdaCalendario85.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario85.setToolTipText("Selecione el dia");
        celdaCalendario85.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario85onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario85);

        celdaCalendario86.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario86.setToolTipText("Selecione el dia");
        celdaCalendario86.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario86onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario86);

        celdaCalendario87.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario87.setToolTipText("Selecione el dia");
        celdaCalendario87.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario87onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario87);

        celdaCalendario88.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario88.setToolTipText("Selecione el dia");
        celdaCalendario88.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario88onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario88);

        celdaCalendario89.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario89.setToolTipText("Selecione el dia");
        celdaCalendario89.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario89onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario89);

        celdaCalendario90.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario90.setToolTipText("Selecione el dia");
        celdaCalendario90.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario90onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario90);

        celdaCalendario91.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario91.setToolTipText("Selecione el dia");
        celdaCalendario91.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaCalendario91onClickDia(evt);
            }
        });
        panelCalendario2.add(celdaCalendario91);

        jButton4.setText("jButton1");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jlMes2.setText("jLabel2");

        jButton3.setText("jButton1");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton5.setText("Borrar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarClick(evt);
            }
        });

        jButton6.setText("Guardar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfNombre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(194, 194, 194)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 43, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jlMes2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 43, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(panelCalendario1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelCalendario2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelCalendario1, panelCalendario2});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jlTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlMes2)
                        .addComponent(jButton4)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelCalendario2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelCalendario1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {panelCalendario1, panelCalendario2});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void celdaLunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaLunesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaLunesActionPerformed

    private void celdaMartesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaMartesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaMartesActionPerformed

    private void celdaDomingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaDomingoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaDomingoActionPerformed

    private void celdaCalendario8onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario8onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario8onClickDia

    private void celdaCalendario9onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario9onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario9onClickDia

    private void celdaCalendario10onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario10onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario10onClickDia

    private void celdaCalendario11onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario11onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario11onClickDia

    private void celdaCalendario12onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario12onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario12onClickDia

    private void celdaCalendario13onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario13onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario13onClickDia

    private void celdaCalendario14onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario14onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario14onClickDia

    private void celdaCalendario15onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario15onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario15onClickDia

    private void celdaCalendario16onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario16onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario16onClickDia

    private void celdaCalendario17onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario17onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario17onClickDia

    private void celdaCalendario18onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario18onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario18onClickDia

    private void celdaCalendario19onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario19onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario19onClickDia

    private void celdaCalendario20onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario20onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario20onClickDia

    private void celdaCalendario21onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario21onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario21onClickDia

    private void celdaCalendario22onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario22onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario22onClickDia

    private void celdaCalendario23onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario23onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario23onClickDia

    private void celdaCalendario24onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario24onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario24onClickDia

    private void celdaCalendario25onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario25onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario25onClickDia

    private void celdaCalendario26onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario26onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario26onClickDia

    private void celdaCalendario27onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario27onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario27onClickDia

    private void celdaCalendario28onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario28onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario28onClickDia

    private void celdaCalendario29onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario29onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario29onClickDia

    private void celdaCalendario30onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario30onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario30onClickDia

    private void celdaCalendario31onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario31onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario31onClickDia

    private void celdaCalendario32onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario32onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario32onClickDia

    private void celdaCalendario33onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario33onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario33onClickDia

    private void celdaCalendario34onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario34onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario34onClickDia

    private void celdaCalendario35onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario35onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario35onClickDia

    private void celdaCalendario36onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario36onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario36onClickDia

    private void celdaCalendario37onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario37onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario37onClickDia

    private void celdaCalendario38onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario38onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario38onClickDia

    private void celdaCalendario39onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario39onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario39onClickDia

    private void celdaCalendario40onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario40onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario40onClickDia

    private void celdaCalendario41onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario41onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario41onClickDia

    private void celdaCalendario42onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario42onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario42onClickDia

    private void celdaCalendario43onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario43onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario43onClickDia

    private void celdaCalendario44onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario44onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario44onClickDia

    private void celdaCalendario45onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario45onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario45onClickDia

    private void celdaCalendario46onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario46onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario46onClickDia

    private void celdaCalendario47onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario47onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario47onClickDia

    private void celdaCalendario48onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario48onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario48onClickDia

    private void celdaCalendario49onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario49onClickDia
        if (seleccionada1 != null) {
            seleccionada1.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada1 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario49onClickDia

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        calendar1.add(Calendar.MONTH, 1);
        setDate(calendar1, panelCalendario1, jlMes1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void celdaLunes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaLunes1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaLunes1ActionPerformed

    private void celdaMartes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaMartes1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaMartes1ActionPerformed

    private void celdaDomingo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaDomingo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaDomingo1ActionPerformed

    private void celdaCalendario51onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario51onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;  // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario51onClickDia

    private void celdaCalendario53onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario53onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario53onClickDia

    private void celdaCalendario54onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario54onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario54onClickDia

    private void celdaCalendario55onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario55onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario55onClickDia

    private void celdaCalendario56onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario56onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario56onClickDia

    private void celdaCalendario57onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario57onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario57onClickDia

    private void celdaCalendario58onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario58onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario58onClickDia

    private void celdaCalendario59onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario59onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario59onClickDia

    private void celdaCalendario60onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario60onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario60onClickDia

    private void celdaCalendario61onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario61onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario61onClickDia

    private void celdaCalendario62onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario62onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario62onClickDia

    private void celdaCalendario63onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario63onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario63onClickDia

    private void celdaCalendario64onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario64onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario64onClickDia

    private void celdaCalendario65onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario65onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario65onClickDia

    private void celdaCalendario66onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario66onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario66onClickDia

    private void celdaCalendario67onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario67onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario67onClickDia

    private void celdaCalendario68onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario68onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario68onClickDia

    private void celdaCalendario69onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario69onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario69onClickDia

    private void celdaCalendario70onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario70onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario70onClickDia

    private void celdaCalendario71onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario71onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario71onClickDia

    private void celdaCalendario72onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario72onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario72onClickDia

    private void celdaCalendario73onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario73onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario73onClickDia

    private void celdaCalendario74onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario74onClickDia
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;        // TODO add your handling code here:
    }//GEN-LAST:event_celdaCalendario74onClickDia

    private void celdaCalendario75onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario75onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario75onClickDia

    private void celdaCalendario76onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario76onClickDia
        // TODO add your handling code here:        
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario76onClickDia

    private void celdaCalendario77onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario77onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario77onClickDia

    private void celdaCalendario78onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario78onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario78onClickDia

    private void celdaCalendario79onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario79onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario79onClickDia

    private void celdaCalendario80onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario80onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario80onClickDia

    private void celdaCalendario81onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario81onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario81onClickDia

    private void celdaCalendario82onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario82onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario82onClickDia

    private void celdaCalendario83onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario83onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario83onClickDia

    private void celdaCalendario84onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario84onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario84onClickDia

    private void celdaCalendario85onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario85onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario85onClickDia

    private void celdaCalendario86onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario86onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario86onClickDia

    private void celdaCalendario87onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario87onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario87onClickDia

    private void celdaCalendario88onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario88onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario88onClickDia

    private void celdaCalendario89onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario89onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario89onClickDia

    private void celdaCalendario90onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario90onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario90onClickDia

    private void celdaCalendario91onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaCalendario91onClickDia
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_celdaCalendario91onClickDia

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        calendar2.add(Calendar.MONTH, 1);
        setDate(calendar2, panelCalendario2, jlMes2);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        calendar2.add(Calendar.MONTH, -1);
        setDate(calendar2, panelCalendario2, jlMes2);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        calendar1.add(Calendar.MONTH, -1);
        setDate(calendar1, panelCalendario1, jlMes1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void clickCalendario2(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickCalendario2
        // TODO add your handling code here:
        if (seleccionada2 != null) {
            seleccionada2.setBackground(new ColorUIResource(243, 244, 248));
        }
        CeldaCalendario celdaSrc = (CeldaCalendario) evt.getSource();
        celdaSrc.setBackground(Color.red);
        seleccionada2 = celdaSrc;
    }//GEN-LAST:event_clickCalendario2

    private void jtfNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNombreActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        final String nombre = jtfNombre.getText();
        final String fecha1 = seleccionada1.fechaFormateada();
        final String fecha2 = seleccionada2.fechaFormateada();
        if (editando) {
            Vacaciones v = new Vacaciones(nombre, fecha1, fecha2, vacacion.getIdRestaurante(), vacacion.getId());
            System.out.println(v.toJson());
            if (v.toJson().equals(vacacion.toJson())) {
                JOptionPane.showMessageDialog(interfazPrincipal, "aNo hay cambios", "Aviso", JOptionPane.PLAIN_MESSAGE);
            } else {
                final int idRestaurante = interfazPrincipal.getRestaurante();
                final String[] responseStr = new String[1];
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        // Conectamos a la pagina con el método que queramos
                        try {
                            URL url = new URL("https://reservante.mjhudesings.com/slim/updatevacacion");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("PUT");
                            connection.setDoOutput(true);
                            connection.setRequestProperty("Content-Type", "application/json");
                            connection.setRequestProperty("Accept", "application/json");
                            OutputStream os = connection.getOutputStream();
                            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                            osw.write(v.toJson());
                            osw.flush();
                            int responseCode = connection.getResponseCode();
                            //Ver si la respuesta es correcta
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                // Si es correcta la leemos
                                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String line;
                                StringBuilder response = new StringBuilder();
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                reader.close();
                                responseStr[0] = response.toString();
                                connection.disconnect();
                            } else {
                                connection.disconnect();
                            }
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (ProtocolException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    System.out.println(responseStr[0]);
                    if (responseStr[0].contains("correctamente")) {
                        JOptionPane.showMessageDialog(interfazPrincipal, "Vacación insertada", "Mensaje", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(interfazPrincipal, "Error al insertar", "Mensaje", JOptionPane.ERROR_MESSAGE);
                    }
                    this.setVisible(false);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        } else {
            final int idRestaurante = interfazPrincipal.getRestaurante();
            final String[] responseStr = new String[1];
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/addvacacion");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonStr = "{\n"
                                + "    \"id_restaurante\":\"#PARAMID#\",\n"
                                + "    \"nombre\":\"#PARAMNOMBRE#\",\n"
                                + "    \"inicio\":\"#PARAMINICIO#\",\n"
                                + "    \"fin\":\"#PARAMFIN#\"\n"
                                + "}";
                        jsonStr = jsonStr.replace("#PARAMID#", String.valueOf(idRestaurante));
                        jsonStr = jsonStr.replace("#PARAMNOMBRE#", nombre);
                        jsonStr = jsonStr.replace("#PARAMINICIO#", fecha1);
                        jsonStr = jsonStr.replace("#PARAMFIN#", fecha2);
                        osw.write(jsonStr);
                        System.out.println(jsonStr);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
                        //Ver si la respuesta es correcta
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            // Si es correcta la leemos
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            responseStr[0] = response.toString();
                            connection.disconnect();
                        } else {
                            connection.disconnect();
                        }
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (ProtocolException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            try {
                System.out.println(responseStr[0]);
                if (responseStr[0].contains("correctamente")) {
                    JOptionPane.showMessageDialog(interfazPrincipal, "Vacación insertada", "Mensaje", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(interfazPrincipal, "Error al insertar", "Mensaje", JOptionPane.ERROR_MESSAGE);
                }
                this.setVisible(false);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void borrarClick(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarClick
        // TODO add your handling code here:
    }//GEN-LAST:event_borrarClick

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InsertarVacacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertarVacacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertarVacacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertarVacacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InsertarVacacion dialog = new InsertarVacacion(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario10;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario11;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario12;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario13;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario14;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario15;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario16;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario17;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario18;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario19;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario20;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario21;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario22;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario23;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario24;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario25;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario26;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario27;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario28;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario29;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario30;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario31;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario32;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario33;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario34;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario35;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario36;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario37;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario38;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario39;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario40;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario41;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario42;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario43;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario44;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario45;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario46;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario47;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario48;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario49;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario50;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario51;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario52;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario53;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario54;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario55;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario56;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario57;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario58;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario59;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario60;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario61;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario62;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario63;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario64;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario65;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario66;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario67;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario68;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario69;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario70;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario71;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario72;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario73;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario74;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario75;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario76;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario77;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario78;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario79;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario8;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario80;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario81;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario82;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario83;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario84;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario85;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario86;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario87;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario88;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario89;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario9;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario90;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario91;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaDomingo;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaDomingo1;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaJueves;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaJueves1;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaLunes;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaLunes1;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaMartes;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaMartes1;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaMiercoles;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaMiercoles1;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaSabado;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaSabado1;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaViernes;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaViernes1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel jlMes1;
    private javax.swing.JLabel jlMes2;
    private javax.swing.JLabel jlTitulo;
    private javax.swing.JTextField jtfNombre;
    private javax.swing.JPanel panelCalendario1;
    private javax.swing.JPanel panelCalendario2;
    // End of variables declaration//GEN-END:variables
}