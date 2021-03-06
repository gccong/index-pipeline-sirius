package uk.ac.ebi.ddi.pipeline.indexer.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * Abstract tasklet, all tasklets should extend this class
 *
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 29/09/15
 */
public abstract class AbstractTasklet implements Tasklet, InitializingBean {
    public static final Logger logger = LoggerFactory.getLogger(AbstractTasklet.class);

    /**
     * Update step execution context using a given map of values
     *
     * @param chunkContext tasklet chunk context
     * @param values       a map of values
     */
    protected void updateStepExecutionContext(ChunkContext chunkContext, Map<String, Object> values) {
        Assert.notNull(values, "Cannot add null values to chunkContext");

        StepContext stepContext = chunkContext.getStepContext();
        ExecutionContext stepExecutionContext = stepContext.getStepExecution().getExecutionContext();

        for (Map.Entry<String, Object> entry : values.entrySet()) {
            stepExecutionContext.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Update step execution context using a pair of key and value
     *
     * @param chunkContext tasklet chunk context
     * @param key          key to identify the value
     * @param value        value assigned to the given key
     */
    protected void updateStepExecutionContext(ChunkContext chunkContext, String key, Object value) {
        Assert.notNull(key, "Context key cannot be null");
        Assert.notNull(value, "Context value cannot be null");

        StepContext stepContext = chunkContext.getStepContext();
        ExecutionContext stepExecutionContext = stepContext.getStepExecution().getExecutionContext();

        stepExecutionContext.put(key, value);
    }

    /**
     * Sets the ExitStatus value for the step executing this tasklet
     *
     * @param chunkContext tasklet chunk context
     * @param exitStatus   the exit status to set
     */
    protected void setExitStatus(ChunkContext chunkContext, String exitStatus) {
        Assert.notNull(exitStatus, "Exit status cannot be null");
        StepContext stepContext = chunkContext.getStepContext();

        stepContext.getStepExecution().setExitStatus(new ExitStatus(exitStatus));

    }

}
