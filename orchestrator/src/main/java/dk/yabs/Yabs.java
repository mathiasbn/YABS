package dk.yabs;

public class Yabs {
    private final PiplineRepository piplineRepository;
    private final Presenter presenter;

    public Yabs(PiplineRepository piplineRepository, Presenter presenter) {

        this.piplineRepository = piplineRepository;
        this.presenter = presenter;
    }

    public void start() {
        presenter.initialData(piplineRepository.loadInitalData());
    }
}
