package ruiliu2.practice.jedisdemo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ruiliu2.practice.jedisdemo.rest.Lattice;
import skynet.ant.rpc.core.RpcStatus;
import skynet.ant.rpc.ice.IceEndpoint;
import skynet.ant.rpc.ice.IceProxyIdentity;
import skynet.ant.rpc.route.RouteParam;
import skynet.ant.rpc.route.RouteReqProxy;
import skynet.ant.rpc.session.SessionReqProxy;
import skynet.ant.rpc.session.SessionRespone;
import skynet.ant.rpc.session.SessionResult;
import skynet.ant.rpc.session.SessionStatus;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by ruiliu2 on 2017/4/25.
 */
@Service
public class TransService {

    private String key;
    private SessionReqProxy proxy;
    private String sessionId;

    @Autowired
    private JedisPool jedisPool;

    private Jedis jedis;


    private SessionRespone sessionRespone = new SessionRespone() {

        private static final long serialVersionUID = -4136355380420070070L;
        private SessionStatus status = new SessionStatus();

        @Override
        public SessionStatus onCallback(SessionResult sessionResult) {
            String json = new String(sessionResult.result, Charset.forName("UTF-8"));
            System.out.println(json);
            Lattice lattice = JSONObject.parseObject(json, Lattice.class);
            if ("sentence".equals(lattice.getMsgType())) {
                jedis.rpush(key, json);
            }
            return status;
        }
    };

    public String start() {
        this.jedis = jedisPool.getResource();
        this.key = UUID.randomUUID().toString();
        String routeName = "skynet.rpc.route.service";
        String sessionName = "hisee.session.service";

        IceEndpoint endpoint = new IceEndpoint();
        endpoint.setPort(2231);
        endpoint.setIpAddress("192.168.86.106");

        IceProxyIdentity routeIdentity = new IceProxyIdentity(routeName, endpoint);

        RouteReqProxy proxy = new RouteReqProxy(routeIdentity);
        RouteParam param = new RouteParam();
        param.setSvcName(sessionName);

        IceProxyIdentity sessionIdentity = proxy.getRouteResult(param).getProxyIdentity();
        this.proxy = new SessionReqProxy(sessionIdentity);
        String sessionParam = "tag=group_liu ,VAD_PARAM_RESPONSETIMEOUT=8000";
        SessionStatus status = this.proxy.begin(this.key, sessionParam, null, this.sessionRespone);
        this.sessionId = status.sessionId;
        System.out.println(this.sessionId);
        if (status.ok == 0) {
            try {
                RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/casa/Data/keqiang.wav", "rw");
                byte[] bytes = new byte[1280];
                while (randomAccessFile.read(bytes) != -1) {
                    RpcStatus rpcStatus = this.proxy.post(this.sessionId, bytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.key;
    }


    public String end() {
        this.proxy.end(this.sessionId);
        this.jedis.close();
        return this.key;
    }


    public List<Lattice> original(String key) {
        Jedis jedis = jedisPool.getResource();
        List<Lattice> lattices = new ArrayList<>();
        try {
            long len = jedis.llen(key);
            for (String latticeString : jedis.lrange(key, 0, len)) {
                lattices.add(JSONObject.parseObject(latticeString, Lattice.class));
            }
            return lattices;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean pushOriginal(int concurrency) {
        try {
            ExecutorService service = Executors.newFixedThreadPool(20);
            List<Future> futures = new ArrayList<>();
            List<Lattice> lattices = original("d9f148b2-6337-49dc-af07-9f436ca36e86");
            for (int i = 0; i < concurrency; i++) {
                futures.add(service.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Jedis jedis = jedisPool.getResource();
                            String key = UUID.randomUUID().toString();
                            for (Lattice lattice : lattices) {
                                jedis.lpush(key, JSON.toJSONString(lattice));
                            }
                        } finally {
                            if (jedis != null) {
                                jedis.close();
                            }
                        }
                    }
                }));
            }
            for (Future future : futures) {
                future.get();
            }
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public boolean pushEdit(int concurrency) {
        try {
            ExecutorService service = Executors.newFixedThreadPool(20);
            Map<String, String> editMap = new HashMap<>();
            List<Future> futures = new ArrayList<>();
            for (Lattice lattice : original("d9f148b2-6337-49dc-af07-9f436ca36e86")) {
                editMap.put(UUID.randomUUID().toString(), JSON.toJSONString(lattice));
            }
            for (int i = 0; i < concurrency; i++) {
                futures.add(service.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Jedis jedis = jedisPool.getResource();
                            jedis.hmset(UUID.randomUUID().toString(), editMap);
                        } finally {
                            if (jedis != null) {
                                jedis.close();
                            }
                        }

                    }
                }));
            }
            for (Future future : futures) {
                future.get();
            }
            return true;
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }
}
