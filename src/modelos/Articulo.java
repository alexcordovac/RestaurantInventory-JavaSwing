/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

/**
 *
 * @author Alex
 */
public class Articulo {
    private int idArticulo, codigo, existencia;
    private String articulo, sucursal, marca, fecha;

    public Articulo() {
    }
    
    public Articulo(int codigo, String articulo, int existencia, String sucursal, String marca) {
        this.codigo = codigo;
        this.existencia = existencia;
        this.articulo = articulo;
        this.sucursal = sucursal;
        this.marca = marca;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Articulo{" + "idArticulo=" + idArticulo + ", codigo=" + codigo + ", existencia=" + existencia + ", articulo=" + articulo + ", sucursal=" + sucursal + ", marca=" + marca + ", fecha=" + fecha + '}';
    }
    
}
