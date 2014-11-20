package dk.yabs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YabsTest {

    private Driver driver;
    private Yabs yabs;
    private PiplineRepository piplineRepository;

    @Before
    public void setUp() throws Exception {
        piplineRepository = new MemoryPiplineRepository();
        StubPresenter presenter = new StubPresenter();
        yabs = new Yabs(piplineRepository, presenter);
        driver = new StubDriver(presenter);

    }

    @Test
    public void whenNoReposConfigured_thenNoPipelinesAreShownAndConfigurationIsShown() throws Exception {
        yabs.start();

        driver.noPipelines();
        driver.atConfiguration();
    }

    @Test
    public void whenStartingUp_thenTheCurrentStateOfASinglePipelineIsShown() throws Exception {
        piplineRepository.addEvent(new CreatePipeline("pipelineName"));

        yabs.start();

        driver.numberOfPipelines(1);
        driver.selectedPipeline("pipelineName");
    }



    private class MemoryPiplineRepository implements PiplineRepository {
        private List<CreatePipeline> pipelines = new ArrayList<>();

        @Override
        public List<PipelineDto> loadInitalData() {
            return pipelines.stream()
                            .map(createPipeline -> new PipelineDto(createPipeline.name))
                            .collect(Collectors.toList());
        }

        @Override
        public void addEvent(CreatePipeline createPipeline) {
            pipelines.add(createPipeline);
        }
    }

    private class StubPresenter implements Presenter {
        public List<PipelineDto> pipelines;

        @Override
        public void initialData(List<PipelineDto> pipelines) {
            this.pipelines = pipelines;
        }
    }

    private class StubDriver implements Driver {
        private StubPresenter presenter;
        private boolean atConfiguration = true;

        public StubDriver(StubPresenter presenter) {
            this.presenter = presenter;
        }

        public void noPipelines() {
            assertTrue(presenter.pipelines.isEmpty());
        }

        public void atConfiguration() {
            assertTrue(atConfiguration);
        }

        @Override
        public void numberOfPipelines(int i) {
            assertEquals(i, presenter.pipelines.size());
        }

        @Override
        public void selectedPipeline(String name) {
            assertTrue(presenter.pipelines.stream()
                                          .findFirst()
                                          .map(pipelineDto -> pipelineDto.name.equals(name))
                                          .orElse(false));
        }
    }
}
