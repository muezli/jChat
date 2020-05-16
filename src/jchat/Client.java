package jchat;

import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Client extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Variables">
    static String uname;
    static String ser;
    static int port;
    static Registry reg;
    static RMIint rmi;
    static int counter;
    public String msg;
    static ArrayList<String> usr = new ArrayList<>();
    static DefaultListModel<String> lmusr = new DefaultListModel<>();
    static Thread watcher;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Create new form Client">
    public Client() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    // </editor-fold>

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt_main = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_usr = new javax.swing.JList<>();
        txt_msg = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menu_con = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("jChat");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txt_main.setColumns(20);
        txt_main.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        txt_main.setRows(5);
        txt_main.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_main.setEnabled(false);
        jScrollPane1.setViewportView(txt_main);

        list_usr.setModel(lmusr);
        list_usr.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(list_usr);

        txt_msg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_msgKeyPressed(evt);
            }
        });

        btn_send.setText("Küld");
        btn_send.setEnabled(false);
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        jMenu1.setText("Csatlakozás");

        menu_con.setText("Csatlakozás");
        menu_con.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menu_conActionPerformed(evt);
            }
        });
        jMenu1.add(menu_con);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_msg)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(btn_send, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_msg, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_send, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menu_conActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menu_conActionPerformed
        ConM.main(null);
    }//GEN-LAST:event_menu_conActionPerformed

    private void txt_msgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_msgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER && btn_send.isEnabled()) {
            send();
        }
    }//GEN-LAST:event_txt_msgKeyPressed

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        send();
    }//GEN-LAST:event_btn_sendActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            rmi.disconnect(uname);
        } catch (RemoteException ex) {
            txt_main.append("Szerver elérési hiba!");
        }
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }

    //Method to get variables from ConM form
    public static void setter(String name, String addr, int por) {
        uname = name;
        ser = addr;
        port = por;
        try {
            conMan();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Connection manager with watcher
    public static void conMan() throws UnknownHostException {
        try {
            //Connect to RMI server and get user list, generate full username
            reg = LocateRegistry.getRegistry(ser, port);
            rmi = (RMIint) reg.lookup("newLib");
            String ip = Integer.toString(InetAddress.getLocalHost().hashCode());
            counter = rmi.newcon(uname, ip);
            uname += " <" + ip + ">";
            usr = rmi.users();
            lmusr.addAll(usr);
            txt_main.append("\n" + rmi.time() + "\nSikeresen csatlakozva: " + ser + ":" + port + " mint: " + uname);

            //Watcher to get messages from the message queue
            watcher = new Thread() {
                public void run() {
                    try {
                        while (true) {
                            if (rmi.users().size() != usr.size()) {
                                usr = rmi.users();
                                lmusr.clear();
                                lmusr.addAll(usr);
                            }
                            int size = rmi.size();
                            if (counter < size) {
                                for (int i = counter; i < size; i++) {
                                    txt_main.append(rmi.msgret(counter));
                                    txt_main.getCaret().setDot(Integer.MAX_VALUE);
                                }
                                counter = size;
                            }
                            Thread.sleep(500);
                        }
                    } catch (RemoteException ex) {
                        txt_main.append("Szerver elérési hiba!");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            watcher.start();
            btn_send.setEnabled(true);
        } catch (RemoteException | NotBoundException ex) {
            JOptionPane.showMessageDialog(null, "Csatlakozási hiba!", "Hiba!", JOptionPane.ERROR_MESSAGE);
        }

    }

    //Method to send message
    public void send() {
        if (!txt_msg.getText().isEmpty()) {
            try {
                rmi.newmsg(uname + ": " + txt_msg.getText());
                txt_msg.setText("");
            } catch (RemoteException ex) {
                txt_main.append("Szerver elérési hiba!");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Variables">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btn_send;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> list_usr;
    private javax.swing.JMenuItem menu_con;
    private static javax.swing.JTextArea txt_main;
    private javax.swing.JTextField txt_msg;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
