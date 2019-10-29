package es.ulpgc.mesa.carlos.pem.Usuario;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;

public class UserScreen {

    public static void configure(UserContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        UserState state = mediator.getUserState();

        UserContract.Router router = new UserRouter(mediator);
        UserContract.Presenter presenter = new UserPresenter(state);
        UserContract.Model model = new UserModel();
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
