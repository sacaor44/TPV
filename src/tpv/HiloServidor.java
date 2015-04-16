/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Isul
 */
public class HiloServidor extends Thread {
private MasterTPV jf;
    public HiloServidor(MasterTPV jf) {
        this.jf=jf;
    }

    @Override
    public void run() {
        int puerto = 2345;
        final int MAX_LEN = 10;
        try {
            DatagramSocket mySocket = new DatagramSocket(puerto);
            byte[] buff = new byte[MAX_LEN];
            DatagramPacket datagram = new DatagramPacket(buff,MAX_LEN);
            mySocket.receive(datagram);
            String msg=new String(buff);
            msg=msg.trim();
            //int num = Integer.parseInt(msg.trim());
            if(msg.trim().equals("T")){
                System.out.println("RECIBIDO: "+msg);
                System.out.println("--REALIZADO CORRECTAMENTE--");
                jf.nuevoInternal();
                
            }
            else if(msg.trim().equals("cerrar")) {
                jf.cerrarITPV(1);
            }
                else{System.out.println("ERROR");
                    }
               
    
            
        } catch (SocketException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

