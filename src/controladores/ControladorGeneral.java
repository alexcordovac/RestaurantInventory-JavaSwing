/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import librerias.TextPrompt;

/**
 *
 * @author Alex
 */
public class ControladorGeneral {
    
    public boolean notificacionCorriendo;
    
    public ControladorGeneral() {
        notificacionCorriendo = false;
    }
    
    public void limpiarTxt(JTextField...txt){
        for (int i = 0; i < txt.length; i++) {
            txt[i].setText("");
        }
        txt[0].requestFocus();
    }
    
    public void placeholder(String[] st, JTextField...txt){
        for (int i = 0; i < txt.length; i++) {
            TextPrompt placeholder = new TextPrompt(st[i],txt[i]);
        }
    }
    public DefaultTableModel definirModeloTabla(String[] encabezado){
        DefaultTableModel modelo = new DefaultTableModel(null, encabezado){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        return modelo;
    }
    
    public void inhabilitarTxt(JTextField...t){
        for (JTextField e: t) {
            e.setEnabled(false);
        }
    }
    
    public void habilitarTxt(JTextField...t){
        for (JTextField e: t) {
            e.setEnabled(true);
        }
    }
    //Función para ocultar un JLabel, generalmente usada como un logger
    public void notificacion(JLabel label, int tiempo, String mensaje, Color co){
        notificacionCorriendo = true;
        label.setForeground(co);
        label.setText(mensaje);
        
        ActionListener tarea = (ActionEvent evt) -> {
            label.setText("");
            notificacionCorriendo = false;
        };
        
        Timer timer = new Timer(tiempo, tarea);
        timer.setRepeats(false);
        timer.start();
    }
    
    public boolean validarFecha(String fecha){
	/* Check if date is 'null' */
	if (fecha.trim().equals(""))
	{
	    return false;
	}else{
	    /*
	     * Set preferred date format,
	     * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    formato.setLenient(false);
	    /* Create Date object
	     * parse the string into date 
             */
	    try{
                Date javaDate = formato.parse(fecha); 
	        System.out.println(fecha+" is valid date format");
	    }catch (ParseException e){
	        System.out.println(fecha+" is Invalid Date format");
	        return false;
	    }
	    return true;
	}
   }
}
