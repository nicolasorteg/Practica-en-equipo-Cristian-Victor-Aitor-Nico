package repository

/**
 * Dictamina las funciones que deben de tener todas las clases y repositorios que hereden de ella
 */
interface RepositoryCRUD <T,ID>{
    fun getAll(): List<T>
    fun getById(id:ID): T?
    fun update(objeto:T,id: ID):T?
    fun delete(id: ID):T?
    fun save(objeto: T):T
}