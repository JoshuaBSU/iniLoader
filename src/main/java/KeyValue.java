public class KeyValue {
    String Key;

    String Value;

    public KeyValue(String key, String value) {
        this.Key = key;
        this.Value = value;
    }

    public String getKey() {
        return this.Key;
    }

    public void setKey(String key) {
        this.Key = key;
    }

    public String getValue() {
        return this.Value;
    }

    public void setValue(String value) {
        this.Value = value;
    }
}
