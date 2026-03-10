package co.com.petstore.stepdefinitions;

import co.com.petstore.models.UserModel;
import co.com.petstore.questions.LaRespuesta;
import co.com.petstore.tasks.ActualizarUsuario;
import co.com.petstore.tasks.CrearUsuario;
import co.com.petstore.tasks.EliminarUsuario;
import co.com.petstore.tasks.ObtenerUsuario;
import io.cucumber.java.Before;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class DefinicionDePasosCrudUsuario {

    private final EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    private String nombreUsuario;
    private UserModel usuarioOriginal;
    private UserModel usuarioActualizado;

    @Before
    public void prepararElEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("un usuario con nombre de usuario {string} y datos de registro válidos")
    public void unUsuarioConNombreDeUsuarioYDatosDeRegistroValidos(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;

        String urlBase = EnvironmentSpecificConfiguration.from(variables).getProperty("restapi.baseurl");
        Actor actor = OnStage.theActorCalled("Usuario");
        actor.whoCan(CallAnApi.at(urlBase));

        usuarioOriginal = new UserModel(
            1001,
            nombreUsuario,
            "Juan",
            "Pérez",
            "juan.perez@automatizacion.com",
            "Clave123!",
            "+573001234567",
            1
        );
    }

    @Cuando("el usuario es registrado en el sistema")
    public void elUsuarioEsRegistradoEnElSistema() {
        theActorInTheSpotlight().attemptsTo(
            CrearUsuario.conDatos(usuarioOriginal)
        );
    }

    @Entonces("el registro debe ser exitoso")
    public void elRegistroDebeSerExitoso() {
        assertThat(LaRespuesta.codigoDeEstado(), equalTo(200));
    }

    @Cuando("los detalles del usuario son recuperados por nombre de usuario")
    public void losDetallesDelUsuarioSonRecuperadosPorNombreDeUsuario() {
        theActorInTheSpotlight().attemptsTo(
            ObtenerUsuario.conNombreDeUsuario(nombreUsuario)
        );
    }

    @Entonces("la información del usuario debe coincidir con los datos registrados")
    public void laInformacionDelUsuarioDebeCoincidir() {
        assertThat(LaRespuesta.codigoDeEstado(), equalTo(200));
        assertThat(LaRespuesta.valorDelCampo("username"), equalTo(usuarioOriginal.getUsername()));
        assertThat(LaRespuesta.valorDelCampo("firstName"), equalTo(usuarioOriginal.getFirstName()));
        assertThat(LaRespuesta.valorDelCampo("lastName"), equalTo(usuarioOriginal.getLastName()));
        assertThat(LaRespuesta.valorDelCampo("email"), equalTo(usuarioOriginal.getEmail()));
    }

    @Cuando("el perfil del usuario es actualizado con nueva información personal")
    public void elPerfilDelUsuarioEsActualizadoConNuevaInformacionPersonal() {
        usuarioActualizado = new UserModel(
            usuarioOriginal.getId(),
            usuarioOriginal.getUsername(),
            "María",
            usuarioOriginal.getLastName(),
            "maria.actualizada@automatizacion.com",
            usuarioOriginal.getPassword(),
            usuarioOriginal.getPhone(),
            usuarioOriginal.getUserStatus()
        );

        theActorInTheSpotlight().attemptsTo(
            ActualizarUsuario.conDatos(nombreUsuario, usuarioActualizado)
        );
    }

    @Entonces("la actualización debe ser exitosa")
    public void laActualizacionDebeSerExitosa() {
        assertThat(LaRespuesta.codigoDeEstado(), equalTo(200));
    }

    @Cuando("los detalles del usuario son recuperados por nombre de usuario nuevamente")
    public void losDetallesDelUsuarioSonRecuperadosPorNombreDeUsuarioNuevamente() {
        theActorInTheSpotlight().attemptsTo(
            ObtenerUsuario.conNombreDeUsuario(nombreUsuario)
        );
    }

    @Entonces("la información del usuario debe reflejar los datos actualizados")
    public void laInformacionDebeReflejarLosDatosActualizados() {
        assertThat(LaRespuesta.codigoDeEstado(), equalTo(200));
        assertThat(LaRespuesta.valorDelCampo("firstName"), equalTo(usuarioActualizado.getFirstName()));
        assertThat(LaRespuesta.valorDelCampo("email"), equalTo(usuarioActualizado.getEmail()));
        assertThat(LaRespuesta.valorDelCampo("firstName"), not(equalTo(usuarioOriginal.getFirstName())));
        assertThat(LaRespuesta.valorDelCampo("email"), not(equalTo(usuarioOriginal.getEmail())));
    }

    @Cuando("el usuario es eliminado del sistema")
    public void elUsuarioEsEliminadoDelSistema() {
        theActorInTheSpotlight().attemptsTo(
            EliminarUsuario.conNombreDeUsuario(nombreUsuario)
        );
    }

    @Entonces("la eliminación debe ser exitosa")
    public void laEliminacionDebeSerExitosa() {
        assertThat(LaRespuesta.codigoDeEstado(), equalTo(200));
    }

    @Cuando("los detalles del usuario son recuperados por nombre de usuario después de la eliminación")
    public void losDetallesDelUsuarioSonRecuperadosDespuesDeLaEliminacion() {
        theActorInTheSpotlight().attemptsTo(
            ObtenerUsuario.conNombreDeUsuario(nombreUsuario)
        );
    }

    @Entonces("el usuario ya no debe existir en el sistema")
    public void elUsuarioYaNoDebeExistirEnElSistema() {
        assertThat(LaRespuesta.codigoDeEstado(), equalTo(404));
    }
}
