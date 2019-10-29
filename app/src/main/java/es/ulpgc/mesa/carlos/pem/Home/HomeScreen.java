package es.ulpgc.mesa.carlos.pem.Home;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.App.Repository;

public class HomeScreen {

    public static void configure(HomeContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        HomeState state = mediator.getHomeState();
        Contract repository= Repository.getInstance(context.get());
        HomeContract.Router router = new HomeRouter(mediator);
        HomeContract.Presenter presenter = new HomePresenter(state);
        HomeContract.Model model = new HomeModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
