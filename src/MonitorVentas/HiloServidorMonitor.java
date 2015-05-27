/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MonitorVentas;


import datos.Datos;
import datos.DatosMonitor;
import datos.DatosSeguridad;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author Isul
 */
public class HiloServidorMonitor extends Thread {
    private JTextField lineas,dia,tarde;
    private boolean momento;
    private double totalT, totalD;
    private int totalL;
    public HiloServidorMonitor(JTextField lineas,JTextField tarde, JTextField dia) 
    {
        this.lineas=lineas;
        this.tarde=tarde;
        this.dia=dia;;

    }
    
    public void run() {
        System.out.println("Arrancado HiloSERVIDORmonitor");
        recibir();
    }
    
    public void dsa(PublicKey publica, byte[] firma,DatosMonitor datosM)
    {
        try {
            Signature vdsa = Signature.getInstance("SHA1withDSA");
            vdsa.initVerify(publica);
            vdsa.update(pasarArrayByte(datosM));
            if(vdsa.verify(firma)){
                System.out.println("La firma del mensaje es correcta.");
                String tt=datosM.getTarde();
                int post=tt.indexOf(".");
                String textT=tt.substring(0,post+2);
                String td=datosM.getDia();
                int posd=td.indexOf(".");
                String textD=td.substring(0,posd+2);
                lineas.setText(datosM.getLineas());
                tarde.setText(textT);
                dia.setText(textD);
            }else{
                System.out.println("La firma es incorrecta.");
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HiloServidorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HiloServidorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(HiloServidorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void recibir(){
        int puerto = 9999;
          try{
         ServerSocket servidor = new ServerSocket(puerto);
            while (true) {
                Socket conexionCliente = servidor.accept();
                if (conexionCliente != null) {
                    ObjectInputStream entrada = new ObjectInputStream(conexionCliente.getInputStream());
                    DatosSeguridad datos = (DatosSeguridad) entrada.readObject();
                    dsa(datos.getPublica(), datos.getFirma(), datos.getDatos());
                    
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
    public byte[] pasarArrayByte(DatosMonitor dat) {
        ObjectOutputStream os = null;
        byte[] bytes=null;
        try {
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bs);
            os.writeObject(dat);  
            os.close();
            bytes = bs.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(HiloServidorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloServidorMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bytes;
    }
}
