# MedpharmFrontend

This project was generated using [Angular CLI](https://github.com/angular/angular-cli) version 22.1.8.

## Development server

To start a local development server, run:

```bash
ng serve
```

Once the server is running, open your browser and navigate to `http://localhost:4200/`. The application will automatically reload whenever you modify any of the source files.

## Code scaffolding

Angular CLI includes powerful code scaffolding tools. To generate a new component, run:

```bash
ng generate component component-name
```

For a complete list of available schematics (such as `components`, `directives`, or `pipes`), run:

```bash
ng generate --help
```

## Building

To build the project run:

```bash
ng build
```

This will compile your project and store the build artifacts in the `dist/` directory. By default, the production build optimizes your application for performance and speed.

## Running unit tests

To execute unit tests with the [Vitest](https://vitest.dev/) test runner, use the following command:

```bash
ng test
```

## Depuracion de autenticacion JWT

El endpoint `GET /api/v1/recetas` esta protegido por Spring Security. Para comprobar el rechazo de una peticion sin credenciales, se puede quitar temporalmente `authInterceptor` de `src/app/app.config.ts`, iniciar sesion y navegar a la lista de recetas. La solicitud aparece en la pestaña **Network** sin el encabezado `Authorization` y el backend responde `401 Unauthorized` (o `403 Forbidden`, segun la configuracion de seguridad).

La causa es que el navegador no envia automaticamente el JWT almacenado en `localStorage`. El interceptor agrega el encabezado requerido a cada solicitud de la API, excepto al login:

```http
Authorization: Bearer <token>
```

Con `authInterceptor` registrado mediante `provideHttpClient(withInterceptors([authInterceptor]))`, el backend puede validar el token y permite acceder a `/api/v1/recetas`. La configuracion debe quedar restaurada despues de la prueba.

La captura de la respuesta no autorizada debe guardarse en `docs/error_jwt_401.png`.

## Running end-to-end tests

For end-to-end (e2e) testing, run:

```bash
ng e2e
```

Angular CLI does not come with an end-to-end testing framework by default. You can choose one that suits your needs.

## Additional Resources

For more information on using the Angular CLI, including detailed command references, visit the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.
