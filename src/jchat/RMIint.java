package jchat;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIint extends Remote {

    void newMsg(String msg) throws RemoteException;

    int newCon(String ip, String uname) throws RemoteException;

    String getTime() throws RemoteException;

    ArrayList getUsers() throws RemoteException;

    String getMsg(int what) throws RemoteException;

    int msgSize() throws RemoteException;

    void disconnect(String user) throws RemoteException;

    ArrayList<Integer> checkPm(String user) throws RemoteException;

    int newPmReq(String from, String to) throws RemoteException;

    void newPm(String msg, int refId) throws RemoteException;

    String getPm(int refId, int what) throws RemoteException;
    
    int getPmSize(int refId) throws RemoteException;

    void pmClose(int refId) throws RemoteException;
    
    void pmDc(int refId, boolean sender) throws RemoteException;

    String getSenStat(int refId) throws RemoteException;

    String getRecStat(int refId) throws RemoteException;
    
    String getSen(int refId) throws RemoteException;

    String getRec(int refId) throws RemoteException;
        
    void pmCon(int refId) throws RemoteException;
    
    boolean pmPairExists(String from, String to) throws RemoteException;
}
