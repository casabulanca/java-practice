package ruiliu2.practice.java.async.lock.atomicIntger.lockPool;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 锁池
 * Created by ruiliu2@iflytek.com on 16/8/11.
 */
@Component
public class LockPool {
    private Cache<String, AtomicInteger> lockPool = CacheBuilder.newBuilder().build();

    public AtomicInteger get(String id) throws ExecutionException {
        return lockPool.get(id, new Callable<AtomicInteger>() {
            public AtomicInteger call() throws Exception {
                return new AtomicInteger(1);
            }
        });
    }

}