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

/**
 *
 * @author Alex
 */
public class InventarioDao{

    public boolean registrar(Articulo objeto) {
        boolean registrar = false;
        
        PreparedStatement stm= null;
        Connection con=null;
        
        String sql="INSERT INTO articulos (codigo, articulo, existencia, sucursal, marca) VALUES (?,?,?,?,?)";
        try {			
            con=Conexion.conectar();
            stm=con.prepareStatement(sql);
            stm.setInt(1, objeto.getCodigo());
            stm.setString(2, objeto.getArticulo());
            stm.setInt(3, objeto.getExistencia());
            stm.setString(4, objeto.getSucursal());
            stm.setString(5, objeto.getMarca());
            stm.executeUpdate();
            
            registrar=true;
            stm.close();
            con.close();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Error: Clase InventarioDao/Metodo registrar");
                e.printStackTrace();
        }
        return registrar;
    }

    public List<Articulo> obtenerArticulos() {
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        
        String sql="SELECT * FROM articulos";
        List<Articulo> listaArticulos= new ArrayList<>();

        try {			
                co= Conexion.conectar();
                stm=co.createStatement();
                rs=stm.executeQuery(sql);
                while (rs.next()) {
                        Articulo art=new Articulo();
                        art.setIdArticulo(rs.getInt(1));
                        art.setCodigo(rs.getInt(2));
                        art.setArticulo(rs.getString(3));
                        art.setExistencia(rs.getInt(4));
                        art.setSucursal(rs.getString(5));
                        art.setMarca(rs.getString(6));
                        art.setFecha(rs.getString(7));
                        listaArticulos.add(art);
                }
                stm.close();
                rs.close();
                co.close();
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Error: Clase InventarioDao/Metodo obtenerArticulos");
                e.printStackTrace();
        }
        return listaArticulos;
    }

    public boolean actualizar(Articulo objeto) {
        Connection connect= null;
        Statement stm= null;
        boolean actualizar=false;

        String sql="UPDATE articulos SET codigo="+objeto.getCodigo()+", articulo='"+objeto.getArticulo()+
                "', existencia="+objeto.getExistencia()+", sucursal='"+objeto.getSucursal()+"', marca='"+objeto.getMarca()+
                "' WHERE idArticulo="+objeto.getIdArticulo();
        try {
            connect=Conexion.conectar();
            stm=connect.createStatement();
            stm.execute(sql);
            actualizar=true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error: Clase InventarioDao/Metodo actualizar");
            e.printStackTrace();
        }		
        return actualizar;
    }
    
    public List<Articulo> buscarArticulo(int codigo, String articulo){
        Connection co =null;
        Statement stm= null;
        ResultSet rs=null;
        
        String sql = "SELECT * FROM articulos WHERE CAST(codigo as CHAR) LIKE '%"+codigo+"%' AND articulo LIKE'%"+articulo+"%'";
        List<Articulo> listaArticulos = new ArrayList<>();
        
        try{
            co= Conexion.conectar();
            stm=co.createStatement();
            rs=stm.executeQuery(sql);
            while (rs.next()) {
                    Articulo art=new Articulo();
                    art.setIdArticulo(rs.getInt(1));
                    art.setCodigo(rs.getInt(2));
                    art.setArticulo(rs.getString(3));
                    art.setExistencia(rs.getInt(4));
                    art.setSucursal(rs.getString(5));
                    art.setMarca(rs.getString(6));
                    art.setFecha(rs.getString(7));
                    listaArticulos.add(art);
            }
            stm.close();
            rs.close();
            co.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error: Clase InventarioDao/Metodo buscarArticulo");
            e.printStackTrace();
        }
        return listaArticulos;
    }
    
    public boolean eliminaraArticulo(Articulo objeto) {
        Connection connect= null;
        Statement stm= null;
        boolean eliminar=false;

        String sql="DELETE FROM articulos WHERE codigo="+objeto.getCodigo()+" AND articulo='"+objeto.getArticulo()+"' AND"
                + " existencia="+objeto.getExistencia()+" AND sucursal='"+objeto.getSucursal()+"' AND marca='"+objeto.getMarca()+"'";
        try {
                connect=Conexion.conectar();
                stm=connect.createStatement();
                stm.execute(sql);
                eliminar=true;
        } catch (SQLException e) {
                System.out.println("Error: Clase InventarioDao/Metodo eliminarArticulo");
                e.printStackTrace();
        }		
        return eliminar;
    }
    
}
