package cache
const val TAMAÑO_CACHE=5
interface Cache<K,V> {
    operator fun get(key: K): V?
    operator fun set(key: K, value: V):V?
    fun clear()
    fun size(): Int
    fun isEmpty(): Boolean
    fun getOrPut(key: K, defaultValue: () -> V): V
    fun values(): Collection<V>
    fun remove(key: K): V?
    fun keys(): Set<K>
    operator fun contains(key: K): Boolean
}