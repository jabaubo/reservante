/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Clases;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.text.Style;

/**
 *
 * @author pokem
 */
public class SalonRender extends JButton implements ListCellRenderer<Salon> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Salon> list, Salon value, int index, boolean isSelected, boolean cellHasFocus) {
        String base = "<html>\n"
                + "    <body>\n"
                + "        <h1><font color='#812B81'>#PARAMNOMBRE#</font> Aforo:#PARAMAFORO# </h1>\n"
                + "    </body>\n"
                + "</html>";
        base = base.replace("#PARAMNOMBRE#",value.getNombre());
        base = base.replace("#PARAMAFORO#",String.valueOf(value.getAforo()));
        this.setText(base);
        this.setFont(new Font("Segoe", Font.BOLD, 24));

        return this;
    }

}
