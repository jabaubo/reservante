/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Interfaces;

import com.jabaubo.proyecto_reservas.Interfaces.dialogs.AdvertenciaDialog;
import com.jabaubo.proyecto_reservas.Interfaces.dialogs.Cargando;
import com.jabaubo.proyecto_reservas.Interfaces.dialogs.Login;
import com.jabaubo.proyecto_reservas.Interfaces.paneles.PanelInicio;
import com.jabaubo.proyecto_reservas.Interfaces.paneles.PanelReservas;
import com.jabaubo.proyecto_reservas.Interfaces.paneles.PanelConfiguracion;
import com.jabaubo.proyecto_reservas.Interfaces.paneles.PanelHorario;
import com.jabaubo.proyecto_reservas.Interfaces.paneles.PanelCalendario;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author pokem
 */
public class InterfazPrincipal extends javax.swing.JFrame {

    private int restaurante = 0;
    PanelConfiguracion panelConfiguracion;
    PanelCalendario panelCalendario;
    PanelInicio panelInicio;
    PanelReservas panelReservas;
    PanelHorario panelHorario;
    ArrayList<JButton> botones;
    ArrayList<JPanel> paneles;
    ArrayList<AdvertenciaDialog> avisos;
    JButton selectedButton;

    /**
     * Creates new form UI
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jbAgenda = new javax.swing.JButton();
        jbConfiguracion = new javax.swing.JButton();
        jbInicio = new javax.swing.JButton();
        jbReservas = new javax.swing.JButton();
        jbHorario = new javax.swing.JButton();
        jpVista = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reservante");
        setBackground(java.awt.Color.darkGray);

        jPanel2.setBackground(new java.awt.Color(109, 34, 109));

        jbAgenda.setBackground(new java.awt.Color(109, 34, 109));
        jbAgenda.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbAgenda.setText("Reservar");
        jbAgenda.setActionCommand("agenda");
        jbAgenda.setBorder(null);
        jbAgenda.setBorderPainted(false);
        jbAgenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbAgenda.setMargin(new java.awt.Insets(14, 14, 3, 14));
        jbAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDePanel(evt);
            }
        });

        jbConfiguracion.setBackground(new java.awt.Color(109, 34, 109));
        jbConfiguracion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbConfiguracion.setText("Configuracion");
        jbConfiguracion.setActionCommand("configuracion");
        jbConfiguracion.setBorder(null);
        jbConfiguracion.setBorderPainted(false);
        jbConfiguracion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbConfiguracion.setMargin(new java.awt.Insets(14, 14, 3, 14));
        jbConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDePanel(evt);
            }
        });

        jbInicio.setBackground(new java.awt.Color(109, 34, 109));
        jbInicio.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbInicio.setText("Inicio");
        jbInicio.setActionCommand("inicio");
        jbInicio.setBorder(null);
        jbInicio.setBorderPainted(false);
        jbInicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbInicio.setMargin(new java.awt.Insets(14, 14, 3, 14));
        jbInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDePanel(evt);
            }
        });

        jbReservas.setBackground(new java.awt.Color(109, 34, 109));
        jbReservas.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbReservas.setText("Lista de reservas");
        jbReservas.setActionCommand("reservas");
        jbReservas.setBorder(null);
        jbReservas.setBorderPainted(false);
        jbReservas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbReservas.setMargin(new java.awt.Insets(14, 14, 3, 14));
        jbReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDePanel(evt);
            }
        });

        jbHorario.setBackground(new java.awt.Color(109, 34, 109));
        jbHorario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbHorario.setText("Horario");
        jbHorario.setActionCommand("horario");
        jbHorario.setBorder(null);
        jbHorario.setBorderPainted(false);
        jbHorario.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jbHorario.setMargin(new java.awt.Insets(14, 14, 3, 14));
        jbHorario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDePanel(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jbInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(jbHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbAgenda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbConfiguracion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jbReservas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jbAgenda, jbConfiguracion, jbInicio, jbReservas});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jbInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jbAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jbConfiguracion, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jbReservas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jbHorario, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(241, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jbAgenda, jbConfiguracion, jbInicio, jbReservas});

        jpVista.setBackground(new java.awt.Color(255, 204, 204));
        jpVista.setLayout(new java.awt.BorderLayout());

        jSeparator1.setBackground(java.awt.Color.darkGray);
        jSeparator1.setOpaque(true);
        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 6));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jpVista, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpVista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public InterfazPrincipal() {
        Login login = new Login(this, true);
        login.setVisible(true);
        //Mostramos el login , si inicia sesión ya mostramos la pantalla
        if (login.isLogin()) {
            initComponents();
            restaurante = login.getRestaurante();
            login.dispose();
            System.out.println(login.isActive());
            Cargando cargando = new Cargando(this, true);
        } else {
            System.exit(2);
        }
    }

    public void cargar(Cargando cargando) {
        //Vamos modificando la barra de carga para hacer referencia a los distintos estados de carga de la app
        cargando.updateEtiqueta("Cargando configuración");
        cargando.updateBarra(15);
        panelConfiguracion = new PanelConfiguracion(this, restaurante);
        cargando.updateEtiqueta("Cargando calendario");
        cargando.updateBarra(30);
        panelCalendario = new PanelCalendario(this, restaurante);
        cargando.updateEtiqueta("Cargando Inicio");
        cargando.updateBarra(45);
        panelInicio = new PanelInicio(this);
        cargando.updateEtiqueta("Cargando reservas");
        cargando.updateBarra(60);
        panelReservas = new PanelReservas(this);
        cargando.updateEtiqueta("Cargando horario");
        cargando.updateBarra(75);
        panelHorario = new PanelHorario(this.restaurante);
        cargando.updateEtiqueta("Terminando preparación");
        cargando.updateBarra(90);
        //Almacenamos los botones , paneles y avisos en arrays , manteniendo el orden
        botones = new ArrayList<>();
        paneles = new ArrayList<>();
        avisos = new ArrayList<>();
        jbInicio.setSelected(true);
        botones.add(jbInicio);
        botones.add(jbAgenda);
        botones.add(jbConfiguracion);
        botones.add(jbReservas);
        botones.add(jbHorario);

        paneles.add(panelInicio);
        paneles.add(panelCalendario);
        paneles.add(panelConfiguracion);
        paneles.add(panelReservas);
        paneles.add(panelHorario);

        avisos.add(new AdvertenciaDialog(this, true, "Ocupación de los próximos días"));
        avisos.add(new AdvertenciaDialog(this, true, "Seleccione fecha y hora"));
        avisos.add(new AdvertenciaDialog(this, true, "Defina los datos de su restaurante"));
        avisos.add(new AdvertenciaDialog(this, true, "Listado completo de las reservas"));
        avisos.add(new AdvertenciaDialog(this, true, "Defina su horario"));

        selected(jbInicio);
        cargando.dispose();
    }

    private void cambioDePanel(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambioDePanel
        // TODO add your handling code here:
        selected((JButton) evt.getSource());
    }//GEN-LAST:event_cambioDePanel

    public void selected(JButton jb) {
        selectedButton = jb;
        for (int i = 0; i < botones.size(); i++) {
            JButton boton = botones.get(i);
            //Si el botón es el seleccionado le aplicamos el estilo de seleccionado y mostramos su vista
            if (boton == jb) {
                boton.setForeground(new Color(109, 34, 109));
                boton.setBackground(new Color(221, 221, 221));
                boton.setIcon(new ImageIcon(String.format("C:\\Users\\pokem\\Documents\\Proyectos\\reservante\\proyecto_reservas\\img\\%s_seleccionado.png", boton.getActionCommand())));
                jpVista.removeAll();
                JPanel panel = paneles.get(i);
                for (Component c : jpVista.getComponents()) {
                    c.setVisible(false);
                }
                //Depende de cual sea el panel cargamso los datos necesarios
                switch (i) {
                    case 0:
                        ((PanelInicio)panel).cargarDatos();
                        break;
                    case 1:
                        ((PanelCalendario)panel).cargarDatos();
                        break;
                    case 2:
                        ((PanelConfiguracion)panel).cargarDatos();
                        break;
                    case 3:
                        ((PanelReservas)panel).cargarDatos();
                        break;
                    case 4:
                        ((PanelHorario)panel).cargarDatos();
                        break;
                }
                panel.setVisible(true);
                jpVista.add(panel);
                jpVista.validate();
                jpVista.repaint();
                AdvertenciaDialog av = avisos.get(i);
                if (av.isMostrar()) {
                    av.setVisible(true);
                }
            } else {
                //A los no seleccionados les damos estilo normal
                boton.setBackground(new Color(109, 34, 109));
                boton.setForeground(new Color(221, 221, 221));
                boton.setIcon(new ImageIcon(String.format("C:\\Users\\pokem\\Documents\\Proyectos\\reservante\\proyecto_reservas\\img\\%s.png", boton.getActionCommand())));
            }
        }
    }

    public int getRestaurante() {
        return restaurante;
    }
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
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InterfazPrincipal ui = new InterfazPrincipal();
                ui.setExtendedState(JFrame.MAXIMIZED_BOTH);
                ui.setVisible(true);
            }
        });
    }

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton jbAgenda;
    private javax.swing.JButton jbConfiguracion;
    private javax.swing.JButton jbHorario;
    private javax.swing.JButton jbInicio;
    private javax.swing.JButton jbReservas;
    private javax.swing.JPanel jpVista;
    // End of variables declaration//GEN-END:variables
}
