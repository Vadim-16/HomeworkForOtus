package ca.study.purpose;

public interface MyCacheEngine<K, V> {

    void put(MyCacheElement<K, V> element);

    MyCacheElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
