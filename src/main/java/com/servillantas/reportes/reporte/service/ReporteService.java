package com.servillantas.reportes.reporte.service;
import com.servillantas.reportes.reporte.dto.request.CrearReporteRequest;
import com.servillantas.reportes.reporte.dto.response.ReporteResponse;
import java.util.UUID;
import org.springframework.data.domain.*;
public interface ReporteService {
    ReporteResponse crear(UUID usuarioId, CrearReporteRequest request);
    Page<ReporteResponse> listar(UUID usuarioId, String tipo, Pageable pageable);
    ReporteResponse detalle(UUID id, UUID usuarioId);
}
