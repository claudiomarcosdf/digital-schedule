package br.com.claudio.infra.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalResponse {
    private Long id;
    private String nickName;
    private Integer document;
    private Integer durationService;
    private Integer intervalService;
    private String phone;
    private String phone2;
}
