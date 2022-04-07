package com.dragonfruit.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.dragonfruit.bean.Greeting;

@Configuration
public class KafkaConsumerConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
	public Map<String,Object> consumerConfig(){
		Map<String,Object> props = new HashMap<>();

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

		return props;
	}	
	
	@Bean
	public ConsumerFactory<String, Greeting> consumerFactory(){
		return new DefaultKafkaConsumerFactory<>(consumerConfig(),new StringDeserializer(),new JsonDeserializer<>(Greeting.class));
	}	
	
	@Bean
	public  ConcurrentKafkaListenerContainerFactory<String,Greeting> factory(
			ConsumerFactory<String, Greeting> consumerFactory){
		var factory = new ConcurrentKafkaListenerContainerFactory<String,Greeting>();
		
		factory.setConsumerFactory(consumerFactory);
		
		return factory;	
	}	
}
