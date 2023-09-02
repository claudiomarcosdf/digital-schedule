package br.com.claudio.infra.config.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MapperConfig {
	
	//public static ModelMapper modelMapper = new ModelMapper();
	
    public static ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        
        return modelMapper;
    }
    
    
	
//    static {
//        modelMapper.addConverter(getConverterPersonType(), PersonType.class, PersonTypeDto.class);
//
//        final var typeMap = modelMapper.typeMap(Person.class, PersonDto.class);
//
//        typeMap
//            .addMappings(map -> map.map(Person::getNickname, PersonDto::setSurname))
//            .addMappings(map -> map.when(getPFCondition()).map(Person::getRegister, PersonDto::setCpf))
//            .addMappings(map -> map.when(getPJCondition()).map(Person::getRegister, PersonDto::setCnpj));
//    }
//
//    private static Condition<String, String> getPFCondition() {
//        return ctx -> Optional.ofNullable(ctx.getSource())
//            .map(register -> register.replace(".", "").replace("-", ""))
//            .filter(register -> register.length() == 11)
//            .isPresent();
//    }
//
//    private static Condition<String, String> getPJCondition() {
//        return ctx -> Optional.ofNullable(ctx.getSource())
//            .map(register -> register.replace(".", "")
//                .replace("-", "")
//                .replace("/", "")
//            )
//            .filter(register -> register.length() == 14)
//            .isPresent();
//    }
//
//    private static Converter<PersonType, PersonTypeDto> getConverterPersonType() {
//        return context -> Optional.ofNullable(context.getSource())
//            .map(personType -> personType == PersonType.PF ? PersonTypeDto.PHYSICAL_PERSON : PersonTypeDto.LEGAL_PERSON)
//            .orElse(null);
//    }	

}
