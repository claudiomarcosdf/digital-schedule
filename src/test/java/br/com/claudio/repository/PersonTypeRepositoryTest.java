package br.com.claudio.repository;

import static br.com.claudio.common.PersonTypeConstants.PERSONTYPESCHEMA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;

import br.com.claudio.infra.config.db.repositories.PersonTypeRepository;
import br.com.claudio.infra.config.db.schemas.PersonTypeSchema;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@DataJpaTest
public class PersonTypeRepositoryTest {
	
	@Autowired
	private PersonTypeRepository personTypeRepository;
	
	@Autowired
	private TestEntityManager testEntityManager;
	
	@AfterEach
	public void afterEach() {
		PERSONTYPESCHEMA.setId(null);
	}
	
	@Test
	public void createPersonType_WithValidData_ReturnsPersonTypeSchema() {
		PersonTypeSchema personType = personTypeRepository.save(PERSONTYPESCHEMA);
		
		PersonTypeSchema sut = testEntityManager.find(PersonTypeSchema.class, personType.getId());
		
		assertThat(sut).isNotNull();
		assertThat(sut.getName()).isEqualTo(PERSONTYPESCHEMA.getName());
	}
	
	@Test
	public void createPersonType_WithInvalidData_ThrowException() {
		PersonTypeSchema emptyPersonTypeSchema = new PersonTypeSchema(); //all null
		PersonTypeSchema invalidPersonTypeSchema = new PersonTypeSchema(null, "");
		PersonTypeSchema invalidNamePersonTypeSchema = new PersonTypeSchema(null, "xxxxxxxxxxxxxx xxxxxxxxxxxxxxxx"); // > 30
		
		assertThatThrownBy(() -> personTypeRepository.save(emptyPersonTypeSchema)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> personTypeRepository.save(invalidPersonTypeSchema)).isInstanceOf(RuntimeException.class);
		assertThatThrownBy(() -> personTypeRepository.save(invalidNamePersonTypeSchema)).isInstanceOf(RuntimeException.class);
	}
	
	@Test
	public void createPersonType_WithExistingName_ThrowException() {
		PersonTypeSchema personTypeSchema = testEntityManager.persistFlushFind(PERSONTYPESCHEMA);
		testEntityManager.detach(personTypeSchema);
		personTypeSchema.setId(null);
//		
//		try {
//			var retorno = personTypeRepository.save(personTypeSchema);
//			
//		} catch (Exception e) {
//			System.err.println(e);
//		}
		
		assertThatThrownBy(() -> personTypeRepository.save(personTypeSchema)).isInstanceOf(RuntimeException.class);
		
	}
	
	@Test
	public void getPersonType_ExistingId_ReturnPersonType() throws Exception {
		PersonTypeSchema personTypeSchema = testEntityManager.persistFlushFind(PERSONTYPESCHEMA);
		Optional<PersonTypeSchema> personTypeSchemaOpt = personTypeRepository.findById(personTypeSchema.getId());
		
		assertThat(personTypeSchemaOpt).isNotEmpty();
		assertThat(personTypeSchemaOpt.get()).isEqualTo(personTypeSchema);
	}
	
	@Test
	public void getPersonType_UnexistingId_ReturnEmpty() throws Exception {
		Optional<PersonTypeSchema> personTypeSchemaOpt = personTypeRepository.findById(1L);
		
		assertThat(personTypeSchemaOpt).isEmpty();
	}
	
	@Sql(scripts = "/import_persontypes.sql")
	@Test
	public void listPersonType_ReturnPersonTypes() {
		List<PersonTypeSchema> list = personTypeRepository.findAll();
		
		assertThat(list).isNotEmpty();
	}
	
	@Test
	public void listPersonType_ReturnEmptyListPersonType() {
		List<PersonTypeSchema> list = personTypeRepository.findAll();
		
		assertThat(list).isEmpty();
	}
	
	@Test
	public void removePersonType_WithExistingId_RemovesPersonTypeFromDatabaseH2() {
		PersonTypeSchema personTypeSchema = testEntityManager.persistFlushFind(PERSONTYPESCHEMA);
		personTypeRepository.deleteById(personTypeSchema.getId());
		
		PersonTypeSchema personTypeNotFound = testEntityManager.find(PersonTypeSchema.class, personTypeSchema.getId());
		assertThat(personTypeNotFound).isNull();
	}
	
	@Test
	public void removePersonType_WithUnexistingId_ThrowsException() {
		PersonTypeSchema personTypeNotFound = testEntityManager.find(PersonTypeSchema.class, 99L);
		assertThrows(InvalidDataAccessApiUsageException.class, () -> personTypeRepository.delete(personTypeNotFound));
	}

}
