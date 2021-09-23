/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.InventarioDao;
import dao.NominaDao;
import formularios.VistaInventarioExistencias;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;

/**
 *
 * @author Alex
 */
public class InventarioExistenciasControlador {
    
    //Variables
    private VistaInventarioExistencias vista;
    private ControladorGeneral ctrl;
    private InventarioDao dao;
    private DefaultTableModel modelo;
    
    //Constructor
    public InventarioExistenciasControlador(VistaInventarioExistencias vista){
        this.vista = vista;
        this.inicializar();
        this.listarArticulos();
    }
    
    private void inicializar(){
        dao = new InventarioDao();
        ctrl = new ControladorGeneral();
        
        //Definimos un modelo para la tabla
        modelo = ctrl.definirModeloTabla(new String[]{"Codigo","Articulo","Sucursal","Existencias","Marca"});
        vista.jTable1.setModel(modelo);
        
        //Boton salir
        vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.setVisible(false);
            }
        });
    }
    
    public void listarArticulos(){
        try{
            List<Articulo> lista = dao.obtenerArticulos();
            modelo.setRowCount(0);

            for (int i = 0; i < lista.size(); i++) {
                Object[] object = {lista.get(i).getCodigo(),lista.get(i).getArticulo(),lista.get(i).getSucursal()
                        ,lista.get(i).getExistencia(),lista.get(i).getMarca()};
                modelo.addRow(object);
            }
            vista.lblNotificacion.setForeground(Color.BLUE);
            vista.lblNotificacion.setText("Mostrado correctamente");
        }catch(Exception e){
            vista.lblNotificacion.setForeground(Color.red);
            vista.lblNotificacion.setText("Mostrado con errores: "+e.getMessage());
            e.printStackTrace();
        }
    }

}
