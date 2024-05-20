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
public class VacacionesRender extends JButton implements ListCellRenderer<Vacaciones> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Vacaciones> list, Vacaciones value, int index, boolean isSelected, boolean cellHasFocus) {
        /*String base = "<html>\n"
                + "    <head>\n"
                + "        <style>\n"
                + "            h1.nombre {\n"
                + "                width: 33%;\n"
                + "                text-align: center;\n"
                + "                float: left;\n"
                + "            }\n"
                + "            h1.inicio {\n"
                + "                width: 33%;\n"
                + "                text-align: center;\n"
                + "                float:left\n"
                + "            }\n"
                + "            h1.fin {\n"
                + "                width: 33%;\n"
                + "                text-align: center;\n"
                + "                float:left\n"
                + "            }\n"
                + "        </style>\n"
                + "    </head>\n"
                + "    <body> \n"
                + "        <h1 class=\"nombre\"><font color='#812B81'>#PARAMNOMBRE#</font></h1>\n"
                + "        <h1 class=\"inicio\">Inicio:#PARAMINICIO#</h1>\n"
                + "        <h1 class=\"fin\">Fin:#PARAMFIN#</h1>\n"
                + "    </body>\n"
                + "</html>";
         */
        String base = "<html>\n"
                + "    <body>\n"
                + "        <h1><font color='#812B81' >#PARAMNOMBRE#</font> Inicio:#PARAMINICIO# Fin:#PARAMFIN#</h1>\n"
                + "    </body>\n"
                + "</html>";
       /* String base = "<html>\n"
                + "    <head>\n"
                + "    </head>\n"
                + "    <body> \n"
                + "        <h1 style=\"width: 33%; float: right; text-align: center;\"><font color='#812B81'>#PARAMNOMBRE#</font></h1>\n"
                + "        <h1 style=\"width: 33%; float: right; text-align: center;\">Inicio:#PARAMINICIO#</h1>\n"
                + "        <h1 style=\"width: 33%; float: right;text-align: center;\">Fin:#PARAMFIN#</h1>\n"
                + "    </body>\n"
                + "</html>";*/
        base = base.replace("#PARAMNOMBRE#", value.getNombre());
        base = base.replace("#PARAMINICIO#", value.getFin().toString());
        base = base.replace("#PARAMFIN#", value.getInicio().toString());
        this.setText(base);
        this.setFont(new Font("Segoe", Font.BOLD, 24));

        return this;
    }

}
