package jchat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Library extends UnicastRemoteObject implements RMIint {

    public static ArrayList<String> usr = new ArrayList<>();
    public static ArrayList<String> msgq = new ArrayList<>();

    public Library() throws RemoteException {
    }

    //Új bejövő üzenet
    @Override
    public void newmsg(String msg) throws RemoteException {
        DateTimeFormatter ctf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        msgq.add("\n[" + ctf.format(now) + "] " + msg);
    }

    //Új felhasználó csatlakozása
    @Override
    public int newcon(String uname, String ip) throws RemoteException {
        uname += " <" + ip + ">";
        usr.add(uname);
        Server.newCon(uname);
        newmsg(uname + " csatlakozott.");
        return msgq.size();
    }

    //Teljes dátum vissza adása
    @Override
    public String time() throws RemoteException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now) + "";
    }

    //User lista vissza adása
    @Override
    public ArrayList users() throws RemoteException {
        return usr;
    }

    //Egy üzenet vissza adása
    @Override
    public String msgret(int what) throws RemoteException {
        return msgq.get(what);
    }

    //Üzenetlista hosszának vissza adása
    @Override
    public int size() throws RemoteException {
        return msgq.size();
    }

    //Felhasználó kiléptetése a listából, üzenet a kilépésről
    @Override
    public void disconnect(String user) throws RemoteException {
        usr.remove(usr.indexOf(user));
        newmsg(user + " kilépett.");
        Server.disCon(user);
    }

}
