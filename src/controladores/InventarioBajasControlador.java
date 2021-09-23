/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.InventarioDao;
import dao.NominaDao;
import formularios.VistaInventarioBajas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import librerias.FiltraEntrada;
import modelos.Articulo;

/**
 *
 * @author Alex
 */
public class InventarioBajasControlador {
    
    //Variables
    private ControladorGeneral ctrl;
    private VistaInventarioBajas vista;
    private InventarioDao dao;
    private DefaultTableModel modelo;
    private List<Articulo> lista;

    public InventarioBajasControlador(VistaInventarioBajas vista) {
        this.vista = vista;
        this.inicializar();
        this.aplicarFiltrado();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new InventarioDao();
        vista.btnEliminar.setEnabled(false);
        
        //Situamos los placeholder en los campos de texto
        ctrl.placeholder(new String[]{"Codigo","Nombre del articulo"}, 
                vista.txtCodigo,vista.txtArticulo);
        
        //Definimos el modelo para la tabla
        modelo = ctrl.definirModeloTabla(new String[]{"Codigo", "Articulo", "Existencia", "Sucursal", "Marca"});
        vista.tabla.setModel(modelo);
        
        //boton buscar (cambiar color hover)
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
                ctrl.limpiarTxt(vista.txtCodigo, vista.txtArticulo);
                modelo.setRowCount(0);
                vista.btnEliminar.setEnabled(false);
            }
        });
        
        //boton buscar
        vista.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if((vista.tabla.getSelectedRow())>=0){
                    vista.btnEliminar.setEnabled(true);
                }
                    
            }
        });
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
            String codigo = vista.txtCodigo.getText();
            String articulo = vista.txtArticulo.getText();
            
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
    
    private void aplicarFiltrado(){
        Document e1 = vista.txtCodigo.getDocument();
        DocumentFilter filtro1 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e1).setDocumentFilter(filtro1);
        
    }
    
    private void funcionEliminar(int fila){
        if(ctrl.notificacionCorriendo==false){
            try{
                Articulo temp = lista.get(fila);
                dao.eliminaraArticulo(temp);
                lista.remove(fila);
                vista.btnEliminar.setEnabled(false);
                ctrl.notificacion(vista.lblNotificacion, 3000, "Exito al eliminar", new Color(35,107,43));                
            }catch(Exception e){
                ctrl.notificacion(vista.lblNotificacion, 3000, "Error al eliminar: "+e.getMessage(), new Color(107,8,8));
                e.printStackTrace();
            }
        }
    }
    
}
