import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MercenaryINI {
    String directory;

    private List<KeyValue> tuple = new ArrayList<>();

    public MercenaryINI(String directory) {
        this.directory = directory;
        ParseINIFiles();
    }

    public List<KeyValue> getINIdata() {
        return this.tuple;
    }

    public String getDirectory() {
        return this.directory;
    }

    public void setINIdata(List<KeyValue> tuple) {
        this.tuple = tuple;
    }

    public void ParseINIFiles() {
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(this.directory), "EUC-KR"));
            String availalbe;
            while ((availalbe = br.readLine()) != null) {
                if (availalbe.contains(";"));
                i++;
                if (availalbe.startsWith(";")) {
                    this.tuple.add(new KeyValue(availalbe, null));
                    continue;
                }
                if (availalbe.contains("[")) {
                    this.tuple.add(new KeyValue(availalbe, null));
                    continue;
                }
                if (availalbe.contains("=")) {
                    String[] parts = availalbe.split("=");
                    if (parts.length == 2) {
                        String part1 = parts[0];
                        String part2 = parts[1];
                        this.tuple.add(new KeyValue(part1 + "=", part2));
                        continue;
                    }
                    if (parts.length == 1) {
                        String part1 = parts[0];
                        this.tuple.add(new KeyValue(part1 + "=", null));
                        continue;
                    }
                    System.out.println("Could not split");
                    this.tuple.add(new KeyValue(availalbe, null));
                    continue;
                }
                this.tuple.add(new KeyValue(availalbe, null));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
