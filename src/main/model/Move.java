package model;

import org.json.JSONObject;
import persistence.Writable;

public class Move implements Writable {
    private String fromSquare;
    private String toSquare;

    public Move(String fromSquare, String toSquare) {
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
    }

    public String getFromSquare() {
        return fromSquare;
    }

    public String getToSquare() {
        return toSquare;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fromSquare", fromSquare);
        json.put("toSquare", toSquare);
        return json;
    }
}
