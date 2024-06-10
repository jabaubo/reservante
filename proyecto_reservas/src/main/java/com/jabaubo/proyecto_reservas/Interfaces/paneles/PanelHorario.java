/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Interfaces.paneles;

import com.jabaubo.proyecto_reservas.Clases.Horario;
import com.jabaubo.proyecto_reservas.Clases.Ocupacion;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Javier
 */
public class PanelHorario extends javax.swing.JPanel {

    /**
     * Creates new form PanelHorario
     */
    private int idRestaurante;

    public PanelHorario(int idRestaurante) {
        this.idRestaurante = idRestaurante;
        initComponents();
// Panel 3
        jPanel3.setName("PanelLunes");
        jComboBox13.setName("ComboBoxHoraInicioM");
        jComboBox14.setName("ComboBoxMinutoInicioM");
        jComboBox15.setName("ComboBoxHoraFinM");
        jComboBox16.setName("ComboBoxMinutoFinM");
        jComboBox17.setName("ComboBoxMinutoFinT");
        jComboBox18.setName("ComboBoxHoraFinT");
        jComboBox19.setName("ComboBoxMinutoInicioT");
        jComboBox20.setName("ComboBoxHoraInicioT");

// Panel 4// Panel 4
        jPanel4.setName("PanelMartes");
        jComboBox21.setName("ComboBoxHoraInicioM");
        jComboBox22.setName("ComboBoxMinutoInicioM");
        jComboBox23.setName("ComboBoxHoraFinM");
        jComboBox24.setName("ComboBoxMinutoFinM");
        jComboBox25.setName("ComboBoxMinutoFinT");
        jComboBox26.setName("ComboBoxHoraFinT");
        jComboBox27.setName("ComboBoxMinutoInicioT");
        jComboBox28.setName("ComboBoxHoraInicioT");

// Panel 5 - Miércoles
        jPanel5.setName("PanelMiércoles");
        jComboBox29.setName("ComboBoxHoraInicioM");
        jComboBox30.setName("ComboBoxMinutoInicioM");
        jComboBox31.setName("ComboBoxHoraFinM");
        jComboBox32.setName("ComboBoxMinutoFinM");
        jComboBox33.setName("ComboBoxMinutoFinT");
        jComboBox34.setName("ComboBoxHoraFinT");
        jComboBox35.setName("ComboBoxMinutoInicioT");
        jComboBox36.setName("ComboBoxHoraInicioT");

// Panel 6 - Jueves
        jPanel6.setName("PanelJueves");
        jComboBox37.setName("ComboBoxHoraInicioM");
        jComboBox38.setName("ComboBoxMinutoInicioM");
        jComboBox39.setName("ComboBoxHoraFinM");
        jComboBox40.setName("ComboBoxMinutoFinM");
        jComboBox41.setName("ComboBoxMinutoFinT");
        jComboBox42.setName("ComboBoxHoraFinT");
        jComboBox43.setName("ComboBoxMinutoInicioT");
        jComboBox44.setName("ComboBoxHoraInicioT");

// Panel 7 - Viernes
        jPanel7.setName("PanelViernes");
        jComboBox45.setName("ComboBoxHoraInicioM");
        jComboBox46.setName("ComboBoxMinutoInicioM");
        jComboBox47.setName("ComboBoxHoraFinM");
        jComboBox48.setName("ComboBoxMinutoFinM");
        jComboBox49.setName("ComboBoxMinutoFinT");
        jComboBox50.setName("ComboBoxHoraFinT");
        jComboBox51.setName("ComboBoxMinutoInicioT");
        jComboBox52.setName("ComboBoxHoraInicioT");

// Panel 8 - Sábado
        jPanel8.setName("PanelSábado");
        jComboBox53.setName("ComboBoxHoraInicioM");
        jComboBox54.setName("ComboBoxMinutoInicioM");
        jComboBox55.setName("ComboBoxHoraFinM");
        jComboBox56.setName("ComboBoxMinutoFinM");
        jComboBox57.setName("ComboBoxMinutoFinT");
        jComboBox58.setName("ComboBoxHoraFinT");
        jComboBox59.setName("ComboBoxMinutoInicioT");
        jComboBox60.setName("ComboBoxHoraInicioT");

// Panel 9 - Domingo
        jPanel9.setName("PanelDomingo");
        jComboBox61.setName("ComboBoxHoraInicioM");
        jComboBox62.setName("ComboBoxMinutoInicioM");
        jComboBox63.setName("ComboBoxHoraFinM");
        jComboBox64.setName("ComboBoxMinutoFinM");
        jComboBox65.setName("ComboBoxMinutoFinT");
        jComboBox66.setName("ComboBoxHoraFinT");
        jComboBox67.setName("ComboBoxMinutoInicioT");
        jComboBox68.setName("ComboBoxHoraInicioT");

        cargarDatos();
    }

    public void cargarDatos(){
        JSONArray jsonArray = cargarHorarioAPI();
        System.out.println(jsonArray);
        if (jsonArray != null) {
            ArrayList<Horario> lista = new ArrayList<>();
            DefaultListModel<Horario> modelo = new DefaultListModel<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String dia = jsonObject.getString("dia");
                    Boolean cerrado = jsonObject.getInt("cerrado") == 1;
                    String hora_inicio_m = jsonObject.getString("hora_inicio_m").substring(0, jsonObject.getString("hora_inicio_m").lastIndexOf(":"));
                    String hora_fin_m = jsonObject.getString("hora_fin_m").substring(0, jsonObject.getString("hora_fin_m").lastIndexOf(":"));
                    String hora_inicio_t = jsonObject.getString("hora_inicio_t").substring(0, jsonObject.getString("hora_inicio_t").lastIndexOf(":"));
                    String hora_fin_t = jsonObject.getString("hora_fin_t").substring(0, jsonObject.getString("hora_fin_t").lastIndexOf(":"));
                    Horario h = new Horario(dia, cerrado, hora_inicio_m, hora_fin_m, hora_inicio_t, hora_fin_t, idRestaurante);
                    lista.add(h);
                    modelo.addElement(h);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("COMPONENTES");
            int horarioLeer = 0;
            for (Component c : jPanel1.getComponents()) {
                if (c.getClass().getName().equals("javax.swing.JPanel") && horarioLeer < 7) {
                    System.out.println(((JPanel) c).getName());
                    JPanel panel = (JPanel) c;
                    Horario h = lista.get(horarioLeer);
                    for (Component child : panel.getComponents()) {

                        if (child.getClass().getName().equals("javax.swing.JPanel")) {
                            Component[] childList2 = ((JPanel) child).getComponents();
                            for (Component child2 : childList2) {
                                if (child2.getClass().getName().equals("javax.swing.JCheckBox")) {
                                    ((JCheckBox) child2).setSelected(h.getCerrado());
                                }
                            }
                        }
                        if (child.getClass().getName().equals("javax.swing.JComboBox")) {
                            JComboBox comboBox = (JComboBox) child;
                            String[] horaIncioMArray = h.getHora_inicio_m().split(":");
                            String[] horaFinMArray = h.getHora_fin_m().split(":");
                            String[] horaIncioTArray = h.getHora_inicio_t().split(":");
                            String[] horaFinTArray = h.getHora_fin_t().split(":");
                            comboBox.setEnabled(!h.getCerrado());
                            switch (comboBox.getName()) {
                                case "ComboBoxHoraInicioM":
                                    comboBox.setSelectedItem(horaIncioMArray[0]);
                                    break;
                                case "ComboBoxMinutoInicioM":
                                    comboBox.setSelectedItem(horaIncioMArray[1]);
                                    break;
                                case "ComboBoxHoraFinM":
                                    comboBox.setSelectedItem(horaFinMArray[0]);
                                    break;
                                case "ComboBoxMinutoFinM":
                                    comboBox.setSelectedItem(horaFinMArray[1]);
                                    break;
                                case "ComboBoxHoraInicioT":
                                    comboBox.setSelectedItem(horaIncioTArray[0]);
                                    break;
                                case "ComboBoxMinutoInicioT":
                                    comboBox.setSelectedItem(horaIncioTArray[1]);
                                    break;
                                case "ComboBoxHoraFinT":
                                    comboBox.setSelectedItem(horaFinTArray[0]);
                                    break;
                                case "ComboBoxMinutoFinT":
                                    comboBox.setSelectedItem(horaFinTArray[1]);
                                    break;
                                default:
                                    throw new AssertionError();
                            }
                        } else {
                            System.out.println(child.getClass().getName());
                        }
                    }
                    horarioLeer++;
                }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        jComboBox13 = new javax.swing.JComboBox<>();
        jComboBox14 = new javax.swing.JComboBox<>();
        jComboBox15 = new javax.swing.JComboBox<>();
        jComboBox16 = new javax.swing.JComboBox<>();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jComboBox18 = new javax.swing.JComboBox<>();
        jComboBox19 = new javax.swing.JComboBox<>();
        jComboBox20 = new javax.swing.JComboBox<>();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jlDia1 = new javax.swing.JLabel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel115 = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        jComboBox21 = new javax.swing.JComboBox<>();
        jComboBox22 = new javax.swing.JComboBox<>();
        jComboBox23 = new javax.swing.JComboBox<>();
        jComboBox24 = new javax.swing.JComboBox<>();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jLabel121 = new javax.swing.JLabel();
        jComboBox25 = new javax.swing.JComboBox<>();
        jComboBox26 = new javax.swing.JComboBox<>();
        jComboBox27 = new javax.swing.JComboBox<>();
        jComboBox28 = new javax.swing.JComboBox<>();
        jLabel122 = new javax.swing.JLabel();
        jLabel123 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jCheckBox10 = new javax.swing.JCheckBox();
        jLabel100 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jComboBox29 = new javax.swing.JComboBox<>();
        jComboBox30 = new javax.swing.JComboBox<>();
        jComboBox31 = new javax.swing.JComboBox<>();
        jComboBox32 = new javax.swing.JComboBox<>();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        jComboBox33 = new javax.swing.JComboBox<>();
        jComboBox34 = new javax.swing.JComboBox<>();
        jComboBox35 = new javax.swing.JComboBox<>();
        jComboBox36 = new javax.swing.JComboBox<>();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jlDia2 = new javax.swing.JLabel();
        jCheckBox11 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jLabel137 = new javax.swing.JLabel();
        jComboBox37 = new javax.swing.JComboBox<>();
        jComboBox38 = new javax.swing.JComboBox<>();
        jComboBox39 = new javax.swing.JComboBox<>();
        jComboBox40 = new javax.swing.JComboBox<>();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jComboBox41 = new javax.swing.JComboBox<>();
        jComboBox42 = new javax.swing.JComboBox<>();
        jComboBox43 = new javax.swing.JComboBox<>();
        jComboBox44 = new javax.swing.JComboBox<>();
        jLabel142 = new javax.swing.JLabel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jlDia4 = new javax.swing.JLabel();
        jCheckBox12 = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jLabel145 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        jComboBox45 = new javax.swing.JComboBox<>();
        jComboBox46 = new javax.swing.JComboBox<>();
        jComboBox47 = new javax.swing.JComboBox<>();
        jComboBox48 = new javax.swing.JComboBox<>();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        jComboBox49 = new javax.swing.JComboBox<>();
        jComboBox50 = new javax.swing.JComboBox<>();
        jComboBox51 = new javax.swing.JComboBox<>();
        jComboBox52 = new javax.swing.JComboBox<>();
        jLabel152 = new javax.swing.JLabel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jCheckBox13 = new javax.swing.JCheckBox();
        jLabel103 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jComboBox53 = new javax.swing.JComboBox<>();
        jComboBox54 = new javax.swing.JComboBox<>();
        jComboBox55 = new javax.swing.JComboBox<>();
        jComboBox56 = new javax.swing.JComboBox<>();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jLabel161 = new javax.swing.JLabel();
        jComboBox57 = new javax.swing.JComboBox<>();
        jComboBox58 = new javax.swing.JComboBox<>();
        jComboBox59 = new javax.swing.JComboBox<>();
        jComboBox60 = new javax.swing.JComboBox<>();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        jLabel164 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jCheckBox14 = new javax.swing.JCheckBox();
        jLabel104 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jComboBox61 = new javax.swing.JComboBox<>();
        jComboBox62 = new javax.swing.JComboBox<>();
        jComboBox63 = new javax.swing.JComboBox<>();
        jComboBox64 = new javax.swing.JComboBox<>();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jComboBox65 = new javax.swing.JComboBox<>();
        jComboBox66 = new javax.swing.JComboBox<>();
        jComboBox67 = new javax.swing.JComboBox<>();
        jComboBox68 = new javax.swing.JComboBox<>();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jCheckBox15 = new javax.swing.JCheckBox();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jLabel167 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        jScrollPane1.setBorder(null);

        jPanel1.setAlignmentX(0.75F);
        jPanel1.setAlignmentY(0.75F);
        jPanel1.setLayout(new java.awt.GridLayout(4, 2, 5, 5));

        jPanel3.setBackground(new java.awt.Color(241, 241, 241));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel105.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(51, 51, 51));
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setText(":");

        jLabel106.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(51, 51, 51));
        jLabel106.setText("Fin");

        jLabel107.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel107.setForeground(new java.awt.Color(51, 51, 51));
        jLabel107.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel107.setText("Fin");

        jComboBox13.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox13.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox13.setEnabled(false);

        jComboBox14.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox14.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox14.setEnabled(false);
        jComboBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox14ActionPerformed(evt);
            }
        });

        jComboBox15.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox15.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox15.setEnabled(false);

        jComboBox16.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox16.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox16.setEnabled(false);

        jLabel108.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(51, 51, 51));
        jLabel108.setText("Inicio");

        jLabel109.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(51, 51, 51));
        jLabel109.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel109.setText(":");

        jLabel110.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(51, 51, 51));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText(":");

        jLabel111.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(51, 51, 51));
        jLabel111.setText("Inicio");

        jComboBox17.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox17.setEnabled(false);

        jComboBox18.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox18.setEnabled(false);

        jComboBox19.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox19.setEnabled(false);

        jComboBox20.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox20.setEnabled(false);

        jLabel112.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel112.setForeground(new java.awt.Color(51, 51, 51));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel112.setText("Fin");

        jLabel113.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel113.setForeground(new java.awt.Color(51, 51, 51));
        jLabel113.setText("Fin");

        jLabel114.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel114.setForeground(new java.awt.Color(51, 51, 51));
        jLabel114.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel114.setText(":");

        jPanel10.setBackground(new java.awt.Color(149, 42, 149));

        jlDia1.setBackground(new java.awt.Color(149, 42, 149));
        jlDia1.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jlDia1.setForeground(new java.awt.Color(240, 240, 240));
        jlDia1.setText("Lunes");

        jCheckBox4.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox4.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox4.setSelected(true);
        jCheckBox4.setText("Cerrado");
        jCheckBox4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jCheckBox4StateChanged(evt);
            }
        });
        jCheckBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox4)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlDia1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                    .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel108, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox13, 0, 81, Short.MAX_VALUE)
                    .addComponent(jComboBox20, 0, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel114, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox19, 0, 81, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox14, 0, 81, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel107, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox15, 0, 81, Short.MAX_VALUE)
                    .addComponent(jComboBox18, 0, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox17, 0, 79, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel109, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox16, 0, 79, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel106)
                        .addComponent(jLabel108)
                        .addComponent(jLabel105)
                        .addComponent(jLabel107)
                        .addComponent(jComboBox13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel109)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel113)
                    .addComponent(jLabel111)
                    .addComponent(jLabel114)
                    .addComponent(jLabel112)
                    .addComponent(jLabel110)
                    .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(241, 241, 241));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel115.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel115.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel115.setText(":");

        jLabel116.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel116.setText("Fin");

        jLabel117.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel117.setText("Fin");

        jComboBox21.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox21.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox21.setEnabled(false);

        jComboBox22.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox22.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox22.setEnabled(false);
        jComboBox22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox22ActionPerformed(evt);
            }
        });

        jComboBox23.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox23.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox23.setEnabled(false);

        jComboBox24.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox24.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox24.setEnabled(false);

        jLabel118.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel118.setText("Inicio");

        jLabel119.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel119.setText(":");

        jLabel120.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setText(":");

        jLabel121.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel121.setText("Inicio");

        jComboBox25.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox25.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox25.setEnabled(false);

        jComboBox26.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox26.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox26.setEnabled(false);

        jComboBox27.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox27.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox27.setEnabled(false);

        jComboBox28.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox28.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox28.setEnabled(false);

        jLabel122.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel122.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel122.setText("Fin");

        jLabel123.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel123.setText("Fin");

        jLabel124.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText(":");

        jPanel14.setBackground(new java.awt.Color(149, 42, 149));

        jCheckBox10.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox10.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox10.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox10.setSelected(true);
        jCheckBox10.setText("Cerrado");
        jCheckBox10.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambiarCheckBox(evt);
            }
        });
        jCheckBox10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });
        jCheckBox10.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cbPropertyCambio(evt);
            }
        });

        jLabel100.setBackground(new java.awt.Color(149, 42, 149));
        jLabel100.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(240, 240, 240));
        jLabel100.setText("Martes");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox10)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel100, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox10, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel123, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(jLabel116, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel121, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox21, 0, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox28, 0, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel122, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel115, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox23, 0, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox26, 0, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox25, 0, 83, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox24, 0, 83, Short.MAX_VALUE)))
                .addGap(7, 7, 7))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel115, jLabel124});

        jPanel4Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox22, jComboBox27});

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel116)
                        .addComponent(jLabel118)
                        .addComponent(jLabel115)
                        .addComponent(jLabel117)
                        .addComponent(jComboBox21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel119)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel123)
                    .addComponent(jLabel121)
                    .addComponent(jLabel124)
                    .addComponent(jLabel122)
                    .addComponent(jLabel120)
                    .addComponent(jComboBox28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(241, 241, 241));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel125.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText(":");

        jLabel126.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel126.setText("Fin");

        jLabel127.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel127.setText("Fin");

        jComboBox29.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox29.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox29.setEnabled(false);

        jComboBox30.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox30.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox30.setEnabled(false);
        jComboBox30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox30ActionPerformed(evt);
            }
        });

        jComboBox31.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox31.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox31.setEnabled(false);

        jComboBox32.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox32.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox32.setEnabled(false);

        jLabel128.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel128.setText("Inicio");

        jLabel129.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel129.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel129.setText(":");

        jLabel130.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel130.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel130.setText(":");

        jLabel131.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel131.setText("Inicio");

        jComboBox33.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox33.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox33.setEnabled(false);

        jComboBox34.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox34.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox34.setEnabled(false);

        jComboBox35.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox35.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox35.setEnabled(false);

        jComboBox36.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox36.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox36.setEnabled(false);

        jLabel132.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel132.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel132.setText("Fin");

        jLabel133.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel133.setText("Fin");

        jLabel134.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel134.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel134.setText(":");

        jPanel11.setBackground(new java.awt.Color(149, 42, 149));

        jlDia2.setBackground(new java.awt.Color(149, 42, 149));
        jlDia2.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jlDia2.setForeground(new java.awt.Color(240, 240, 240));
        jlDia2.setText("Miércoles");

        jCheckBox11.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox11.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox11.setSelected(true);
        jCheckBox11.setText("Cerrado");
        jCheckBox11.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambiarCheckBox(evt);
            }
        });
        jCheckBox11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlDia2, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox11)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCheckBox11, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jlDia2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel133, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel128, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel131, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox29, 0, 81, Short.MAX_VALUE)
                    .addComponent(jComboBox36, 0, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel134, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox35, 0, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel132, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel125, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox30, 0, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox31, 0, 81, Short.MAX_VALUE)
                    .addComponent(jComboBox34, 0, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel130, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox33, 0, 81, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel129, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox32, 0, 81, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel129, jLabel130});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel126)
                        .addComponent(jLabel128)
                        .addComponent(jLabel125)
                        .addComponent(jLabel127)
                        .addComponent(jComboBox29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel129)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel133)
                    .addComponent(jLabel131)
                    .addComponent(jLabel134)
                    .addComponent(jLabel132)
                    .addComponent(jLabel130)
                    .addComponent(jComboBox36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(241, 241, 241));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel135.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel135.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel135.setText(":");

        jLabel136.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel136.setText("Fin");

        jLabel137.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel137.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel137.setText("Fin");

        jComboBox37.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox37.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox37.setEnabled(false);

        jComboBox38.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox38.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox38.setEnabled(false);
        jComboBox38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox38ActionPerformed(evt);
            }
        });

        jComboBox39.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox39.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox39.setEnabled(false);

        jComboBox40.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox40.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox40.setEnabled(false);

        jLabel138.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel138.setText("Inicio");

        jLabel139.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel139.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel139.setText(":");

        jLabel140.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel140.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel140.setText(":");

        jLabel141.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel141.setText("Inicio");

        jComboBox41.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox41.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox41.setEnabled(false);

        jComboBox42.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox42.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox42.setEnabled(false);

        jComboBox43.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox43.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox43.setEnabled(false);

        jComboBox44.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox44.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox44.setEnabled(false);

        jLabel142.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel142.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel142.setText("Fin");

        jLabel143.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel143.setText("Fin");

        jLabel144.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel144.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel144.setText(":");

        jPanel15.setBackground(new java.awt.Color(149, 42, 149));

        jlDia4.setBackground(new java.awt.Color(149, 42, 149));
        jlDia4.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jlDia4.setForeground(new java.awt.Color(240, 240, 240));
        jlDia4.setText("Jueves");

        jCheckBox12.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox12.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox12.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox12.setSelected(true);
        jCheckBox12.setText("Cerrado");
        jCheckBox12.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambiarCheckBox(evt);
            }
        });
        jCheckBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlDia4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox12)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlDia4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox12, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel143, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                    .addComponent(jLabel136, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel138, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel141, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox37, 0, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox44, 0, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel144, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox43, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel142, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel135, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox38, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel137, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox39, 0, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox42, 0, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel140, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox41, 0, 82, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel139, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox40, 0, 82, Short.MAX_VALUE)))
                .addGap(10, 10, 10))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel135, jLabel144});

        jPanel6Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox38, jComboBox43});

        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel136)
                        .addComponent(jLabel138)
                        .addComponent(jLabel135)
                        .addComponent(jLabel137)
                        .addComponent(jComboBox37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel139)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel143)
                    .addComponent(jLabel141)
                    .addComponent(jLabel144)
                    .addComponent(jLabel142)
                    .addComponent(jLabel140)
                    .addComponent(jComboBox44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(241, 241, 241));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel145.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel145.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel145.setText(":");

        jLabel146.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel146.setText("Fin");

        jLabel147.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel147.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel147.setText("Fin");

        jComboBox45.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox45.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox45.setEnabled(false);

        jComboBox46.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox46.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox46.setEnabled(false);
        jComboBox46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox46ActionPerformed(evt);
            }
        });

        jComboBox47.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox47.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox47.setEnabled(false);

        jComboBox48.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox48.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox48.setEnabled(false);

        jLabel148.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel148.setText("Inicio");

        jLabel149.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel149.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel149.setText(":");

        jLabel150.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel150.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel150.setText(":");

        jLabel151.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel151.setText("Inicio");

        jComboBox49.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox49.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox49.setEnabled(false);

        jComboBox50.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox50.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox50.setEnabled(false);

        jComboBox51.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox51.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox51.setEnabled(false);

        jComboBox52.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox52.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox52.setEnabled(false);

        jLabel152.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel152.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel152.setText("Fin");

        jLabel153.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel153.setText("Fin");

        jLabel154.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel154.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel154.setText(":");

        jPanel12.setBackground(new java.awt.Color(149, 42, 149));

        jCheckBox13.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox13.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox13.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox13.setSelected(true);
        jCheckBox13.setText("Cerrado");
        jCheckBox13.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambiarCheckBox(evt);
            }
        });
        jCheckBox13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });

        jLabel103.setBackground(new java.awt.Color(149, 42, 149));
        jLabel103.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(240, 240, 240));
        jLabel103.setText("Viernes");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox13)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel153, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                    .addComponent(jLabel146, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel148, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel151, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox45, 0, 81, Short.MAX_VALUE)
                    .addComponent(jComboBox52, 0, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel154, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox51, 0, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel152, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel145, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox46, 0, 80, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel147, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox47, 0, 81, Short.MAX_VALUE)
                    .addComponent(jComboBox50, 0, 81, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel150, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox49, 0, 79, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel149, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox48, 0, 79, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel146)
                        .addComponent(jLabel148)
                        .addComponent(jLabel145)
                        .addComponent(jLabel147)
                        .addComponent(jComboBox45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel149)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel153)
                    .addComponent(jLabel151)
                    .addComponent(jLabel154)
                    .addComponent(jLabel152)
                    .addComponent(jLabel150)
                    .addComponent(jComboBox52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(241, 241, 241));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel155.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel155.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel155.setText(":");

        jLabel156.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel156.setText("Fin");

        jLabel157.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel157.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel157.setText("Fin");

        jComboBox53.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox53.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox53.setEnabled(false);

        jComboBox54.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox54.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox54.setEnabled(false);
        jComboBox54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox54ActionPerformed(evt);
            }
        });

        jComboBox55.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox55.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox55.setEnabled(false);

        jComboBox56.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox56.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox56.setEnabled(false);

        jLabel158.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel158.setText("Inicio");

        jLabel159.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel159.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel159.setText(":");

        jLabel160.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel160.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel160.setText(":");

        jLabel161.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel161.setText("Inicio");

        jComboBox57.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox57.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox57.setEnabled(false);

        jComboBox58.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox58.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox58.setEnabled(false);

        jComboBox59.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox59.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox59.setEnabled(false);

        jComboBox60.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox60.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox60.setEnabled(false);

        jLabel162.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel162.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel162.setText("Fin");

        jLabel163.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel163.setText("Fin");

        jLabel164.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel164.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel164.setText(":");

        jPanel16.setBackground(new java.awt.Color(149, 42, 149));

        jCheckBox14.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox14.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox14.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox14.setSelected(true);
        jCheckBox14.setText("Cerrado");
        jCheckBox14.setName(""); // NOI18N
        jCheckBox14.setOpaque(true);
        jCheckBox14.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambiarCheckBox(evt);
            }
        });
        jCheckBox14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });

        jLabel104.setBackground(new java.awt.Color(149, 42, 149));
        jLabel104.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(240, 240, 240));
        jLabel104.setText("Sábado");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox14)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox14, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel163, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                    .addComponent(jLabel156, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel158, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel161, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox53, 0, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox60, 0, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel164, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox59, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel162, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel155, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox54, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel157, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox55, 0, 80, Short.MAX_VALUE)
                    .addComponent(jComboBox58, 0, 80, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel160, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox57, 0, 79, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel159, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox56, 0, 79, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
            .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jComboBox54, jComboBox59});

        jPanel8Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel155, jLabel164});

        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel156)
                        .addComponent(jLabel158)
                        .addComponent(jLabel155)
                        .addComponent(jLabel157)
                        .addComponent(jComboBox53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel159)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel163)
                    .addComponent(jLabel161)
                    .addComponent(jLabel164)
                    .addComponent(jLabel162)
                    .addComponent(jLabel160)
                    .addComponent(jComboBox60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox57, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(241, 241, 241));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel177.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel177.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel177.setText(":");

        jLabel178.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel178.setText("Fin");

        jComboBox61.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox61.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox61.setEnabled(false);

        jComboBox62.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox62.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox62.setEnabled(false);
        jComboBox62.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox62ActionPerformed(evt);
            }
        });

        jComboBox63.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox63.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox63.setEnabled(false);

        jComboBox64.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox64.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox64.setEnabled(false);

        jLabel180.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel180.setText("Inicio");

        jLabel181.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel181.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel181.setText(":");

        jLabel182.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel182.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel182.setText(":");

        jLabel183.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel183.setText("Inicio");

        jComboBox65.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox65.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox65.setEnabled(false);

        jComboBox66.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox66.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox66.setEnabled(false);

        jComboBox67.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox67.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        jComboBox67.setEnabled(false);

        jComboBox68.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jComboBox68.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jComboBox68.setEnabled(false);

        jLabel185.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel185.setText("Fin");

        jLabel186.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel186.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel186.setText(":");

        jPanel13.setBackground(new java.awt.Color(149, 42, 149));

        jCheckBox15.setBackground(new java.awt.Color(149, 42, 149));
        jCheckBox15.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jCheckBox15.setForeground(new java.awt.Color(240, 240, 240));
        jCheckBox15.setSelected(true);
        jCheckBox15.setText("Cerrado");
        jCheckBox15.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                cambiarCheckBox(evt);
            }
        });
        jCheckBox15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clickCheckBox(evt);
            }
        });

        jLabel165.setBackground(new java.awt.Color(149, 42, 149));
        jLabel165.setFont(new java.awt.Font("MS UI Gothic", 1, 24)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(240, 240, 240));
        jLabel165.setText("Domingo");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel165, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox15)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jCheckBox15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel165, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel166.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel166.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel166.setText("Fin");

        jLabel167.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel167.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel167.setText("Fin");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel185, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jLabel178, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel180, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel183, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox61, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox68, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel186, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox67, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel166, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel177, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox62, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel167, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox63, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox66, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel182, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox65, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel181, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox64, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel178)
                        .addComponent(jLabel180)
                        .addComponent(jLabel177)
                        .addComponent(jComboBox61, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox62, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox63, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel167))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox64, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel181)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel185)
                    .addComponent(jLabel183)
                    .addComponent(jLabel186)
                    .addComponent(jLabel182)
                    .addComponent(jComboBox68, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox67, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox66, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox65, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel166))
                .addContainerGap())
        );

        jPanel1.add(jPanel9);

        jPanel2.setBackground(new java.awt.Color(221, 221, 221));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton2.setText("jButton2");
        jButton2.setBorder(null);
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
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2);

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1427, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ArrayList<Horario> listaMandar = new ArrayList<>();
        boolean todoOk = true;
        int contador = 1;
        for (Component c : jPanel1.getComponents()) {
            if (c.getClass().getName().equals("javax.swing.JPanel") && c.getName() != null) {
                System.out.println(((JPanel) c).getName());
                JPanel panel = (JPanel) c;
                Horario h = new Horario();
                h.setIdRestaurante(idRestaurante);
                System.out.println("ID RESTAURANTE: " + idRestaurante);
                h.setDia(c.getName().replace("Panel", ""));
                String horaInicioM = "#PARAMHORA#:#PARAMMIN#";
                String horaFinM = "#PARAMHORA#:#PARAMMIN#";
                String horaInicioT = "#PARAMHORA#:#PARAMMIN#";
                String horaFinT = "#PARAMHORA#:#PARAMMIN#";
                for (Component child : panel.getComponents()) {
                    if (child.getClass().getName().equals("javax.swing.JPanel")) {
                        JPanel panelChild = (JPanel) child;
                        for (Component c2 : panelChild.getComponents()) {
                            if (c2.getClass().getName().equals("javax.swing.JCheckBox")) {
                                System.out.println("ENTRO");
                                h.setCerrado(((JCheckBox) c2).isSelected());
                                h.setOrden(contador);
                                ++contador;
                            }
                        }
                    } else if (child.getClass().getName().equals("javax.swing.JComboBox")) {
                        JComboBox comboBox = (JComboBox) child;
                        switch (comboBox.getName()) {
                            case "ComboBoxHoraInicioM":
                                horaInicioM = horaInicioM.replace("#PARAMHORA#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxMinutoInicioM":
                                horaInicioM = horaInicioM.replace("#PARAMMIN#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxHoraFinM":
                                horaFinM = horaFinM.replace("#PARAMHORA#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxMinutoFinM":
                                horaFinM = horaFinM.replace("#PARAMMIN#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxHoraInicioT":
                                horaInicioT = horaInicioT.replace("#PARAMHORA#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxMinutoInicioT":
                                horaInicioT = horaInicioT.replace("#PARAMMIN#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxHoraFinT":
                                horaFinT = horaFinT.replace("#PARAMHORA#", comboBox.getSelectedItem().toString());
                                break;
                            case "ComboBoxMinutoFinT":
                                horaFinT = horaFinT.replace("#PARAMMIN#", comboBox.getSelectedItem().toString());
                                break;
                            default:
                                throw new AssertionError();
                        }
                    } else {
                        System.out.println("ERROR: " + child.getClass().getName());
                    }
                    System.out.println(child.getClass().getName().equals("javax.swing.JCheckBox"));
                }
                if (LocalTime.parse(horaInicioM).isBefore(LocalTime.parse(horaFinM))
                        && LocalTime.parse(horaFinM).isBefore(LocalTime.parse(horaInicioT))
                        && LocalTime.parse(horaInicioT).isBefore(LocalTime.parse(horaFinT))
                        && !h.getCerrado()) {
                    h.setHora_inicio_m(horaInicioM);
                    h.setHora_fin_m(horaFinM);
                    h.setHora_inicio_t(horaInicioT);
                    h.setHora_fin_t(horaFinT);
                } else if (h.getCerrado()) {
                    h.setHora_inicio_m("00:00");
                    h.setHora_fin_m("00:00");
                    h.setHora_inicio_t("00:00");
                    h.setHora_fin_t("00:00");
                } else { 
                    if (todoOk){
                        JOptionPane.showMessageDialog(this, "Revise los horarios , hay solapación de horas", "Error",JOptionPane.ERROR_MESSAGE);
                    }
                    todoOk = false;
                }
                listaMandar.add(h);
            } else {
                System.out.println(c.getClass() + c.getName());
            }
        }
        if (todoOk) {
            String jsonArrayStr = "[";
            for (int i = 0; i < listaMandar.size(); i++) {
                jsonArrayStr += listaMandar.get(i).toJson();
                if ((i + 1) != listaMandar.size()) {
                    jsonArrayStr += ",";
                }
            }
            jsonArrayStr += "]";
            final String json = jsonArrayStr;
            if (!jsonArrayStr.contains("null")) {
                try {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // Conectamos a la pagina con el método que queramos
                            try {
                                URL url = new URL("https://reservante.mjhudesings.com/slim/addhorario");
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("POST");
                                connection.setDoOutput(true);
                                connection.setRequestProperty("Content-Type", "application/json");
                                connection.setRequestProperty("Accept", "application/json");
                                OutputStream os = connection.getOutputStream();
                                OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                                osw.write(json);
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
                                    System.out.println("JSON: " + json);
                                    System.out.println("Respuesta " + response);
                                }
                                connection.disconnect();
                            } catch (MalformedURLException e) {
                                throw new RuntimeException(e);
                            } catch (ProtocolException e) {
                                System.out.println(e.getMessage());
                                throw new RuntimeException(e);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            } catch (IOException ex) {
                                Logger.getLogger(PanelCalendario.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    };
                    Thread thread = new Thread(runnable);
                    thread.start();
                    thread.join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void clickCheckBox(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clickCheckBox
        // TODO add your handling code here:
        JPanel panel = (JPanel) ((JCheckBox) evt.getSource()).getParent().getParent();
        System.out.println(panel.getName());
        for (Component c : panel.getComponents()) {
            if (c.getClass().getName().equals("javax.swing.JComboBox")) {
                c.setEnabled(!((JCheckBox) evt.getSource()).isSelected());
            }
        }
    }//GEN-LAST:event_clickCheckBox

    private void cambiarCheckBox(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_cambiarCheckBox
        // TODO add your handling code here:
        JPanel panel = (JPanel) ((JCheckBox) evt.getSource()).getParent();
        for (Component c : panel.getComponents()) {
            if (c.getClass().getName().equals("javax.swing.JComboBox")) {
                c.setEnabled(!((JCheckBox) evt.getSource()).isSelected());
            }
        }
    }//GEN-LAST:event_cambiarCheckBox

    private void jComboBox62ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox62ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox62ActionPerformed

    private void jComboBox54ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox54ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox54ActionPerformed

    private void jComboBox46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox46ActionPerformed

    private void jComboBox38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox38ActionPerformed

    private void jComboBox30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox30ActionPerformed

    private void jComboBox22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox22ActionPerformed

    private void jCheckBox4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jCheckBox4StateChanged
        // TODO add your handling code here:
        JPanel panel = (JPanel) ((JCheckBox) evt.getSource()).getParent();
        for (Component c : panel.getComponents()) {
            if (c.getClass().getName().equals("javax.swing.JComboBox")) {
                c.setEnabled(!((JCheckBox) evt.getSource()).isSelected());
            }
        }
    }//GEN-LAST:event_jCheckBox4StateChanged

    private void jComboBox14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox14ActionPerformed

    private void cbPropertyCambio(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cbPropertyCambio
        // TODO add your handling code here:
        JPanel panel = (JPanel) ((JCheckBox) evt.getSource()).getParent();
        for (Component c : panel.getComponents()) {
            if (c.getClass().getName().equals("javax.swing.JComboBox")) {
                c.setEnabled(!((JCheckBox) evt.getSource()).isSelected());
            }
        }
    }//GEN-LAST:event_cbPropertyCambio

    public JSONArray cargarHorarioAPI() {
        String[] responseStr = new String[1];
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
                    jsonFecha = jsonFecha.replace("#PARAMID#", String.valueOf(idRestaurante));
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
            System.out.println("Respuesta horario: " + responseStr[0]);
            JSONArray jsonArray = new JSONObject(responseStr[0]).getJSONArray("horarios");
            return jsonArray;
        } catch (JSONException e) {
            System.out.println("Error al leer horarios");
            return null;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox10;
    private javax.swing.JCheckBox jCheckBox11;
    private javax.swing.JCheckBox jCheckBox12;
    private javax.swing.JCheckBox jCheckBox13;
    private javax.swing.JCheckBox jCheckBox14;
    private javax.swing.JCheckBox jCheckBox15;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox<String> jComboBox13;
    private javax.swing.JComboBox<String> jComboBox14;
    private javax.swing.JComboBox<String> jComboBox15;
    private javax.swing.JComboBox<String> jComboBox16;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JComboBox<String> jComboBox21;
    private javax.swing.JComboBox<String> jComboBox22;
    private javax.swing.JComboBox<String> jComboBox23;
    private javax.swing.JComboBox<String> jComboBox24;
    private javax.swing.JComboBox<String> jComboBox25;
    private javax.swing.JComboBox<String> jComboBox26;
    private javax.swing.JComboBox<String> jComboBox27;
    private javax.swing.JComboBox<String> jComboBox28;
    private javax.swing.JComboBox<String> jComboBox29;
    private javax.swing.JComboBox<String> jComboBox30;
    private javax.swing.JComboBox<String> jComboBox31;
    private javax.swing.JComboBox<String> jComboBox32;
    private javax.swing.JComboBox<String> jComboBox33;
    private javax.swing.JComboBox<String> jComboBox34;
    private javax.swing.JComboBox<String> jComboBox35;
    private javax.swing.JComboBox<String> jComboBox36;
    private javax.swing.JComboBox<String> jComboBox37;
    private javax.swing.JComboBox<String> jComboBox38;
    private javax.swing.JComboBox<String> jComboBox39;
    private javax.swing.JComboBox<String> jComboBox40;
    private javax.swing.JComboBox<String> jComboBox41;
    private javax.swing.JComboBox<String> jComboBox42;
    private javax.swing.JComboBox<String> jComboBox43;
    private javax.swing.JComboBox<String> jComboBox44;
    private javax.swing.JComboBox<String> jComboBox45;
    private javax.swing.JComboBox<String> jComboBox46;
    private javax.swing.JComboBox<String> jComboBox47;
    private javax.swing.JComboBox<String> jComboBox48;
    private javax.swing.JComboBox<String> jComboBox49;
    private javax.swing.JComboBox<String> jComboBox50;
    private javax.swing.JComboBox<String> jComboBox51;
    private javax.swing.JComboBox<String> jComboBox52;
    private javax.swing.JComboBox<String> jComboBox53;
    private javax.swing.JComboBox<String> jComboBox54;
    private javax.swing.JComboBox<String> jComboBox55;
    private javax.swing.JComboBox<String> jComboBox56;
    private javax.swing.JComboBox<String> jComboBox57;
    private javax.swing.JComboBox<String> jComboBox58;
    private javax.swing.JComboBox<String> jComboBox59;
    private javax.swing.JComboBox<String> jComboBox60;
    private javax.swing.JComboBox<String> jComboBox61;
    private javax.swing.JComboBox<String> jComboBox62;
    private javax.swing.JComboBox<String> jComboBox63;
    private javax.swing.JComboBox<String> jComboBox64;
    private javax.swing.JComboBox<String> jComboBox65;
    private javax.swing.JComboBox<String> jComboBox66;
    private javax.swing.JComboBox<String> jComboBox67;
    private javax.swing.JComboBox<String> jComboBox68;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlDia1;
    private javax.swing.JLabel jlDia2;
    private javax.swing.JLabel jlDia4;
    // End of variables declaration//GEN-END:variables
}
