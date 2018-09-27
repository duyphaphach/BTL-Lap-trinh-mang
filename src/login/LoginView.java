/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import model.User;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class LoginView extends javax.swing.JFrame {

    /**
     * Creates new form ClientView
     */
    public LoginView() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);
        jPanel1.add(txtPassword);
        txtPassword.setBounds(100, 70, 170, 20);

        btnLogin.setText("ĐĂNG NHẬP");
        jPanel1.add(btnLogin);
        btnLogin.setBounds(100, 110, 130, 23);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 0, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ĐĂNG NHẬP");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(160, 10, 109, 22);

        jLabel2.setText("password");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(10, 70, 80, 14);

        jLabel1.setText("username");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 40, 80, 14);
        jPanel1.add(txtUsername);
        txtUsername.setBounds(100, 40, 170, 20);

        jLabel5.setIcon(new javax.swing.ImageIcon("E:\\LAP_TRINH_MANG\\SOURCE CODE\\LTM_BAI TAP LON\\images\\background_login.jpeg")); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(0, 0, 440, 230);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public User getUser() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        return new User(username, password);
    }

    public void showMessenge(String mes) {
        JOptionPane.showMessageDialog(this, mes, "thông báo", JOptionPane.OK_OPTION);
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.setFocusable(true);
    }

    public void addLoginListener(ActionListener al) {
        btnLogin.addActionListener(al);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
