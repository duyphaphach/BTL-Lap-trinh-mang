/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import chat.ChatRoomControl;
import chat.ChatRoomView;
import model.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class LoginControl {

    private LoginView clientView;
    private int port = 1997;
    private String host = "localhost";
    private Socket clientSocket;
    // biến dùng cho đồng bộ thời gian server
    private long differenceBetweenEpochs = 2208988800L;
    public LoginControl(LoginView view) {
        this.clientView = view;
        this.clientView.setVisible(true);
        clientView.addLoginListener(new LoginListener());

    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            User user = clientView.getUser();
            ObjectOutputStream objectOutputStream = null;
            InputStream inputStream = null;
            try {
                clientSocket = new Socket(host, port);
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                
                // gửi dữ liệu Object User đến server
                objectOutputStream.writeObject(user);
                // nhận dữ liệu gửi từ server về
                Date date = SyncTime(inputStream);
                String SyncTime = date.toString();
                
                if (SyncTime instanceof String) {
                    String receive = (String) SyncTime;
                    System.out.println(receive);
                    if (receive.contains("ICT 1900")){
                        clientView.showMessenge("tài khoản đã tồn tại");
                }
                    else {
                        
                        clientView.dispose();

                        new ChatRoomControl(user.getUsername(),date);

                    } 
                } 
            } catch (IOException ex) {
                Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(clientView, "máy chủ chưa được bật, hoặc lỗi đường truyền", "lỗi", JOptionPane.OK_OPTION);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(clientView, " máy chủ đang gặp sự cố", "lỗi", JOptionPane.OK_OPTION);
            } finally {

                try {
                    if (clientSocket != null) {
                        clientSocket.close();
                    }
                    if (objectOutputStream != null) {
                        objectOutputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }

    }
    private Date SyncTime(InputStream inputStream){
       
        
       try {
           //
           
           inputStream = clientSocket.getInputStream();
           
           long secondSince1900 = 0;
            for (int i = 0 ; i< 4; i ++){
                secondSince1900 = (secondSince1900 << 8) | inputStream.read();
                
            }
            long secondSince1970 = secondSince1900 - differenceBetweenEpochs;
            long msSince1970 = secondSince1970 * 1000;
            Date date = new Date(msSince1970);
            return date;
           
            
       } catch (IOException ex) {
           Logger.getLogger(ChatRoomView.class.getName()).log(Level.SEVERE, null, ex);
       } finally{
           if ( inputStream != null)
               try {
                   inputStream.close();
           } catch (IOException ex) {
               Logger.getLogger(ChatRoomView.class.getName()).log(Level.SEVERE, null, ex);
           }
           if ( clientSocket != null)
               try {
                   clientSocket.close();
           } catch (IOException ex) {
               Logger.getLogger(ChatRoomView.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
       return null;
    }
}
