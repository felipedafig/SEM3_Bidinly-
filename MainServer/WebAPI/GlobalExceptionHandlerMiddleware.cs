using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;

namespace MainServer.WebAPI.Middlewares
{
    public class GlobalExceptionHandlerMiddleware : IMiddleware
    {
        public async Task InvokeAsync(HttpContext context, RequestDelegate next)
        {
            try
            {
                await next(context);
            }
            catch (KeyNotFoundException ex)
            {
                context.Response.StatusCode = 404;
                await context.Response.WriteAsync(ex.Message);
            }
            catch (DllNotFoundException ex)
            {
                context.Response.StatusCode = 404;
                await context.Response.WriteAsync(ex.Message);
            }
            catch (InvalidOperationException ex)
            {
                context.Response.StatusCode = 409;
                await context.Response.WriteAsync(ex.Message);
            }
            catch (DbUpdateException ex)
            {
                context.Response.StatusCode = 400;
                // Get the inner exception message which contains the actual database error
                string errorMessage = ex.InnerException?.Message ?? ex.Message;
                await context.Response.WriteAsync(errorMessage);
            }
            catch (Exception ex)
            {
                context.Response.StatusCode = 500;
                await context.Response.WriteAsync(ex.Message);
            }
        }
    }
}

