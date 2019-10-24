package com.sudhirt.practice.batch.multiline.reader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.sudhirt.practice.batch.multiline.dto.Person;
import com.sudhirt.practice.batch.multiline.exception.InputFileParseException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.SingleItemPeekableItemReader;
import org.springframework.core.io.ClassPathResource;

public class MultiLineItemReader implements ItemStreamReader<Person> {

	private static final String ITEM_HEADER = "ITEM_BEGIN";

	private static final String ITEM_FOOTER = "ITEM_END";

	private static final DateTimeFormatter BIRTH_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private final String pathToFile;

	public MultiLineItemReader(String pathToFile) {
		this.pathToFile = pathToFile;
	}

	private SingleItemPeekableItemReader<FieldSet> delegate;

	@BeforeStep
	public void before(StepExecution stepExecution) {
		FlatFileItemReader<FieldSet> flatFileItemReader = new FlatFileItemReader<>();
		DefaultLineMapper<FieldSet> defaultLineMapper = new DefaultLineMapper<>();
		LineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(new PassThroughFieldSetMapper());
		flatFileItemReader.setResource(new ClassPathResource(pathToFile));
		flatFileItemReader.setLineMapper(defaultLineMapper);
		delegate = new SingleItemPeekableItemReader<>();
		delegate.setDelegate(flatFileItemReader);
	}

	@AfterStep
	public void afterStep(StepExecution stepExecution) {
		delegate.close();
	}

	@Override
	public void open(ExecutionContext executionContext) {
		delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) {
		delegate.update(executionContext);

	}

	@Override
	public void close() {
		delegate.close();
	}

	@Override
	public Person read() throws Exception {
		Person person = null;
		FieldSet line;
		while ((line = delegate.read()) != null) {
			String firstWord = line.readString(0);
			if (ITEM_HEADER.equals(firstWord)) {
				person = new Person();
			}
			else if (!ITEM_FOOTER.equals(firstWord)) {
				if (person == null) {
					throw new InputFileParseException("Expected item header missing in input file");
				}
				map(firstWord, line.readString(1), person);
			}

			FieldSet nextLine = delegate.peek();
			if (nextLine == null || nextLine.readString(0).equals(ITEM_FOOTER)) {
				break;
			}
		}
		return person;
	}

	private Person map(String identifier, String value, Person person) {
		Person newPerson;
		switch (identifier) {
		case "FIRST_NAME":
			newPerson = person.withFirstName(value);
			break;
		case "LAST_NAME":
			newPerson = person.withLastName(value);
			break;
		case "DATE_OF_BIRTH":
			newPerson = person.withDateOfBirth(LocalDate.parse(value, BIRTH_DATE_FORMATTER));
			break;
		case "GENDER":
			newPerson = person.withGender(value);
			break;
		default:
			throw new InputFileParseException("Unexpected element encountered while reading input file");
		}
		return newPerson;
	}

}