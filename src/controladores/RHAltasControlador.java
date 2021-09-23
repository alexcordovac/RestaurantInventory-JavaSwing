/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.RHDao;
import formularios.VistaRHAltas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import librerias.FiltraEntrada;
import modelos.Empleado;

/**
 *
 * @author Alex
 */
public class RHAltasControlador {
    private ControladorGeneral ctrl;
    private RHDao dao;
    private VistaRHAltas vista;

    public RHAltasControlador(VistaRHAltas vista) {
        this.vista = vista;
        this.inicializar();
        this.eventos();
        this.aplicarFiltrado();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new RHDao();
        
        //Mandamos los placeholder y los campos de texto
        ctrl.placeholder(new String[]{"Matricula","Nombre","Apellidos","Fecha de nacimiento (AAAA-MM-DD)","CURP","RFC",
            "Sueldo","Puesto", "Sucursal", "Fecha de ingreso"}, 
                vista.txtMatricula, vista.txtNombre,vista.txtApellidos, vista.txtFechaNacimiento, vista.txtCurp, vista.txtRfc, vista.txtSueldo, 
                vista.txtPuesto, vista.txtSucursal);
    }
    
    private void eventos(){
        
        //boton limpiar
        vista.btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrl.limpiarTxt(vista.txtMatricula, vista.txtNombre,vista.txtApellidos, vista.txtFechaNacimiento, vista.txtCurp, vista.txtRfc, vista.txtSueldo, 
                vista.txtPuesto, vista.txtSucursal);
            }
        });
        
        //boton salir
        vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista.setVisible(false);
            }
        });
        
        //boton guardar
        vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarEmpleado();
                
            }
        });
    
    }
    
    public void guardarEmpleado(){
        if(!vista.txtMatricula.getText().isEmpty() & !vista.txtNombre.getText().isEmpty() & !vista.txtApellidos.getText().isEmpty()
                & !vista.txtFechaNacimiento.getText().isEmpty() & !vista.txtCurp.getText().isEmpty() & !vista.txtRfc.getText().isEmpty()
                        & !vista.txtSueldo.getText().isEmpty() & !vista.txtPuesto.getText().isEmpty() & !vista.txtSucursal.getText().isEmpty()){
            if(ctrl.validarFecha(vista.txtFechaNacimiento.getText())){
                try{
                    Empleado temporal = new Empleado();
                    temporal.setMatricula(vista.txtMatricula.getText());
                    temporal.setNombre(vista.txtNombre.getText());
                    temporal.setApellidos(vista.txtApellidos.getText());
                    temporal.setFechaNacimiento(vista.txtFechaNacimiento.getText());
                    temporal.setCurp(vista.txtCurp.getText());
                    temporal.setRfc(vista.txtRfc.getText());
                    temporal.setSueldo(Integer.parseInt(vista.txtSueldo.getText()));
                    temporal.setPuesto(vista.txtPuesto.getText());
                    temporal.setSucursal(vista.txtSucursal.getText());

                    boolean consulta = dao.registrarEmpleado(temporal);
                    if (consulta){
                        vista.btnLimpiar.doClick();
                        ctrl.notificacion(vista.lblNotificacion, 3000, "¡Agregado!", new Color(35,107,43));
                    }else{
                        ctrl.notificacion(vista.lblNotificacion, 3000, "¡Error!", new Color(107,8,8));
                    }
                }catch(Exception e){
                    ctrl.notificacion(vista.lblNotificacion, 3000, "¡Error!", new Color(107,8,8));
                    e.printStackTrace();
                }
            }else{
                ctrl.notificacion(vista.lblNotificacion, 3000, "Fecha no valida", new Color(107,8,8));
            }
        }else{
            ctrl.notificacion(vista.lblNotificacion, 3000, "Campos vacios", Color.BLUE);
        }
    }
    
    public void aplicarFiltrado(){
        Document e1 = vista.txtMatricula.getDocument();
        DocumentFilter filtro1 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e1).setDocumentFilter(filtro1);
        
        Document e2 = vista.txtSueldo.getDocument();
        DocumentFilter filtro2 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e2).setDocumentFilter(filtro2);
    }
    
    
}
