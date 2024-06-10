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
public class OcupacionRender extends JButton implements ListCellRenderer<Ocupacion> {

    @Override
    public Component getListCellRendererComponent(JList<? extends Ocupacion> list, Ocupacion value, int index, boolean isSelected, boolean cellHasFocus) {
        //Formato texto del boton
       String base = "<html>\n" +
"    <head>\n" +
"        <style>\n" +
"        body {\n" +
"          background-color: linen;\n" +
"        }        \n" +
"        h1 {\n" +
"          color: #812B81;\n" +
"        }\n" +
"        p {\n" +
"            color: darkgrey;\n" +
"        }\n" +
"        </style>\n" +
"    </head>\n" +
"    <body>\n" +
"        <h1>#PARAMHORA# RESERVAS:#PARAMRESERVAS#</h1>\n" +
"        <p>#OCUPACION#</p>\n" +
"    </body>\n" +
"</html>";
        base = base.replace("#PARAMHORA#", value.getHora().toString()).
                replace("#PARAMRESERVAS#", String.valueOf(value.getnReservas())).
                replace("#OCUPACION#", value.getOcupacion());
        this.setText(base);
        this.setFont(new Font("Segoe", Font.BOLD, 24));

        return this;
    }

}
