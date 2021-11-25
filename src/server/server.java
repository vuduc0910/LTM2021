/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

//import com.sun.jdi.connect.spi.Connection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import classes.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vuduc
 */
public class server {

    public static int getSinhVien(String user, String pwd) {
        SinhVien sv = new SinhVien();
        try {
            Connection connect = (Connection) MSSQLJDBCConnection.getJDBCConnection();
            String sql = "SELECT * FROM SINHVIEN WHERE USERNAME ='" + user + "' AND PWD ='" + pwd + "' ";
            Statement statement = null;
            ResultSet rs = null;
            statement = connect.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                sv.setId((String) rs.getString("USERNAME"));
                sv.setName((String) rs.getNString("HOTEN"));
                sv.setClasses((String) rs.getString("LOP"));
            }
            rs.close();
            statement.close();
            connect.close();
            if (sv.getId() == null) {
                return 1;
            } else {
                return 2;
            }
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public static ArrayList<CauHoi> getListCauHoi() {
        try {
            ArrayList<CauHoi> list = new ArrayList<CauHoi>();
            Connection connect = (Connection) MSSQLJDBCConnection.getJDBCConnection();
            String sql = "SELECT TOP (20) * FROM dbo.CAUHOI INNER JOIN dbo.DSDAPAN ON DSDAPAN.ID_CAUHOI = CAUHOI.ID_CAUHOI ORDER BY NEWID()";
            Statement statement = null;
            ResultSet rs = null;
            statement = connect.createStatement();
            
            rs = statement.executeQuery(sql);
            while(rs.next()){
                CauHoi a = new CauHoi();
                a.setQuestion(rs.getNString("CAUHOI"));
                a.setCorrectAnswer(rs.getNString("DAPANDUNG"));
                ArrayList<String> da = new ArrayList<>();
                da.add(rs.getNString("DA1"));
                da.add(rs.getNString("DA2"));
                da.add(rs.getNString("DA3"));
                da.add(rs.getNString("DA4"));
                a.setListAnswer(da);
                list.add(a);
            }
            int sizeRs = list.size();
            return sizeRs == 0 ? null : list;
        } catch (SQLException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(2021);
        ThreadTime threadTime = null;
        ArrayList<CauHoi> listCauHoi = null;
        while (true) {
            Socket client = server.accept();
            DataInputStream din = new DataInputStream(client.getInputStream());
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
            String n = din.readUTF();
            switch (n) {
                case "LOGIN": {
                    String userName = din.readUTF();
                    String pwd = din.readUTF();
                    int result = getSinhVien(userName, pwd);
                    dout.writeInt(result);
                    break;
                }
                case "START": {
                    threadTime = new ThreadTime();
                    threadTime.start();
                    listCauHoi = getListCauHoi();
                    if(listCauHoi == null){
                        dout.writeInt(0);
                    }
                    else{
                        dout.writeInt(listCauHoi.size());
                    }
                    for(CauHoi cauHoi: listCauHoi){
                        dout.writeUTF(cauHoi.getQuestion());
                        for(String da: cauHoi.getListAnswer()){
                            dout.writeUTF(da);
                        }
                    }
                    break;
                }
                case "FINISH": {
                    if (threadTime != null) {
                        threadTime.detroy();
                    }
                    float diem = 0;
                    for(int i =0; i< listCauHoi.size(); i++){
                        String da = din.readUTF(); //dap an nhan duoc
                        String dad = listCauHoi.get(i).getCorrectAnswer(); // dap an dung
                        String yda = ""; // your answer
                        switch(da){
                            case "A":
                            {
                                yda = listCauHoi.get(i).getListAnswer().get(0);
                            }
                            case "B":
                            {
                                yda = listCauHoi.get(i).getListAnswer().get(1);
                            }
                            case "C":
                            {
                                yda = listCauHoi.get(i).getListAnswer().get(2);
                            }
                            case "D":
                            {
                                yda = listCauHoi.get(i).getListAnswer().get(3);
                            }
                        }
                        if(yda.equalsIgnoreCase(dad)){
                            diem+=1;
                        }
                    }
                    dout.writeFloat(diem);
                    break;
                }
            }
        }
    }
}
