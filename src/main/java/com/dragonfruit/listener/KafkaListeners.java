package com.dragonfruit.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dragonfruit.bean.Greeting;

@Component
public class KafkaListeners {
	@KafkaListener(topics="dragonfruit",groupId="cats",
			containerFactory="factory")
	void listener(Greeting greeting) {
		System.out.println("listener received msg:"+ greeting.getMsg()+","+ greeting.getName() + " tada");
	}
}
