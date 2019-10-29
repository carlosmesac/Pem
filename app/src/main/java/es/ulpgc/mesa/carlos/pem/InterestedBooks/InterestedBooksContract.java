package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import java.lang.ref.WeakReference;

public interface InterestedBooksContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(InterestedBooksViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();
    }

    interface Model {
        String fetchData();
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(InterestedBooksState state);

        InterestedBooksState getDataFromPreviousScreen();
    }
}
