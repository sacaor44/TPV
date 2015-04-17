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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Isul
 */
public class HiloServidor extends Thread {
    private String msg;
    private ServerSocket ss;
    private Socket cs;
    private PrintWriter out;
    private BufferedReader in;
    private int puerto;
    public HiloServidor() {
        puerto = 2345;
        try {
            ss=new ServerSocket(puerto);
            cs=ss.accept();
            out = new PrintWriter(cs.getOutputStream(),true);
            
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            
            ss=new ServerSocket(puerto);
            cs=ss.accept();
            out = new PrintWriter(cs.getOutputStream(),true);
            in= new BufferedReader(new InputStreamReader(cs.getInputStream()));
            msg= in.readLine();
            System.out.println("Servidor recibe: "+msg);
            System.out.println("Servdor envia: "+ msg);
            out.println(msg);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
               
    
            
        } 
    }


