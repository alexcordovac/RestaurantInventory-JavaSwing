/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Alex
 */
public class Conexion {

    static Connection con;

    public static Connection conectar() {
        con = null;

        String usuario = "root";
        String contrasena = "root";
        String url = "jdbc:mysql://localhost:3308/dbrestaurante?useSSL=false&user=" + usuario + "&password=" + contrasena;
        try {
            con = DriverManager.getConnection(url);
            if (con != null) {
                System.out.println("Conectado");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos" + e);
            e.printStackTrace();
        }
        return con;
    }
    
    public static boolean desconectar(){
        boolean x = false;
        
        try {
            con.close();
            x = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return x;
    }
}
