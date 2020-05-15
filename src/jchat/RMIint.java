package jchat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIint extends Remote {

    void newmsg(String msg) throws RemoteException;

    int newcon(String ip, String uname) throws RemoteException;

    String time() throws RemoteException;

    ArrayList users() throws RemoteException;

    String msgret(int what) throws RemoteException;

    int size() throws RemoteException;

    void disconnect(String user) throws RemoteException;
}
