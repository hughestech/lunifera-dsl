package org.lunifera.dsl.tests.carstore.entities.dtos.mapper;

import java.util.List;
import org.lunifera.dsl.dto.lib.Context;
import org.lunifera.dsl.tests.carstore.entities.Address;
import org.lunifera.dsl.tests.carstore.entities.Car;
import org.lunifera.dsl.tests.carstore.entities.Person;
import org.lunifera.dsl.tests.carstore.entities.dtos.AddressDto;
import org.lunifera.dsl.tests.carstore.entities.dtos.CarDto;
import org.lunifera.dsl.tests.carstore.entities.dtos.PersonDto;
import org.lunifera.dsl.tests.carstore.entities.dtos.mapper.BaseDtoMapper;

/**
 * This class maps the dto {@link PersonDto} to and from the entity {@link Person}.
 * 
 */
@SuppressWarnings("all")
public class PersonDtoMapper<DTO extends PersonDto, ENTITY extends Person> extends BaseDtoMapper<DTO, ENTITY> {
  /**
   * Maps the entity {@link Person} to the dto {@link PersonDto}.
   * 
   * @param dto - The target dto
   * @param entity - The source entity
   * 
   */
  public void mapToDTO(final PersonDto dto, final Person entity) {
    super.mapToDTO(dto, entity);
    
    
    dto.setFirstname(toDto_firstname(entity));
    dto.setLastname(toDto_lastname(entity));
    for(org.lunifera.dsl.tests.carstore.entities.dtos.CarDto _dtoValue : toDto_ownsCars(entity)) {
    	dto.addToOwnsCars(_dtoValue);
    }
    dto.setHomeAddress(toDto_homeAddress(entity));
    dto.setWorkAddress(toDto_workAddress(entity));
  }
  
  /**
   * Maps the dto {@link PersonDto} to the entity {@link Person}.
   * 
   * @param dto - The source dto
   * @param entity - The target entity
   * 
   */
  public void mapToEntity(final PersonDto dto, final Person entity) {
    super.mapToEntity(dto, entity);
    
    
    entity.setFirstname(toEntity_firstname(dto));
    
    entity.setLastname(toEntity_lastname(dto));
    
    List<Car> ownsCars_entities = new java.util.ArrayList<Car>();
    for(Car _entityValue : toEntity_ownsCars(dto)) {
    	ownsCars_entities.add(_entityValue);
    }
    entity.setOwnsCars(ownsCars_entities);
    
    entity.setHomeAddress(toEntity_homeAddress(dto));
    
    entity.setWorkAddress(toEntity_workAddress(dto));
    
  }
  
  /**
   * Maps the property firstname from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected String toDto_firstname(final Person in) {
    return in.getFirstname();
  }
  
  /**
   * Maps the property firstname from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected String toEntity_firstname(final PersonDto in) {
    return in.getFirstname();
  }
  
  /**
   * Maps the property lastname from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected String toDto_lastname(final Person in) {
    return in.getLastname();
  }
  
  /**
   * Maps the property lastname from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected String toEntity_lastname(final PersonDto in) {
    return in.getLastname();
  }
  
  /**
   * Maps the property ownsCars from the given entity to the dto.
   * 
   * @param in - The source entity
   * @return A list of mapped dtos
   * 
   */
  protected List<CarDto> toDto_ownsCars(final Person in) {
    org.lunifera.dsl.dto.lib.IMapper<CarDto, Car> mapper = getMapper(CarDto.class, Car.class);
    if(mapper == null) {
    	throw new IllegalStateException("Mapper must not be null!");
    } 
    
    List<CarDto> results = new java.util.ArrayList<CarDto>();
    for (Car _entity : in.getOwnsCars()) {
    	CarDto _dto = new CarDto();
    	mapper.mapToDTO(_dto, _entity);
    	results.add(_dto);
    }
    return results;
  }
  
  /**
   * Maps the property ownsCars from the given dto to the entity.
   * 
   * @param in - The source dto
   * @return A list of mapped entities
   * 
   */
  protected List<Car> toEntity_ownsCars(final PersonDto in) {
    org.lunifera.dsl.dto.lib.IMapper<CarDto, Car> mapper = getMapper(CarDto.class, Car.class);
    if(mapper == null) {
    	throw new IllegalStateException("Mapper must not be null!");
    }
    
    List<Car> results = new java.util.ArrayList<Car>();
    for (CarDto _dto : in.getOwnsCars()) {
    	Car _entity = new Car();
    	mapper.mapToEntity(_dto, _entity);
    	results.add(_entity);
    }
    return results;
  }
  
  /**
   * Maps the property homeAddress from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected AddressDto toDto_homeAddress(final Person in) {
    org.lunifera.dsl.dto.lib.IMapper<AddressDto, Address> mapper = getMapper(AddressDto.class, Address.class);
    if(mapper == null) {
    	throw new IllegalStateException("Mapper must not be null!");
    }
     
    if(in.getHomeAddress() != null) {
    	AddressDto dto = new AddressDto();
    	mapper.mapToDTO(dto, in.getHomeAddress());
    	return dto;
    } else {
    	return null;
    }
  }
  
  /**
   * Maps the property homeAddress from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected Address toEntity_homeAddress(final PersonDto in) {
    org.lunifera.dsl.dto.lib.IMapper<AddressDto, Address> mapper = getMapper(AddressDto.class, Address.class);
    if(mapper == null) {
    	throw new IllegalStateException("Mapper must not be null!");
    }
    
    if(in.getHomeAddress() != null) {
    	Address entity = new Address();
    	mapper.mapToEntity(in.getHomeAddress(), entity);
    	return entity;							
    } else {
    	return null;
    }
  }
  
  /**
   * Maps the property workAddress from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected AddressDto toDto_workAddress(final Person in) {
    org.lunifera.dsl.dto.lib.IMapper<AddressDto, Address> mapper = getMapper(AddressDto.class, Address.class);
    if(mapper == null) {
    	throw new IllegalStateException("Mapper must not be null!");
    }
     
    if(in.getWorkAddress() != null) {
    	AddressDto dto = new AddressDto();
    	mapper.mapToDTO(dto, in.getWorkAddress());
    	return dto;
    } else {
    	return null;
    }
  }
  
  /**
   * Maps the property workAddress from the given entity to dto property.
   * 
   * @param in - The source entity
   * @return the mapped value
   * 
   */
  protected Address toEntity_workAddress(final PersonDto in) {
    org.lunifera.dsl.dto.lib.IMapper<AddressDto, Address> mapper = getMapper(AddressDto.class, Address.class);
    if(mapper == null) {
    	throw new IllegalStateException("Mapper must not be null!");
    }
    
    if(in.getWorkAddress() != null) {
    	Address entity = new Address();
    	mapper.mapToEntity(in.getWorkAddress(), entity);
    	return entity;							
    } else {
    	return null;
    }
  }
  
  public PersonDtoMapper createDto() {
    return new PersonDto();
  }
  
  public PersonDtoMapper copy(final PersonDtoMapper dto, final Context context) {
    this.context = context;
    if (context == null) {
    	throw new IllegalArgumentException("Context must not be null!");
    }
    
    PersonDto newDto = createDto();
    context.register(dto, newDto);
    
    copyContainments(dto, newDto);
    copyCrossReferences(dto, newDto);
    
    return newDto;
  }
  
  public void copyContainments(final PersonDtoMapper dto, final PersonDtoMapper newDto, final Context context) {
    if (context == null) {
    	throw new IllegalArgumentException("Context must not be null!");
    }
    
    super.copyContainments(dto, newDto, context);
    
  }
  
  public void copyCrossReferences(final PersonDtoMapper dto, final PersonDtoMapper newDto, final org.lunifera.dsl.dto.lib.Context context) {
    if (context == null) {
    	throw new IllegalArgumentException("Context must not be null!");
    }
    
    super.copyCrossReferences(dto, newDto, context);
  }
}
