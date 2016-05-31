package uk.ac.ebi.ddi.pipeline.indexer.tasklet.ega;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author Yasset Perez-Riverol (ypriverol@gmail.com)
 * @date 17/11/2015
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:jobs/ega/ddi-indexer-test-ega-import.xml"})
public class GenerateEBeyeEGAXMLTaskletTest {

    public static final String INDEXER_PARAMETER = "inderxer.param";
    public static final String TEST_MODE = "test.mode";


    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;


    private JobParameters jobParameters;


    @Before
    public void setUp() throws Exception {
        initJobParameters();
    }


    private void initJobParameters() {
        this.jobParameters =  new JobParametersBuilder().addString(INDEXER_PARAMETER,INDEXER_PARAMETER)
                .addString(TEST_MODE, "true")
                .toJobParameters();
    }

    @Test
    public void testLaunchJobWithJobLauncher() throws Exception {
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }
}