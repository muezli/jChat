package jchat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

public class Server extends javax.swing.JFrame {
    // <editor-fold defaultstate="collapsed" desc="Variables">
    int port;
    char[] pw;
    static Registry reg;
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    static LocalDateTime now;
    static ArrayList<String> usr = new ArrayList<>();
    static DefaultListModel<String> lmusr = new DefaultListModel<>();
    Thread watcher;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Create new form"> 
    public Server() {
        initComponents();
        this.setLocationRelativeTo(null);
    }// </editor-fold> 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txt_log = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        list_usr = new javax.swing.JList<>();
        btn_start = new javax.swing.JButton();
        btn_stop = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_port = new javax.swing.JTextField();
        btn_log = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Szerver");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        txt_log.setColumns(20);
        txt_log.setFont(new java.awt.Font("Monospaced", 1, 13)); // NOI18N
        txt_log.setRows(5);
        txt_log.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        txt_log.setEnabled(false);
        jScrollPane1.setViewportView(txt_log);

        list_usr.setModel(lmusr);
        list_usr.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(list_usr);

        btn_start.setText("Start");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });

        btn_stop.setText("Stop");
        btn_stop.setEnabled(false);
        btn_stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stopActionPerformed(evt);
            }
        });

        jLabel1.setText("Port");

        txt_port.setText("2674");
        txt_port.setMinimumSize(new java.awt.Dimension(7, 60));

        btn_log.setText("Üzenetnapló");
        btn_log.setEnabled(false);
        btn_log.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 641, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_start))
                        .addGap(37, 37, 37)
                        .addComponent(btn_log)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_stop)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_stop)
                            .addComponent(btn_start)
                            .addComponent(btn_log))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        port = Integer.parseInt(txt_port.getText());
        //Új registry
        try {
            reg = LocateRegistry.createRegistry(port);
            reg.rebind("newLib", new Library());
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        btn_start.setEnabled(!btn_start.isEnabled());
        btn_stop.setEnabled(!btn_stop.isEnabled());
        btn_log.setEnabled(!btn_log.isEnabled());
        txt_port.setEnabled(false);

        //Naplózás kezdete
        now = LocalDateTime.now();
        txt_log.append("\n" + dtf.format(now) + "\nSzerver fut...\nHallgatás a " + port + " porton...\n");
        Library.msgq.add("\n"+dtf.format(now) + " Szerver fut a " + port + " porton.");
        txt_log.getCaret().setDot(Integer.MAX_VALUE);
        
        //Figyelő fonál, ha nincs csatlakozott felhasználó üzenetnapló mentése
        watcher = new Thread() {
            public void run() {
                while(true){
                    try {
                        if (usr.isEmpty() && !Library.msgq.isEmpty()) {
                            saveLog();
                        }                            
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        watcher.start();
    }//GEN-LAST:event_btn_startActionPerformed

    private void btn_stopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stopActionPerformed
        //Unbinding registry
        btn_start.setEnabled(!btn_start.isEnabled());
        btn_stop.setEnabled(!btn_stop.isEnabled());
        btn_log.setEnabled(!btn_log.isEnabled());
        txt_port.setEnabled(true);
        now = LocalDateTime.now();
        txt_log.append("\n" + dtf.format(now) + "\nSzerver leállítva...\n");
        txt_log.getCaret().setDot(Integer.MAX_VALUE);
        watcher.interrupt();
        Library.msgq.add("\n"+dtf.format(now)+" a szerver leáll.");
        saveLog();
        try {
            reg.unbind("newLib");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_stopActionPerformed

    private void btn_logActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logActionPerformed
        //Message queue tömbbe
        String[] tmp = new String[Library.msgq.size()];
        for (int i = 0; i < Library.msgq.size(); i++) {
            tmp[i] = Library.msgq.get(i);
        }
        //Chatlognak átadja az üzenetlistát
        ChatLog.main(tmp);
    }//GEN-LAST:event_btn_logActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        watcher.interrupt();
        Library.msgq.add("\n"+dtf.format(now)+" a szerver leáll.");
        if (!Library.msgq.isEmpty()) {
            saveLog();
        }
    }//GEN-LAST:event_formWindowClosing

    //Új kliens csatlakozása
    public static void newCon(String uname) {
        usr.add(uname);
        lmusr.addElement(uname);
        txt_log.append("\n" + dtf.format(now) + "\n" + uname + " csatlakozott.\n");
        txt_log.getCaret().setDot(Integer.MAX_VALUE);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
                txt_log.append("A szerver nem fut\n");
            }
        });
    }

    //Kliens kilépése
    public static void disCon(String user) {
        usr = Library.usr;
        lmusr.clear();
        lmusr.addAll(usr);
        txt_log.append("\n" + dtf.format(now) + "\n" + user + " kilépett.\n");
        txt_log.getCaret().setDot(Integer.MAX_VALUE);
    }

    //Üzenetnapló mentése fájlba
    public static void saveLog(){
        try {
            DateTimeFormatter tdtf = DateTimeFormatter.ofPattern("yyyy_MM_dd-HH_mm_ss");
            Path out = Paths.get(tdtf.format(now)+".txt");
            Files.write(out, Library.msgq, Charset.defaultCharset());
            Library.msgq = new ArrayList<>();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Variables">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton btn_log;
    private static javax.swing.JButton btn_start;
    private static javax.swing.JButton btn_stop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> list_usr;
    private static javax.swing.JTextArea txt_log;
    private javax.swing.JTextField txt_port;
    // End of variables declaration//GEN-END:variables
    // </editor-fold>
}
