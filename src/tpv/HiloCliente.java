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

    public HiloCliente() {
        puerto = 2345;
        
        try {
            cs = new Socket("localhost",puerto);
            out = new PrintWriter(cs.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void run() {
        msg="T";
        System.out.println("Cliente enviando: "+msg);
        out.println(msg);
        System.out.println("Cliente recibiendo: "+msg);

    }

    /*public int cerrarVentana() {
        
    }*/
}
