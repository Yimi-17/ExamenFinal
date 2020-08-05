/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Asignatura {
    private int id;
    private String asignatura;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(String curso) {
        this.asignatura = curso;
    }
    public Asignatura (){
        
        
    }
    public Asignatura (String nombre){
        this.asignatura = nombre;
        
    }
    public String toString(){
        return asignatura;
}
    
    
}
