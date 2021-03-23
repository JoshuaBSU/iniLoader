import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class LostSagaINI {
    private JPanel mainWindow;

    private JButton selectFolder;

    private JButton saveChanges;

    private JTable iniLoadedFields;

    private JComboBox heroSelect;

    private JComboBox heroINISelect;

    private JButton button1;

    JFileChooser chooser;

    File mercDir;

    MercenaryINI currentINI;

    List<Mercenary> Mercs;

    Mercenary curMerc;

    public LostSagaINI() {
        $$$setupUI$$$();
        this.Mercs = new ArrayList<>();
        this.iniLoadedFields.setSize(10, 10);
        final DefaultTableModel model = (DefaultTableModel)this.iniLoadedFields.getModel();
        model.addColumn("Attribute");
        model.addColumn("Value");
        this.selectFolder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                LostSagaINI.this.chooser = new JFileChooser();
                LostSagaINI.this.chooser.setCurrentDirectory(new File("."));
                LostSagaINI.this.chooser.setFileSelectionMode(1);
                if (LostSagaINI.this.chooser.showOpenDialog(LostSagaINI.this.selectFolder) == 0) {
                    System.out.println("getCurrentDirectory(): " + LostSagaINI.this.chooser
                            .getCurrentDirectory());
                    System.out.println("getSelectedFile() : " + LostSagaINI.this.chooser
                            .getSelectedFile());
                    LostSagaINI.this.mercDir = LostSagaINI.this.chooser.getSelectedFile();
                    System.out.println(LostSagaINI.this.mercDir);
                    File folder = LostSagaINI.this.mercDir;
                    String[] files = folder.list();
                    File[] files1 = folder.listFiles();
                    assert files1 != null;
                    for (File file : files1) {
                        if (file.isDirectory()) {
                            Mercenary tempMercenary = new Mercenary(file.getName(), "tempName", "" + LostSagaINI.this.mercDir + "\\" + LostSagaINI.this.mercDir);
                            LostSagaINI.this.Mercs.add(tempMercenary);
                        }
                    }
                    for (Mercenary mercenary : LostSagaINI.this.Mercs)
                        LostSagaINI.this.heroSelect.addItem(mercenary.getId());
                } else {
                    System.out.println("No Selection ");
                }
            }
        });
        this.heroSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                LostSagaINI.this.heroINISelect.removeAllItems();
                for (Mercenary mercenary : LostSagaINI.this.Mercs) {
                    if (LostSagaINI.this.heroSelect.getSelectedItem() == mercenary.getId())
                        for (MercenaryINI mercINI : mercenary.getINIList()) {
                            LostSagaINI.this.curMerc = mercenary;
                            LostSagaINI.this.heroINISelect.addItem(mercINI.getDirectory());
                        }
                }
            }
        });
        this.heroINISelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                for (MercenaryINI cur : LostSagaINI.this.curMerc.getINIList()) {
                    if (cur.getDirectory() == LostSagaINI.this.heroINISelect.getSelectedItem()) {
                        LostSagaINI.this.currentINI = cur;
                        System.out.println(cur.toString());
                        System.out.println(cur.getDirectory());
                    }
                }
                model.setRowCount(0);
                for (KeyValue keyValueList : LostSagaINI.this.currentINI.getINIdata()) {
                    model.addRow(new Object[] { keyValueList.getKey(), keyValueList.getValue() });
                }
            }
        });
        this.saveChanges.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                if (LostSagaINI.this.currentINI != null) {
                    File iniToSave = new File(LostSagaINI.this.currentINI.getDirectory());
                    JOptionPane.showMessageDialog(LostSagaINI.this.saveChanges, "Saving to:" + LostSagaINI.this.currentINI.getDirectory());
                    try {
                        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(iniToSave), "EUC-KR"));
                        int row = model.getRowCount();
                        int col = model.getColumnCount();
                        for (int i = 0; i < row; i++) {
                            String builderHelper = (String)model.getValueAt(i, 0);
                            String builderHelper2 = (String)model.getValueAt(i, 1);
                            if (builderHelper == null) {
                                out.write("\n");
                            } else if (builderHelper2 == null) {
                                out.write(builderHelper);
                            } else {
                                out.write(builderHelper + builderHelper);
                            }
                            out.newLine();
                        }
                        out.close();
                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(LostSagaINI.this.saveChanges, "Error Saving");
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(LostSagaINI.this.saveChanges, "Nothing was selected to save");
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("LostSagaINI");
        frame.setContentPane((new LostSagaINI()).mainWindow);
        frame.setDefaultCloseOperation(3);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException|IllegalAccessException|InstantiationException|javax.swing.UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.updateComponentTreeUI(frame);
        frame.setBounds(10, 10, 10, 10);
        frame.pack();
        frame.setVisible(true);
    }
}
