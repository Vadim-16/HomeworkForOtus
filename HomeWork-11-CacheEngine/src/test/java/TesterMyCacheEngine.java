
import ca.study.purpose.MyCache.MyCacheEngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TesterMyCacheEngine {
    private MyCacheEngineImpl<Long, String> cache;


    @BeforeEach
    public void setUp() {
        cache = new MyCacheEngineImpl<>(5, 0, 0, true);
    }

    @Test
    public void testPutAndGet() {
        cache.put(10L, "ten");
        cache.put(20L, "twenty");
        String value = cache.get(20L);
        assertEquals("twenty", value);
    }

    @Test
    public void testHitCount() {
        cache.put(10L, "ten");
        cache.put(20L, "twenty");
        cache.get(20L);
        cache.get(10L);
        assertEquals(2, cache.getHitCount());
    }

    @Test
    public void testMissCount() {
        cache.put(10L, "ten");
        cache.put(20L, "twenty");
        cache.get(30L);
        cache.get(30L);
        assertEquals(2, cache.getMissCount());
    }

    @Test
    public void testTimer() {
        cache = new MyCacheEngineImpl<>(5, 500, 0, false);
        cache.put(10L, "ten");
        cache.put(20L, "twenty");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertNull(cache.get(10L));
        assertNull(cache.get(20L));
    }

    @Test
    public void testDispose() {
        cache = new MyCacheEngineImpl<>(5, 500, 0, false);
        cache.put(10L, "ten");
        cache.put(20L, "twenty");
        cache.dispose();
        try {
            Thread.sleep(1000); //lifetime = 500ms, elements should be deleted after sleep
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals("ten", cache.get(10L));
        assertEquals("twenty", cache.get(20L));
    }


}

