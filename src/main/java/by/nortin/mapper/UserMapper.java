package by.nortin.mapper;

import by.nortin.dto.UserDto;
import by.nortin.model.User;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User convertToModel(UserDto userDto);
}
