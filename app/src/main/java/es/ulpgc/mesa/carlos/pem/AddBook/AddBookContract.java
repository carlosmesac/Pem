package es.ulpgc.mesa.carlos.pem.AddBook;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface AddBookContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(AddBookViewModel viewModel);
    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void cancelAddBook();


        void addBook(String isbn, String author, String title, ImageView imageView);
    }

    interface Model {
        String fetchData();




        void addBook(String isbn, String author, String title, ImageView imageView, Contract.CreateBookEntryCallBack createBookEntry);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(AddBookState state);

        AddBookState getDataFromPreviousScreen();

        void goHome();
    }
}
