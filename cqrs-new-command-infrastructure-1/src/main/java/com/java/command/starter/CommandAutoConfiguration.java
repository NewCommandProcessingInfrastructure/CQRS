package com.java.command.starter;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import({CommandConfiguration.class, CommandPersistenceConfiguration.class})
public class CommandAutoConfiguration {

}
