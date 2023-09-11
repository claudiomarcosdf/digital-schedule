package br.com.claudio.infra.config.db.schemas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import br.com.claudio.infra.config.db.schemas.enums.StatusSchedule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules", uniqueConstraints = @UniqueConstraint(columnNames = {"professional_id", "start_date"}) )
@EntityListeners(AuditingEntityListener.class)
public class ScheduleSchema implements Serializable {
	private static final long serialVersionUID = -131784870050280983L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	@FutureOrPresent(message = "A data inicial deve ser presente ou futura")
	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;
	
	@FutureOrPresent(message = "A data final deve ser presente ou futura")
	@Column(name = "end_date", nullable = false)
	private LocalDateTime endDate;
	
	@Column(length = 100)
	private String description;
	
	@Column(name = "amount_paid")
	private BigDecimal amountPaid = BigDecimal.ZERO;
	
	@JoinColumn(name = "professional_type_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ProfessionalTypeSchema professionalType;
	
	@JoinColumn(name = "professional_id", referencedColumnName = "id")
	@ManyToOne
	private ProfessionalSchema professional;
	
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	@ManyToOne
	private PatientSchema patient;
	
	@JoinColumn(name = "procedure_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY)	
	private ProcedureSchema procedure;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)	
	private StatusSchedule status = StatusSchedule.AGENDADO;
	
	@Column(columnDefinition = "boolean default 'true'")
	private Boolean active = true;
	
	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdDate;
	
	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedDate;	
}
