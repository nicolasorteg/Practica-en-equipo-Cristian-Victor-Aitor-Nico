package practicaenequipocristianvictoraitornico.players.repository

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

import org.mockito.kotlin.whenever
import practicaenequipocristianvictoraitornico.players.dao.PersonaDao
import practicaenequipocristianvictoraitornico.players.dao.PersonaEntity
import practicaenequipocristianvictoraitornico.players.mappers.PersonaMapper
import practicaenequipocristianvictoraitornico.players.models.*
import practicaenequipocristianvictoraitornico.users.service.UsersService
import java.time.LocalDate


@ExtendWith(MockitoExtension::class)
 class PersonasRepositoryImplementationTest {
  @Mock
  private lateinit var dao: PersonaDao
  @Mock
  private lateinit var mapper: PersonaMapper
  @InjectMocks
  private lateinit var repository: PersonasRepositoryImplementation

  private val persona= Jugadores(
      id = 1,
      nombre = "Jugadora",
      apellidos = "hola",
      fechaNacimiento = LocalDate.parse("2020-01-01"),
      fechaIncorporacion = LocalDate.parse("2020-01-02"),
      salario = 3000.0,
      pais = "españa",
      posicion = Posicion.DEFENSA,
      dorsal = 12,
      altura = 100.0,
      peso = 100.0,
      goles = 10,
      partidosJugados = 10,
      imagen = TODO()
  )
  private val persona2 = Entrenadores(
   id = 2,
   nombre = "Entrenadora",
   apellidos = "hola",
   fechaNacimiento = LocalDate.parse("2020-01-01"),
   fechaIncorporacion = LocalDate.parse("2020-01-02"),
   salario = 3000.0,
   pais = "españa",
   especialidad = Especialidad.ENTRENADOR_PORTEROS
  )
  private val persona3 = PersonaEntity(
      id = 2,
      tipo = "Entrenadores",
      nombre = "Entrenadora",
      apellidos = "hola",
      fechaNacimiento = LocalDate.parse("2020-01-01"),
      fechaIncorporacion = LocalDate.parse("2020-01-02"),
      salario = 3000.0,
      pais = "españa",
      especialidad = "ENTRENADOR_PORTEROS",
      posicion = TODO(),
      dorsal = TODO(),
      altura = TODO(),
      peso = TODO(),
      goles = TODO(),
      partidosJugados = TODO(),
      imagen = TODO()
  )
  private val persona4 = PersonaEntity(
      id = 3,
      tipo = "Jugadores",
      nombre = "Entrenadora",
      apellidos = "hola",
      fechaNacimiento = LocalDate.parse("2020-01-01"),
      fechaIncorporacion = LocalDate.parse("2020-01-02"),
      salario = 3000.0,
      pais = "españa",
      posicion = "DEFENSA",
      dorsal = 12,
      altura = 100.0,
      peso = 100.0,
      goles = 10,
      partidosJugados = 10,
      especialidad = TODO(),
      imagen = TODO()
  )

@Test
 fun getAll() {
  whenever(dao.getAll()) doReturn listOf(persona3, persona4)
  whenever(mapper.toDatabaseModel(persona3)) doReturn persona2
  whenever(mapper.toDatabaseModel(persona4)) doReturn persona

  val result=repository.getAll()
  assertEquals(result.size,2,"debe contener dos personas")
  assertNotNull(result,"no deberia ser nulo")
  assertTrue(result.isNotEmpty())

 verify(dao,times(1)).getAll()
 verify(mapper, times(1)).toDatabaseModel(persona3)
 verify(mapper, times(1)).toDatabaseModel(persona4)
 }

@Test
 fun getById() {}

@Test
 fun update() {}

@Test
 fun delete() {}

@Test
 fun save() {}
}