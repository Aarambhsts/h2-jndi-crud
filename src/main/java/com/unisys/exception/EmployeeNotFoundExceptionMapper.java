package com.unisys.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EmployeeNotFoundExceptionMapper implements ExceptionMapper<EmployeeNotFoundException> {

    @Override
    public Response toResponse(EmployeeNotFoundException exception) {
        ResponseError responseError = new ResponseError(exception.getMessage(), Response.Status.NOT_FOUND.getStatusCode());
        return Response.status(Response.Status.NOT_FOUND)
                .entity(responseError)
                .type("application/json")
                .build();
    }
}
