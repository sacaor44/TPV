/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.security.PublicKey;

/**
 *
 * @author Isul
 */
public class DatosSeguridad implements Serializable{
    private PublicKey publica;
    private byte[] firma;
    private DatosMonitor datosM;

    public DatosSeguridad(PublicKey publica, DatosMonitor datosM, byte[] firma) {
        this.publica = publica;
        this.datosM = datosM;
        this.firma = firma;
    }

    public PublicKey getPublica() {
        return publica;
    }

    public void setPublica(PublicKey publica) {
        this.publica = publica;
    }

    public DatosMonitor getDatos() {
        return datosM;
    }

    public void setDatos(DatosMonitor datosM) {
        this.datosM = datosM;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }
    
}
