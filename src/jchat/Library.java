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

    //new incoming message to the message queue
    @Override
    public void newmsg(String msg) throws RemoteException {
        DateTimeFormatter ctf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        msgq.add("\n[" + ctf.format(now) + "] " + msg);
    }

    //Handle new connections
    @Override
    public int newcon(String uname, String ip) throws RemoteException {
        uname += " <" + ip + ">";
        usr.add(uname);
        Server.newCon(uname);
        newmsg(uname+" csatlakozott.");
        return msgq.size();
    }

    //Returns server time for logging
    @Override
    public String time() throws RemoteException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now) + "";
    }

    //Returns the plain user list
    @Override
    public ArrayList users() throws RemoteException {
        return usr;
    }

    //Returns a specific message from the message queue
    @Override
    public String msgret(int what) throws RemoteException {
        return msgq.get(what);
    }

    //Returns the length of the message queue
    @Override
    public int size() throws RemoteException {
        return msgq.size();
    }

    //Removes user from user list, adds new message
    @Override
    public void disconnect(String user) throws RemoteException {
        usr.remove(usr.indexOf(user));
        newmsg(user+" kilépett.");
        Server.disCon(user);
    }

}
