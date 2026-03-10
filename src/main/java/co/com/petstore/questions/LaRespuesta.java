package co.com.petstore.questions;

import io.restassured.response.ValidatableResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.rest.SerenityRest;

public class LaRespuesta implements Question<ValidatableResponse> {

    @Override
    public ValidatableResponse answeredBy(Actor actor) {
        return SerenityRest.lastResponse().then();
    }

    public static LaRespuesta recibida() {
        return new LaRespuesta();
    }

    public static int codigoDeEstado() {
        return SerenityRest.lastResponse().statusCode();
    }

    public static String valorDelCampo(String nombreCampo) {
        return SerenityRest.lastResponse().jsonPath().getString(nombreCampo);
    }
}
