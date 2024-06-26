    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jabaubo.proyecto_reservas.Clases;

import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.plaf.ColorUIResource;
public class CeldaCalendario extends JButton{
    private Date date;
    private boolean title;
   
    
    public CeldaCalendario(){
        //Diseño del componente
        setOpaque(true);
        setContentAreaFilled(true);
        setBorder(null);
        setHorizontalAlignment(JLabel.CENTER);
        Font f = new Font("Segoe", Font.PLAIN, 20);
        this.setFont(f);
    }

    public boolean isTitle() {
        return title;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public String fechaFormateada() {
        //Formatear la fecha asignada
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        String fecha = c.get(Calendar.YEAR)+"-";
        if (c.get(Calendar.MONTH)+1<10) {
            fecha+=0;
        }
        int valor = c.get(Calendar.MONTH)+1;
        fecha+=valor+"-";
        if (c.get(Calendar.DAY_OF_MONTH)<10) {
            fecha+=0;
        }
        fecha+=c.get(Calendar.DAY_OF_MONTH);
        return fecha;
    }
    
    public void currentMonth(boolean act) {
        //Ver si es un dia del mes seleccionado en el calendario
        if (act) {
            setForeground(Color.black);
        }
        else{
            setForeground(Color.lightGray);
        }
    }

    
}
