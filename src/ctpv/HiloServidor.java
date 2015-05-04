/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctpv;

import ctpv.CTPV;
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
    private CTPV ctpv;
    private int num;
    private int contador;

    public HiloServidor(CTPV ctpv) {
        this.ctpv = ctpv;
        puerto = 2345;
        contador=0;

    }

    @Override
    public void run() {
        try {
            System.out.println("Servidor comienza");
            ss = new ServerSocket(puerto);
            while (contador<=6) {
                cs = ss.accept();
                out = new PrintWriter(cs.getOutputStream(), true);

                in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                msg = in.readLine();
                System.out.println("Servidor recibe: " + msg);
                if (msg.trim().equals("T")) {
                    if (!ctpv.lleno()) {
                        num = ctpv.nuevoInternal();
                        contador++;
                        System.out.println("Servidor envia: " + num);
                        out.println(num + "");
                        
                    } else {
                        ctpv.lanzarMensaje("No se pueden crear mas TPV");

                        msg="X";
                        System.out.println("Servidor envia: " + msg);
                        out.println(msg);
                    }
                }
                else{
                    try{
                        num=Integer.parseInt(msg.trim());
                    }catch(NumberFormatException e){
                        
                    }
                    ctpv.mostrarMsgITPV(num);
                    sleep(2000);
                    contador--;
                    ctpv.cerrarITPV(num);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
