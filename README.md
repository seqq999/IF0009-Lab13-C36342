# MedPharm Frontend

Este proyecto fue generado con [Angular CLI](https://github.com/angular/angular-cli), versión 22.1.8.

## Servidor de desarrollo

Para iniciar el servidor local de desarrollo, ejecuta:

```bash
ng serve
```

Cuando el servidor esté funcionando, abre el navegador en `http://localhost:4200/`. La aplicación se recargará automáticamente cada vez que modifiques los archivos fuente.

## Generación de componentes

Angular CLI incluye herramientas para generar componentes. Para generar un componente nuevo, ejecuta:

```bash
ng generate component component-name
```

Para consultar la lista completa de esquemas disponibles, como `components`, `directives` o `pipes`, ejecuta:

```bash
ng generate --help
```

## Compilación

Para compilar el proyecto, ejecuta:

```bash
ng build
```

Esto compilará el proyecto y guardará los archivos generados en la carpeta `dist/`. De forma predeterminada, la compilación de producción optimiza la aplicación para mejorar su rendimiento.

## Pruebas unitarias

Para ejecutar las pruebas unitarias con [Vitest](https://vitest.dev/), ejecuta:

```bash
ng test
```

## Depuración de autenticación JWT

El endpoint `GET /api/v1/recetas` está protegido por Spring Security. Para comprobar el rechazo de una petición sin credenciales, se puede quitar temporalmente `authInterceptor` de `src/app/app.config.ts`, iniciar sesión y navegar a la lista de recetas. La solicitud aparece en la pestaña **Network** sin el encabezado `Authorization` y el backend responde `401 Unauthorized` (o `403 Forbidden`, según la configuración de seguridad).

La causa es que el navegador no envía automáticamente el JWT almacenado en `localStorage`. El interceptor agrega el encabezado requerido a cada solicitud de la API, excepto durante el inicio de sesión:

```http
Authorization: Bearer <token>
```

Con `authInterceptor` registrado mediante `provideHttpClient(withInterceptors([authInterceptor]))`, el backend puede validar el token y permite acceder a `/api/v1/recetas`. La configuración debe quedar restaurada después de la prueba.

La captura de la respuesta no autorizada debe guardarse en `docs/error_jwt_401.png`.

