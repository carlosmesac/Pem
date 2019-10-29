package es.ulpgc.mesa.carlos.pem.AddBook;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.App.Repository;

public class AddBookScreen {

    public static void configure(AddBookContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        AddBookState state = mediator.getAddBookState();
        Contract repository = Repository.getInstance(context.get());
        AddBookContract.Router router = new AddBookRouter(mediator);
        AddBookContract.Presenter presenter = new AddBookPresenter(state);
        AddBookContract.Model model = new AddBookModel(repository);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
