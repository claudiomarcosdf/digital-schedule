package br.com.claudio.infra.schedule.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureResponse {
    private Long id;
    private String name;
    private BigDecimal price;	
}
