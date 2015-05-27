/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctpv;

import datos.DatosMonitor;
import datos.DatosSeguridad;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Isul
 */
public class HiloClienteMonitor extends Thread {

    private boolean momento;
    private double totalT, totalD;
    private int totalL;
    private DatosMonitor datos;

    public HiloClienteMonitor() {
        datos = new DatosMonitor(null, null, null);
    }

    public void run() {
        while(true){
        leer();
        try {
            sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloClienteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }

    public void leer(){
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File("datos.txt");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("-")) {
                    if (comprobarHora(linea.substring(1, 3))) {
                        momento = true;
                    } else {
                        momento = false;
                    }
                    System.out.println(linea.substring(1, 3));
                } else if (linea.startsWith("#")) {
                    System.out.println(linea.substring(1));
                    sumarATotal(linea.substring(1));
                }
                if(linea.trim().equals("")){
                    
                }else{
                   totalL++; 
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                    datos.setDia((totalD*100/100) + "");
                    datos.setLineas(totalL + "");
                    datos.setTarde((totalT*100/100) + "");
                    totalD=0;
                    totalT=0;
                    totalL=0;
                    prepararClaves(datos);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
    public void enviar(byte[] firma,DatosMonitor datosM, PublicKey publica)
    {
        int puerto = 9999;
        try {
            Socket cliente = new Socket("localhost", puerto);
            System.out.println("Conexion establecida");
            ObjectOutputStream oos = new ObjectOutputStream(cliente.getOutputStream());
            //ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            DatosSeguridad ds = new DatosSeguridad(publica, datosM, firma);
            oos.writeObject(ds);
            //ois.readObject();
            cliente.close();
            oos.close();
        } catch (IOException ex) {
          
        } 
    }
    
    public void prepararClaves(DatosMonitor dat) {
        try {
            KeyPairGenerator gc = KeyPairGenerator.getInstance("DSA");
            SecureRandom ga = SecureRandom.getInstance("SHA1PRNG");
            gc.initialize(1024, ga);
            KeyPair par = gc.generateKeyPair();
            PrivateKey privada = par.getPrivate();
            PublicKey publica = par.getPublic();
            Signature dsa = Signature.getInstance("SHA1withDSA");
            dsa.initSign(privada);
            byte[] aDatos= pasarArrayByte(dat);
            dsa.update(aDatos);
            byte[] firma= dsa.sign();
            enviar(firma,dat,publica);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HiloClienteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(HiloClienteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(HiloClienteMonitor.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(HiloClienteMonitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloClienteMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return bytes;
    }

    public boolean comprobarHora(String hora) {
        int time = 0;
        try {
            time = Integer.parseInt(hora);
            if (time < 15) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return true;
        }

    }

    public void sumarATotal(String cant) {
        double cuanto = Double.parseDouble(cant);
        if (momento) {
            totalD += cuanto;
        } else {
            totalT += cuanto;
        }
    }

    public double acortar(double num) {
        return Math.rint(num * 100) / 100;
    }
}
