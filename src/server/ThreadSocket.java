/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dao.JDBCConnection;
import java.net.Socket;
import java.io.*;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import login.LoginView;
import model.User;

/**
 *
 * @author Admin
 */
public class ThreadSocket extends Thread {

    private Socket socket;
    private ServerView view;
    private ArrayList<String> listUser;
    public ThreadSocket(Socket s, ServerView view, ArrayList<String> listUser) {
        this.socket = s;
        this.view = view;
        this.listUser = listUser;
    }

    public void run() {
        try {
            // tạo luồng để đọc dữ liệu từ client tới server
            // và trả về dữ liệu từ server về client
            OutputStream objectOutputStream = this.socket.getOutputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            
            try {
                Object obj = objectInputStream.readObject();
                
                if (obj instanceof User) {
                    User user = (User) obj;
                    if (checkUser(user)) {
                        // gửi về cho client : byte[] để lưu thời gian
                        objectOutputStream.write(SyncTime());
                        objectOutputStream.flush();

                    } else {
                         
                         
                        objectOutputStream.write(null);
                    }
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // kiểm tra client có trong cơ sở dữ liệu
    public boolean checkUser(User user) {
        Connection conn = new JDBCConnection().getConnetion();
        String sql = "SELECT * FROM USER_LOGIN WHERE USERNAME = ? AND PASSWORD = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && !checkUserAlive(user.getUsername())) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);

        }
        return false;
    }
    
    // bỏ qua
    private boolean checkUserAlive(String string){
        for( int i = 0 ; i< listUser.size(); i++){
            if(listUser.get(i).equalsIgnoreCase(string))
                return true;
        }
        listUser.add(string);
        for( int i = 0 ; i< listUser.size(); i++){
            System.out.println(listUser.get(i) + " : ");
        }
        return false;
    }

    // tính toán thời gian của server
    public byte[] SyncTime() {

        long differenceBetweenEpochs = 2208988800L;

        try {

            Date now = new Date();
            long msSince1970 = now.getTime();

            long secondSince1970 = msSince1970 / 1000;
            long seconSince1900 = secondSince1970 + differenceBetweenEpochs;
            byte[] time = new byte[4];
            time[0] = (byte) ((seconSince1900 & 0x00000000FF000000L) >> 24);
            time[1] = (byte) ((seconSince1900 & 0x0000000000FF0000L) >> 16);
            time[2] = (byte) ((seconSince1900 & 0x000000000000FF00L) >> 8);
            time[3] = (byte) (seconSince1900 & 0x00000000000000FFL);
            return time;

        } catch (Exception ex) {
            Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
