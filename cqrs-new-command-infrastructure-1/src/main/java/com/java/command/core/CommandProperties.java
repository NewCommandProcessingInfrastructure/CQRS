package com.java.command.core;

import com.lmax.disruptor.dsl.ProducerType;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@ConfigurationProperties(prefix = "helloworld.command")
public final class CommandProperties implements Serializable {
	
	@Serial
	private static final long serialVersionUID = 1L;

	@Builder.Default
	private Boolean enabled = true;
	
	@Builder.Default
	private CommandExecutorType executor = CommandExecutorType.sync;
	
	@Builder.Default
	private Integer ringBufferSize = 1024;
	
	@Builder.Default
	private ProducerType producerType = ProducerType.SINGLE;
	
	public enum CommandExecutorType {
			sync,
			async,
			disruptor
	}
}
