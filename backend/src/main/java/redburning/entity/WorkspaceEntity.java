package redburning.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name = "workspace")
public class WorkspaceEntity {

	@Id
	@GeneratedValue(generator = "definitionGenerator")
	@GenericGenerator(name = "definitionGenerator", strategy = "uuid")
	private String id;
	
	private String name;
	
	@Column(name = "monitor_path")
	private String monitorPath;
	
	@Column(name = "monitor_files")
	@Convert(converter = StringListConverter.class)
	private List<String> monitorFiles;
	
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "base_peak_current")
	private String basePeakCurrent;
	
}


@Converter
class StringListConverter implements AttributeConverter<List<String>, String> {

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