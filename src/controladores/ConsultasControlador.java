/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.NominaDao;
import formularios.VistaConsultas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import reporte.Reporte;

/**
 *
 * @author Alex
 */
public class ConsultasControlador {
    
    //Variables
    private ControladorGeneral ctrl;
    private VistaConsultas vista;
    private NominaDao dao;
    private DefaultTableModel modelo;
    private List<Object[]> lista;
    private int index;

    public ConsultasControlador(VistaConsultas vista) {
        this.vista = vista;
        this.inicializar();
        //this.aplicarFiltrado();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new NominaDao();
        
        //Place holder
        ctrl.placeholder(new String[]{"Ingrese la matricula"}, vista.txtBusqueda);
        
        //Deshabilitamos algunos elementos visuales
        vista.btnEliminar.setEnabled(false);
        vista.btnPdf.setEnabled(false);
        
        //Definimos el modelo para la tabla
        modelo = ctrl.definirModeloTabla(new String[]{"Matricula", "Nombre", "Apellidos", "Incentivo", "Descuento", "Total"});
        vista.tabla.setModel(modelo);
        
        //boton buscar (cambiar color hover)
        vista.btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vista.btnBuscar.setBackground(new Color(223,223,223));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vista.btnBuscar.setBackground(new Color(0,0,0));
            }
        });
        
        //boton salir
        vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.setVisible(false);
            }
        });
        
        //boton limpiar
        vista.btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrl.limpiarTxt(vista.txtBusqueda);
                modelo.setRowCount(0);
                vista.btnEliminar.setEnabled(false);
                vista.btnPdf.setEnabled(false);
            }
        });
        
        //boton buscar
        vista.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.btnEliminar.setEnabled(false);
                funcionBuscar();
            }
        });
        
        //boton eliminar
        vista.btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.tabla.getSelectedRow();
                if((fila)>=0){
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog(null, "¿Eliminar este elemento?", "Aceptar", dialogButton);
                    
                    if(dialogResult==0){
                        funcionEliminar(fila);
                        funcionImprimirLista();
                    }
                }
            }
        });
        
        //Tabla
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vista.btnEliminar.setEnabled(true);
                vista.btnPdf.setEnabled(true);
            }
        });
        
        //PDF
        vista.btnPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int fila = vista.tabla.getSelectedRow();
                
                if((fila)>=0){
                    try {
                        imprimirPDF(fila);
                        JOptionPane.showMessageDialog(null, "Exportado correctamente");
                    } catch (SQLException | JRException ex) {
                        Logger.getLogger(ConsultasControlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
 
    }
    private void funcionImprimirLista(){
        modelo.setRowCount(0);
        for (int i = 0; i < lista.size(); i++) {
            Object[] ob = lista.get(i);
            modelo.addRow(ob);
        }
    }
    
    private void imprimirPDF(int fila) throws SQLException, JRException{
        Reporte rep = new Reporte();
        String matricula = modelo.getValueAt(fila, 0).toString();
        rep.consulta(matricula);
    }
    
      
    private void funcionBuscar(){
        //Si no hay ninguna notificacion visual en ejecucion se procede
        if(!ctrl.notificacionCorriendo){
            String matricula = vista.txtBusqueda.getText();

            //Si el codigo esta vacio no se procede la busqueda
            if(matricula.isEmpty()){
                ctrl.notificacion(vista.lblNotificacion, 3000, "Numero no puede estar vacio", new Color(107,8,8));
                return;
            }
            lista = dao.buscar(matricula);
            //Si la tabla es vacia
            vista.btnLimpiar.doClick();
            if(lista.isEmpty()){
                ctrl.notificacion(vista.lblNotificacion, 3000, "No se encontraron coincidencias", new Color(107,8,8));
            }else{
                //Si no esta vacia
                try{
                    funcionImprimirLista();
                    ctrl.notificacion(vista.lblNotificacion, 3000, "Se encontraron coincidencias", new Color(35,107,43));
                }catch(Exception e){
                    ctrl.notificacion(vista.lblNotificacion, 3000, "Ocurrio un error: "+e.getMessage(), new Color(107,8,8));
                    e.printStackTrace();
                }
            }
        }
    }

    private void funcionEliminar(int fila){
        if(!ctrl.notificacionCorriendo){
            try{
                dao.eliminar(modelo.getValueAt(fila, 0).toString());
                lista.remove(fila);
                vista.btnEliminar.setEnabled(false);
                vista.btnPdf.setEnabled(false);
                ctrl.notificacion(vista.lblNotificacion, 3000, "Exito al eliminar", new Color(35,107,43));                
            }catch(Exception e){
                ctrl.notificacion(vista.lblNotificacion, 3000, "Error al eliminar: "+e.getMessage(), new Color(107,8,8));
                e.printStackTrace();
            }
        }
    }
    
}
