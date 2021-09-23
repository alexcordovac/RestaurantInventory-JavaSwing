/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.InventarioDao;
import dao.NominaDao;
import formularios.VistaInventarioModificar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableModel;
import modelos.Articulo;

/**
 *
 * @author Alex
 */
public class InventarioModificarControlador {
    
    private ControladorGeneral ctrl;
    private VistaInventarioModificar vista;
    private DefaultTableModel modelo;
    private InventarioDao dao;
    private Articulo temp;
    private List<Articulo> lista;
    private int index;
    

    public InventarioModificarControlador(VistaInventarioModificar vista) {
        this.vista = vista;
        this.inicializar();
        this.eventos();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new InventarioDao();
        temp = null;
        index =-1;
        
        //Configuramos los placeholder
        ctrl.placeholder(new String[]{"Codigo", "Articulo","Codigo","Articulo","Sucursal","Existencia","Marca"}, 
                vista.txtBCodigo,vista.txtBArticulo, vista.txtCodigo,vista.txtArticulo,vista.txtSucursal,vista.txtExistencia,vista.txtMarca);
        
        //Definimos un modelo para la tabla
        modelo = ctrl.definirModeloTabla(new String[]{"Codigo","Articulo","Sucursal","Existencia","Marca"});
        vista.tabla.setModel(modelo);
        
        //Inhabilitamos componentes
        ctrl.inhabilitarTxt(vista.txtCodigo,vista.txtArticulo,vista.txtSucursal,vista.txtExistencia,vista.txtMarca);
        vista.btnGuardar.setEnabled(false);
    }
    
    private void eventos(){
        //boton buscar
        vista.btnBuscar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vista.btnBuscar.setBackground(new Color(12, 51,51));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vista.btnBuscar.setBackground(new Color(23,35,51));
            }
        });
        
        vista.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionBuscar();
            }
        });
        
        //boton limpiar
        vista.btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrl.limpiarTxt(vista.txtBCodigo,vista.txtBArticulo,vista.txtCodigo,vista.txtArticulo,vista.txtSucursal,vista.txtExistencia,vista.txtMarca);
                modelo.setRowCount(0);
                vista.btnGuardar.setEnabled(false);
                ctrl.inhabilitarTxt(vista.txtCodigo,vista.txtArticulo,vista.txtSucursal,vista.txtExistencia,vista.txtMarca);
                temp = null;
                index= -1;
            }
        });
        
        vista.btnLimpiar.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                vista.btnLimpiar.setBackground(new Color(12, 51,51));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                vista.btnLimpiar.setBackground(new Color(23,35,51));
            }
        });
        
        //boton salir
        vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.setVisible(false);
                
            }
        });
        
        //Tabla
        vista.tabla.addMouseListener(new MouseAdapter() {
           @Override
           public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarDatosArticulo();
                ctrl.habilitarTxt(vista.txtCodigo,vista.txtArticulo,vista.txtSucursal,vista.txtExistencia,vista.txtMarca);
            } 
        });
        
        //boton guardar
        vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                funcionGuardar();
            }
        });
        
    }
    private void llenarDatosArticulo(){
        index = vista.tabla.getSelectedRow();
        
        if (index>=0){
            temp = lista.get(index);
            vista.txtCodigo.setText(Integer.toString(temp.getCodigo()));
            vista.txtArticulo.setText(temp.getArticulo());
            vista.txtExistencia.setText(Integer.toString(temp.getExistencia()));
            vista.txtSucursal.setText(temp.getSucursal());
            vista.txtMarca.setText(temp.getMarca());
            vista.btnGuardar.setEnabled(true);
        }
    }
    
    private void funcionGuardar(){
        try{
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "¿Modificar este elemento?", "Aceptar", dialogButton);

            if(dialogResult==0){
                temp.setCodigo(Integer.parseInt(vista.txtCodigo.getText()));
                temp.setArticulo(vista.txtArticulo.getText());
                temp.setExistencia(Integer.parseInt(vista.txtExistencia.getText()));
                temp.setSucursal(vista.txtSucursal.getText());
                temp.setMarca(vista.txtMarca.getText());
                dao.actualizar(temp);
                lista.set(index, temp);
                ctrl.notificacion(vista.lblNotificacion, 3000, "Datos actualizados", new Color(35,107,43));
                vista.btnLimpiar.doClick();
                funcionImprimirLista();
                index = -1;
                temp = null;
            }else{
                ctrl.notificacion(vista.lblNotificacion, 3000, "Cancelado",  new Color(107,8,8));
            }
        }catch(Exception e){
            ctrl.notificacion(vista.lblNotificacion, 3000, "Ocurrio un error: "+e.getMessage(), new Color(107,8,8));
            e.printStackTrace();
        }
    }
    
    private void funcionImprimirLista(){
        modelo.setRowCount(0);
        for (int i = 0; i < lista.size(); i++) {
            Object[] object = {lista.get(i).getCodigo(),lista.get(i).getArticulo(),lista.get(i).getSucursal()
                    ,lista.get(i).getExistencia(),lista.get(i).getMarca()};
            modelo.addRow(object);
        }
    }
    
    private void funcionBuscar(){
        //Si no hay ninguna notificacion visual en ejecucion se procede
        if(ctrl.notificacionCorriendo==false){
            String codigo = vista.txtBCodigo.getText();
            String articulo = vista.txtBArticulo.getText();
            
            //Si el codigo esta vacio no se procede la busqueda
            if(codigo.isEmpty()){
                ctrl.notificacion(vista.lblNotificacion, 3000, "Codigo no puede estar vacio", new Color(107,8,8));
                return;
            }
            
            lista = dao.buscarArticulo(Integer.parseInt(codigo), articulo);
            //Si no esta vacia se lista en la tabla
            if(lista.isEmpty()){
                ctrl.notificacion(vista.lblNotificacion, 3000, "No se encontraron coincidencias", new Color(107,8,8));
            }else{
                //modelo.setRowCount(0);
                try{
                  //  modelo.setRowCount(0);
                    funcionImprimirLista();
                    ctrl.notificacion(vista.lblNotificacion, 3000, "Se encontraron coincidencias", new Color(35,107,43));
                }catch(Exception e){
                    ctrl.notificacion(vista.lblNotificacion, 3000, "Ocurrio un error: "+e.getMessage(), new Color(107,8,8));
                    e.printStackTrace();
                }
            }
        }
    }
}
