/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctpv;

import datos.Datos;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isul
 */
public class HiloComServidor extends Thread {
    private InternalTPV itpv;
    private int puerto;

    public HiloComServidor(int puerto,InternalTPV itpv) {
        this.puerto = puerto;
        this.itpv=itpv;
    }
    
    
    public void run()
    {
        try{
         ServerSocket servidor = new ServerSocket(puerto);
            while (true) {
                Socket conexionCliente = servidor.accept();
                if (conexionCliente != null) {
                    ObjectInputStream entrada = new ObjectInputStream(conexionCliente.getInputStream());
                    Datos datos = (Datos) entrada.readObject();
                    if(datos.getAccion().trim().equals("actualizar")){
                        itpv.actualizar(datos.getTotal(), datos.getModelo());
                    }else if(datos.getAccion().equals("cerrar"))
                    {
                        sleep(3000);
                        itpv.cerrar();
                    }
                    
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloComServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
