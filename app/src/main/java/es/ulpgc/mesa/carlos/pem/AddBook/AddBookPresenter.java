package es.ulpgc.mesa.carlos.pem.AddBook;

import android.widget.ImageView;

import java.lang.ref.WeakReference;

import es.ulpgc.mesa.carlos.pem.App.Contract;

public class AddBookPresenter implements AddBookContract.Presenter {

    public static String TAG = AddBookPresenter.class.getSimpleName();

    private WeakReference<AddBookContract.View> view;
    private AddBookViewModel viewModel;
    private AddBookContract.Model model;
    private AddBookContract.Router router;

    public AddBookPresenter(AddBookState state) {
        viewModel = state;
    }



    @Override
    public void cancelAddBook() {
        router.goHome();
    }

    @Override
    public void addBook(String isbn, String author, String title, ImageView imageView) {
        model.addBook(isbn,author,title,imageView,new Contract.CreateBookEntryCallBack() {
            @Override
            public void onAddNewBook(boolean error) {
                if(!error){
                    viewModel.message="Book Added";
                    view.get().displayData(viewModel);
                    router.goHome();


                }else{
                    viewModel.message="Could not add";
                    view.get().displayData(viewModel);


                }
            }
        });
    }


    @Override
    public void injectView(WeakReference<AddBookContract.View> view) {
        this.view = view;
    }

    @Override
    public void injectModel(AddBookContract.Model model) {
        this.model = model;
    }

    @Override
    public void injectRouter(AddBookContract.Router router) {
        this.router = router;
    }

    @Override
    public void fetchData() {

    }
}
