
## Depuración de autenticación JWT

El endpoint `GET /api/v1/recetas` está protegido por Spring Security. Para comprobar el rechazo de una petición sin credenciales, se puede quitar temporalmente `authInterceptor` de `src/app/app.config.ts`, iniciar sesión y navegar a la lista de recetas. La solicitud aparece en la pestaña **Network** sin el encabezado `Authorization` y el backend responde `401 Unauthorized` (o `403 Forbidden`, según la configuración de seguridad).

La causa es que el navegador no envía automáticamente el JWT almacenado en `localStorage`. El interceptor agrega el encabezado requerido a cada solicitud de la API, excepto durante el inicio de sesión:

```http
Authorization: Bearer <token>
```

Con `authInterceptor` registrado mediante `provideHttpClient(withInterceptors([authInterceptor]))`, el backend puede validar el token y permite acceder a `/api/v1/recetas`. La configuración debe quedar restaurada después de la prueba.

La captura de la respuesta no autorizada debe guardarse en `docs/error_jwt_401.png`.

