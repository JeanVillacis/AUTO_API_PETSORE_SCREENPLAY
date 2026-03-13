# language: es
Característica: Ciclo de vida CRUD de usuario en la API PetStore

  Esquema del escenario: Ciclo CRUD completo de un usuario
    Dado un usuario con nombre de usuario "<username>", nombre "<firstName>", apellido "<lastName>", correo "<email>", contraseña "<password>" y teléfono "<phone>"
    Cuando el usuario es registrado en el sistema
    Entonces el registro debe ser exitoso

    Cuando los detalles del usuario son recuperados por nombre de usuario
    Entonces la información del usuario debe coincidir con los datos registrados

    Cuando el perfil del usuario es actualizado con nombre "<updatedFirstName>" y correo "<updatedEmail>"
    Entonces la actualización debe ser exitosa

    Cuando los detalles del usuario son recuperados por nombre de usuario nuevamente
    Entonces la información del usuario debe reflejar los datos actualizados

    Cuando el usuario es eliminado del sistema
    Entonces la eliminación debe ser exitosa

    Cuando los detalles del usuario son recuperados por nombre de usuario después de la eliminación
    Entonces el usuario ya no debe existir en el sistema

    Ejemplos:
      | username      | firstName | lastName | email                         | password | phone          | updatedFirstName | updatedEmail                    |
      | user_auto_01  | Juan      | Perez    | juan.auto01@automation.com    | Pass123! | +573001234567  | Maria           | updated.auto01@automation.com   |
      | user_auto_02  | Carlos    | Gomez    | carlos.auto02@automation.com  | Pass456! | +573009876543  | Ana             | updated.auto02@automation.com   |
