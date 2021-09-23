/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.NominaDao;
import formularios.VistaNominaPersonal;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import librerias.FiltraEntrada;
import modelos.Nomina;

/**
 *
 * @author Alex
 */
public class NominaPersonalControlador {
    private ControladorGeneral ctrl;
    private NominaDao dao;
    private VistaNominaPersonal vista;

    public NominaPersonalControlador(VistaNominaPersonal vista) {
        this.vista = vista;
        this.inicializar();
        this.eventos();
        this.aplicarFiltrado();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new NominaDao();
        
        //Mandamos los placeholder y los campos de texto
        ctrl.placeholder(new String[]{"Matricula","Area","Salario","Incentivo","Dias","Descuento","Total"}, 
                vista.txtMatricula, vista.txtArea,vista.txtSalario, vista.txtIncentivo, vista.txtDias, 
                vista.txtDescuento, vista.txtTotal);
    }
    
    private void eventos(){
        
        //boton limpiar
        vista.btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "¿Limpiar?", "Aceptar", dialogButton);

                if(dialogResult==0){
                    ctrl.limpiarTxt(vista.txtMatricula, vista.txtArea,vista.txtSalario, vista.txtIncentivo, vista.txtDias, 
                    vista.txtDescuento, vista.txtTotal);
                }
            }
        });
        
        //boton salir
        vista.btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "¿Salir?", "Aceptar", dialogButton);

                if(dialogResult==0){
                    vista.setVisible(false);
                }
            }
        });
        
        //boton guardar
        vista.btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardar();
            }
        });
    
    }
    
    public void guardar(){
        if(!ctrl.notificacionCorriendo){
            if(!vista.txtMatricula.getText().isEmpty() & !vista.txtArea.getText().isEmpty() & !vista.txtSalario.getText().isEmpty()
                    & !vista.txtIncentivo.getText().isEmpty() & !vista.txtDias.getText().isEmpty() & !vista.txtDescuento.getText().isEmpty()
                            & !vista.txtTotal.getText().isEmpty()){
                try{
                    Nomina temporal = new Nomina();
                    temporal.setMatricula(vista.txtMatricula.getText());
                    temporal.setArea(vista.txtArea.getText());
                    temporal.setSalario(Integer.parseInt(vista.txtSalario.getText()));
                    temporal.setIncentivo(Integer.parseInt(vista.txtIncentivo.getText()));
                    temporal.setDias(Short.parseShort(vista.txtDias.getText()));
                    temporal.setDescuento(Integer.parseInt(vista.txtDescuento.getText()));
                    temporal.setTotal(Integer.parseInt(vista.txtTotal.getText()));

                    boolean consulta = dao.registrar(temporal);
                    if (consulta){
                        vista.btnLimpiar.doClick();
                        ctrl.notificacion(vista.lblNotificacion, 3000, "¡Agregado!", new Color(35,107,43));
                    }else{
                        ctrl.notificacion(vista.lblNotificacion, 3000, "¡No agregado!", new Color(107,8,8));
                    }
                }catch(Exception e){
                    ctrl.notificacion(vista.lblNotificacion, 3000, "¡Error :(!", new Color(107,8,8));
                    e.printStackTrace();
                }
            }else{
                ctrl.notificacion(vista.lblNotificacion, 3000, "Campos vacios", Color.BLUE);
            }
        }
    }
    
    public void aplicarFiltrado(){
        Document e1 = vista.txtMatricula.getDocument();
        DocumentFilter filtro1 = new FiltraEntrada(FiltraEntrada.NUM_LETRAS, false);
        ((AbstractDocument)e1).setDocumentFilter(filtro1);
        
        Document e2 = vista.txtArea.getDocument();
        DocumentFilter filtro2 = new FiltraEntrada(FiltraEntrada.NUM_LETRAS, false);
        ((AbstractDocument)e2).setDocumentFilter(filtro2);
        
        Document e3 = vista.txtSalario.getDocument();
        DocumentFilter filtro3 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e3).setDocumentFilter(filtro3);
        
        Document e4 = vista.txtIncentivo.getDocument();
        DocumentFilter filtro4 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e4).setDocumentFilter(filtro4);
        
        Document e5 = vista.txtDias.getDocument();
        DocumentFilter filtro5 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e5).setDocumentFilter(filtro5);
        
        Document e6 = vista.txtDescuento.getDocument();
        DocumentFilter filtro6 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e6).setDocumentFilter(filtro6);
        
        Document e7 = vista.txtTotal.getDocument();
        DocumentFilter filtro7 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e7).setDocumentFilter(filtro7);
        
    }
    
    
}
