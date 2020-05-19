package jchat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Library extends UnicastRemoteObject implements RMIint {

    public static ArrayList<String> usr = new ArrayList<>();
    public static ArrayList<String> msgq = new ArrayList<>();
    public static ArrayList<String[]> pmSen = new ArrayList<>();
    public static ArrayList<String[]> pmRec = new ArrayList<>();
    public static ArrayList<ArrayList> pms = new ArrayList<>();

    public Library() throws RemoteException {
    }

    //Új bejövő üzenet
    @Override
    public void newMsg(String msg) throws RemoteException {
        DateTimeFormatter ctf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        msgq.add("\n[" + ctf.format(now) + "] " + msg);
    }

    //Új felhasználó csatlakozása
    @Override
    public int newCon(String uname, String ip) throws RemoteException {
        uname += " <" + ip + ">";
        usr.add(uname);
        Server.newCon(uname);
        newMsg(uname + " csatlakozott.");
        return msgq.size();
    }

    //Teljes dátum vissza adása
    @Override
    public String getTime() throws RemoteException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now) + "";
    }

    //User lista vissza adása
    @Override
    public ArrayList getUsers() throws RemoteException {
        return usr;
    }

    //Egy üzenet vissza adása
    @Override
    public String getMsg(int what) throws RemoteException {
        return msgq.get(what);
    }

    //Üzenetlista hosszának vissza adása
    @Override
    public int msgSize() throws RemoteException {
        return msgq.size();
    }

    //Felhasználó kiléptetése a listából, üzenet a kilépésről
    @Override
    public void disconnect(String user) throws RemoteException {
        usr.remove(usr.indexOf(user));
        newMsg(user + " kilépett.");
        Server.disCon(user);
    }

    @Override
    public ArrayList<Integer> checkPm(String user) throws RemoteException {
        ArrayList<Integer> a = new ArrayList<>();
        if (pmRec.isEmpty()) {
            return a;
        }
        for (int i = 0; i < pmRec.size(); i++) {
            if (pmRec.get(i)[0].equals(user)) {
                a.add(i);
            }
        }
        return a;
    }

    //Új privát beszélgetés inicializálása
    @Override
    public int newPmReq(String from, String to) throws RemoteException {
        int tmp = pmSen.size();
        String[] f = {from,"1"};
        String[] t = {to,"0"};
        pmSen.add(tmp, f);
        pmRec.add(tmp, t);
        ArrayList<String> a = new ArrayList<>();
        pms.add(tmp, a);        
        return tmp;
    }

    @Override
    public String getPm(int refId, int what) throws RemoteException {
        return (String) (pms.get(refId).get(what));
    }

    @Override
    public int getPmSize(int refId) throws RemoteException {
        return pms.get(refId).size();
    }

    //Új privát üzenet
    @Override
    public void newPm(String msg, int refId) throws RemoteException {
        DateTimeFormatter ctf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        pms.get(refId).add("\n["+ctf.format(now)+"] "+msg);
    }

    //Privát beszélgetés megszüntetése
    @Override
    public void pmClose(int refId) throws RemoteException {
        pmSen.remove(refId);
        pmRec.remove(refId);
        pms.remove(refId);
    }

    
    @Override
    public String getSen(int refId) throws RemoteException {
        return pmSen.get(refId)[1];
    }

    @Override
    public String getRec(int refId) throws RemoteException {
        return pmRec.get(refId)[1];
    }

    @Override
    public void pmCon(int refId) throws RemoteException {
        String[] tmp = pmRec.get(refId);
        tmp[1] = "1";
        pmRec.set(refId, tmp);
    }

    @Override
    public boolean pmPairExists(String from, String to) throws RemoteException {
        for (int i = 0; i < pmRec.size(); i++) {
            if (pmSen.get(i)[0].equals(from) && pmRec.get(i)[0].equals(to)) {
                return true;
            }
        }
        return false;
    }

}
