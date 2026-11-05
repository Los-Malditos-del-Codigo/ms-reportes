package com.servillantas.reportes.reporte.dto.response;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;
public record ReporteResponse(UUID id, UUID usuarioId, String tipo, String formato,
        Map<String, Object> parametros, String archivoUrl, OffsetDateTime createdAt) {}
