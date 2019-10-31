package es.ulpgc.mesa.carlos.pem.MyBooks;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface MyBooksContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(MyBooksViewModel viewModel);

        void displayBooks(MyBooksViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void fillBooksArray();

        void addBook();
    }

    interface Model {
        String fetchData();

        ArrayList<BookItem> fillBooksArray( Contract.FillBooksArray callback);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(MyBooksState state);

        MyBooksState getDataFromPreviousScreen();

        void addBook();
    }
}
