/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.administradorh14;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author bto_u
 */
public class Producto {
    
    private String nombre, id; 
    private double temperatura, valorBase, costo;
    
    public Producto(String nombre, String id, double temperatura, double valorBase, double costo){
        this.nombre = nombre;
        this.id = id;
        this.temperatura = temperatura;
        this.valorBase = valorBase;
        this.costo = costo;
    }
    public Producto(){}
    
    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    
    public double getTempaeratura() {return temperatura;}
    public void setTemperatura(double temperatura) {this.temperatura = temperatura;}
    
    public double getValorBase(){return valorBase;}
    public void setValorBase(double valorBase){this.valorBase = valorBase;}
    
    public void setCosto(double temperatura){
        this.costo = (temperatura < 25)?getValorBase()+(getValorBase()*0.20): getValorBase()+(getValorBase()*0.10);
    }
    public double getCosto(){return costo;}
 
 
    // metodos para interactuar con BD
    public boolean crearProducto() {
        String peticion = "INSERT INTO productos (id, Nombre,Temperatura,Valor_Base,Costo) " +
            "VALUES ('" + id + "','" + nombre + "','" + temperatura + "'," + valorBase + ",'" + costo + "')";
        ConexionBD conn = new ConexionBD();
        
        if(conn.setAutoCommitBD(false)) {
            if(conn.insertarBD(peticion)) {
                conn.commitBD();
                conn.cerrarConexion();
                return true;
            } else {
                conn.rollbackBD();
                conn.cerrarConexion();
                return false;
            }
        } else {
            conn.cerrarConexion();
            return false;
        }
    }
    
    public boolean eliminarProducto() {
        String peticion = "DELETE FROM productos WHERE id= " + id;
        ConexionBD conn = new ConexionBD();
        if(conn.setAutoCommitBD(false)) {
            if(conn.borrarBD(peticion)){
                conn.commitBD();
                conn.cerrarConexion();
                return true;
            } else {
                conn.rollbackBD();
                conn.cerrarConexion();
                return false;
            }
        }else {
            conn.cerrarConexion();
            return false;
        }
    }
    
    public boolean actualizarUsuario() {
        String peticion = "UPDATE productos SET Nombre='" + nombre + "', Temperatura='"
                + temperatura + "',Valor_Base=" + valorBase + ", Costo='" + costo
                + "' WHERE id=" + id;
        System.out.println(peticion);
        ConexionBD conn = new ConexionBD();
        
        if(conn.setAutoCommitBD(false)) {
            if(conn.actualizarBD(peticion)){
                conn.commitBD();
                conn.cerrarConexion();
                return true;
            } else {
                conn.rollbackBD();
                conn.cerrarConexion();
                return false;
            }
        }else {
            conn.cerrarConexion();
            return false;
        }
    }
    
    public List<Producto> leerProductos() {
        List<Producto> productos = new ArrayList<>();
        String consulta = "SELECT * FROM productos";
        ConexionBD conn = new ConexionBD();
        ResultSet result = conn.consultarBD(consulta);
        
        try {
            while(result.next()) {
                Producto produc = new Producto();
                produc.setId(result.getString("id"));
                produc.setNombre(result.getString("Nombre"));
                produc.setTemperatura(result.getDouble("Temperatura"));
                produc.setValorBase(result.getDouble("Valor_Base"));
                produc.setCosto(result.getDouble("Temperatura"));
                productos.add(produc);
            }
        } catch (SQLException e) {
            System.err.println("Error BD: " + e.getMessage());
        }
        
        conn.cerrarConexion();
        return productos;
    }
}
