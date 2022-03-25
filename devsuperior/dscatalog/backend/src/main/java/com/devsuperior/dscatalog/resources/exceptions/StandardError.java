package com.devsuperior.dscatalog.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardError {
    private static final long serialVersionUID = 4386240649033540624L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String  path;
    private String message;
}
