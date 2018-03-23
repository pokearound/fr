package com.rathna.fr;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Tracer implements Serializable {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private String oldFileName;
	private String newFileName;
	private Boolean renamed;
	private Boolean ignored;
	private Boolean replaced;
	private Boolean failed;
	private Boolean dry;

	@Override
	public String toString() {
		try {
			return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return "Tracer [oldFileName=" + oldFileName + ", newFileName=" + newFileName + ", renamed=" + renamed
					+ ", ignored=" + ignored + ", replaced=" + replaced + "]";
		}
	}
}