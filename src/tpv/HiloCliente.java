/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tpv;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Isul
 */
public class HiloCliente extends Thread {

    private DatagramSocket mySocket;
    private String msg;
    private int puerto;
    private DatagramPacket datagram;
    private InetAddress host;
    private byte[] buff;

    public HiloCliente() {
        puerto = 2345;
        try {
            host = InetAddress.getByName("localhost");

            msg = "T";

            mySocket = new DatagramSocket();

            //mySocket.close();
        } catch (SocketException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        buff = msg.getBytes();

        datagram = new DatagramPacket(buff, buff.length, host, puerto);
        System.out.println("Cliente: ENVIANDO " + msg);
        try {
            mySocket.send(datagram);
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int cerrarVentana() {
        msg="cerrar";
         buff = msg.getBytes();

        datagram = new DatagramPacket(buff, buff.length, host, puerto);
        System.out.println("Cliente: ENVIANDO " + msg);
        try {
            mySocket.send(datagram);
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}
