package co.com.petstore.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.rest.SerenityRest;

public class ResponseField implements Question<String> {

    private final String fieldName;

    public ResponseField(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String answeredBy(Actor actor) {
        return SerenityRest.lastResponse().jsonPath().getString(fieldName);
    }

    public static ResponseField named(String fieldName) {
        return new ResponseField(fieldName);
    }
}
