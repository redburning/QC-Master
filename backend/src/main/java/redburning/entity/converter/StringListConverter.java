package redburning.entity.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

	private static final String DELIMITER = ",";
	
	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (attribute == null || attribute.isEmpty()) {
			return null;
		}
		return String.join(DELIMITER, attribute);
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (dbData == null || dbData.trim().isEmpty()) {
			return new ArrayList<>();
		}
		return Arrays.asList(dbData.split(DELIMITER));
	}
}
