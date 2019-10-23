package com.sudhirt.practice.batch.multiline.config;

import com.sudhirt.practice.batch.multiline.dto.Person;
import com.sudhirt.practice.batch.multiline.reader.MultiLineItemReader;
import com.sudhirt.practice.batch.multiline.writer.ConsoleItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	private static final String OVERRIDDEN_BY_EXPRESSION = null;

	@Bean
	public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
		return jobBuilderFactory.get("processJob").incrementer(new RunIdIncrementer())
				.start(processStep(stepBuilderFactory)).build();
	}

	@Bean
	public Step processStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("processStep").<Person, Person>chunk(1)
				.reader(itemReader(OVERRIDDEN_BY_EXPRESSION)).writer(itemWriter()).build();
	}

	@Bean
	@StepScope
	public MultiLineItemReader itemReader(@Value("#{jobParameters[pathToFile]}") String pathToFile) {
		return new MultiLineItemReader(pathToFile);
	}

	@Bean
	public ItemWriter<Person> itemWriter() {
		return new ConsoleItemWriter();
	}

}
