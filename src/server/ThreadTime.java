/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.logging.Level;
import java.util.logging.Logger;
import classes.*;

/**
 *
 * @author vuduc
 */
public class ThreadTime extends Thread {

    Time time = null;
    private boolean flag = true;

    public String checkTime(int a) {
        String time;
        if (a < 10) {
            time = "0" + a;
        } else {
            time = "" + a;
        }
        return time;
    }

    @Override
    public void run() {
        time = new Time();
        time.setVisible(true);
        String times;
        for (int i = 0; i < 1800; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadTime.class.getName()).log(Level.SEVERE, null, ex);
            }
            times = checkTime(i / 60) + ":" + checkTime(i % 60);
            time.setTextTime(times);
            if (i == 1763) {
                Notification notification = new Notification();
                notification.setVisible(true);
            }
        }
        detroy();

    }

    public void detroy() {
        if (time != null) {
            time.setVisible(false);
        }
        Thread.interrupted();

//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
