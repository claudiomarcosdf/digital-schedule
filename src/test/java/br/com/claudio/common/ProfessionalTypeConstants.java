package br.com.claudio.common;

import java.util.ArrayList;
import java.util.List;

import br.com.claudio.entities.professionaltype.model.ProfessionalType;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase.CreateInput;
import br.com.claudio.usecase.professionaltype.ProfessionalTypeUseCase.UpdateInput;

public class ProfessionalTypeConstants {
	
	public static final ProfessionalType PROFESSIONALTYPE_CREATE = new ProfessionalType("new professional type");
	
	public static final ProfessionalType PROFESSIONALTYPE_UPDATE = new ProfessionalType(1L, "new professional type", true);
	
	public static final ProfessionalType INVALID_PROFESSIONALTYPE = new ProfessionalType(null, null, null);
	public static final ProfessionalType PROFESSIONAL1 = new ProfessionalType(1L, "professional1", true);
	public static final ProfessionalType PROFESSIONAL2 = new ProfessionalType(2L, "professional2", true);
	
	@SuppressWarnings("serial")
	public static final List<ProfessionalType> PROFESSIONALTYPELIST = new ArrayList<>() {
		{
			add(PROFESSIONAL1);
			add(PROFESSIONAL2);
		}
	};
	
	public static final CreateInput CREATEINPUT = new CreateInput("name");
	public static final UpdateInput UPDATEINPUT = new UpdateInput(1L, "professional1", true);
}
