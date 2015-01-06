package jahubba;

import java.io.File;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class ContextTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {		
		Foo foo = new Foo();
		foo.setFoo("batch isolation");
		foo.setTest(5);

		StepExecution stepExecution = chunkContext.getStepContext().getStepExecution();
		JobExecution jobExecution = stepExecution.getJobExecution();
		jobExecution.getExecutionContext().put("foo_context", foo);
		stepExecution.setStatus(BatchStatus.FAILED);
		stepExecution.setExitStatus(ExitStatus.FAILED);

		return RepeatStatus.FINISHED;
	}

}
