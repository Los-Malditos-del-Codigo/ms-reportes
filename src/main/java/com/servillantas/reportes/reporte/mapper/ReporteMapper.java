package com.servillantas.reportes.reporte.mapper;
import com.servillantas.reportes.reporte.dto.response.ReporteResponse;
import com.servillantas.reportes.reporte.entity.Reporte;
import java.util.Map;
import org.springframework.stereotype.Component;
@Component public class ReporteMapper {
    public ReporteResponse toResponse(Reporte v) { return new ReporteResponse(v.id, v.usuarioId, v.tipo, v.formato,
            v.parametros == null ? Map.of() : Map.copyOf(v.parametros), v.archivoUrl, v.createdAt); }
}
