/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.Timestamp;


/**
 *
 * @author vasit
 */
public class testObject {
    
    private int idmatrizinfo;
    private String name;
    private Timestamp fecha;
    private String observaciones;

    public testObject(int idmatrizinfo, String name, Timestamp fecha, String observaciones) {
        this.idmatrizinfo = idmatrizinfo;
        this.name = name;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public int getIdmatrizinfo() {
        return idmatrizinfo;
    }

    public String getName() {
        return name;
    }
    
    public Timestamp getFecha() {
        return fecha;
    }
    
    public String getObservaciones() {
        return observaciones;
    }

    public void setIdmatrizinfo(int idmatrizinfo) {
        this.idmatrizinfo = idmatrizinfo;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    public void setObervaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    
}
