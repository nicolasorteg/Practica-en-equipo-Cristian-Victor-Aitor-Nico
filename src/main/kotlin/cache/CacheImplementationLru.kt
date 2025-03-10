package cache

import org.lighthousegames.logging.logging
import java.util.concurrent.ConcurrentHashMap

/**
 * Data clase de datos que incluye los valores necesarios para la caché
 * @param value es el valor original
 * @param timestamp sirve capa comprobar la fecha de acceso
 */
data class CacheEntrada<V>(val value: V, var timestamp:Long)

/**
 * Clase que implementa la CacheLru
 * @param capacidad dictamina la capacidad maxima de la caché
 */
class CacheImplementationLru<K:Any,V>(private val capacidad:Int= TAMAÑO_CACHE):Cache<K,V> {
    private var cache = ConcurrentHashMap<K,CacheEntrada<V>>(capacidad)
    private val logger = logging()

    /**
     * Obtiene el valor en función de la llave y actualiza la fecha de acceso
     * @param key llave a buscar
     * @return valor asociado a esa llave
     */
    override fun get(key: K): V? {
        logger.debug { "obteniendo valor de la posición $key" }
        return cache[key]?.also {
            it.timestamp=System.nanoTime()
        }?.value
    }

    /**
     * limpia la cache
     */
    override fun clear() {
        logger.debug { "limpiando cache" }
        cache.clear()
    }

    /**
     * Devuelve el tamaño de la caché
     * @return tamaño
     */
    override fun size(): Int {
        return cache.size
    }

    /**
     * comprueba si esta vació
     * @return true si esta vació false si tiene contenido
     */
    override fun isEmpty(): Boolean {
        logger.info { "comprobando si esta vació" }
        return cache.isEmpty()
    }

    /**
     * devuelve los valores del cache en forma de colección
     * @return la colección a devolver
     */
    override fun values(): Collection<V> {
        logger.debug { "devolviendo valores de cache" }
        return cache.values.map { it.value }
    }

    /**
     * elimina un valor de la cache
     * @return el valor eliminado si existe o null si no existe
     */
    override fun remove(key: K): V? {
        logger.debug { "eliminando valor de $key" }
        return cache.remove(key)?.value
    }

    /**
     * Ordena las llaves del caché
     * @return
     */
    override fun keys(): Set<K> {
        return cache.keys
    }

    /**
     * comprueba si en la cache hay una llave en concreto
     * @param key la llave a buscar
     * @return si esta la llave devuelve true si no está devuelve false
     */
    override fun contains(key: K): Boolean {
        logger.info { "comprobando si contiene la llave $key" }
        return cache.containsKey(key)
    }

    /**
     * obtiene un valor en base a una llave o genera uno si no tiene
     * @param key llave a buscar
     * @param defaultValue genera un valor por defecto llamando a la función set y usando key y a sí mismo
     * @return devuelve la persona en el get o la genera en el set
     */
    override fun getOrPut(key: K, defaultValue: () -> V): V {
        logger.debug{"consiguiendo una llave en vase a $key o asignando una por defecto"}
        return get(key) ?: defaultValue().also {set(key, it)}
    }

    /**
     * Genera un nuevo valor en la caché
     * @param key llave del valor
     * @param value valor a guarda
     * @return devuelve el valor guardado
     */
    override fun set(key: K, value: V): V? {
        logger.info { "guardando valor con la llave $key" }
        if(cache.size>capacidad){
            eliminarMenosUsado()
        }
        val valor = cache.put(key,CacheEntrada(value,System.nanoTime()))
        return valor?.value
    }

    /**
     * Elimina el valor que lleva más tiempo sin usarse
     */
    private fun eliminarMenosUsado() {
        logger.info {"eliminando menos usado"}
      cache.minByOrNull{ it.value.timestamp }?.key.let {
          cache.remove(it)
      }
    }
}