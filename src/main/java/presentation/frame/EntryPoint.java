package presentation.frame;

public class EntryPoint {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Opening");
        HomeScreenPresenter presenter = new HomeScreenPresenter();
        presenter.execute();
        System.out.println("Opened");
    }

}
