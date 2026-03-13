package co.com.petstore.stepdefinitions;

import co.com.petstore.models.UserModel;
import co.com.petstore.questions.ResponseCode;
import co.com.petstore.questions.ResponseField;
import co.com.petstore.tasks.ActualizarUsuario;
import co.com.petstore.tasks.CrearUsuario;
import co.com.petstore.tasks.EliminarUsuario;
import co.com.petstore.tasks.ObtenerUsuario;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
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

public class UserCrudStepDefinitions {

    private final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
    private UserModel originalUser;
    private UserModel updatedUser;

    @Before
    public void setupScenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("un usuario con nombre de usuario {string}, nombre {string}, apellido {string}, correo {string}, contraseña {string} y teléfono {string}")
    public void aUserWithData(String username, String firstName, String lastName, String email, String password, String phone) {
        String baseUrl = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("restapi.baseurl");
        Actor actor = OnStage.theActorCalled("User");
        actor.whoCan(CallAnApi.at(baseUrl));

        originalUser = new UserModel(
            Math.abs(username.hashCode()),
            username,
            firstName,
            lastName,
            email,
            password,
            phone,
            1
        );
    }

    @Cuando("el usuario es registrado en el sistema")
    public void theUserIsRegisteredInTheSystem() {
        theActorInTheSpotlight().attemptsTo(
            CrearUsuario.conDatos(originalUser)
        );
    }

    @Entonces("el registro debe ser exitoso")
    public void theRegistrationShouldBeSuccessful() {
        assertThat(theActorInTheSpotlight().asksFor(ResponseCode.get()), equalTo(200));
    }

    @Cuando("los detalles del usuario son recuperados por nombre de usuario")
    public void theUserDetailsAreRetrievedByUsername() {
        theActorInTheSpotlight().attemptsTo(
            ObtenerUsuario.conNombreDeUsuario(originalUser.getUsername())
        );
    }

    @Entonces("la información del usuario debe coincidir con los datos registrados")
    public void theUserInformationShouldMatchRegisteredData() {
        assertThat(theActorInTheSpotlight().asksFor(ResponseCode.get()), equalTo(200));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("username")), equalTo(originalUser.getUsername()));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("firstName")), equalTo(originalUser.getFirstName()));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("lastName")), equalTo(originalUser.getLastName()));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("email")), equalTo(originalUser.getEmail()));
    }

    @Cuando("el perfil del usuario es actualizado con nombre {string} y correo {string}")
    public void theUserProfileIsUpdatedWithNameAndEmail(String updatedFirstName, String updatedEmail) {
        updatedUser = new UserModel(
            originalUser.getId(),
            originalUser.getUsername(),
            updatedFirstName,
            originalUser.getLastName(),
            updatedEmail,
            originalUser.getPassword(),
            originalUser.getPhone(),
            originalUser.getUserStatus()
        );

        theActorInTheSpotlight().attemptsTo(
            ActualizarUsuario.conDatos(originalUser.getUsername(), updatedUser)
        );
    }

    @Entonces("la actualización debe ser exitosa")
    public void theUpdateShouldBeSuccessful() {
        assertThat(theActorInTheSpotlight().asksFor(ResponseCode.get()), equalTo(200));
    }

    @Cuando("los detalles del usuario son recuperados por nombre de usuario nuevamente")
    public void theUserDetailsAreRetrievedAgain() {
        theActorInTheSpotlight().attemptsTo(
            ObtenerUsuario.conNombreDeUsuario(originalUser.getUsername())
        );
    }

    @Entonces("la información del usuario debe reflejar los datos actualizados")
    public void theUserInformationShouldReflectUpdatedData() {
        assertThat(theActorInTheSpotlight().asksFor(ResponseCode.get()), equalTo(200));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("firstName")), equalTo(updatedUser.getFirstName()));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("email")), equalTo(updatedUser.getEmail()));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("firstName")), not(equalTo(originalUser.getFirstName())));
        assertThat(theActorInTheSpotlight().asksFor(ResponseField.named("email")), not(equalTo(originalUser.getEmail())));
    }

    @Cuando("el usuario es eliminado del sistema")
    public void theUserIsDeletedFromTheSystem() {
        theActorInTheSpotlight().attemptsTo(
            EliminarUsuario.conNombreDeUsuario(originalUser.getUsername())
        );
    }

    @Entonces("la eliminación debe ser exitosa")
    public void theDeletionShouldBeSuccessful() {
        assertThat(theActorInTheSpotlight().asksFor(ResponseCode.get()), equalTo(200));
    }

    @Cuando("los detalles del usuario son recuperados por nombre de usuario después de la eliminación")
    public void theUserDetailsAreRetrievedAfterDeletion() {
        theActorInTheSpotlight().attemptsTo(
            ObtenerUsuario.conNombreDeUsuario(originalUser.getUsername())
        );
    }

    @Entonces("el usuario ya no debe existir en el sistema")
    public void theUserShouldNoLongerExistInTheSystem() {
        assertThat(theActorInTheSpotlight().asksFor(ResponseCode.get()), equalTo(404));
    }
}
