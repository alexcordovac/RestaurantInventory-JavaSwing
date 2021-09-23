/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import dao.RHDao;
import formularios.VistaRHBajas;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import librerias.FiltraEntrada;
import modelos.Empleado;

/**
 *
 * @author Alex
 */
public class RHBajasControlador {
    
    //Variables
    private ControladorGeneral ctrl;
    private VistaRHBajas vista;
    private RHDao dao;
    private DefaultTableModel modelo;
    private List<Empleado> lista;
    private int index;

    public RHBajasControlador(VistaRHBajas vista) {
        this.vista = vista;
        this.inicializar();
        this.aplicarFiltrado();
    }
    
    private void inicializar(){
        ctrl = new ControladorGeneral();
        dao = new RHDao();
        
        //Situamos los placeholder en los campos de texto
        ctrl.placeholder(new String[]{"No. Empleado", "No Empleado","Nombre","Apellidos","Fecha de nacimiento","CURP","RFC",
            "Sueldo","Puesto", "Sucursal"}, vista.txtBusqueda, vista.txtMatricula, vista.txtNombre,vista.txtApellidos, vista.txtFechaNacimiento, vista.txtCurp, vista.txtRfc, vista.txtSueldo, 
                vista.txtPuesto, vista.txtSucursal);
        
        //Deshabilitamos algunos elementos visuales
        ctrl.inhabilitarTxt(vista.txtMatricula, vista.txtNombre,vista.txtApellidos, vista.txtFechaNacimiento, vista.txtCurp, vista.txtRfc, vista.txtSueldo, 
                vista.txtPuesto, vista.txtSucursal);
        vista.btnEliminar.setEnabled(false);
        
        //Definimos el modelo para la tabla
        modelo = ctrl.definirModeloTabla(new String[]{"No. Empleado", "Nombre", "CURP"});
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
                ctrl.limpiarTxt(vista.txtBusqueda, vista.txtMatricula, vista.txtNombre,vista.txtApellidos, vista.txtFechaNacimiento, vista.txtCurp, vista.txtRfc, vista.txtSueldo, 
                vista.txtPuesto, vista.txtSucursal);
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
        
        //Tabla
        vista.tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarDatosEmpleado();
            }
        });
    }
    private void funcionImprimirLista(){
        modelo.setRowCount(0);
        for (int i = 0; i < lista.size(); i++) {
            Object[] object = {lista.get(i).getMatricula(), lista.get(i).getNombre(), lista.get(i).getCurp()};
            modelo.addRow(object);
        }
    }
    
    private void llenarDatosEmpleado(){
        index = vista.tabla.getSelectedRow();
        
        if (index>=0){
            vista.txtMatricula.setText(lista.get(index).getMatricula());
            vista.txtNombre.setText(lista.get(index).getNombre());
            vista.txtApellidos.setText(lista.get(index).getApellidos());
            vista.txtFechaNacimiento.setText(lista.get(index).getFechaNacimiento());
            vista.txtCurp.setText(lista.get(index).getCurp());
            vista.txtRfc.setText(lista.get(index).getRfc());
            vista.txtSueldo.setText(Integer.toString(lista.get(index).getSueldo()));
            vista.txtPuesto.setText(lista.get(index).getPuesto());
            vista.txtSucursal.setText(lista.get(index).getSucursal());
            
            vista.btnEliminar.setEnabled(true);
        }
    }
    
    
    private void funcionBuscar(){
        //Si no hay ninguna notificacion visual en ejecucion se procede
        if(ctrl.notificacionCorriendo==false){
            String matricula = vista.txtBusqueda.getText();

            //Si el codigo esta vacio no se procede la busqueda
            if(matricula.isEmpty()){
                ctrl.notificacion(vista.lblNotificacion, 3000, "Numero no puede estar vacio", new Color(107,8,8));
                return;
            }
            lista = dao.buscarEmpleado(matricula);
            //Si la tabla es vacia
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
    
    private void aplicarFiltrado(){
        Document e1 = vista.txtBusqueda.getDocument();
        DocumentFilter filtro1 = new FiltraEntrada(FiltraEntrada.SOLO_NUMEROS, false);
        ((AbstractDocument)e1).setDocumentFilter(filtro1);
        
    }
    
    private void funcionEliminar(int fila){
        if(ctrl.notificacionCorriendo==false){
            try{
                Empleado temp = lista.get(fila);
                dao.eliminarEmpleado(temp.getIdEmpleado());
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
