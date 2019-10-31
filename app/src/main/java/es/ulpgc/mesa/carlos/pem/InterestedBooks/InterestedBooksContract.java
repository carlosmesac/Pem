package es.ulpgc.mesa.carlos.pem.InterestedBooks;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;
import es.ulpgc.mesa.carlos.pem.MyBooks.MyBooksViewModel;

public interface InterestedBooksContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(InterestedBooksViewModel viewModel);



        void displayInterestedBooks(InterestedBooksViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void fillInterestedBooksArray();
    }

    interface Model {
        String fetchData();

        void fillInterestedBooksArray(Contract.FillInterestedBooksArray fillInterestedBooksArray);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(InterestedBooksState state);

        InterestedBooksState getDataFromPreviousScreen();
    }
}
