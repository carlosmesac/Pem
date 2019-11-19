package es.ulpgc.mesa.carlos.pem.Usuario;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.App.Repository;

public class UserScreen {

    public static void configure(UserContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        UserState state = mediator.getUserState();

        UserContract.Router router = new UserRouter(mediator);
        UserContract.Presenter presenter = new UserPresenter(state);
        Contract contract= Repository.getInstance(context.get());
        UserContract.Model model = new UserModel(contract);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
