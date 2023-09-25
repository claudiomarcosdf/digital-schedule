package br.com.claudio.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.procedure.model.Procedure;
import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.usecase.procedure.ProcedureCreateInput;
import br.com.claudio.usecase.procedure.ProcedureUpdateInput;

public class ProcedureConstants {
	
	public static final ProfessionalType VALID_PROFESSIONALTYPE = new ProfessionalType(1L, "Professional1", true);
	public static final ProfessionalType INVALID_PROFESSIONALTYPE = new ProfessionalType(3L, "Professional3", false);
	
	public static final Procedure INVALID_PROCEDURE = new Procedure(null, "", null, true, INVALID_PROFESSIONALTYPE);
	
	public static final Procedure PROCEDURE1 = new Procedure(1L, "procedure 1", BigDecimal.TEN, true, VALID_PROFESSIONALTYPE);
	public static final Procedure PROCEDURE2 = new Procedure(6L, "procedure 6", BigDecimal.ONE, true, VALID_PROFESSIONALTYPE);
	
	@SuppressWarnings("serial")
	public static final List<Procedure> PROCEDURELIST = new ArrayList<>() {
		{
			add(PROCEDURE1);
			add(PROCEDURE2);
		}
	};
	
	public static final ProcedureCreateInput PROCEDURECREATEIMPUT = new ProcedureCreateInput("New procedure", BigDecimal.ZERO, VALID_PROFESSIONALTYPE);
	public static final ProcedureUpdateInput PROCEDUREUPDATEIMPUT = new ProcedureUpdateInput(1l, "procedure 1", BigDecimal.TEN, true, VALID_PROFESSIONALTYPE);
	public static final ProcedureCreateInput PROCEDURECREATEIMPUT_INACTIVE_PROFESSIONALTYPE = new ProcedureCreateInput("New procedure", BigDecimal.ZERO, INVALID_PROFESSIONALTYPE);
	
}
