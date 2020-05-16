package com.example.stilla_app.Data.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class TextItem {

    @Element(name = "description", data = true)
    private String description;

    // todo: ef þetta er notað þá þarf að laga formatið það lítur svona út:
    /**
     * Á þriðjudag:<br/> Vestlæg átt, 8-15 m/s. Skýjað með köflum og úrkomulítið V-til, en léttskýjað eystra. Hiti 6 til 15 stig að deginum, hlýjast á SA-landi. <br/><br/>Á miðvikudag:<br/> Hæg suðvestlæg eða breytileg átt og léttskýjað, en  skýjað að mestu S- og V-lands. Fremur hlýtt í veðri. <br/><br/>Á fimmtudag:<br/> Breytileg átt og skýjað með köflum. Heldur kólnandi. <br/><br/>Á föstudag:<br/> Norðlæg átt, dálítil él og vægt frost norðanlands, en léttskýjað sunnan heiða og hiti að 6 stigum yfir daginn. <br/><br/>Á laugardag:<br/> Útlit fyrir breytilega átt, úrkomulítið og svalt veður.
     *
     * Gildir frá: 05 May 2020 12:00:00 GMT
     * Gildir til: 10 May 2020 12:00:00 GMT
     * @return
     */
    public String getDescription() {
        return description;
    }
}
