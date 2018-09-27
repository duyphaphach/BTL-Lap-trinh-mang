/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */

//bỏ qua
public class UpdateTimeServer extends Thread{

    private ServerView view;
    public UpdateTimeServer(ServerView view){
        this.view = view;
    }
    @Override
    public void run() {
        while(true){
            view.updateLblTime(new Date());
        }
    }
    
}
