/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelos.Empleado;

/**
 *
 * @author Alex
 */
public class RHDao{

    public boolean registrarEmpleado(Empleado objeto) {
        boolean registrar = false;
        PreparedStatement stm= null;
        Connection con=null;
        
        
        String sql="INSERT INTO empleados (matricula, nombre, apellidos, fechaNacimiento, curp, rfc, sueldo, puesto, sucursal) VALUES (?,?,?,?,?,?,?,?,?)";
        try {			
            con=Conexion.conectar();
            stm=con.prepareStatement(sql);
            stm.setString(1, objeto.getMatricula());
            stm.setString(2, objeto.getNombre());
            stm.setString(3, objeto.getApellidos());
            stm.setString(4, objeto.getFechaNacimiento());
            stm.setString(5, objeto.getCurp());
            stm.setString(6, objeto.getRfc());
            stm.setInt(7, objeto.getSueldo());
            stm.setString(8, objeto.getPuesto());
            stm.setString(9, objeto.getSucursal());
            stm.executeUpdate();
            
            registrar=true;
            stm.close();
            con.close();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Error: Clase RHDao/Metodo registrarEmpleado");
                e.printStackTrace();
        }
        return registrar;
    }

//    public List<Empleado> obtenerEmpleados() {
//        Connection co =null;
//        Statement stm= null;
//        ResultSet rs=null;
//        
//        String sql="SELECT * FROM articulos";
//        List<Empleado> listaCliente= new ArrayList<>();
//
//        try {			
//                co= Conexion.conectar();
//                stm=co.createStatement();
//                rs=stm.executeQuery(sql);
//                while (rs.next()) {
//                        Articulo art=new Articulo();
//                        art.setIdArticulo(rs.getInt(1));
//                        art.setCodigo(rs.getInt(2));
//                        art.setArticulo(rs.getString(3));
//                        art.setExistencia(rs.getInt(4));
//                        art.setSucursal(rs.getString(5));
//                        art.setMarca(rs.getString(6));
//                        art.setFecha(rs.getString(7));
//                        listaCliente.add(art);
//                }
//                stm.close();
//                rs.close();
//                co.close();
//        } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null,"Error: Clase RHDao/Metodo obtenerEmpleados");
//                e.printStackTrace();
//        }
//        return listaCliente;
//    }
//
    public boolean actualizarEmpleados(Empleado objeto) {
        Connection connect= null;
        Statement stm= null;
        boolean actualizar=false;

        String sql="UPDATE empleados SET matricula="+objeto.getMatricula()+", nombre='"+objeto.getNombre()
                +"', apellidos='"+objeto.getApellidos()+"', fechaNacimiento='"+objeto.getFechaNacimiento()
                +"', curp='"+objeto.getCurp()+"', rfc='"+objeto.getRfc()+"', sueldo="+objeto.getSueldo()
                +", puesto='"+objeto.getPuesto()+"', sucursal='"+objeto.getSucursal()
                +"' WHERE idEmpleado="+objeto.getIdEmpleado();
        try {
            connect=Conexion.conectar();
            stm=connect.createStatement();
            stm.execute(sql);
            actualizar=true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: Clase RHDao/Metodo actualizarEmplado");
            e.printStackTrace();
        }		
        return actualizar;
    }
//    
    public List<Empleado> buscarEmpleado(String matricula){
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        
        String sql = "SELECT * FROM empleados WHERE matricula LIKE '%"+matricula+"%'";
        List<Empleado> listaEmpleados= new ArrayList<>();
        
        try{
            co= Conexion.conectar();
            stm=co.createStatement();
            rs=stm.executeQuery(sql);
            while (rs.next()) {
                    Empleado emple=new Empleado();
                    
                    emple.setIdEmpleado(rs.getInt(1));
                    emple.setMatricula(rs.getString(2));
                    emple.setNombre(rs.getString(3));
                    emple.setApellidos(rs.getString(4));
                    emple.setFechaNacimiento(rs.getString(5));
                    emple.setCurp(rs.getString(6));
                    emple.setRfc(rs.getString(7));
                    emple.setSueldo(rs.getInt(8));
                    emple.setPuesto(rs.getString(9));
                    emple.setSucursal(rs.getString(10));
                    emple.setFechaIngreso(rs.getString(11));
                    
                    listaEmpleados.add(emple);
            }
            stm.close();
            rs.close();
            co.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error: Clase RHDao/Metodo buscarEmpleado");
            e.printStackTrace();
        }
        return listaEmpleados;
    }
//    
    public boolean eliminarEmpleado(int id) {
        Connection connect= null;
        Statement stm= null;
        boolean eliminar=false;

        String sql="DELETE FROM empleados WHERE idEmpleado="+id;
        try {
                connect=Conexion.conectar();
                stm=connect.createStatement();
                stm.execute(sql);
                eliminar=true;
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Error: Clase RHDao/Metodo eliminarEmpleado");
                e.printStackTrace();
        }		
        return eliminar;
    }
    
}
