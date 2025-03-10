package repository

import mappers.toLocalDate
import models.Jugadores
import models.Persona
import models.Posicion
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import java.time.LocalDate

class CrudPersonasImplementationTest {
 private lateinit var personaRepository: CrudPersonasImplementation
 @BeforeEach
 fun setUp() {
  personaRepository=CrudPersonasImplementation()
 }

@Test
 fun obtenerTodo() {
  val listapersona=personaRepository.getAll()
 assertTrue(listapersona.isEmpty(),"tiene que estar vacia")
 }

@Test
 fun guardaryobtenerPorId() {
 val persona= Jugadores(1,"mateo","martin",
  LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)
 val persona2= Jugadores(1,"felipe","destructor",LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)

 val personaGuardada= personaRepository.save(persona)
 val personaGuardada2= personaRepository.save(persona2)

 val personaObtenido= personaRepository.getById(2L)

  assertNotNull(personaObtenido,"no deberia ser nulo")
  assertEquals(personaGuardada2,personaObtenido,"deberian ser iguales")
  assertNotEquals(personaGuardada.id,personaGuardada2.id,"deberian tener ids distintos debido a que se asignan solas")
 }

 @Test
 fun idIncorrecta(){
  val persona= Jugadores(1,"mateo","martin",
   LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)
  val personaGuardada= personaRepository.save(persona)
  val personaObtenido= personaRepository.getById(2L)
  assertNull(personaObtenido,"deberia ser nulo")
 }

@Test
 fun update() {
 val persona= Jugadores(1,"mateo","martin",
  LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)
 val persona2= Jugadores(1,"mateo2","martin",
  LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)
 val personaGuardada= personaRepository.save(persona)
 val personaActualizada= personaRepository.update(persona2,personaGuardada.id)
 assertNotNull(personaActualizada,"no deberia ser nulo es una persona con una id valida")
 assertNotEquals(personaGuardada.nombre,personaActualizada?.nombre!!,"nombre actualizado")
 }
 @Test
 fun updateIncorrecto(){
  val persona= Jugadores(1,"mateo","martin",
   LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)
  val persona2= Jugadores(2,"mateo2","martin",
   LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"),12.23,"España",Posicion.PORTERO,3,1.86,56.00,7,3)
  val personaGuardada= personaRepository.save(persona)
  val personaActualizada= personaRepository.update(persona2,persona2.id)
  assertTrue(personaActualizada==null,"devuelve nulo cuando tiene una id invalida$personaActualizada")
  assertTrue(personaRepository.getAll().maxOfOrNull { it.id }==1L,"solo tiene una persona guardada")
 }

@Test
 fun delete() {
 val persona = Jugadores(
  1, "mateo", "martin",
  LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"), 12.23, "España", Posicion.PORTERO, 3, 1.86, 56.00, 7, 3
 )
 val personaGuardada = personaRepository.save(persona)
 val personaEliminada = personaRepository.delete(personaGuardada.id)
 assertTrue(personaRepository.getAll().isEmpty(), "no deberia contener personas")
 assertEquals(personaGuardada, personaEliminada, "deberian ser iguales")
}
 @Test
 fun deleteIncorrecto() {
  val persona = Jugadores(
   1, "mateo", "martin",
   LocalDate.parse("2005-02-01"), LocalDate.parse("2022-02-01"), 12.23, "España", Posicion.PORTERO, 3, 1.86, 56.00, 7, 3
  )
  val personaGuardada = personaRepository.save(persona)
  val personaEliminada = personaRepository.delete(2L)
  assertTrue(personaRepository.getAll().isNotEmpty(), "deberia contener personas")
  assertNotEquals(personaGuardada, personaEliminada, "deberian ser iguales")
 }
}