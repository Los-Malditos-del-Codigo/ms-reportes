package com.servillantas.reportes.reporte.controller;
import com.servillantas.reportes.reporte.dto.request.CrearReporteRequest;
import com.servillantas.reportes.reporte.dto.response.ReporteResponse;
import com.servillantas.reportes.reporte.service.ReporteService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/reportes")
public class ReportesController {
    private final ReporteService service;
    public ReportesController(ReporteService service) { this.service = service; }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ReporteResponse crear(@RequestHeader("X-User-Id") UUID usuarioId,
            @Valid @RequestBody CrearReporteRequest request) { return service.crear(usuarioId, request); }
    @GetMapping
    public Page<ReporteResponse> listar(@RequestHeader("X-User-Id") UUID usuarioId,
            @RequestParam(required = false) String tipo,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.listar(usuarioId, tipo, pageable);
    }
    @GetMapping("/{id}")
    public ReporteResponse detalle(@PathVariable UUID id, @RequestHeader("X-User-Id") UUID usuarioId) {
        return service.detalle(id, usuarioId);
    }
}
