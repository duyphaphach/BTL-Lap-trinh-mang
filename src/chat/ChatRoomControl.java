/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.MessageContent;

/**
 *
 * @author Admin
 */
public class ChatRoomControl {

    
    private ChatRoomView view;
    private MulticastSend multicastSend;
    private MulticastReceive multicastReceive;
    private MessageContent messageContentSend;
    private MessageContent messageContentReceive;
    private int portSyncTime = 2000;
    private String host = "localhost";
    private Socket socketSyncTime;
    private long differenceBetweenEpochs = 2208988800L;

    public ChatRoomControl(String name, Date date) {
        String nickname = name;
        this.view = new ChatRoomView();
        this.view.setVisible(true);
        //set con trỏ chuột tại ô txtField
        this.view.getTxtSendContent().requestFocus(true);
       // set time  
        this.view.setLblTime(date);
       
        // set nick name for user
        this.view.setContentLblNickName(nickname);
        UpdateTime updateTime = new UpdateTime(date, this.view);
        updateTime.start();
        
        // event of btnSend
        eventBtnSend();
        // event of btnExit
        eventBtnExit();
        // event  : press key ENTER for txtSendContent;
        eventTxtSendContent();
        receive();
    }
    
   

    private void eventBtnSend() {
        this.view.addbtnSendActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                send();

            }
        });
    }

    private void eventBtnExit() {
        this.view.addBtnExitActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int selected = JOptionPane.showConfirmDialog(view, "bạn có muốn kết thúc phiên nói chuyện ?", "thông báo", JOptionPane.OK_CANCEL_OPTION);
                if (selected == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    private void eventTxtSendContent() {
        this.view.addTxtSendContentKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {

            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    send();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {

            }
        });
    }

    // gửi gói tin chứa Object MessageContent đi
    public void send() {

        messageContentSend = view.getMessageContent();
        messageContentSend.setTimeMessage(view.getLblTime().getText());
        multicastSend = new MulticastSend(messageContentSend);
        this.view.getTxtSendContent().setText("");
        this.view.getTxtSendContent().requestFocus(true);
    }

    // nhận gói tin trả về
    public void receive() {
        /*
        1, multicastReceive là Thread nhận gói tin gửi về.
        2, update txtArea trong ChatRoomView.
         */
        multicastReceive = new MulticastReceive(this.view);
        multicastReceive.start();

    }

}
