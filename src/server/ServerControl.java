/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import dao.JDBCConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.LoginView;

/**
 *
 * @author Admin
 */
public class ServerControl {
   
    private ServerView serverView;
    private ThreadSocket threadSocket;
    private UpdateTimeServer updateTimeServer;
    private ServerSocket serverSocket;
    
    private Socket socket;
    private int port = 1997;
    // bỏ qua
    private ArrayList<String> listUser ;

    public ServerControl(ServerView view) {
        listUser = new ArrayList<>();
        this.serverView = view;
         this.serverView.setVisible(true);
        openServer(this.port);
         this.serverView.addbtnExitActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
         updateTimeServer = new UpdateTimeServer(this.serverView);
         updateTimeServer.start();
        while(true){
            
            listening();
        }
        
    }

    public void openServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listening() {
        try {
            // luồng được tạo cho mỗi Client 
            threadSocket = new ThreadSocket(serverSocket.accept(),this.serverView,listUser);
            threadSocket.start();
            
        
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            
        }
        
        
    }

    
   
}
