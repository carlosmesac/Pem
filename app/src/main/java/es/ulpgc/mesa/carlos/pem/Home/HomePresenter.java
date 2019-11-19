package es.ulpgc.mesa.carlos.pem.Home;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Contract;

public class HomePresenter implements HomeContract.Presenter {

    public static String TAG = HomePresenter.class.getSimpleName();

    private WeakReference<HomeContract.View> view;
    private HomeViewModel viewModel;
    private HomeContract.Model model;
    private HomeContract.Router router;
    private FirebaseAuth mAuth;
    private DatabaseReference mdb;
    public HomePresenter(HomeState state) {
        viewModel = state;
        mAuth= FirebaseAuth.getInstance();
        mdb = FirebaseDatabase.getInstance().getReferenceFromUrl("https://proyectopem-54056.firebaseio.com/"); // DB reference

    }

    @Override
    public void fetchData() {
        // Log.e(TAG, "fetchData()");

        // use passed state if is necessary
        HomeState state = router.getDataFromPreviousScreen();
        if (state != null) {

            // update view and model state
            viewModel.message = state.message;

            // update the view
            view.get().displayData(viewModel);

            return;
        }

        // call the model
        String data = model.fetchData();

        // set view state
        viewModel.message = data;

        // update the view
        view.get().displayData(viewModel);

    }

    @Override
    public void goAddBook() {
        router.goAddBookActivity();
    }

    @Override
    public void signOut() {
        model.signOut(new Contract.SignOutCallback() {
            @Override
            public void signOut(boolean error) {
                if (!error){
                    goLogin();
                }
            }
        });
    }

    @Override
    public void goLogin() {
        router.goLogin();
    }

    @Override
    public void isLogin() {
        model.isLogin(new Contract.IsLoginCallBack(){

            @Override
            public void isLogin(boolean error) {
                if(error){

                }else{
                    router.goLogin();
                }
            }
        });
    }

    @Override
    public void selectBookListData(BookItem bookItem) {

    }

    @Override
    public void homeBooksArrayList() {
        model.fillHomeBooksArrayList(new Contract.FillHomeBooksArray() {
            @Override
            public void onFillHomeBooksArray(boolean error, ArrayList<BookItem> homeBooksArrayList) {
                viewModel.bookItemArrayList=homeBooksArrayList;
                view.get().displayHomeBooks(viewModel);
            }
        });
    }


    @Override
    public void injectView(WeakReference<HomeContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(HomeContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(HomeContract.Router router) {
        this.router = router;
    }
}
