package com.sudhirt.practice.batch.multiline.writer;

import java.util.List;
import com.sudhirt.practice.batch.multiline.dto.Person;
import org.springframework.batch.item.support.AbstractItemStreamItemWriter;
import lombok.extern.slf4j.Slf4j;

/**
 * ConsoleItemWriter
 */
@Slf4j
public class ConsoleItemWriter extends AbstractItemStreamItemWriter<Person> {

	@Override
	public void write(List<? extends Person> people) throws Exception {
		people.forEach(person -> log.info(person.toString()));
	}

}