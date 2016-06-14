package sb.quickchecks.serialization.uid;

import java.io.Serializable;

/**
 * Created by slwk on 06.06.16.
 */
public class Entity implements Serializable {

    private String name;
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
