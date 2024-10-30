package todo.mapper;

import org.mapstruct.*;
import todo.domain.ToDo;
import todo.dto.TodoDTO;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TodoMapper {
    public TodoDTO toDTO(ToDo domain);
    public ToDo toDomain(TodoDTO dto);

    @Mapping(target = "created", source = "created", qualifiedByName = "localDateTimeToTimestamp")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "localDateTimeToTimestamp")
    public ToDo merge(TodoDTO from, @MappingTarget ToDo to);

    @Named("localDateTimeToTimestamp")
    default Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Timestamp.valueOf(localDateTime);
    }
}
