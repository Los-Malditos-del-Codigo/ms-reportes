package com.servillantas.reportes.controller;

import com.servillantas.reportes.domain.Reporte;
import com.servillantas.reportes.repository.ReporteRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@RestController @RequestMapping("/api/v1/reportes")
public class ReportesController {
    private final ReporteRepository repository;
    public ReportesController(ReporteRepository repository){this.repository=repository;}
    public record CrearReporte(@NotBlank @Size(max=60) String tipo,@NotBlank @Pattern(regexp="(?i)PDF|XLSX") String formato,Map<String,Object> parametros,String archivoUrl){}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Reporte crear(@RequestHeader("X-User-Id") UUID usuarioId,@Valid @RequestBody CrearReporte r){if(r.parametros()!=null&&r.parametros().size()>50)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Demasiados parámetros");return repository.save(new Reporte(usuarioId,r.tipo().trim().toUpperCase(),r.formato().toUpperCase(),r.parametros(),normalizarUrl(r.archivoUrl())));}
    @GetMapping public Page<Reporte> listar(@RequestHeader("X-User-Id") UUID usuarioId,@RequestParam(required=false) String tipo,@PageableDefault(sort="createdAt",direction=Sort.Direction.DESC) Pageable p){
        Pageable segura=PageRequest.of(Math.max(0,p.getPageNumber()),Math.min(Math.max(1,p.getPageSize()),100),p.getSort());
        if(tipo!=null)return repository.findByUsuarioIdAndTipoIgnoreCase(usuarioId,tipo,segura);
        return repository.findByUsuarioId(usuarioId,segura);
    }
    @GetMapping("/{id}") public Reporte detalle(@PathVariable UUID id,@RequestHeader("X-User-Id") UUID usuarioId){Reporte r=repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Reporte no encontrado"));if(r.usuarioId!=null&&!r.usuarioId.equals(usuarioId))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"El reporte pertenece a otro usuario");return r;}
    private String normalizarUrl(String url){if(url==null||url.isBlank())return null;String limpia=url.trim();if(!(limpia.startsWith("https://")||limpia.startsWith("s3://")))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"archivo_url debe usar https:// o s3://");return limpia;}
}
