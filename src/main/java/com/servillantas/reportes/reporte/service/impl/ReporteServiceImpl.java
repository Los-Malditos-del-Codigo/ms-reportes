package com.servillantas.reportes.reporte.service.impl;
import com.servillantas.reportes.reporte.dto.request.CrearReporteRequest;
import com.servillantas.reportes.reporte.dto.response.ReporteResponse;
import com.servillantas.reportes.reporte.entity.Reporte;
import com.servillantas.reportes.reporte.mapper.ReporteMapper;
import com.servillantas.reportes.reporte.repository.ReporteRepository;
import com.servillantas.reportes.reporte.service.ReporteService;
import java.util.UUID;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
@Service public class ReporteServiceImpl implements ReporteService {
    private final ReporteRepository repository; private final ReporteMapper mapper;
    public ReporteServiceImpl(ReporteRepository repository, ReporteMapper mapper) {
        this.repository = repository; this.mapper = mapper;
    }
    @Override @Transactional
    public ReporteResponse crear(UUID usuarioId, CrearReporteRequest r) {
        if (r.parametros() != null && r.parametros().size() > 50)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Demasiados parámetros");
        Reporte value = new Reporte(usuarioId, r.tipo().trim().toUpperCase(), r.formato().toUpperCase(),
                r.parametros(), normalizarUrl(r.archivoUrl()));
        return mapper.toResponse(repository.save(value));
    }
    @Override @Transactional(readOnly = true)
    public Page<ReporteResponse> listar(UUID usuarioId, String tipo, Pageable pageable) {
        Pageable segura = PageRequest.of(Math.max(0, pageable.getPageNumber()),
                Math.min(Math.max(1, pageable.getPageSize()), 100), pageable.getSort());
        Page<Reporte> values = tipo == null ? repository.findByUsuarioId(usuarioId, segura)
                : repository.findByUsuarioIdAndTipoIgnoreCase(usuarioId, tipo.trim(), segura);
        return values.map(mapper::toResponse);
    }
    @Override @Transactional(readOnly = true)
    public ReporteResponse detalle(UUID id, UUID usuarioId) {
        Reporte value = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Reporte no encontrado"));
        if (value.usuarioId != null && !value.usuarioId.equals(usuarioId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El reporte pertenece a otro usuario");
        return mapper.toResponse(value);
    }
    private String normalizarUrl(String url) {
        if (url == null || url.isBlank()) return null;
        String limpia = url.trim();
        if (!(limpia.startsWith("https://") || limpia.startsWith("s3://")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "archivo_url debe usar https:// o s3://");
        return limpia;
    }
}
