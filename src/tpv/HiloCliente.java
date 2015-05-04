/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Isul
 */
public class HiloCliente extends Thread {

    private String msg;
    private int puerto;
    private Socket cs;
    private PrintWriter out;
    private BufferedReader in;
    private TPVJFrame tpv;

    public HiloCliente(TPVJFrame tpv, String msg) {
        puerto = 2345;
        this.tpv = tpv;
        this.msg = msg;
    }

    public HiloCliente(String msg) {
        puerto = 2345;
        this.msg = msg;

    }

    @Override
    public void run() {
        try {
            cs = new Socket("localhost", puerto);
            out = new PrintWriter(cs.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Cliente enviando: " + msg);
        out.println(msg);
        try {
            msg = in.readLine();
            cs.close();
            out.close();
            in.close();
            int num=-2;
            try {
                num = Integer.parseInt(msg.trim());
            } catch (NumberFormatException e) {
            }
            System.out.println("Cliente recibiendo: " + msg);
            if (msg.trim().equals("X")) {
                tpv.salir();
                this.interrupt();
                
            }
            else if (num>-1) {
                    tpv.setNum(num);
                    
                    }

            
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*public int cerrarVentana() {
        
     }*/
}
