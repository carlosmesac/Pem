package es.ulpgc.mesa.carlos.pem.MyBooks;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import es.ulpgc.mesa.carlos.pem.App.BookItem;
import es.ulpgc.mesa.carlos.pem.App.Contract;

public class MyBooksPresenter implements MyBooksContract.Presenter {

    public static String TAG = MyBooksPresenter.class.getSimpleName();

    private WeakReference<MyBooksContract.View> view;
    private MyBooksViewModel viewModel;
    private MyBooksContract.Model model;
    private MyBooksContract.Router router;
    private MyBooksAdapter myBooksAdapter;
    private RecyclerView recyclerView;

    public MyBooksPresenter(MyBooksState state) {
        viewModel = state;
    }

    @Override
    public void fetchData() {
        // Log.e(TAG, "fetchData()");

        // use passed state if is necessary
        MyBooksState state = router.getDataFromPreviousScreen();
        if (state != null) {

            // update view and model state
            viewModel.data = state.data;

            // update the view
            view.get().displayData(viewModel);

            return;
        }

        // call the model  

        // set view state

        // update the view

        view.get().displayBooks(viewModel);

    }

    @Override
    public void fillBooksArray() {
        model.fillBooksArray(new Contract.FillBooksArray() {
            @Override
            public void onFillBooksArray(boolean error, ArrayList<BookItem> bookItemArrayList) {
                if(!error){
                    viewModel.bookItemArrayList=bookItemArrayList;
                    view.get().displayBooks(viewModel);
                }
            }
        });
    }

    @Override
    public void addBook() {
        router.addBook();
    }

    @Override
    public void injectView(WeakReference<MyBooksContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(MyBooksContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(MyBooksContract.Router router) {
        this.router = router;
    }
}
