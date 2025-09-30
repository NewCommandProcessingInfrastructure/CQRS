package com.java.command.project.command;

import com.java.command.core.Command;
import com.java.command.project.entity.UserRequest;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCommand extends Command<UserRequest> {

	@Serial
	private static final long serialVersionUID = 1L;
}
