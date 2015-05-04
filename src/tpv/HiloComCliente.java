/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import datos.Datos;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isul
 */
public class HiloComCliente  extends Thread {
    
    private Datos datos;
    private int puerto;

    public HiloComCliente(Datos datos, int puerto) {
        this.datos = datos;
        this.puerto = puerto;
    }
    
    public void run()
    {
         try {
            Socket cliente = new Socket("localhost", puerto);
            System.out.println("Conexion establecida");
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            //ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            oos.writeObject(datos);
            //ois.readObject();
            cliente.close();
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(TPVJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } 
         this.stop();
    }
    
}
