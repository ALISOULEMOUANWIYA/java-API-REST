package com.chat.Service.entity;
/**
 * @author ali
 *
 */
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserValide {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@NotBlank(message = "Please add User code")
	@Column(name ="code",length = 100, nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String code;

	@NotBlank(message = "Please add User code")
	@Column(name ="timeExp",length = 100, nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String timeExp;

	@NotBlank(message = "Please add User code")
	@Column(name ="dateExp",length = 100, nullable = false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String dateExp;

	@Email
	@NotBlank(message = "Please add User Email")
	@Column(name ="email", length = 150, nullable = false, unique = true)
	private String email;
}
