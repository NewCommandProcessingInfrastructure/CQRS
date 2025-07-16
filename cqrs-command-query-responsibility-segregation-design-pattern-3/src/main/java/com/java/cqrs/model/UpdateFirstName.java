package com.java.cqrs.model;

import java.io.Serializable;

public record UpdateFirstName(Long id, String firstName) implements Serializable {

}
