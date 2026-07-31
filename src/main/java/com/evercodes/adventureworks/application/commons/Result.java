package com.evercodes.adventureworks.application.commons;

import com.evercodes.adventureworks.application.enums.ResultType;
import lombok.Data;

import java.util.List;

@Data
public class Result<T> 
{

    private boolean success;
    private T data;
    private int totalRecords;
    private String message;
    private List<String> errors;
    private ResultType type;

    private Result() 
    {
    }

    public static <T> Result<T> Success(T data, int totalRecords) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        result.setTotalRecords(totalRecords);
        result.setType(ResultType.Success);
        return result;
    }

    public static <T> Result<T> Success(T data) 
    {
        return Success(data, 0);
    }

    public static <T> Result<T> Success(T data, String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        result.setMessage(message);
        result.setType(ResultType.Success);
        return result;
    }

    public static <T> Result<T> NotFound(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.NotFound);
        return result;
    }

    public static <T> Result<T> BadRequest(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.BadRequest);
        return result;
    }

    public static <T> Result<T> Invalid(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.Invalid);
        return result;
    }

    public static <T> Result<T> ValidationError(String message, List<String> errors) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setErrors(errors);
        result.setType(ResultType.ValidationError);
        return result;
    }

    public static <T> Result<T> Error(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.Error);
        return result;
    }

    public static <T> Result<T> NoContent(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setType(ResultType.NoContent);
        result.setMessage(message);
        result.setData(null);
        result.setTotalRecords(0);
        return result;
    }

    public static <T> Result<T> Unauthorized(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.Unauthorized);
        return result;
    }

    public static <T> Result<T> Forbidden(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.Forbidden);
        return result;
    }

    public static <T> Result<T> Conflict(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.Conflict);
        return result;
    }

    public static <T> Result<T> ServiceUnavailable(String message) 
    {
        Result<T> result = new Result<>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setType(ResultType.ServiceUnavailable);
        return result;
    }
}
