package persistence;

import org.json.JSONObject;

// Note: code mostly based off of Writable from JSONSerializationDemo
//Represents a class that can be saved
public interface Writable {
    JSONObject toJson();
}
