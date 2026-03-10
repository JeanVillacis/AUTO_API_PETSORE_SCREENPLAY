package co.com.petstore.tasks;

import co.com.petstore.endpoints.EndpointsUsuario;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ObtenerUsuario implements Task {

    private final String nombreUsuario;

    public ObtenerUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public static ObtenerUsuario conNombreDeUsuario(String nombreUsuario) {
        return instrumented(ObtenerUsuario.class, nombreUsuario);
    }

    @Override
    @Step("{0} obtiene los detalles del usuario {1}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Get.resource(EndpointsUsuario.OBTENER_USUARIO)
                .with(request -> request.pathParam("username", nombreUsuario))
        );
    }
}
