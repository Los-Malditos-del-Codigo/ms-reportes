package com.servillantas.reportes.reporte.dto.request;
import jakarta.validation.constraints.*;
import java.util.Map;
public record CrearReporteRequest(@NotBlank @Size(max = 60) String tipo,
        @NotBlank @Pattern(regexp = "(?i)PDF|XLSX") String formato,
        Map<String, Object> parametros, String archivoUrl) {}
