package com.sudhirt.practice.batch.multiline.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.With;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {

	@With
	private String firstName;

	@With
	private String lastName;

	@With
	private LocalDate dateOfBirth;

	@With
	private String gender;

}