/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;

/**
 *
 * @author Isul
 */
public class DatosMonitor implements Serializable{
    private String lineas, dia, tarde;

    public DatosMonitor(String lineas, String dia, String tarde) {
        this.lineas = lineas;
        this.dia = dia;
        this.tarde = tarde;
    }

    public String getLineas() {
        return lineas;
    }

    public void setLineas(String lineas) {
        this.lineas = lineas;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTarde() {
        return tarde;
    }

    public void setTarde(String tarde) {
        this.tarde = tarde;
    }
    
}
