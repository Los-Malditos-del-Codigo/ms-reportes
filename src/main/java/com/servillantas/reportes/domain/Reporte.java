package com.servillantas.reportes.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.OffsetDateTime;
import java.util.*;

@Entity @Table(name="reportes")
public class Reporte {
    @Id @GeneratedValue(strategy=GenerationType.UUID) public UUID id;
    @Column(name="usuario_id") public UUID usuarioId;
    @Column(nullable=false,length=60) public String tipo;
    @Column(nullable=false,length=10) public String formato;
    @JdbcTypeCode(SqlTypes.JSON) @Column(nullable=false,columnDefinition="jsonb") public Map<String,Object> parametros=new LinkedHashMap<>();
    @Column(name="archivo_url",columnDefinition="text") public String archivoUrl;
    @Column(name="created_at",nullable=false,insertable=false,updatable=false) public OffsetDateTime createdAt;
    protected Reporte(){}
    public Reporte(UUID u,String t,String f,Map<String,Object> p,String a){usuarioId=u;tipo=t;formato=f;parametros=p==null?new LinkedHashMap<>():p;archivoUrl=a;}
}
