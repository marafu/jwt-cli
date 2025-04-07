package assis.matheus.artigo.jwt.implementations;

import com.google.gson.Gson;
import assis.matheus.artigo.jwt.interfaces.JSON;

public class GsonJSON implements JSON {
    private final Gson gson = new Gson();

    @Override
    public String create(Object value) {
        return gson.toJson(value);
    }
}
