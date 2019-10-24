package com.sudhirt.practice.batch.multiline;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@SpringBatchTest
class ApplicationTests {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void should_process_input_csv_successfully() throws Exception {
		var jobExecution = jobLauncherTestUtils.launchJob(buildJobParameters());
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
	}

	@Test
	public void should_fail_while_processing_input_with_invalid_fieldname() throws Exception {
		var jobExecution = jobLauncherTestUtils.launchJob(buildJobParameters("input_invalid_field_name.csv"));
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("FAILED");
	}

	@Test
	public void should_fail_while_processing_input_with_missing_item_header() throws Exception {
		var jobExecution = jobLauncherTestUtils.launchJob(buildJobParameters("input_missing_item_header.csv"));
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("FAILED");
	}

	@Test
	public void should_fail_while_processing_input_with_missing_first_item_header() throws Exception {
		var jobExecution = jobLauncherTestUtils.launchJob(buildJobParameters("input_missing_first_item_header.csv"));
		assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("FAILED");
	}

	private JobParameters buildJobParameters() {
		return buildJobParameters("input.csv");
	}

	private JobParameters buildJobParameters(String filePath) {
		return new JobParametersBuilder().addString("pathToFile", filePath)
				.addLong("currentTimeInMillis", System.currentTimeMillis()).toJobParameters();
	}

}
