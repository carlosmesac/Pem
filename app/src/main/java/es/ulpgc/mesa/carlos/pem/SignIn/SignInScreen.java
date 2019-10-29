package es.ulpgc.mesa.carlos.pem.SignIn;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.App.Repository;

public class SignInScreen {

    public static void configure(SignInContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        SignInState state = mediator.getSignInState();
        Contract repository = Repository.getInstance(context.get());

        SignInContract.Router router = new SignInRouter(mediator);
        SignInContract.Presenter presenter = new SignInPresenter(state);
        SignInContract.Model model = new SignInModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
