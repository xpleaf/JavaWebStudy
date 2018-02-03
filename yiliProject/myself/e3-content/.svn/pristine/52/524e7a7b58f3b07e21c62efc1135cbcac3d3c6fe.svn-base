package cn.e3mall.testJedis;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class TestJedis {
	@Test
	public void testJedis() throws Exception {
		Jedis jedis = new Jedis("192.168.56.104", 6379);
		jedis.set("hello", "123");
		String result = jedis.get("hello");
		System.out.println(result);
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception {
		JedisPool jedisPool = new JedisPool("192.168.56.104", 6379);
		Jedis jedis = jedisPool.getResource();
		jedis.set("jedis", "test");
		String result = jedis.get("jedis");
		System.out.println(result);
		jedis.close();
		jedisPool.close();
	}
	
	@Test
	public void testJedisCluster() throws Exception {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.56.104", 7001));
		nodes.add(new HostAndPort("192.168.56.104", 7002));
		nodes.add(new HostAndPort("192.168.56.104", 7003));
		nodes.add(new HostAndPort("192.168.56.104", 7004));
		nodes.add(new HostAndPort("192.168.56.104", 7005));
		nodes.add(new HostAndPort("192.168.56.104", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("hello", "100");
		String result = jedisCluster.get("hello");
		System.out.println(result);
		jedisCluster.close();
	}
	
}
