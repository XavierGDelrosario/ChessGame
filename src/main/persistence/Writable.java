package persistence;

import org.json.JSONObject;

//Represents a class that can be saved
public interface Writable {
    JSONObject toJson();
}
