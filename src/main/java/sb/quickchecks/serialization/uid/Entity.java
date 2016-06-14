package sb.quickchecks.serialization.uid;

import java.io.Serializable;

/**
 * Created by slwk on 06.06.16.
 */
public class Entity implements Serializable {

    private static final long serialVersionUID = -600452755178723601L;
    private String name;
    private String data;
    private long ID;



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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
}
