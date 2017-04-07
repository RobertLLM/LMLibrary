package robert.com.baselibrary;

/**
 * Created by LL on 15/8/12.
 */
public class LSEventBusData {


    public LSEventBusData(Object object) {
        super();
        this.object = object;
    }

    public LSEventBusData(String id) {
        this.id = id;
    }

    private String id = "none";
    private Object object;

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
