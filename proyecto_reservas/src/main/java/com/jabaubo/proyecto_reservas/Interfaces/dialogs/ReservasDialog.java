/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Interfaces.dialogs;

import com.jabaubo.proyecto_reservas.Clases.Reserva;
import java.awt.Desktop;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.plaf.ColorUIResource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author pokem
 */
public class ReservasDialog extends javax.swing.JDialog {

    /**
     * Creates new form ReservasDialog
     */
    private ArrayList<Reserva> listaCompleta;
    private ArrayList<Reserva> lista;
    private String fecha;
    private String tramo;
    private int id;
    private String[] salones;

    public ReservasDialog(java.awt.Frame parent, boolean modal, LocalDate fecha, LocalTime tramo, ArrayList<Reserva> lista, int id) {
        super(parent, modal);
        initComponents();
        this.id = id;
        this.fecha = fecha.toString();
        this.tramo = tramo.toString();
        jlTitulo.setText(String.format("FECHA: %s TRAMO: %s", fecha.toString(), tramo.toString()));
        this.lista = lista;
        this.listaCompleta = lista;
        salones = leerSalones();
        cargarReservas();
        jbActualizar.setEnabled(false);
        jbBorrar.setEnabled(false);
        jbCorreo.setEnabled(false);
        jlTitulo.setForeground(new ColorUIResource(221, 221, 221));
        setLocationRelativeTo(null);
    }

    public ReservasDialog(java.awt.Frame parent, boolean modal, LocalDate fecha, LocalTime tramo, int id) {
        super(parent, modal);
        initComponents();
        this.id = id;
        this.fecha = fecha.toString();
        this.tramo = tramo.toString();
        jlTitulo.setText(String.format("FECHA: %s TRAMO: %s", fecha.toString(), tramo.toString()));
        this.lista = new ArrayList<>();
        this.listaCompleta = lista;

        salones = leerSalones();
        jbActualizar.setEnabled(false);
        jbBorrar.setEnabled(false);
        jbCorreo.setEnabled(false);
        jlTitulo.setForeground(new ColorUIResource(221, 221, 221));
        setLocationRelativeTo(null);
    }

    public void cargarReservas() {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (int i = 0; i < lista.size(); i++) {
            Reserva r = lista.get(i);
            modelo.addElement("Cliente : " + r.getNombre_apellidos() + " Comensales : " + r.getN_personas());
        }
        jListReservas.setModel(modelo);
    }

    /*
    public String[] leerSalones() {
        final JSONArray[] jsonArray = new JSONArray[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getsalones");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    System.out.println("Respuesta insertar aforo" + (responseCode == HttpURLConnection.HTTP_OK));
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
                        connection.disconnect();
                        System.out.println("Respuesta insertar aforo" + response);
                        jsonArray[0] = new JSONObject(String.valueOf(response)).getJSONArray("aforo");
                        System.out.println(jsonArray[0]);
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
                    throw new RuntimeException(e);
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
        String[] textos = new String[jsonArray[0].length() + 1];
        textos[0] = "--- Seleccione filtro ---";
        for (int i = 0; i < jsonArray[0].length(); i++) {
            try {
                JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                System.out.println("SALON json" + jsonObject);
                textos[i + 1] = String.format("%s - %s", jsonObject.getString("id_salon"), jsonObject.getString("nombre"));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        for (String s : textos) {
            jcbFiltro.addItem(s);
        }
        return textos;
    }
     */
    public String[] leerSalones() {
        final JSONArray[] jsonArray = new JSONArray[1];
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Conectamos a la pagina con el método que queramos
                try {
                    URL url = new URL("https://reservante.mjhudesings.com/slim/getaforotramo");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    String json = "{\n"
                            + "    \"fecha\":\"#FECHA#\",\n"
                            + "    \"id\":\"#PARAMID#\",\n"
                            + "    \"hora\":\"#HORA#\"   \n"
                            + "}";
                    json = json.replace("#FECHA#", fecha);
                    json = json.replace("#HORA#", tramo);
                    json = json.replace("#PARAMID#", String.valueOf(id));
                    osw.write(json);
                    System.out.println("MANDO :" + json);
                    osw.flush();
                    int responseCode = connection.getResponseCode();
                    System.out.println("Respuesta insertar aforo" + (responseCode == HttpURLConnection.HTTP_OK));
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
                        connection.disconnect();
                        System.out.println("Respuesta insertar aforo" + response);
                        jsonArray[0] = new JSONObject(String.valueOf(response)).getJSONArray("aforo");
                        System.out.println(jsonArray[0]);
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
                    throw new RuntimeException(e);
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
        if (jsonArray[0] == null) {
            JOptionPane.showMessageDialog(this.getParent(), "No hay reservas", "Aviso", JOptionPane.PLAIN_MESSAGE);
            return null;
        } else {
            String[] textos = new String[jsonArray[0].length() + 1];
            textos[0] = "--- Seleccione filtro ---";
            for (int i = 0; i < jsonArray[0].length(); i++) {
                try {
                    JSONObject jsonObject = (JSONObject) jsonArray[0].get(i);
                    System.out.println(jsonObject);
                    textos[i + 1] = String.format("%s - %s libre: %s/%s ", jsonObject.getString("id_salon"), jsonObject.getString("nombre"), jsonObject.getString("disponible"), jsonObject.getString("aforo"));
                    textos[i + 1] = String.format("%s - %s libre: %s/%s ", jsonObject.getString("id_salon"), jsonObject.getString("nombre"), jsonObject.getString("disponible"), jsonObject.getString("aforo"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
            jcbFiltro.removeAllItems();
            for (String s : textos) {
                jcbFiltro.addItem(s);
            }
            return textos;
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

        jlTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListReservas = new javax.swing.JList<>();
        jcbFiltro = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jbActualizar = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jbCorreo = new javax.swing.JButton();
        jbInsertar = new javax.swing.JButton();
        jbBorrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jlEmail = new javax.swing.JLabel();
        jlTelefono = new javax.swing.JLabel();
        jlObservaciones = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jlComensales = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jlSalon = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jlCliente = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jlTitulo.setBackground(new java.awt.Color(109, 34, 109));
        jlTitulo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jlTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlTitulo.setText("FECHA : 30-04-2024 TRAMO 12:30");
        jlTitulo.setOpaque(true);

        jListReservas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListReservasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListReservas);

        jcbFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFiltroActionPerformed(evt);
            }
        });
        jcbFiltro.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jcbFiltroPropertyChange(evt);
            }
        });

        jLabel15.setText("Filtro");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel16.setText("Reservas");

        jbActualizar.setText("Editar");
        jbActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbActualizarActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setText("Datos");

        jbCorreo.setText("Correo");
        jbCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCorreoActionPerformed(evt);
            }
        });

        jbInsertar.setText("Insertar");
        jbInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbInsertarActionPerformed(evt);
            }
        });

        jbBorrar.setText("Borrar");
        jbBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBorrarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Cliente");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setText("Salón");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("Email");

        jlEmail.setText(" ");

        jlTelefono.setText(" ");

        jlObservaciones.setText(" ");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setText("Observaciones");

        jlComensales.setText(" ");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Comensales");

        jlSalon.setText(" ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("Teléfono");

        jlCliente.setText(" ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlTelefono, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlEmail, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlComensales, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlSalon, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlObservaciones, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlCliente, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(9, 9, 9)
                .addComponent(jlCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlTelefono)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlEmail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlComensales)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlSalon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlObservaciones)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbFiltro, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 9, Short.MAX_VALUE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.CENTER, jPanel1Layout.createSequentialGroup()
                        .addComponent(jbInsertar)
                        .addGap(18, 18, 18)
                        .addComponent(jbActualizar)
                        .addGap(18, 18, 18)
                        .addComponent(jbCorreo)
                        .addGap(18, 18, 18)
                        .addComponent(jbBorrar)
                        .addGap(31, 31, 31))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(2, 6, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbInsertar)
                    .addComponent(jbActualizar)
                    .addComponent(jbBorrar)
                    .addComponent(jbCorreo)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jlTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbInsertarActionPerformed
        // TODO add your handling code here:
        DatosReservaDialog dialog = new DatosReservaDialog((Frame) this.getParent(), true, salones, fecha, tramo);
        dialog.setVisible(true);
        System.out.println("INSERTANDO");
        listaCompleta = verReservas(fecha, tramo);
        System.out.println(listaCompleta.size());
        jcbFiltro.setSelectedIndex(0);
        lista = listaCompleta;
        cargarReservas();
        salones = leerSalones();
        jListReservas.validate();
    }//GEN-LAST:event_jbInsertarActionPerformed

    private void jbActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbActualizarActionPerformed
        // TODO add your handling code here:
        DatosReservaDialog dialog = new DatosReservaDialog((Frame) this.getParent(), true, lista.get(jListReservas.getSelectedIndex()), salones);
        dialog.setVisible(true);
        Reserva r = lista.get(0);
        System.out.println("Actualizando");
        listaCompleta = verReservas(r.getFecha(), r.getHora());
        jcbFiltro.setSelectedIndex(0);
        lista = listaCompleta;
        cargarReservas();
        salones = leerSalones();
        jListReservas.repaint();
        System.out.println("LISTA");
    }//GEN-LAST:event_jbActualizarActionPerformed

    private void jListReservasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListReservasValueChanged
        // TODO add your handling code here:
        if (jListReservas.getSelectedIndex() >= 0) {
            Reserva r = lista.get(jListReservas.getSelectedIndex());
            if (r.getNombre_apellidos().equals("")) {
                jlCliente.setText(" ");
            } else {
                jlCliente.setText(r.getNombre_apellidos());
            }
            if (String.valueOf(r.getN_personas()).equals("")) {
                jlComensales.setText(" ");
            } else {
                jlComensales.setText(String.valueOf(r.getN_personas()));
            }
            if (r.getEmail().equals("")) {
                jlEmail.setText(" ");
            } else {
                jlEmail.setText(r.getEmail());
            }
            if (String.valueOf(r.getId_salon()).equals("")) {
                jlSalon.setText(" ");
            } else {
                jlSalon.setText(String.valueOf(r.getId_salon()));
            }
            if (r.getObservaciones().equals("")) {
                jlObservaciones.setText(" ");
            } else {
                jlObservaciones.setText(r.getObservaciones());
            }
            if (r.getTelefono().equals("")) {
                jlTelefono.setText(" ");
            } else {
                jlTelefono.setText(r.getTelefono());
            }
            jbActualizar.setEnabled(true);
            jbCorreo.setEnabled(true);
            jbBorrar.setEnabled(true);
        } else {
            jlCliente.setText(" ");
            jlComensales.setText(" ");
            jlEmail.setText(" ");
            jlSalon.setText(" ");
            jlObservaciones.setText(" ");
            jlTelefono.setText(" ");
            jbActualizar.setEnabled(false);
            jbCorreo.setEnabled(false);
            jbBorrar.setEnabled(false);
        }
    }//GEN-LAST:event_jListReservasValueChanged

    private void jbBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBorrarActionPerformed
        // TODO add your handling code here:
        Reserva r = lista.get(jListReservas.getSelectedIndex());
        int response = JOptionPane.showConfirmDialog(this.getParent(), "¿Quieres borrar la reserva de " + r.getNombre_apellidos() + " ?", "Advertencia", JOptionPane.WARNING_MESSAGE);
        if (response == JOptionPane.OK_OPTION) {
            lista.remove(r);
            String jsonStr = "{\n"
                    + "            \"id_reserva\": \"#PARAMRESERVA#\"\n"
                    + "}";
            jsonStr = jsonStr.replace("#PARAMRESERVA#", String.valueOf(r.getId()));
            String finalJsonStr = jsonStr;

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/deletereserva");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        connection.setDoOutput(true);
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        osw.write(finalJsonStr);
                        System.out.println("JSON EN LA PETICIÓN: " + finalJsonStr);
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
                            System.out.println(response);
                            reader.close();
                            connection.disconnect();
                            //actualizarLista();
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cargarReservas();
        }

    }//GEN-LAST:event_jbBorrarActionPerformed

    private void jcbFiltroPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jcbFiltroPropertyChange
        // TODO add your handling code here:
        if (jcbFiltro.getSelectedItem() != null) {
            String filtro = jcbFiltro.getSelectedItem().toString();
            if (!filtro.equals("--- Seleccione filtro ---")) {
                String id = filtro.substring(0, filtro.indexOf("-") - 1);
                lista = new ArrayList<>();
                for (Reserva r : listaCompleta) {
                    if (r.getId_salon() == Integer.valueOf(id)) {
                        lista.add(r);
                    }
                }
            } else {
                lista = listaCompleta;
            }
            cargarReservas();
        }
    }//GEN-LAST:event_jcbFiltroPropertyChange

    private void jcbFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFiltroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbFiltroActionPerformed

    private void jbCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCorreoActionPerformed
        // TODO add your handling code here:
        Desktop desktop;
        if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            try {
                String uri = "mailto:#CORREO#?subject=Hello%20World";
                uri = uri.replace("#CORREO#", jlEmail.getText());
                URI mailto = new URI(uri);
                desktop.mail(mailto);
            } catch (IOException ex) {
                Logger.getLogger(ReservasDialog.class.getName()).log(Level.SEVERE, null, ex);
            } catch (URISyntaxException ex) {
                Logger.getLogger(ReservasDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jbCorreoActionPerformed

    public ArrayList<Reserva> verReservas(String fecha, String hora) {
        final JSONArray[] jsonArray = new JSONArray[1];
        ArrayList<Reserva> lista = new ArrayList<>();
        try {
            System.out.println("Pa dentro");
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    // Conectamos a la pagina con el método que queramos
                    try {
                        URL url = new URL("https://reservante.mjhudesings.com/slim/getreservahora");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        OutputStream os = connection.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                        String jsonRequest = "{\n"
                                + "    \"fecha\":\"#PARAMFECHA#\",\n"
                                + "    \"hora\":\"#PARAMHORA#\",\n"
                                + "    \"id\":\"#PARAMID#\"\n"
                                + "}";
                        jsonRequest = jsonRequest.replace("#PARAMFECHA#", fecha);
                        jsonRequest = jsonRequest.replace("#PARAMHORA#", hora);
                        jsonRequest = jsonRequest.replace("#PARAMID#", String.valueOf(id));
                        System.out.println("jsonRequest " + jsonRequest);
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
                            System.out.println(response);
                            jsonArray[0] = new JSONObject(response.toString()).getJSONArray("reservas");
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
                                System.out.println(r);
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
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jListReservas;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbActualizar;
    private javax.swing.JButton jbBorrar;
    private javax.swing.JButton jbCorreo;
    private javax.swing.JButton jbInsertar;
    private javax.swing.JComboBox<String> jcbFiltro;
    private javax.swing.JLabel jlCliente;
    private javax.swing.JLabel jlComensales;
    private javax.swing.JLabel jlEmail;
    private javax.swing.JLabel jlObservaciones;
    private javax.swing.JLabel jlSalon;
    private javax.swing.JLabel jlTelefono;
    private javax.swing.JLabel jlTitulo;
    // End of variables declaration//GEN-END:variables
}
