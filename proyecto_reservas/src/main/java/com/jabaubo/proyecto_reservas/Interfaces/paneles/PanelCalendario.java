/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Interfaces.paneles;

import com.jabaubo.proyecto_reservas.Clases.CeldaCalendario;
import com.jabaubo.proyecto_reservas.Clases.Ocupacion;
import com.jabaubo.proyecto_reservas.Clases.OcupacionRender;
import com.jabaubo.proyecto_reservas.Clases.Reserva;
import com.jabaubo.proyecto_reservas.Clases.ReservaFechas;
import com.jabaubo.proyecto_reservas.Interfaces.InterfazPrincipal;
import com.jabaubo.proyecto_reservas.Interfaces.dialogs.ReservasDialog;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author pokem
 */
public class PanelCalendario extends javax.swing.JPanel {

    private int restaurante;
    private int month;
    private int year;
    private Calendar fecha;
    private InterfazPrincipal interfazPrincipal;

    public PanelCalendario() {
        initComponents();
//Arrancamos en la fecha actual
        month = LocalDate.now().getMonth().getValue();
        year = LocalDate.now().getYear();
    }

    public PanelCalendario(InterfazPrincipal interfazPrincipal, int restaurante) {
        //Arrancamos en la fecha actual
        this.month = LocalDate.now().getMonthValue();
        this.year = LocalDate.now().getYear();
        this.interfazPrincipal = interfazPrincipal;
        this.restaurante = restaurante;
        initComponents();
        cargarDatos();
    }

    public void cargarDatos() {
        //Creamos un calendar con el mes actual
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        //Depende del mes ponemos un título u otro en la etiqueta
        switch (c.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                jlFechaCalendario.setText("Enero de " + c.get(Calendar.YEAR));
                break;
            case Calendar.FEBRUARY:
                jlFechaCalendario.setText("Febrero de " + c.get(Calendar.YEAR));
                break;
            case Calendar.MARCH:
                jlFechaCalendario.setText("Marzo de " + c.get(Calendar.YEAR));
                break;
            case Calendar.APRIL:
                jlFechaCalendario.setText("Abril de " + c.get(Calendar.YEAR));
                break;
            case Calendar.MAY:
                jlFechaCalendario.setText("Mayo de " + c.get(Calendar.YEAR));
                break;
            case Calendar.JUNE:
                jlFechaCalendario.setText("Junio de " + c.get(Calendar.YEAR));
                break;
            case Calendar.JULY:
                jlFechaCalendario.setText("Julio de " + c.get(Calendar.YEAR));
                break;
            case Calendar.AUGUST:
                jlFechaCalendario.setText("Agosto de " + c.get(Calendar.YEAR));
                break;
            case Calendar.SEPTEMBER:
                jlFechaCalendario.setText("Septiembre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.OCTOBER:
                jlFechaCalendario.setText("Octubre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.NOVEMBER:
                jlFechaCalendario.setText("Noviembre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.DECEMBER:
                jlFechaCalendario.setText("Diciembre de " + c.get(Calendar.YEAR));
                break;
            default:
                jlFechaCalendario.setText("Error al obtener el mes");
                break;
        }
        //Marcamos como título los días de la semana
        celdaLunes.setTitle(true);
        celdaMartes.setTitle(true);
        celdaMiercoles.setTitle(true);
        celdaJueves.setTitle(true);
        celdaViernes.setTitle(true);
        celdaSabado.setTitle(true);
        celdaDomingo.setTitle(true);
        jListOcupacionReservas.setCellRenderer(new OcupacionRender());
        setDate();
    }

    public void setDate() {
        //Creamos un calendario para la fecha de hoy
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        //Calculamos desde que dia empezamos
        int start = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        //Si el mes empieza en domingo hay que empezar una semana antes
        if (start < 0) {
            calendar.add(Calendar.DATE, -7);
        }
        calendar.add(Calendar.DATE, -start);
        //Vamos recorriendo el panel
        for (Component com : panelCalendario.getComponents()) {
            CeldaCalendario c = (CeldaCalendario) com;
            //Si la celda es de día formateamos en base a día y mes
            if (!c.isTitle()) {
                c.setFocusPainted(true);
                c.setText(calendar.get(Calendar.DATE) + "");
                c.setDate(calendar.getTime());
                c.setBackground(new ColorUIResource(243, 244, 248));
                c.currentMonth(calendar.get(Calendar.MONTH) == month - 1);
                calendar.add(Calendar.DATE, 1);
           //Si no , damos formato de cabecera
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
        jbAnterior = new javax.swing.JButton();
        jbSiguiente = new javax.swing.JButton();
        jlFechaCalendario = new javax.swing.JLabel();
        jlFechaSeleccionada = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelCalendario = new javax.swing.JPanel();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jListOcupacionReservas = new JList<>();

        setBackground(new java.awt.Color(243, 244, 248));

        jPanel1.setBackground(new java.awt.Color(125, 59, 125));
        jPanel1.setOpaque(false);

        jbAnterior.setText("Mes anterior");
        jbAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAnteriorActionPerformed(evt);
            }
        });

        jbSiguiente.setText("Mes siguiente");
        jbSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSiguienteActionPerformed(evt);
            }
        });

        jlFechaCalendario.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlFechaCalendario.setForeground(new java.awt.Color(51, 51, 51));
        jlFechaCalendario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFechaCalendario.setText("a");
        jlFechaCalendario.setRequestFocusEnabled(false);

        jlFechaSeleccionada.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlFechaSeleccionada.setForeground(new java.awt.Color(51, 51, 51));
        jlFechaSeleccionada.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlFechaSeleccionada.setText("Fecha:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbAnterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlFechaCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jlFechaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbAnterior, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jbSiguiente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlFechaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlFechaCalendario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel3.setOpaque(false);

        panelCalendario.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.darkGray));
        panelCalendario.setOpaque(false);
        panelCalendario.setLayout(new java.awt.GridLayout(7, 7));

        celdaLunes.setBackground(new java.awt.Color(255, 255, 102));
        celdaLunes.setText("Lunes");
        celdaLunes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaLunesActionPerformed(evt);
            }
        });
        panelCalendario.add(celdaLunes);

        celdaMartes.setBackground(new java.awt.Color(255, 255, 102));
        celdaMartes.setText("Martes");
        celdaMartes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaMartesActionPerformed(evt);
            }
        });
        panelCalendario.add(celdaMartes);

        celdaMiercoles.setBackground(new java.awt.Color(255, 255, 102));
        celdaMiercoles.setText("Miercoles");
        panelCalendario.add(celdaMiercoles);

        celdaJueves.setBackground(new java.awt.Color(255, 255, 102));
        celdaJueves.setText("Jueves");
        panelCalendario.add(celdaJueves);

        celdaViernes.setBackground(new java.awt.Color(255, 255, 102));
        celdaViernes.setText("Viernes");
        panelCalendario.add(celdaViernes);

        celdaSabado.setBackground(new java.awt.Color(255, 255, 102));
        celdaSabado.setText("Sábado");
        panelCalendario.add(celdaSabado);

        celdaDomingo.setBackground(new java.awt.Color(255, 255, 102));
        celdaDomingo.setText("Domingo");
        celdaDomingo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                celdaDomingoActionPerformed(evt);
            }
        });
        panelCalendario.add(celdaDomingo);

        celdaCalendario8.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario8.setToolTipText("Selecione el dia");
        celdaCalendario8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario8);

        celdaCalendario9.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario9.setToolTipText("Selecione el dia");
        celdaCalendario9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario9);

        celdaCalendario10.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario10.setToolTipText("Selecione el dia");
        celdaCalendario10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario10);

        celdaCalendario11.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario11.setToolTipText("Selecione el dia");
        celdaCalendario11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario11);

        celdaCalendario12.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario12.setToolTipText("Selecione el dia");
        celdaCalendario12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario12);

        celdaCalendario13.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario13.setToolTipText("Selecione el dia");
        celdaCalendario13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario13);

        celdaCalendario14.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario14.setToolTipText("Selecione el dia");
        celdaCalendario14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario14);

        celdaCalendario15.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario15.setToolTipText("Selecione el dia");
        celdaCalendario15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario15);

        celdaCalendario16.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario16.setToolTipText("Selecione el dia");
        celdaCalendario16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario16);

        celdaCalendario17.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario17.setToolTipText("Selecione el dia");
        celdaCalendario17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario17);

        celdaCalendario18.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario18.setToolTipText("Selecione el dia");
        celdaCalendario18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario18);

        celdaCalendario19.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario19.setToolTipText("Selecione el dia");
        celdaCalendario19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario19);

        celdaCalendario20.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario20.setToolTipText("Selecione el dia");
        celdaCalendario20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario20);

        celdaCalendario21.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario21.setToolTipText("Selecione el dia");
        celdaCalendario21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario21);

        celdaCalendario22.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario22.setToolTipText("Selecione el dia");
        celdaCalendario22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario22);

        celdaCalendario23.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario23.setToolTipText("Selecione el dia");
        celdaCalendario23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario23);

        celdaCalendario24.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario24.setToolTipText("Selecione el dia");
        celdaCalendario24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario24);

        celdaCalendario25.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario25.setToolTipText("Selecione el dia");
        celdaCalendario25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario25);

        celdaCalendario26.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario26.setToolTipText("Selecione el dia");
        celdaCalendario26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario26);

        celdaCalendario27.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario27.setToolTipText("Selecione el dia");
        celdaCalendario27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario27);

        celdaCalendario28.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario28.setToolTipText("Selecione el dia");
        celdaCalendario28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario28);

        celdaCalendario29.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario29.setToolTipText("Selecione el dia");
        celdaCalendario29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario29);

        celdaCalendario30.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario30.setToolTipText("Selecione el dia");
        celdaCalendario30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario30);

        celdaCalendario31.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario31.setToolTipText("Selecione el dia");
        celdaCalendario31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario31);

        celdaCalendario32.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario32.setToolTipText("Selecione el dia");
        celdaCalendario32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario32);

        celdaCalendario33.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario33.setToolTipText("Selecione el dia");
        celdaCalendario33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario33);

        celdaCalendario34.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario34.setToolTipText("Selecione el dia");
        celdaCalendario34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario34);

        celdaCalendario35.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario35.setToolTipText("Selecione el dia");
        celdaCalendario35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario35);

        celdaCalendario36.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario36.setToolTipText("Selecione el dia");
        celdaCalendario36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario36);

        celdaCalendario37.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario37.setToolTipText("Selecione el dia");
        celdaCalendario37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario37);

        celdaCalendario38.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario38.setToolTipText("Selecione el dia");
        celdaCalendario38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario38);

        celdaCalendario39.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario39.setToolTipText("Selecione el dia");
        celdaCalendario39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario39);

        celdaCalendario40.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario40.setToolTipText("Selecione el dia");
        celdaCalendario40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario40);

        celdaCalendario41.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario41.setToolTipText("Selecione el dia");
        celdaCalendario41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario41);

        celdaCalendario42.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario42.setToolTipText("Selecione el dia");
        celdaCalendario42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario42);

        celdaCalendario43.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario43.setToolTipText("Selecione el dia");
        celdaCalendario43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario43);

        celdaCalendario44.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario44.setToolTipText("Selecione el dia");
        celdaCalendario44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario44);

        celdaCalendario45.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario45.setToolTipText("Selecione el dia");
        celdaCalendario45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario45);

        celdaCalendario46.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario46.setToolTipText("Selecione el dia");
        celdaCalendario46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario46);

        celdaCalendario47.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario47.setToolTipText("Selecione el dia");
        celdaCalendario47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario47);

        celdaCalendario48.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario48.setToolTipText("Selecione el dia");
        celdaCalendario48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario48);

        celdaCalendario49.setBackground(new java.awt.Color(255, 255, 102));
        celdaCalendario49.setToolTipText("Selecione el dia");
        celdaCalendario49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClickDia(evt);
            }
        });
        panelCalendario.add(celdaCalendario49);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCalendario, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCalendario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );

        jListOcupacionReservas.setBackground(new java.awt.Color(39, 39, 39));
        jListOcupacionReservas.setBorder(new javax.swing.border.MatteBorder(null));
        jListOcupacionReservas.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jListOcupacionReservas.setOpaque(false);
        jListOcupacionReservas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListOcupacionReservasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jListOcupacionReservas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void celdaLunesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaLunesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaLunesActionPerformed

    private void celdaDomingoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaDomingoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaDomingoActionPerformed

    private void celdaMartesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_celdaMartesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_celdaMartesActionPerformed

    private void onClickDia(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onClickDia
        CeldaCalendario celda = (CeldaCalendario) evt.getSource();
        jlFechaSeleccionada.setText("Fecha :" + celda.fechaFormateada());
        System.out.println(celda.fechaFormateada());
        cargarOcupacion(celda.fechaFormateada());
    }//GEN-LAST:event_onClickDia

    private void jbSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSiguienteActionPerformed
        // TODO add your handling code here:
        //Pasar al mes siguiente
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.MONTH, 1);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        //Cargamos el mes en base al valor
        switch (c.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                jlFechaCalendario.setText("Enero de " + c.get(Calendar.YEAR));
                break;
            case Calendar.FEBRUARY:
                jlFechaCalendario.setText("Febrero de " + c.get(Calendar.YEAR));
                break;
            case Calendar.MARCH:
                jlFechaCalendario.setText("Marzo de " + c.get(Calendar.YEAR));
                break;
            case Calendar.APRIL:
                jlFechaCalendario.setText("Abril de " + c.get(Calendar.YEAR));
                break;
            case Calendar.MAY:
                jlFechaCalendario.setText("Mayo de " + c.get(Calendar.YEAR));
                break;
            case Calendar.JUNE:
                jlFechaCalendario.setText("Junio de " + c.get(Calendar.YEAR));
                break;
            case Calendar.JULY:
                jlFechaCalendario.setText("Julio de " + c.get(Calendar.YEAR));
                break;
            case Calendar.AUGUST:
                jlFechaCalendario.setText("Agosto de " + c.get(Calendar.YEAR));
                break;
            case Calendar.SEPTEMBER:
                jlFechaCalendario.setText("Septiembre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.OCTOBER:
                jlFechaCalendario.setText("Octubre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.NOVEMBER:
                jlFechaCalendario.setText("Noviembre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.DECEMBER:
                jlFechaCalendario.setText("Diciembre de " + c.get(Calendar.YEAR));
                break;
            default:
                jlFechaCalendario.setText("Error al obtener el mes");
                break;
        }
        //Formateamos las celdas otra vez
        setDate();
    }//GEN-LAST:event_jbSiguienteActionPerformed

    private void jbAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAnteriorActionPerformed
        // TODO add your handling code here:
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.YEAR, year);
        c.add(Calendar.MONTH, -1);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        //Cargamos el titulo en base al valor del mes
        switch (c.get(Calendar.MONTH)) {
            case Calendar.JANUARY:
                jlFechaCalendario.setText("Enero de " + c.get(Calendar.YEAR));
                break;
            case Calendar.FEBRUARY:
                jlFechaCalendario.setText("Febrero de " + c.get(Calendar.YEAR));
                break;
            case Calendar.MARCH:
                jlFechaCalendario.setText("Marzo de " + c.get(Calendar.YEAR));
                break;
            case Calendar.APRIL:
                jlFechaCalendario.setText("Abril de " + c.get(Calendar.YEAR));
                break;
            case Calendar.MAY:
                jlFechaCalendario.setText("Mayo de " + c.get(Calendar.YEAR));
                break;
            case Calendar.JUNE:
                jlFechaCalendario.setText("Junio de " + c.get(Calendar.YEAR));
                break;
            case Calendar.JULY:
                jlFechaCalendario.setText("Julio de " + c.get(Calendar.YEAR));
                break;
            case Calendar.AUGUST:
                jlFechaCalendario.setText("Agosto de " + c.get(Calendar.YEAR));
                break;
            case Calendar.SEPTEMBER:
                jlFechaCalendario.setText("Septiembre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.OCTOBER:
                jlFechaCalendario.setText("Octubre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.NOVEMBER:
                jlFechaCalendario.setText("Noviembre de " + c.get(Calendar.YEAR));
                break;
            case Calendar.DECEMBER:
                jlFechaCalendario.setText("Diciembre de " + c.get(Calendar.YEAR));
                break;
            default:
                jlFechaCalendario.setText("Error al obtener el mes");
                break;
        }
        //Formateamos las celdas
        setDate();

    }//GEN-LAST:event_jbAnteriorActionPerformed

    private void jListOcupacionReservasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListOcupacionReservasMouseClicked
        // TODO add your handling code here:
        //Comprobamos la elegida y vemos las reservas para esa fecha y hora
        Ocupacion o = jListOcupacionReservas.getSelectedValue();
        ArrayList<Reserva> lista = verReservas(o.getFecha().toString(), o.getHora().toString());
        //Vemos si hay reservas
        if (lista.size() <= 0) {
            //Si hay, abrimos el dialog con reservas
            ReservasDialog reservasDialog = new ReservasDialog(interfazPrincipal, true, o.getFecha(), o.getHora(), interfazPrincipal.getRestaurante());
            reservasDialog.setVisible(true);
            cargarOcupacion(o.getFecha().toString());
        } else {
            //Si no, abrimos el dialog vacío
            ReservasDialog reservasDialog = new ReservasDialog(interfazPrincipal, true, o.getFecha(), o.getHora(), lista, interfazPrincipal.getRestaurante());
            reservasDialog.setVisible(true);
            cargarOcupacion(o.getFecha().toString());
        }
    }//GEN-LAST:event_jListOcupacionReservasMouseClicked
    
    public ArrayList<Reserva> verReservas(String fecha, String hora) {
        final JSONArray[] jsonArray = new JSONArray[1];
        //Lista en la que se van a almacenar las reservas
        ArrayList<Reserva> lista = new ArrayList<>();
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservahora");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        //Json petición
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\"fecha\": \"#PARAMFECHA#\",\"hora\":\"#PARAMHORA#\",\"id\":\"#PARAMID#\"}";
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#", fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#", hora);
                        jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(restaurante));
                        osw.write(jsonRequest);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
                        System.out.println((responseCode == HttpURLConnection.HTTP_OK) + " " + responseCode);
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
                            jsonArray[0] = new JSONObject(response.toString()).getJSONArray("reservas");
                            //Recorremos el array y vamos rellenando
                            for (int i = 0; i < jsonArray[0].length(); i++) {
                                Reserva r = new Reserva();
                                JSONObject json = jsonArray[0].getJSONObject(i);
                                r.setId(json.getInt("id_reserva"));
                                r.setFecha(json.getString("fecha"));
                                r.setId_salon(json.getInt("id_salon"));
                                r.setN_personas(json.getInt("n_personas"));
                                r.setHora(json.getString("hora"));
                                r.setObservaciones(json.getString("observaciones"));
                                r.setNombre_apellidos(json.getString("nombre_apellidos"));
                                r.setTelefono(json.getString("telefono"));
                                r.setEmail(json.getString("email"));
                                lista.add(r);
                            }
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
                    } catch (JSONException e) {
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void cargarOcupacion(String fecha) {
        //Modelo en el q vamos a almacenar la ocupación
        DefaultListModel<Ocupacion> modelo = new DefaultListModel<>();
        JSONObject[] json = new JSONObject[1];
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getocupacion");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        //Calculamos los tramos y generamos una serie de valores en base a estos
                        String consulta = leerTramos(fecha);
                        String jsonFecha = "{\n"
                                + "    \"consulta\":\"SELECT range_values.value,salones.nombre,(SELECT COUNT(*) FROM salones WHERE id_restaurante = #PARAMID#) as n_salones,COUNT(reservas.id_salon) AS n_reservas,COALESCE(SUM(reservas.n_personas), 0) AS n_personas,salones.aforo AS aforo  FROM (#TRAMOS#) AS range_values  CROSS JOIN salones on salones.id_salon in (SELECT id_salon FROM salones WHERE id_restaurante = #PARAMID#) LEFT JOIN reservas ON range_values.value = reservas.hora AND reservas.fecha = '#PARAMFECHA#' AND salones.id_salon = reservas.id_salon  GROUP BY range_values.value, salones.id_salon  ORDER BY range_values.value ASC\",\n"
                                + "    \"fecha\":\"#PARAMFECHA#\",\n"
                                + "    \"id_restaurante\":\"#PARAMID#\",\n"
                                + "    \"dia\":\"#PARAMDIA#\"}";
                        jsonFecha = jsonFecha.replace("#TRAMOS#", consulta);
                        jsonFecha = jsonFecha.replace("#PARAMFECHA#", fecha);
                        jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(restaurante));
                        jsonFecha = jsonFecha.replace("#PARAMDIA#", String.valueOf(LocalDate.parse(fecha).getDayOfWeek().getValue()));
                        System.out.println(jsonFecha);
                        osw.write(jsonFecha);
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
                            json[0] = new JSONObject(response.toString());
                        }
                        connection.disconnect();
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (ProtocolException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        System.exit(2030);
                        throw new RuntimeException(e);
                    } catch (IOException ex) {
                        Logger.getLogger(PanelCalendario.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            //Comprobamos las respuestas
            if (json[0] == null) {
                JOptionPane.showMessageDialog(interfazPrincipal, "No se han encontrado tramos , revise el horario , la duración de las reservas y los salones", "Error", JOptionPane.PLAIN_MESSAGE);
            } else if (json[0].toString().contains("restaurante cerrado")) {
                jListOcupacionReservas.setModel(modelo);
                JOptionPane.showMessageDialog(interfazPrincipal, "Ese día no está abierto el restaurante", "Aviso", JOptionPane.PLAIN_MESSAGE);
            } else if (json[0].toString().contains("restaurante de vacaciones")) {
                jListOcupacionReservas.setModel(modelo);
                JOptionPane.showMessageDialog(interfazPrincipal, "Ese día está dentro de un periódo vacaciconal", "Aviso", JOptionPane.PLAIN_MESSAGE);
            } else if (json[0].getInt("codigo") == 1) {
                JSONArray jsonArray = json[0].getJSONArray("reservas");
                int n_salones = jsonArray.getJSONObject(0).getInt("n_salones");
                //Vamos recorriendo el array y rellenando
                for (int i = 0; i < jsonArray.length(); i += n_salones) {
                    int reservasTotal = 0;
                    String ocupacion = "";
                    for (int j = i; j < (i + n_salones); j++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(j);
                        String nombreSalon = jsonObject.getString("nombre");
                        String nReservas = jsonObject.getString("n_reservas");
                        reservasTotal += Integer.valueOf(nReservas);
                        String nPersonas = jsonObject.getString("n_personas");
                        String aforoSalon = jsonObject.getString("aforo");
                        //Formateamos ocupación en base a %
                        float ratio = Float.parseFloat(nPersonas) / Float.parseFloat(aforoSalon);
                        if (ratio < 0.33f) {
                            ocupacion += String.format("%s <font color='#008000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                        } else if (ratio < 0.66f) {
                            ocupacion += String.format("%s <font color='#FFEB00'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                        } else {
                            ocupacion += String.format("%s <font color='#8B0000'>%s</font>/%s<br></br>", nombreSalon, nPersonas, aforoSalon);
                        }

                    }
                    Ocupacion o = new Ocupacion();
                    o.setHora(LocalTime.parse(jsonArray.getJSONObject(i).getString("value")));
                    o.setnReservas(reservasTotal);
                    o.setOcupacion(ocupacion);
                    o.setFecha(LocalDate.parse(fecha));
                    modelo.addElement(o);
                }
                jListOcupacionReservas.setModel(modelo);
            } else {
                JOptionPane.showMessageDialog(this, "Revise la duración de reservas, el horario y los salones", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(PanelCalendario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String leerTramos(String fecha) {
        LocalDate fechaDate = LocalDate.parse(fecha);
        final JSONArray[] horario = {new JSONArray()};
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/gethorario");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");
                    OutputStream os = connection.getOutputStream();
                    System.out.println("TETica");
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String jsonFecha = "{\n"
                            + "    \"id\":\"#PARAMID#\"\n"
                            + "}";
                    jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(interfazPrincipal.getRestaurante()));
                    osw.write(jsonFecha);
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
                        horario[0] = new JSONObject(response.toString()).getJSONArray("horarios");
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
                } catch (JSONException e) {
                }

            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Leemos el horario del día que nos interese
        int dia = fechaDate.getDayOfWeek().getValue();
        JSONObject jsonObject;
        try {
            jsonObject = (horario[0].getJSONObject(dia - 1));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        
        LocalTime incremento = leerIncremento();
        LocalDateTime inicio_m;
        LocalDateTime fin_m;
        LocalDateTime inicio_t;
        LocalDateTime fin_t;
        LocalDateTime[] tramos;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            //Cargamos los topes y calculamos la cantidad de tramos
            inicio_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_m"), dateTimeFormatter);
            fin_m = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_m"), dateTimeFormatter);
            inicio_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_inicio_t"), dateTimeFormatter);
            fin_t = LocalDateTime.parse(fecha + " " + jsonObject.getString("hora_fin_t"), dateTimeFormatter);
            Long tramos_m = inicio_m.until(fin_m, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
            Long tramos_t = inicio_t.until(fin_t, ChronoUnit.MINUTES) / (incremento.getHour() * 60 + incremento.getMinute());
            tramos = new LocalDateTime[(int) (tramos_m + tramos_t) + 2];
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        int contador = 0;
        //Empezamos en el inicio de la mañana
        LocalDateTime tramo = inicio_m;
        while (fin_m.isAfter(tramo)) {
            tramos[contador] = tramo;
            contador++;
            //Sumamos el incremento
            tramo = tramo.plusHours(incremento.getHour());
            tramo = tramo.plusMinutes(incremento.getMinute());
            if (fin_m.isBefore(tramo)) {
                //Si nos hemos salido de la mañana , nos salimos del bucle
                break;
            }
        }
        //Vamos al inicio de la tarde
        tramo = inicio_t;
        while (fin_t.isAfter(tramo)) {
            tramos[contador] = tramo;
            contador++;
            //Sumamos el incremento
            tramo = tramo.plusHours(incremento.getHour());
            tramo = tramo.plusMinutes(incremento.getMinute());
            if (fin_t.isBefore(tramo)) {
                //Si nos hemos salido de la tarde , nos salimos del bucle
                break;
            }
        }
        //Preparamos la subconsulta con los tramos
        String texto = "SELECT '#PARAM1#' AS value ";
        for (LocalDateTime t : tramos) {
            if (t != null) {
                if (texto.contains("'#PARAM1#'")) {
                    texto = texto.replace("#PARAM1#", t.toLocalTime().toString());
                } else {
                    texto += " UNION SELECT '" + t.toLocalTime().toString() + "'";
                }
            }
        }
        return texto;
    }

    public LocalTime leerIncremento() {
        final LocalTime[] incremento = new LocalTime[1];
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getincremento");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Accept", "application/json");
                        //Json a mandar
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonFecha = "{\n"
                                + "    \"id\":\"#PARAMID#\"\n"
                                + "}";
                        jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(interfazPrincipal.getRestaurante()));
                        osw.write(jsonFecha);
                        osw.flush();
                        int responseCode = connection.getResponseCode();
//Ver si la respuesta es correctadai
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            // Si es correcta la leemos
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            StringBuilder response = new StringBuilder();
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            JSONObject jsonObject = new JSONObject(response.toString()).getJSONObject("resultado");
                            String incrementoStr = jsonObject.getString("duracion_reservas");
                            incremento[0] = LocalTime.parse(incrementoStr);
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
                    } catch (JSONException e) {
                    }

                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return incremento[0];
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
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario8;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaCalendario9;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaDomingo;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaJueves;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaLunes;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaMartes;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaMiercoles;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaSabado;
    private com.jabaubo.proyecto_reservas.Clases.CeldaCalendario celdaViernes;
    private javax.swing.JList<Ocupacion> jListOcupacionReservas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAnterior;
    private javax.swing.JButton jbSiguiente;
    private javax.swing.JLabel jlFechaCalendario;
    private javax.swing.JLabel jlFechaSeleccionada;
    private javax.swing.JPanel panelCalendario;
    // End of variables declaration//GEN-END:variables
}
