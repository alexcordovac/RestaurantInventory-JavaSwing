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
import modelos.Articulo;
import modelos.Nomina;

/**
 *
 * @author Alex
 */
public class NominaDao{

    public boolean registrar(Nomina objeto) {
        boolean registrar = false;
        
        PreparedStatement stm= null;
        Connection con=null;
        
        String sql="INSERT INTO nomina (matricula, area, salario, incentivo, dias, descuento, total) VALUES (?,?,?,?,?,?,?)";
        try {			
            con=Conexion.conectar();
            stm=con.prepareStatement(sql);
            stm.setString(1, objeto.getMatricula());
            stm.setString(2, objeto.getArea());
            stm.setInt(3, objeto.getSalario());
            stm.setInt(4, objeto.getIncentivo());
            stm.setShort(5, objeto.getDias());
            stm.setInt(6, objeto.getDescuento());
            stm.setInt(7, objeto.getTotal());
            stm.executeUpdate();
            
            
            registrar=true;
            stm.close();
            con.close();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Error: Clase NominaDao/Metodo registrar");
                e.printStackTrace();
        }
        return registrar;
    }

//    public List<Articulo> obtenerArticulos() {
//        Connection co =null;
//        Statement stm= null;
//        ResultSet rs=null;
//        
//        String sql="SELECT * FROM articulos";
//        List<Articulo> listaArticulos= new ArrayList<>();
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
//                        listaArticulos.add(art);
//                }
//                stm.close();
//                rs.close();
//                co.close();
//        } catch (SQLException e) {
//                JOptionPane.showMessageDialog(null,"Error: Clase InventarioDao/Metodo obtenerArticulos");
//                e.printStackTrace();
//        }
//        return listaArticulos;
//    }
//
//    public boolean actualizar(Articulo objeto) {
//        Connection connect= null;
//        Statement stm= null;
//        boolean actualizar=false;
//
//        String sql="UPDATE articulos SET codigo="+objeto.getCodigo()+", articulo='"+objeto.getArticulo()+
//                "', existencia="+objeto.getExistencia()+", sucursal='"+objeto.getSucursal()+"', marca='"+objeto.getMarca()+
//                "' WHERE idArticulo="+objeto.getIdArticulo();
//        try {
//            connect=Conexion.conectar();
//            stm=connect.createStatement();
//            stm.execute(sql);
//            actualizar=true;
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null,"Error: Clase InventarioDao/Metodo actualizar");
//            e.printStackTrace();
//        }		
//        return actualizar;
//    }
//    
    public List<Object[]> buscar(String matricula){
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        
        String sql = "SELECT empleados.matricula, empleados.nombre, empleados.apellidos, nomina.incentivo, nomina.descuento, nomina.total FROM\n" +
"  empleados INNER JOIN nomina ON empleados.matricula = nomina.matricula HAVING matricula ='"+matricula+"'";
        List<Object[]> lista=  new ArrayList<>();
        
        try{
            co= Conexion.conectar();
            stm=co.createStatement();
            rs=stm.executeQuery(sql);
            while (rs.next()) {
                    
                    Object[] objeto = {
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getInt(6)
                    };
                    lista.add(objeto);
            }
            stm.close();
            rs.close();
            co.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error: Clase NominaDao/Metodo buscar");
            e.printStackTrace();
        }
        return lista;
    }

    public boolean eliminar(String matricula) {
        Connection connect= null;
        Statement stm= null;
        boolean eliminar=false;

        String sql="DELETE FROM nomina WHERE matricula='"+matricula+"'";
        try {
                connect=Conexion.conectar();
                stm=connect.createStatement();
                stm.execute(sql);
                eliminar=true;
        } catch (SQLException e) {
                System.out.println("Error: Clase NominaDao/Metodo eliminar");
                e.printStackTrace();
        }		
        return eliminar;
    }
    
}
