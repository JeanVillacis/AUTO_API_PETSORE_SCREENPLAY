package co.com.petstore.tasks;

import co.com.petstore.endpoints.EndpointsUsuario;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class EliminarUsuario implements Task {

    private final String nombreUsuario;

    public EliminarUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public static EliminarUsuario conNombreDeUsuario(String nombreUsuario) {
        return instrumented(EliminarUsuario.class, nombreUsuario);
    }

    @Override
    @Step("{0} elimina el usuario {1}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Delete.from(EndpointsUsuario.ELIMINAR_USUARIO)
                .with(request -> request.pathParam("username", nombreUsuario))
        );
    }
}
