package com.Xangars;


import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;

public class DoubleTypeAdapter extends TypeAdapter<Number> {
    @Override
    public void write(JsonWriter out, Number value) throws IOException {

        out.value(value);

    }
    @Override
    public Number read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        try {
            String result = in.nextString();
            if ("".equals(result)) {
                return null;
            }

            if(!NumberUtils.isCreatable(result)) {

                return -0.00;
            };

            System.out.println("Number Format " + NumberUtils.isCreatable(result));

            return Double.parseDouble(result)   ;
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }



    }
}
