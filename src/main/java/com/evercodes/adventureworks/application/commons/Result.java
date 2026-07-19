package com.evercodes.adventureworks.application.commons;

import com.evercodes.adventureworks.application.enums.ResultType;
import lombok.Data;

import java.util.List;

@Data
public class Result<T> 
{

    private boolean esExito;
    private T datos;
    private int registrosTotales;
    private String mensajeError;
    private List<String> errores;
    private ResultType tipo;

    private Result() 
    {
    }

    public static <T> Result<T> Success(T datos, int registrosTotales) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(true);
        result.setDatos(datos);
        result.setRegistrosTotales(registrosTotales);
        result.setTipo(ResultType.Success);
        return result;
    }

    public static <T> Result<T> Success(T datos) 
    {
        return Success(datos, 0);
    }

    public static <T> Result<T> Success(T datos, String mensaje) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(true);
        result.setDatos(datos);
        result.setMensajeError(mensaje);
        result.setTipo(ResultType.Success);
        return result;
    }

    public static <T> Result<T> NotFound(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.NotFound);
        return result;
    }

    public static <T> Result<T> BadRequest(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.BadRequest);
        return result;
    }

    public static <T> Result<T> Invalid(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.Invalid);
        return result;
    }

    public static <T> Result<T> ValidationError(String mensajeError, List<String> errores) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setErrores(errores);
        result.setTipo(ResultType.ValidationError);
        return result;
    }

    public static <T> Result<T> Error(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.Error);
        return result;
    }

    public static <T> Result<T> NoContent(String mensaje) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(true);
        result.setTipo(ResultType.NoContent);
        result.setMensajeError(mensaje);
        result.setDatos(null);
        result.setRegistrosTotales(0);
        return result;
    }

    public static <T> Result<T> Unauthorized(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.Unauthorized);
        return result;
    }

    public static <T> Result<T> Forbidden(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.Forbidden);
        return result;
    }

    public static <T> Result<T> Conflict(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.Conflict);
        return result;
    }

    public static <T> Result<T> ServiceUnavailable(String mensajeError) 
    {
        Result<T> result = new Result<>();
        result.setEsExito(false);
        result.setMensajeError(mensajeError);
        result.setTipo(ResultType.ServiceUnavailable);
        return result;
    }
}
