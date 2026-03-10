package co.com.petstore.tasks;

import co.com.petstore.models.UserModel;
import co.com.petstore.endpoints.EndpointsUsuario;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Put;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ActualizarUsuario implements Task {

    private final String nombreUsuario;
    private final UserModel usuarioActualizado;

    public ActualizarUsuario(String nombreUsuario, UserModel usuarioActualizado) {
        this.nombreUsuario = nombreUsuario;
        this.usuarioActualizado = usuarioActualizado;
    }

    public static ActualizarUsuario conDatos(String nombreUsuario, UserModel usuarioActualizado) {
        return instrumented(ActualizarUsuario.class, nombreUsuario, usuarioActualizado);
    }

    @Override
    @Step("{0} actualiza el usuario {1}")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Put.to(EndpointsUsuario.ACTUALIZAR_USUARIO)
                .with(request -> request
                    .pathParam("username", nombreUsuario)
                    .header("Content-Type", "application/json")
                    .body(usuarioActualizado))
        );
    }
}
