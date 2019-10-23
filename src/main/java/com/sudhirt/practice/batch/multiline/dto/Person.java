package com.sudhirt.practice.batch.multiline.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Person {

	private String firstName;

	private String lastName;

	private LocalDate dateOfBirth;

	private String gender;

}