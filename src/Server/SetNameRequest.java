package Server;

import java.io.Serializable;

public class SetNameRequest implements Request {

    private String name;

    public SetNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
