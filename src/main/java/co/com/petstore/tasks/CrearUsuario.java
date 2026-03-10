package co.com.petstore.tasks;

import co.com.petstore.models.UserModel;
import co.com.petstore.endpoints.EndpointsUsuario;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.annotations.Step;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class CrearUsuario implements Task {

    private final UserModel usuario;

    public CrearUsuario(UserModel usuario) {
        this.usuario = usuario;
    }

    public static CrearUsuario conDatos(UserModel usuario) {
        return instrumented(CrearUsuario.class, usuario);
    }

    @Override
    @Step("{0} crea un nuevo usuario")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Post.to(EndpointsUsuario.CREAR_USUARIO)
                .with(request -> request
                    .header("Content-Type", "application/json")
                    .body(usuario))
        );
    }
}
