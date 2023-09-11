package br.com.claudio.infra.schedule.dto;

import java.time.LocalDate;

import br.com.claudio.infra.config.db.schemas.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private Long id;
    private String fullName;
    private LocalDate birthDay;
    private Gender gender;
    private String cpf;
    private String phone;
    private String phone2; 	
}
