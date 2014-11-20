package dk.yabs;

import java.util.List;

public interface PiplineRepository {
    List<PipelineDto> loadInitalData();

    void addEvent(CreatePipeline createPipeline);
}
