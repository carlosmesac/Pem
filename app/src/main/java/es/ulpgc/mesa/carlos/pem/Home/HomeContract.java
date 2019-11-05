package es.ulpgc.mesa.carlos.pem.Home;

import android.view.Menu;
import android.view.MenuInflater;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Contract;

public interface HomeContract {

    interface View {
        void injectPresenter(Presenter presenter);

        void displayData(HomeViewModel viewModel);

        void displayHomeBooks(HomeViewModel viewModel);

    }

    interface Presenter {
        void injectView(WeakReference<View> view);

        void injectModel(Model model);

        void injectRouter(Router router);

        void fetchData();

        void goAddBook();

        void signOut();

        void goLogin();

        void isLogin();

        void selectBookListData(BookItem bookItem);

        void homeBooksArrayList();
    }

    interface Model {
        String fetchData();

        void signOut(Contract.SignOutCallback callback);

        void isLogin(Contract.IsLoginCallBack isLoginCallBack);

        void fillHomeBooksArrayList(Contract.FillHomeBooksArray fillHomeBooksArray);
    }

    interface Router {
        void navigateToNextScreen();

        void passDataToNextScreen(HomeState state);

        HomeState getDataFromPreviousScreen();

        void goAddBookActivity();

        void goLogin();

        void goHome();
    }
}
