# MS Reportes

Registra y consulta los reportes solicitados por los usuarios.

## Responsabilidades

- Propietario de la tabla `reportes`.
- Registra tipo, formato (`PDF` o `XLSX`), parámetros y ubicación del archivo.
- Limita la cantidad de parámetros y valida URLs `https://` o `s3://`.
- Aísla los reportes por usuario.

Este servicio conserva metadatos y trazabilidad. La generación física de PDF/XLSX debe conectarse mediante un exportador o trabajador; no se afirma que el archivo se genere dentro del flujo actual.

## API

- `POST/GET /api/v1/reportes`.
- `GET /api/v1/reportes/{id}`.

## Configuración y ejecución

Se registra como `ms-reportes`, puerto `8088`, y obtiene `ms-reportes.yml` desde Config Server. Requiere PostgreSQL y Eureka.

```bash
./mvnw test
./mvnw spring-boot:run
```
