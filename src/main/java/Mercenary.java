import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mercenary {
    private String id;

    private String mercName;

    private String directory;

    private List<MercenaryINI> MercINI = new ArrayList<>();

    public String getMercName() {
        return this.mercName;
    }

    public void setMercName(String mercName) {
        this.mercName = mercName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MercenaryINI> getINIList() {
        return this.MercINI;
    }

    public void setINIList(List<MercenaryINI> MercINI) {
        this.MercINI = MercINI;
    }

    public Mercenary(String id, String mercName, String directory) {
        this.id = id;
        this.mercName = mercName;
        this.directory = directory;
        fileParse();
    }

    public void fileParse() {
        File folder = new File(this.directory + "\\");
        String[] files = folder.list();
        if (files != null) {
            for (String file : files) {
                if (file.toLowerCase().contains(".ini")) {
                    MercenaryINI iniParse;
                    this.MercINI.add(iniParse = new MercenaryINI(this.directory + "\\" + this.directory));
                }
            }
        } else {
            System.out.println("No files in this directory: " + this.directory + this.id);
        }
    }
}
