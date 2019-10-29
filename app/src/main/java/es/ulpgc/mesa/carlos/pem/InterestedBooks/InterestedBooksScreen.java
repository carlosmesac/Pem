package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import androidx.fragment.app.FragmentActivity;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.AppMediator;

public class InterestedBooksScreen {

    public static void configure(InterestedBooksContract.View view) {

        WeakReference<FragmentActivity> context =
                new WeakReference<>((FragmentActivity) view);

        AppMediator mediator = (AppMediator) context.get().getApplication();
        InterestedBooksState state = mediator.getInterestedBooksState();

        InterestedBooksContract.Router router = new InterestedBooksRouter(mediator);
        InterestedBooksContract.Presenter presenter = new InterestedBooksPresenter(state);
        InterestedBooksContract.Model model = new InterestedBooksModel();
        presenter.injectModel(model);
        presenter.injectRouter(router);
        presenter.injectView(new WeakReference<>(view));

        view.injectPresenter(presenter);

    }
}
