package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;

public class JedisTest {

	@Test
	public void testJedisSingle(){
		//创建一个jedis对象
		Jedis jedis = new Jedis("192.168.142.129", 6379);
		//调用jedis对象的方法，方法名称和redis命令一致
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.println("result =" + string);
		//关闭jedis
		jedis.close();
	}

	/**
	 * 使用连接池
	 */
	@Test
	public void testJedisPool(){
		//创建jedis连接池
		JedisPool jedisPool = new JedisPool("192.168.142.129", 6379);
		//从连接池获取jedis对象
		Jedis jedis1 = jedisPool.getResource();
		String string = jedis1.get("key1");
		System.out.println("pool01 result = " +  string);
		jedis1.close();

	}

	/**
	 * 集群版测试
	 */
	@Test
	public void testJedisCluster() throws IOException {
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.142.129", 7001));
		nodes.add(new HostAndPort("192.168.142.129", 7002));
		nodes.add(new HostAndPort("192.168.142.129", 7003));
		nodes.add(new HostAndPort("192.168.142.129", 7004));
		nodes.add(new HostAndPort("192.168.142.129", 7005));
		nodes.add(new HostAndPort("192.168.142.129", 7006));

		JedisCluster jedisCluster = new JedisCluster(nodes);

		jedisCluster.set("key1","1000");
		String string = jedisCluster.get("key1");
		System.out.println("jedisCluster " +  string);

		jedisCluster.close();
	}

	/**
	 * 单机版spring jedis测试
	 */
	@Test
	public void testSpringJedisSingle(){
		ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");

		Jedis jedis = jedisPool.getResource();
		String str = jedis.get("key1");
		System.out.println(str);
		jedis.close();
		jedisPool.close();

	}

	/**
	 * 集群版测试
	 */
	@Test
	public void testSpringJedisCluster() throws IOException {
		ApplicationContext applicationContext =
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");

		String str = jedisCluster.get("key1");
		System.out.println(str);
		jedisCluster.close();
	}
}
