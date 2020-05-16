package is.hi.lokaverkefni.stilla.stilla_backend.Extras;

import is.hi.lokaverkefni.stilla.stilla_backend.Extras.Forecast;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "station", strict = false)
public class Station {

    @Attribute(name = "id")
    private int id;

    @Attribute(name = "valid")
    private boolean valid;

    @Element(name = "name")
    private String name;

    @Element(name = "atime")
    private String atime;

    @Element(name = "err", required = false)
    private String err;

    @Element(name = "link", data = true)
    private String link;

    @ElementList(inline = true)
    private List<Forecast> forecast;

    public int getId() {
        return id;
    }

    public boolean isValid() {
        return valid;
    }

    public String getName() {
        return name;
    }

    public String getAtime() {
        return atime;
    }

    public String getErr() {
        return err;
    }

    public String getLink() {
        return link;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }
}
