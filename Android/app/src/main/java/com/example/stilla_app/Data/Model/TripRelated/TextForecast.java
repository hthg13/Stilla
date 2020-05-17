package com.example.stilla_app.Data.Model.TripRelated;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class TextForecast {

    @Element(name = "channel")
    private TextNextDays textNextDays;

    public TextNextDays getTextNextDays() {
        return textNextDays;
    }
}
