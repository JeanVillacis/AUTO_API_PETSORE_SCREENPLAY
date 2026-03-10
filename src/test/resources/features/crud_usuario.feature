# language: es
Característica: Ciclo de vida CRUD de usuario en la API PetStore

  Antecedentes:
    Dado un usuario con nombre de usuario "testuser_automation" y datos de registro válidos

  Escenario: Ciclo CRUD completo de un usuario
    Cuando el usuario es registrado en el sistema
    Entonces el registro debe ser exitoso

    Cuando los detalles del usuario son recuperados por nombre de usuario
    Entonces la información del usuario debe coincidir con los datos registrados

    Cuando el perfil del usuario es actualizado con nueva información personal
    Entonces la actualización debe ser exitosa

    Cuando los detalles del usuario son recuperados por nombre de usuario nuevamente
    Entonces la información del usuario debe reflejar los datos actualizados

    Cuando el usuario es eliminado del sistema
    Entonces la eliminación debe ser exitosa

    Cuando los detalles del usuario son recuperados por nombre de usuario después de la eliminación
    Entonces el usuario ya no debe existir en el sistema
