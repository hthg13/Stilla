package com.example.stilla_app.Data.Model.TripRelated;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "channel", strict = false)
public class TextNextDays {

    @Element(name = "item",required = false)
    private TextItem item;

    public TextItem getDescription() {
        return item;
    }
}
