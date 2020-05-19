package jchat;

import java.awt.event.KeyEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jchat.Client.reg;
import static jchat.Client.rmi;

public class PrivateMsg extends javax.swing.JFrame {

    static String ser;
    static int port;
    static int refId;
    static String uname;
    static boolean sender;
    static int counter;
    static ArrayList<String> users = new ArrayList<>();
    static Thread watcher;

    public PrivateMsg() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt_main = new javax.swing.JTextArea();
        txt_msg = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txt_main.setColumns(20);
        txt_main.setRows(5);
        txt_main.setEnabled(false);
        jScrollPane1.setViewportView(txt_main);

        txt_msg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_msgKeyPressed(evt);
            }
        });

        btn_send.setText("Küld");
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txt_msg)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_send)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_msg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_send))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        sender();
    }//GEN-LAST:event_btn_sendActionPerformed

    private void txt_msgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_msgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && btn_send.isEnabled()) {
            sender();
        }
    }//GEN-LAST:event_txt_msgKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            if ((sender && rmi.getRec(refId) == "0") || (!sender && rmi.getSen(refId) == "0")) {
                rmi.pmClose(refId);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(PrivateMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
        Client.activePm.remove(Client.activePm.get(refId));
    }//GEN-LAST:event_formWindowClosing
    //port,server,refID,uname,s/r
    public static void main(String args[]) {
        port = Integer.parseInt(args[0]);
        ser = args[1];
        refId = Integer.parseInt(args[2]);
        uname = args[3];
        sender = args[4] == "s" ? true : false;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrivateMsg().setVisible(true);
            }
        });
        conM();
    }

    private static void conM() {
        try {
            reg = LocateRegistry.getRegistry(ser, port);
            rmi = (RMIint) reg.lookup("newLib");

            if (!sender) {
                rmi.pmCon(refId);
            }
            counter = 0;
            watcher = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            int size = rmi.getPmSize(refId);
                            if (counter < size) {
                                for (int i = counter; i < size; i++) {
                                    txt_main.append(rmi.getPm(refId, i));
                                    txt_main.getCaret().setDot(Integer.MAX_VALUE);
                                }
                                counter = size;
                            }
                            Thread.sleep(500);
                        } catch (RemoteException | InterruptedException ex) {
                            Logger.getLogger(PrivateMsg.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };

            watcher.start();
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(PrivateMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sender() {
        try {
            rmi.newPm(uname + ": " + txt_msg.getText(), refId);
            txt_msg.setText("");
        } catch (RemoteException ex) {
            Logger.getLogger(PrivateMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_send;
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea txt_main;
    private static javax.swing.JTextField txt_msg;
    // End of variables declaration//GEN-END:variables
}
