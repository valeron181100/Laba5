package Finances;

import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.Serializable;

public interface Money extends Serializable {
    int getAmount();
    JSONObject getJson();
}
