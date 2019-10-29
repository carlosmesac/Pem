package es.ulpgc.mesa.carlos.pem.MyBooks;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;
import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.App.Repository;

public class MyBooksScreen {

    public static void configure(MyBooksContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        MyBooksState state = mediator.getMyBooksState();

        MyBooksContract.Router router = new MyBooksRouter(mediator);
        MyBooksContract.Presenter presenter = new MyBooksPresenter(state);
        Contract contract= Repository.getInstance(context.get());
        MyBooksContract.Model model = new MyBooksModel(contract);
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
