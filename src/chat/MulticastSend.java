/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.MessageContent;

/**
 *
 * @author Admin
 */
public class MulticastSend {

    private String host = "234.5.6.8";
    private int port = 6666;
    private MulticastSocket socket;

    

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MulticastSend(MessageContent obj) {
        
        InetAddress inetAdress = null;
        try {
            socket = new MulticastSocket();
            inetAdress = InetAddress.getByName(this.host);
            if (obj instanceof MessageContent) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                oos.flush();
                byte[] sendData = baos.toByteArray();
                DatagramPacket datagramPacketSend = new DatagramPacket(sendData, sendData.length, inetAdress, this.port);
                socket.send(datagramPacketSend);
                
            } else{
                System.out.println("false  MulticastSend");
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MulticastSend.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MulticastSend.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
