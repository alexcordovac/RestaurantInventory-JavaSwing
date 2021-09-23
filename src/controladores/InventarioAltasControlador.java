/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.InventarioDao;
import dao.NominaDao;
import formularios.VistaInventarioAltas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import librerias.FiltraEntrada;
import modelos.Articulo;

/**
 *
 * @author Alex
 */
public class InventarioAltasControlador {
    
    //Variables
    private ControladorGeneral ctrl;
    private InventarioDao dao;
    private VistaInventarioAltas vista;

    public InventarioAltasControlador(VistaInventarioAltas vista) {
       
        this.vista = vista;
        this.inicializar();
        this.aplicarFiltrado();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new InventarioDao();
        
        //Ubicamos los placeholder en los campos de texto
        ctrl.placeholder(new String[]{"Codigo","Articulo","Sucursal","Existencia","Marca"}, 
                vista.txtCodigo,vista.txtArticulo,vista.txtSucursal,vista.txtExistencia,vista.txtMarca);
        
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
                ctrl.limpiarTxt(vista.txtCodigo, vista.txtArticulo, vista.txtSucursal, vista.txtExistencia, vista.txtMarca);
            }
        });
        
        //boton guardar
        vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarArticulo();
                
            }
        });
        
    }
    
    public void guardarArticulo(){
        
        if(!vista.txtArticulo.getText().isEmpty() & !vista.txtCodigo.getText().isEmpty() & !vista.txtExistencia.getText().isEmpty()
                & !vista.txtMarca.getText().isEmpty() & !vista.txtSucursal.getText().isEmpty()){
            try{
                Articulo temporal = new Articulo();
                temporal.setCodigo(Integer.parseInt(vista.txtCodigo.getText()));
                temporal.setArticulo(vista.txtArticulo.getText());
                temporal.setExistencia(Integer.parseInt(vista.txtExistencia.getText()));
                temporal.setSucursal(vista.txtSucursal.getText());
                temporal.setMarca(vista.txtMarca.getText());
                System.out.println(temporal.toString());
                dao.registrar(temporal);
                vista.btnLimpiar.doClick();
                ctrl.notificacion(vista.lblNotificacio, 2000, "Guardado correctamente", new Color(35,107,43));
            }catch(Exception e){
                ctrl.notificacion(vista.lblNotificacio, 2000, "Ocurrio un error al guardar", new Color(107,8,8));
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }else{
            ctrl.notificacion(vista.lblNotificacio, 2000, "Llene todos los campos primero", Color.BLUE);
        }
    }
    
    public void aplicarFiltrado(){
        Document e1 = vista.txtCodigo.getDocument();
        DocumentFilter filtro1 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e1).setDocumentFilter(filtro1);
        
        Document e2 = vista.txtExistencia.getDocument();
        DocumentFilter filtro2 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e2).setDocumentFilter(filtro2);
    }
    
}
