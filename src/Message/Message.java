package Message;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Serializable {

    String text, source;
    Date creation_date;

    public Message(String msg, String s, Date cd) {
        text = msg;
        source = s;
        creation_date = cd;
    }

    public String getMessage() {
        return text;
    }

    public String getSource() {
        return source;
    }

    public Date getCreationDate() {
        return creation_date;
    }

    @Override
    public String toString() {
        return (new SimpleDateFormat("yyyy-MM-dd:hh.mm").format(creation_date)) + " " + source + ": " + text;
    }
}

