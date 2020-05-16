package is.hi.lokaverkefni.stilla.stilla_backend.Extras;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false)
public class Forecast {

    @Element(name = "ftime", required = false)
    private String ftime;

    @Element(name = "F", required = false)
    private int F;

    @Element(name = "D", required = false)
    private String D;

    @Element(name = "T", required = false)
    private int T;

    @Element(name = "W", required = false)
    private String W;

    public String getFtime() {
        return ftime;
    }

    public int getF() {
        return F;
    }

    public String getD() {
        return D;
    }

    public int getT() {
        return T;
    }

    public String getW() {
        return W;
    }
}
